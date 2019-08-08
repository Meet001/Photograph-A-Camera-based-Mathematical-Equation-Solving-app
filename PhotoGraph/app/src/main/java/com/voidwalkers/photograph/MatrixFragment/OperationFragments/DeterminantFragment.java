package com.voidwalkers.photograph.MatrixFragment.OperationFragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
 * Fragment for showing determinant
 */

public class DeterminantFragment extends ListFragment {

    ArrayList<Matrix> SquareList;
    String TAG = this.getClass().getSimpleName();

    /**
     * Displays the list of matrices
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
     * Returns Determinant of the matrix by standard algorithm
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        double result = ((GlobalValues)getActivity().getApplication()).GetCompleteList().get(position).GetDeterminant();
        final String formatted = GetText(result);
        String formatted2  = "Determinant is " +formatted;
        new AlertDialog.Builder(getContext())
                .setCancelable(true)
                .setMessage(formatted2)
                .setTitle(R.string.determinant)
                .setPositiveButton(R.string.copy, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // copying to clipboard
                        ClipboardManager manager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData data = ClipData.newPlainText("Determinant",formatted);
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

    private String GetText(double res) {

        if (!PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("DECIMAL_USE", true)) {
            DecimalFormat decimalFormat = new DecimalFormat("###############");
            return decimalFormat.format(res);
        } else
        {
            switch (Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("ROUNDIND_INFO","0"))) {
                case 0:
                    return String.valueOf(res);
                case 1:
                    DecimalFormat single = new DecimalFormat("########.#");
                    return single.format(res);
                case 2:
                    DecimalFormat Double = new DecimalFormat("########.##");
                    return Double.format(res);
                case 3:
                    DecimalFormat triple = new DecimalFormat("########.###");
                    return triple.format(res);
                default:
                    return String.valueOf(res);
            }
        }
    }

}
