
package com.voidwalkers.photograph.MatrixFragment.base_fragments;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.voidwalkers.photograph.GlobalValues;
import com.voidwalkers.photograph.R;

import java.text.DecimalFormat;


public class ViewMatrixFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int index= getArguments().getInt("INDEX");

        View v=inflater.inflate(R.layout.view_matrix_frag, container, false);
        CardView cardView = (CardView) v.findViewById(R.id.DynamicCardView);

        CardView.LayoutParams params1= new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        GridLayout gridLayout = new GridLayout(getContext());
        gridLayout.setRowCount(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetRow());
        gridLayout.setColumnCount(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetCol());
        for(int i=0;i<((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetRow();i++)
        {
            for(int j=0;j<((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetCol();j++)
            {
                TextView textView = new TextView(getContext());
                textView.setGravity(Gravity.CENTER);
                textView.setText( GetText(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetElementof(i,j)));
                textView.setWidth(CalculatedWidth(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetCol()));
                textView.setTextSize(SizeReturner(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetRow(),((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetCol(),
                        PreferenceManager.getDefaultSharedPreferences(getContext()).
                                getBoolean("EXTRA_SMALL_FONT",false)));
                textView.setHeight(CalculatedHeight(((GlobalValues)getActivity().getApplication()).GetCompleteList().get(index).GetRow()));
                GridLayout.Spec Row = GridLayout.spec(i,1);
                GridLayout.Spec Col = GridLayout.spec(j,1);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(Row,Col);
                gridLayout.addView(textView,params);
            }
        }
        gridLayout.setLayoutParams(params1);
        cardView.addView(gridLayout);

        return v;
    }

    private String GetText(float res) {

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

    public int CalculatedHeight(int a)
    {
        switch (a)
        {
            case 1 : return 155;
            case 2 : return 135;
            case 3 : return 125;
            case 4 : return 115;
            case 5 : return 105;
            case 6 : return 95;
            case 7 : return 85;
            case 8 : return 85;
            case 9 : return 80;

        }
        return 0;
    }
    public int CalculatedWidth(int a)
    {
        switch (a)
        {
            case 1 : return 150;
            case 2 : return 130;
            case 3 : return 120;
            case 4 : return 110;
            case 5 : return 100;
            case 6 : return 90;
            case 7 : return 80;
            case 8 : return 80;
            case 9 : return 74;

        }
        return 0;
    }
    public int SizeReturner(int r, int c,boolean b)
    {
        if(!b) {
            if (r > c) {
                switch (r) {
                    case 1:
                        return 18;
                    case 2:
                        return 17;
                    case 3:
                        return 15;
                    case 4:
                        return 13;
                    case 5:
                        return 12;
                    case 6:
                        return 11;
                    case 7:
                        return 10;
                    case 8:
                        return 10;
                    case 9:
                        return 9;
                }
            } else {
                switch (c) {
                    case 1:
                        return 18;
                    case 2:
                        return 17;
                    case 3:
                        return 15;
                    case 4:
                        return 13;
                    case 5:
                        return 12;
                    case 6:
                        return 11;
                    case 7:
                        return 10;
                    case 8:
                        return 10;
                    case 9:
                        return 9;
                }
            }
        }
        else
        {
            if (r > c) {
                switch (r) {
                    case 1:
                        return 15;
                    case 2:
                        return 14;
                    case 3:
                        return 12;
                    case 4:
                        return 10;
                    case 5:
                        return 9;
                    case 6:
                        return 8;
                    case 7:
                        return 7;
                    case 8:
                        return 7;
                    case 9:
                        return 6;
                }
            } else {
                switch (c) {
                    case 1:
                        return 15;
                    case 2:
                        return 14;
                    case 3:
                        return 12;
                    case 4:
                        return 10;
                    case 5:
                        return 9;
                    case 6:
                        return 8;
                    case 7:
                        return 7;
                    case 8:
                        return 7;
                    case 9:
                        return 6;
                }
            }
        }

        return 0;
    }


}