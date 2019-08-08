/**
 * Creates a cardview to display the result
 * Can Save the result to existing variables
 */

package com.voidwalkers.photograph.MatrixFragment.base_classes;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.voidwalkers.photograph.GlobalValues;
import com.voidwalkers.photograph.MatrixFragment.MatrixMain;
import com.voidwalkers.photograph.MatrixFragment.Matrix;
import com.voidwalkers.photograph.R;


import java.text.DecimalFormat;

public class ShowResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);
        /**
         * Show cardview and grid layout
         */
        CardView cardView = (CardView) findViewById(R.id.DynamicCard2);

        CardView.LayoutParams params1= new CardView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        GridLayout gridLayout = new GridLayout(getApplicationContext());
        gridLayout.setRowCount(getIntent().getExtras().getInt("ROW",0));
        gridLayout.setColumnCount(getIntent().getExtras().getInt("COL",0));
        float var [][] = new Matrix().Expand(getIntent().getExtras().getInt("ROW",0),getIntent().getIntExtra("COL",0),getIntent().getFloatArrayExtra("VALUES"));
        for(int i=0;i<getIntent().getExtras().getInt("ROW",0);i++)
        {
            for(int j=0;j<getIntent().getExtras().getInt("COL",0);j++)
            {
                TextView textView = new TextView(getApplicationContext());
                textView.setGravity(Gravity.CENTER);
                try {
                    textView.setText("   " + GetText(var[i][j]) + "   ");
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("Element","Element in Matrix is Null");
                }
                textView.setTextSize(SizeReturner(getIntent().getExtras().getInt("ROW",0),getIntent().getExtras().getInt("COL",0),
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).
                                getBoolean("EXTRA_SMALL_FONT",false)));
                textView.setHeight(CalculatedHeight(getIntent().getExtras().getInt("ROW",0)));
                GridLayout.Spec Row = GridLayout.spec(i,1);
                GridLayout.Spec Col = GridLayout.spec(j,1);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(Row,Col);
                gridLayout.addView(textView,params);
            }
        }
        gridLayout.setLayoutParams(params1);
        cardView.addView(gridLayout);
        if(getIntent().getFloatExtra("DETERMINANT_FOR_INVERSE",0.0f) != 0.0f){
            TextView textView = (TextView) findViewById(R.id.TextContainer);
            String val = "1 / " + GetText(getIntent().getFloatExtra("DETERMINANT_FOR_INVERSE",0.0f));
            textView.setText(val);
        }

    }
    @Override
    /**
     * Display the options
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.result_actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    /**
     * On clicking the item save the matrix
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.SaveResult :
                Matrix matrix = new Matrix();
                matrix.SetFromBundle(getIntent().getExtras());
                String auto_name = "Result " + String.valueOf(((GlobalValues) getApplication()).AutoSaved);
                matrix.SetName(auto_name);
                ((GlobalValues) getApplication()).AddResultToGlobal(matrix);
                Toast.makeText(getApplicationContext(), (getString(R.string.SavedAs) + " " + auto_name), Toast.LENGTH_SHORT).show();
                Intent home = new Intent(getApplication(), MatrixMain.class);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Return height of card
     * @param a
     * @return
     */

    private int CalculatedHeight(int a)
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

    private int SizeReturner(int r, int c,boolean b)
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

    /**
     * Get text from float depending on the number of decimal places
     */

    private String GetText(float res) {
        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("DECIMAL_USE", true)) {
            DecimalFormat decimalFormat = new DecimalFormat("###############");
            return decimalFormat.format(res);
        } else
        {
            switch (Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this).getString("ROUNDIND_INFO","0"))) {
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
