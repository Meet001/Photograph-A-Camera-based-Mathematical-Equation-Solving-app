package com.voidwalkers.photograph.MatrixFragment.OperationFragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.voidwalkers.photograph.GlobalValues;
import com.voidwalkers.photograph.MatrixFragment.Matrix;
import com.voidwalkers.photograph.MatrixFragment.MatrixAdapter;
import com.voidwalkers.photograph.R;
import com.voidwalkers.photograph.MatrixFragment.base_classes.ShowResult;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Fragment for finding inverse of the matrix
 */

public class InverseFragment extends ListFragment {

    final String KEY = "DETERMINANT_FOR_INVERSE";
    boolean ENABLED_NO_DECIMAL;

    ArrayList<Matrix> SquareList;

    private static class MyHandler extends Handler{
        private final WeakReference<InverseFragment> weakReference;
        MyHandler(InverseFragment inverseFragment){
            weakReference = new WeakReference<>(inverseFragment);
        }
        @Override
        public  void handleMessage(Message message) {
                Intent intent = new Intent(weakReference.get().getActivity(), ShowResult.class);
                if (message.getData().getFloat("DETERMINANT", 0) == 0) {
                    intent.putExtras(message.getData());
                    weakReference.get().startActivity(intent);
                }
            }
        }

    MyHandler myHandler = new MyHandler(this);

    @Override
    public void onActivityCreated(Bundle savedInstances) {
        super.onActivityCreated(savedInstances);
        SquareList=new ArrayList<>();
        for(int i = 0; i<((GlobalValues)getActivity().getApplication()).GetCompleteList().size(); i++)
        {
            if(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(i).is_squareMatrix())
                SquareList.add(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(i));
        }
        MatrixAdapter MatriXadapter = new MatrixAdapter(getActivity(), R.layout.list_layout_fragment,SquareList);
        getListView().setDividerHeight(1);
        setListAdapter(MatriXadapter);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        ENABLED_NO_DECIMAL = preferences.getBoolean("NO_FRACTION_ENABLED",false);

    }
    @Override
    public void onListItemClick(ListView L, View V, int position, long id)
    {
        if(ENABLED_NO_DECIMAL)
            RunAndGetDeterminantWithAdjoint(position);
        else
            RunNewGetInverse(position);
    }

    /**
     * Getting inverse, created a new thread
     * @param pos
     */
    public void RunNewGetInverse(final int pos)
    {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Matrix res = SquareList.get(pos).Inverse();
                Message message = new Message();
                if(res!=null){
                    message.setData(res.GetDataBundled());
                    myHandler.sendMessage(message);
                }
                else{
                    myHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),R.string.NoInverse,Toast.LENGTH_SHORT).show();
                        }
                    },0);
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * Create a thread that gets determinant
     * @param i
     */
    public void RunAndGetDeterminantWithAdjoint(final int i){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                Bundle bundle = new Bundle();
                float detr = (float) SquareList.get(i).GetDeterminant();
                if(detr == 0.0f){
                    myHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),R.string.NoInverse,Toast.LENGTH_SHORT).show();
                        }
                    },0);
                }
                else {
                    bundle.putFloat("DETERMINANT",detr);
                    Matrix res = SquareList.get(i).ReturnAdjoint();
                    bundle.putAll(res.GetDataBundled());
                    message.setData(bundle);
                    myHandler.sendMessage(message);
                }

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

}
