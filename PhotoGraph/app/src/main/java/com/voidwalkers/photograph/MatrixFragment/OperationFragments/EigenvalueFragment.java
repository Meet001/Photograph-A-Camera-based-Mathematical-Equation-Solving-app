package com.voidwalkers.photograph.MatrixFragment.OperationFragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.voidwalkers.photograph.GlobalValues;
import com.voidwalkers.photograph.MatrixFragment.Matrix;
import com.voidwalkers.photograph.MatrixFragment.MatrixAdapter;
import com.voidwalkers.photograph.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Shows eigenvalues
 */

public class EigenvalueFragment extends ListFragment {

    ArrayList<Matrix> SquareList;
    String TAG = this.getClass().getSimpleName();

    /**
     * Displays the list of square matrices
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SquareList = new ArrayList<>();
        for (int i = 0; i < ((GlobalValues) getActivity().getApplication()).GetCompleteList().size(); i++) {
            if (((GlobalValues) getActivity().getApplication()).GetCompleteList().get(i).is_squareMatrix())
                SquareList.add(((GlobalValues) getActivity().getApplication()).GetCompleteList().get(i));
        }

        MatrixAdapter adapter = new MatrixAdapter(getContext(), R.layout.list_layout_fragment,SquareList);
        getListView().setDividerHeight(1);
        setListAdapter(adapter);
    }

    /**
     * Returns eigenvalues of the matrix
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        final String result = ((GlobalValues)getActivity().getApplication()).GetCompleteList().get(position).GetEigenValues();
        String formatted2  = "EigenValues are " + result;
        new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .setMessage(formatted2)
                .setTitle("Eigenvalues")
                .setPositiveButton(R.string.copy, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // copying to clipboard
                        ClipboardManager manager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData data = ClipData.newPlainText("EigenValues",result);
                        manager.setPrimaryClip(data);
                        if(manager.hasPrimaryClip())
                            Toast.makeText(getContext(), R.string.CopyToClip, Toast.LENGTH_SHORT).show();
                        else
                            Log.e(TAG,"Cannot Put Data to Clip");
                        dialog.dismiss();
                    }
                })
                .setNeutralButton(R.string.Done, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
