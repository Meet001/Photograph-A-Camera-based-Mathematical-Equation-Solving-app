package com.voidwalkers.photograph.MatrixFragment.base_classes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.voidwalkers.photograph.R;

/**
 * Activity for setting the matrix exponent
 * returns a view for the user to input a value
 * returns a matrix multiplied with itself if input is less than 15
 */

public class ExponentSetter extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exponent_setter);

        // referencing the buttons
        Button Confirm = (Button) findViewById(R.id.ConfirmSetFill);
        Button Cancel = (Button) findViewById(R.id.CancelSetFill);
        final EditText editText = (EditText) findViewById(R.id.ExponentSetter);

        //OnClickListener
        /**
         * OnClickListener for entered value
         * Toast if no value provided or value greater than 15 is provided
         */
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Please Provide a value", Toast.LENGTH_SHORT).show();
                else {
                    if (Integer.parseInt(editText.getText().toString())>15)
                        Toast.makeText(getApplicationContext(), "Enter Smaller Value", Toast.LENGTH_LONG).show();
                    else {
                        Intent intent = new Intent();
                        intent.putExtra("QWERTYUIOP", Integer.parseInt(editText.getText().toString()));
                        setResult(500, intent);
                        finish();
                    }
                }
            }
        });
        /**
         * If clicked on cancel, finish the activity
         */
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
