package com.voidwalkers.photograph.ScannerFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent ;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.Gravity ;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import com.jjoe64.graphview.series.LineGraphSeries;
import com.voidwalkers.photograph.AlgebraFragment.Linear;
import com.voidwalkers.photograph.AlgebraFragment.Quadratic;
import com.voidwalkers.photograph.R;

import java.lang.reflect.Array;

import butterknife.BindView;

public class CameraActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Log.e("TAG23","HEREIAM") ;
        if(!MarshmallowPermissions.checkPermissionForCamera(this)) {
            Log.e("TAG23","INSIDE") ;

            MarshmallowPermissions.requestPermissionForCamera(this);
        }
        else {
            Log.e("TAG23","OUT") ;
        }

        Log.e("TAG23","SET") ;


        setContentView(R.layout.activity_camera_main);

//        Button button = (Button) findViewById(R.id.sample_button);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.v("TAG2","ClickedButton") ;
//
//                Log.v("TAG2","Graphing") ;
//
//                GraphView myGraph = (GraphView) findViewById(R.id.outputGraph) ;
//                TextView valofCoeffs = (TextView) findViewById(R.id.valofCoeffs);
//                TextView Standard = (TextView) findViewById(R.id.standardForm);
//                TextView myAns = (TextView) findViewById(R.id.Finalans);
//
////                LinearLayout linearLayout= (LinearLayout)findViewById(R.id.answerLayout);      //find the linear layout
////                linearLayout.removeAllViews();                              //add this too
////                for(int i=0; i<5;i++){          //looping to create 5 textviews
////                    TextView textView= new TextView(null);              //dynamically create textview
////                    textView.setLayoutParams(new LinearLayout.LayoutParams(             //select linearlayoutparam- set the width & height
////                     ViewGroup.LayoutParams.MATCH_PARENT, 48));
////                    textView.setGravity(Gravity.CENTER_VERTICAL);                       //set the gravity too
////                    textView.setText("outputAns: "+i);                                    //adding text
////                    linearLayout.addView(textView);                                     //inflating :)
////                }
//
////                TextView outputTextViews = (TextView) findViewById(R.id.outputAns[0]);
////                TextView Standard = (TextView) findViewById(R.id.standardForm);
////                TextView myAns = (TextView) findViewById(R.id.Finalans);
//
//                myGraph.removeAllSeries();
//
//                LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>() ;
//
//                series = Quadratic.getSeries();
//
//                double[] myArray = new double[4];
//
//                myArray = Quadratic.getarray();
//
//                String Standardform = Quadratic.gettransformedlatex();
//
//                valofCoeffs.setText(" a = " + String.valueOf(myArray[0])+
//                        ", b = " + String.valueOf(myArray[1])+
//                        ", c = " + String.valueOf(myArray[2]) +
//                        ", d = " + String.valueOf(myArray[3])
//                );
//
//                double p = myArray[0];
//                double q = myArray[1];
//                double w = myArray[2];
//                double s = myArray[3];
//
//                Standard.setText("ax^2 + bx + c = d ");
//
//                if (q*q >= 4*p*(w-s)){
//                    double x1 = Math.round (100*(-1*q + Math.sqrt(q*q - 4*p*(w-s)))/(2*p))/100;
//                    double x2 = Math.round(100*(-1*q - Math.sqrt(q*q - 4*p*(w-s)))/(2*p))/100;
//
//                    myAns.setText("root1 = " + String.valueOf(x1) + " root2 = " + String.valueOf(x2));
//
//                }else {
//                    myAns.setText("he equation has non-real solutions");
//                }
//
//                Log.i("TAG23","Appendedtoseries") ;
//                myGraph.addSeries(series) ;
//            }
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
