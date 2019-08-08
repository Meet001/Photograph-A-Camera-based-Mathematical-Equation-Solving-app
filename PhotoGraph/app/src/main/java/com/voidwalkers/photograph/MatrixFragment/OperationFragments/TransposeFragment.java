package com.voidwalkers.photograph.MatrixFragment.OperationFragments;

/**
 * Calculate the transpose of the matrix and store it to a new variable
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.voidwalkers.photograph.MatrixFragment.Matrix;
import com.voidwalkers.photograph.MatrixFragment.MatrixAdapter;
import com.voidwalkers.photograph.R;
import com.voidwalkers.photograph.GlobalValues;
import com.voidwalkers.photograph.MatrixFragment.base_classes.ShowResult;

public class TransposeFragment extends ListFragment {
    int ClickPos;

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MatrixAdapter adapter = new MatrixAdapter(getContext(),
                R.layout.list_layout_fragment,((GlobalValues)getActivity().
                getApplication()).GetCompleteList());
        getListView().setDividerHeight(1);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
                    Intent i2 = new Intent(getContext(), ShowResult.class);
                    Matrix original = ((GlobalValues)getActivity().getApplication()).GetCompleteList().get(ClickPos);
                    i2.putExtras(original.Transpose().GetDataBundled());
                    startActivity(i2);
    }

}
