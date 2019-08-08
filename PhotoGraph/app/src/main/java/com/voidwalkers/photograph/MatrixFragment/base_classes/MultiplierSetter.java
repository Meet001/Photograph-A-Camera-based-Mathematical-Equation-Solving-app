package com.voidwalkers.photograph.MatrixFragment.base_classes;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.voidwalkers.photograph.R;

/**
 * Multiplying activity, adds matrices in the row according to multiplication condition.
 */

public class MultiplierSetter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplier_setter);

        final EditText editText = (EditText) findViewById(R.id.MultiplierSetterInput);
        Button button1 = (Button) findViewById(R.id.ConfirmSetFillScalar);
        Button button2 =  (Button) findViewById(R.id.CancelSetFillScalar);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText.getText().toString().isEmpty()) {
                    if(editText.getText().toString().contains(".") &&
                            !PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("DECIMAL_USE",true)) {
                        Toast.makeText(getApplicationContext(), "Allow Decimals", Toast.LENGTH_SHORT).show();
                    }
                    else{

                        Intent intent = new Intent();
                        intent.putExtra("MULTIPLIER_VAL", Float.parseFloat(editText.getText().toString()));
                        setResult(1054, intent);
                        finish();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),R.string.NoValue,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
