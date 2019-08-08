/**
 * Main Activity of the application
 * User can choose between matrix operations and other operations
 */
package com.voidwalkers.photograph;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.voidwalkers.photograph.MatrixFragment.MatrixMain;
import com.voidwalkers.photograph.ScannerFragment.CameraActivity;
import com.voidwalkers.photograph.ScannerFragment.CameraFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_activity);


        Button AlgebraButton = (Button) findViewById(R.id.algebra_button) ;

        AlgebraButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
//                        Log.e("HHH","B") ;
                        Intent i = new Intent(v.getContext(), CameraActivity.class) ;
                        startActivity(i);
                    }
                }
        );

        Button MatrixButton = (Button) findViewById(R.id.matrix_button) ;

        MatrixButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
//                        Log.e("HHH","C") ;

                        Intent i = new Intent(v.getContext(), MatrixMain.class) ;
                        startActivity(i);
                    }
                }
        );
    }
}