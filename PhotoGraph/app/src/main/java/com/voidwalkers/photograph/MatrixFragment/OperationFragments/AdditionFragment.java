/**
 * AdditionFragment
 * Starts when addition operation is selected
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
import com.voidwalkers.photograph.MatrixFragment.base_fragments.VariableListAdd;

import java.util.ArrayList;

public class AdditionFragment extends Fragment {

    View root;

    /**
     * Shows current buffer of items to be added
     * Checks
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((GlobalValues)getActivity().getApplication()).MatrixQueue.clear();
        //Empty the Queue
        VariableListAdd variableList = new VariableListAdd();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.AdapterAddContainer,variableList,"VARIABLE_ADDER");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
        View view =inflater.inflate(R.layout.addition_fragement, container, false);
        root = view;

        Button proceedAdd = (Button) view.findViewById(R.id.ConfirmAdd);
        proceedAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((GlobalValues)getActivity().getApplication()).MatrixQueue.size()>=2){
                    Intent intent = new Intent(getContext(), ShowResult.class);
                    intent.putExtras(SumAll().GetDataBundled());
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
     * Sum of all the elements
     * @return
     */
    private Matrix SumAll(){
        ArrayList<Matrix> buffer =((GlobalValues)getActivity().getApplication()).MatrixQueue;
        Matrix res = new Matrix(buffer.get(1).GetRow(),buffer.get(1).GetCol());
        for(int i=0;i<buffer.size();i++){
            res.AddtoThis(buffer.get(i));
        }
        return res;
    }

    /**
     * Remove from the Matrix Queue
     * Called when Remove button is clicked
     */
    private void RemoveFromQueue(){
        TextView textView = (TextView) root.findViewById(R.id.AdditionStatus);
        String Initial = textView.getText().toString();
        if(Initial.isEmpty()){
            ((GlobalValues)getActivity().getApplication()).MatrixQueue.clear();
            Toast.makeText(getContext(),"Nothing to remove",Toast.LENGTH_SHORT).show();
        }
        else {
            if(Initial.contains("+")) {
                String NewName = Initial.substring(0, Initial.lastIndexOf("+"));
                textView.setText(NewName);
                ((GlobalValues)getActivity().getApplication()).MatrixQueue.remove(((GlobalValues)getActivity().getApplication()).MatrixQueue.size()-1);
            }
            else
            {
                textView.setText(null);
                VariableListAdd variableListAdd = (VariableListAdd)getChildFragmentManager().findFragmentByTag("VARIABLE_ADDER");
                variableListAdd.RestoreClick();
                ((GlobalValues)getActivity().getApplication()).MatrixQueue.clear();
            }
        }
    }

}