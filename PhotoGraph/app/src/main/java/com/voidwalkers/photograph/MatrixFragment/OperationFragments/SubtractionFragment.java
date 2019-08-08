
package com.voidwalkers.photograph.MatrixFragment.OperationFragments;

/**
 * Same as addition fragment just + is replaced with -
 * and the titles are changed
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.voidwalkers.photograph.MatrixFragment.Matrix;
import com.voidwalkers.photograph.R;
import com.voidwalkers.photograph.GlobalValues;
import com.voidwalkers.photograph.MatrixFragment.base_classes.ShowResult;
import com.voidwalkers.photograph.MatrixFragment.base_fragments.VariableListSub;

import java.util.ArrayList;

public class SubtractionFragment extends Fragment {

    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((GlobalValues)getActivity().getApplication()).MatrixQueue.clear();
        //Empty the Queue
        VariableListSub variableList = new VariableListSub();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.AdapterAddContainer,variableList,"VARIABLE_ADDER_SUB");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
        View view =inflater.inflate(R.layout.addition_fragement, container, false);
        root = view;


        TextView tv = (TextView) view.findViewById(R.id.TitleOfAdd);
        TextView tv2 = (TextView) view.findViewById(R.id.SubtitleofADD);
        tv.setText(R.string.SubQueue);
        tv2.setText(R.string.SubTip);
        Button proceedAdd = (Button) view.findViewById(R.id.ConfirmAdd);
        proceedAdd.setText(R.string.Proceed);
        proceedAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((GlobalValues)getActivity().getApplication()).MatrixQueue.size()>=2){
                    Intent intent = new Intent(getContext(), ShowResult.class);
                    intent.putExtras(SubAll().GetDataBundled());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getContext(),R.string.Notdefined,Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button remove = (Button) view.findViewById(R.id.RemoveLast);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveFromQueue();
            }
        });
        return view;
    }
    private Matrix SubAll(){
        ArrayList<Matrix> buffer =((GlobalValues)getActivity().getApplication()).MatrixQueue;
        Matrix res = new Matrix(buffer.get(0).GetRow(),buffer.get(0).GetCol());
        res.CloneFrom(buffer.get(0));
        for(int i=1;i<buffer.size();i++){
            res.SubtoThis(buffer.get(i));
        }
        return res;
    }
    private void RemoveFromQueue(){
        TextView textView = (TextView) root.findViewById(R.id.AdditionStatus);
        String Initial = textView.getText().toString();
        if(Initial.isEmpty()){
            ((GlobalValues)getActivity().getApplication()).MatrixQueue.clear();
            Toast.makeText(getContext(),R.string.NothingTORemove,Toast.LENGTH_SHORT).show();
        }
        else {
            if(Initial.contains("-")) {
                String NewName = Initial.substring(0, Initial.lastIndexOf("-"));
                textView.setText(NewName);
                ((GlobalValues)getActivity().getApplication()).MatrixQueue.remove(((GlobalValues)getActivity().getApplication()).MatrixQueue.size()-1);
            }
            else
            {
                textView.setText(null);
                VariableListSub variableListSub = (VariableListSub)getChildFragmentManager().findFragmentByTag("VARIABLE_ADDER_SUB");
                variableListSub.UpdateInfo();
                ((GlobalValues)getActivity().getApplication()).MatrixQueue.clear();
            }
        }
    }


}
