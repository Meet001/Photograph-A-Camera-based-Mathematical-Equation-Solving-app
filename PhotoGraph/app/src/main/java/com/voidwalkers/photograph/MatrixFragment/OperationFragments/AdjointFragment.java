/**
 * Computes and displays the adjoint of square matrices
 */

package com.voidwalkers.photograph.MatrixFragment.OperationFragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.voidwalkers.photograph.GlobalValues;
import com.voidwalkers.photograph.MatrixFragment.Matrix;
import com.voidwalkers.photograph.MatrixFragment.MatrixAdapter;
import com.voidwalkers.photograph.R;
import com.voidwalkers.photograph.MatrixFragment.base_classes.ShowResult;

import java.util.ArrayList;

public class AdjointFragment extends ListFragment {

    ArrayList<Matrix> SquareList;

    /**
     * Displays the list fragment of square matrices
     * @param savedInstances
     */
    @Override
    public void onActivityCreated(Bundle savedInstances) {
        super.onActivityCreated(savedInstances);
        SquareList=new ArrayList<>();
        for(int i = 0; i<((GlobalValues)getActivity().getApplication()).GetCompleteList().size(); i++)
        {
            if(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(i).is_squareMatrix())
                //if square matrix is present them only ajoint can be found hence only square matrix being selected
                SquareList.add(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(i));
        }
        MatrixAdapter MatriXadapter = new MatrixAdapter(getActivity(), R.layout.list_layout_fragment,SquareList);
        getListView().setDividerHeight(1);
        setListAdapter(MatriXadapter);

    }

    /**
     * OnClickListener for matrix
     * Calls ReturnAdjoint
     * passes the result via a bundle to Showresult
     * @param L
     * @param V
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView L, View V, int position, long id)
    {
        Bundle bundle = SquareList.get(position).ReturnAdjoint().GetDataBundled();
        Message message = new Message();
        message.setData(bundle);
        Intent i = new Intent(getActivity(),ShowResult.class) ;
        i.putExtras(bundle) ;
        startActivity(i);
    }
}
