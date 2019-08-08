/**
 * Calculating the exponent of a square matrix
 */
package com.voidwalkers.photograph.MatrixFragment.OperationFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.voidwalkers.photograph.MatrixFragment.base_classes.ExponentSetter;
import com.voidwalkers.photograph.GlobalValues;
import com.voidwalkers.photograph.MatrixFragment.Matrix;
import com.voidwalkers.photograph.MatrixFragment.MatrixAdapter;
import com.voidwalkers.photograph.R;
import com.voidwalkers.photograph.MatrixFragment.base_classes.ShowResult;

import java.util.ArrayList;

public class ExponentFragment extends ListFragment {

    int Clicked_pos;
    ArrayList<Matrix> SquareList;

    /**
     * Display square matrices
     * @param savedInstance
     */
    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        SquareList=new ArrayList<>();
        for(int i = 0; i<((GlobalValues)getActivity().getApplication()).GetCompleteList().size(); i++)
        {
            if(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(i).is_squareMatrix())
                SquareList.add(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(i));
        }
        MatrixAdapter MatriXadapter = new MatrixAdapter(getActivity(), R.layout.list_layout_fragment,SquareList);
        getListView().setDividerHeight(1);
        setListAdapter(MatriXadapter);
    }

    /**
     * Calling exponensetter class
     * @param L
     * @param V
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView L, View V, int position, long id)
    {
        Clicked_pos = position;
        Intent intent = new Intent(getActivity(),ExponentSetter.class);
        startActivityForResult(intent,500);
    }

    /**
     * Calls RaiseTo function and sends the result to showresult.class
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==500)
        {
            Matrix res = new Matrix(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(Clicked_pos).GetRow()
                    ,((GlobalValues)getActivity().getApplication()).GetCompleteList().get(Clicked_pos).GetCol());
            res.CloneFrom(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(Clicked_pos));
            res.Raiseto(data.getIntExtra("QWERTYUIOP",0));
            Intent i = new Intent(getActivity(),ShowResult.class) ;
            i.putExtras(res.GetDataBundled()) ;
            startActivity(i);
        }
    }

}
