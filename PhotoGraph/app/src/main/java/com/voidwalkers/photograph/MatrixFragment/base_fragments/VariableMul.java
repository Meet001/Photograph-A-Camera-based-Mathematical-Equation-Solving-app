/**
 * Fragment for Multiplication
 */

package com.voidwalkers.photograph.MatrixFragment.base_fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.voidwalkers.photograph.MatrixFragment.Matrix;
import com.voidwalkers.photograph.MatrixFragment.MatrixAdapter;
import com.voidwalkers.photograph.R;
import com.voidwalkers.photograph.GlobalValues;

public class VariableMul extends ListFragment {

    MatrixAdapter adapter;
    int Row;
    int Col;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new MatrixAdapter(getContext(), R.layout.list_layout_fragment, ((GlobalValues) getActivity().getApplication()).GetCompleteList());
        getListView().setDividerHeight(1);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Matrix m = ((GlobalValues) getActivity().getApplication()).GetCompleteList().get(position);
        if (Row == 0 || Col == 0) {
            Row = m.GetRow();
            Col = m.GetCol();
            AddToQueue(m);
        } else {
            if (Col == m.GetRow()) {
                AddToQueue(m);
                Row = m.GetRow();
                Col = m.GetCol();
            } else {
                Toast.makeText(getContext(), "Select a Matrix with " + String.valueOf(Col) + " Row", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Editing the textviews
     * @param click
     */
    private void AddToQueue(Matrix click) {
        try {
            TextView textView = (TextView) getParentFragment().getView().findViewById(R.id.AdditionStatus);
            String Initial = textView.getText().toString();
            if (Initial.isEmpty()) {
                textView.setText(click.GetName());
                ((GlobalValues) getActivity().getApplication()).MatrixQueue.add(click);
            } else {
                String Complete = Initial + " x " + click.GetName();
                textView.setText(Complete);
                ((GlobalValues) getActivity().getApplication()).MatrixQueue.add(click);
            }
        } catch (NullPointerException e) {
            Log.d("AddToQueue", "Exception raised, cannot get textview from parent fragment");
            e.printStackTrace();
        }

    }

    /**
     * Updating the rows and columns
     * @param r
     * @param c
     */
    public void UpdateRowCol(int r, int c){
        Row=r;
        Col=c;
    }
    public void Restore(){
        Row =0;
        Col =0;
    }
}
