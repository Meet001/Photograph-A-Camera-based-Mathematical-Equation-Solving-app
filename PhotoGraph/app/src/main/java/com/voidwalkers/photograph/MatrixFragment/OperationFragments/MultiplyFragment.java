/**
 * Multiplies two or more matrices that satisfy matrix multiplication
 */
package com.voidwalkers.photograph.MatrixFragment.OperationFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.voidwalkers.photograph.MatrixFragment.base_fragments.VariableMul;

import java.util.ArrayList;

public class MultiplyFragment extends Fragment {

    View root;
    @Override
    /**
     * Displays all the matrices in a listView via matrix adapter
     * Can add matrices to the queue satisfying matrix multiplication property
     * Can remove matrices from the queue
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((GlobalValues)getActivity().getApplication()).MatrixQueue.clear();
        //Empty the Queue
        VariableMul variableList = new VariableMul();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.AdapterAddContainer,variableList,"VARIABLE_ADDER_MUL");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
        View view =inflater.inflate(R.layout.addition_fragement, container, false);
        root = view;

        TextView tv = (TextView) view.findViewById(R.id.TitleOfAdd);
        TextView tv2 = (TextView) view.findViewById(R.id.SubtitleofADD);
        tv.setText(R.string.MulQueue);
        tv2.setText(R.string.MulTip);
        Button proceedAdd = (Button) view.findViewById(R.id.ConfirmAdd);
        proceedAdd.setText(R.string.Proceed);
        proceedAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((GlobalValues)getActivity().getApplication()).MatrixQueue.size()>=2){
                    Intent intent = new Intent(getContext(), ShowResult.class);
                    intent.putExtras(MultiplyAll().GetDataBundled());
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

    /**
     * Removes matrices from the queue and reflects in textView
     */
    private void RemoveFromQueue(){
        TextView textView = (TextView) root.findViewById(R.id.AdditionStatus);
        String Initial = textView.getText().toString();
        if(Initial.isEmpty()){
            ((GlobalValues)getActivity().getApplication()).MatrixQueue.clear();
            Toast.makeText(getContext(),R.string.NothingTORemove,Toast.LENGTH_SHORT).show();
        }
        else {
            if(Initial.contains("x")) {
                String NewName = Initial.substring(0, Initial.lastIndexOf("x"));
                textView.setText(NewName);
                ((GlobalValues)getActivity().getApplication()).MatrixQueue.remove(((GlobalValues)getActivity().getApplication()).MatrixQueue.size()-1);
                int pos = ((GlobalValues)getActivity().getApplication()).MatrixQueue.size();
                int r =  ((GlobalValues)getActivity().getApplication()).MatrixQueue.get(pos-1).GetRow();
                int c =  ((GlobalValues)getActivity().getApplication()).MatrixQueue.get(pos-1).GetCol();
                VariableMul child = ((VariableMul) getChildFragmentManager().findFragmentByTag("VARIABLE_ADDER_MUL"));
                if(child != null){
                    child.UpdateRowCol(r,c);
                }
            }
            else
            {
                textView.setText(null);
                VariableMul child = ((VariableMul) getChildFragmentManager().findFragmentByTag("VARIABLE_ADDER_MUL"));
                child.Restore();
                ((GlobalValues)getActivity().getApplication()).MatrixQueue.clear();
            }
        }
    }

    /**
     * Calls the arrayList of matrices and adds it to the answermatrix
     * @return
     */
    private Matrix MultiplyAll(){
        ArrayList<Matrix> m =((GlobalValues)getActivity().getApplication()).MatrixQueue;
        Matrix res = new Matrix(m.get(0).GetRow(),m.get(0).GetCol());
        // copy the matrix
        res.CloneFrom(m.get(0));
        for(int i=1;i<m.size();i++){
            res.MultiplytoThis(m.get(i));
        }
        return res;
    }
}
