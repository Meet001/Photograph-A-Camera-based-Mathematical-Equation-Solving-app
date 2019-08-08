package com.voidwalkers.photograph.MatrixFragment;

import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.voidwalkers.photograph.R;

import java.util.ArrayList;


public class MatrixAdapter extends ArrayAdapter<Matrix> {

    public MatrixAdapter(Context context, int t,ArrayList<Matrix> matrices){
        super(context,t,matrices);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final Matrix m = getItem(position);

        if(convertView==null)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_layout_fragment,parent,false);

        //Grab References
        TextView Row= ((TextView) convertView.findViewById(R.id.MatrixTitleRow));
        TextView Col= ((TextView) convertView.findViewById(R.id.MatrixTitleCol));
        TextView Naam= ((TextView) convertView.findViewById(R.id.MatrixTitle));
        ImageView icon = ((ImageView)convertView.findViewById(R.id.ImageForMatrix));
        CardView cardView = ((CardView)convertView.findViewById(R.id.Matrix_holder));

        //Set Values
        @SuppressWarnings({"ConstantConditions"})
        String r="Row : "+String.valueOf(m.GetRow());
        String c="Column : "+String.valueOf(m.GetCol());

        Row.setText(r);
        Col.setText(c);
        Naam.setText(m.GetName());

        //Return the View
        return convertView;

    }


}
