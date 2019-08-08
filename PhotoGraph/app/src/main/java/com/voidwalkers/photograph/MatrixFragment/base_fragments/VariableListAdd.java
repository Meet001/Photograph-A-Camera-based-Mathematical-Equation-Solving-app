/**
 * Fragment used for addition operation
 * Contains of
 * <ul>
 *     <li>A TextView of matrices to be processed</li>
 *     <li>A ListView of added matrices</li>
 *     <li>Confirm and Cancel Buttons</li>
 * </ul>
 *
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

public class VariableListAdd extends ListFragment {

    MatrixAdapter adapter;
    int Row;
    int Col;
    int ClickNo;

    /**
     * On Creating, a matrix adapter gets created, layout is changed to list fragment and the contents of the list are displayed via the adapter
     * Adds compatible matrices to a queue and adds them
     * @return A ListView of Matrices
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new MatrixAdapter(getContext(),R.layout.list_layout_fragment,((GlobalValues)getActivity().getApplication()).GetCompleteList());
        getListView().setDividerHeight(1);
        setListAdapter(adapter);
    }

    /**
     * Event Listener for list items,
     * On the first click set the row and columns to the given matrix, and on subsequent clicks check if the rows and columns match and add to queue,
     * otherwise raise a Toast message
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Matrix m = ((GlobalValues) getActivity().getApplication()).GetCompleteList().get(position);
        if (ClickNo == 0) {
            Row =m.GetRow();
            Col = m.GetCol();
            AddToQueue(m);
            ClickNo++;
        }
        else{
            if(m.GetRow() == Row && m.GetCol() ==Col){
                AddToQueue(m);
            }
            else
            {
                Toast.makeText(getContext(),"You can only select " + String.valueOf(Row) +" x "+ String.valueOf(Col) + " Matrix ",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * takes input a matrix and adds it to the matrix queue
     * @param click (matrix clicked)
     */
    private void AddToQueue(Matrix click){
        try {
            // Get the textview of displayed matrices
            TextView textView = (TextView) getParentFragment().getView().findViewById(R.id.AdditionStatus);
            String Initial = textView.getText().toString();
            if(Initial.isEmpty()){
                // if the textview was empty, set it equal to the name of the clicked matrix and add it to the queue of matrices
                textView.setText(click.GetName());
                ((GlobalValues)getActivity().getApplication()).MatrixQueue.add(click);
            }
            else {
                // else change the textview to initial_name + '+' + name of the clicked matrix and add it to the queue of matrices
                String Complete = Initial +  " + " + click.GetName();
                textView.setText(Complete);
                ((GlobalValues)getActivity().getApplication()).MatrixQueue.add(click);
            }
        }catch  (NullPointerException e){
            Log.d("TAG2","NullptrException");
            e.printStackTrace();
        }

    }
    public void RestoreClick(){
        ClickNo = 0;
    }

}
