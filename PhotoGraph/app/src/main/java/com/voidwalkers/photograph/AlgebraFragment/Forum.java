package com.voidwalkers.photograph.AlgebraFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.voidwalkers.photograph.Latex;
import com.voidwalkers.photograph.MainActivity;
import com.voidwalkers.photograph.MatrixFragment.MatrixMain;
import com.voidwalkers.photograph.R;

/**
 * Forum class handles all forum activities when input is not found in standard template
 */
public class Forum extends AppCompatActivity {
    EditText Userinput;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        TextView Unsupportedlatex = (TextView) findViewById(R.id.unsupported_latex);
         Userinput = (EditText) findViewById(R.id.editTextMessage);


        Unsupportedlatex.setText(Latex.latexInput);

        Button mybutton = (Button) findViewById(R.id.buttonSend);

        mybutton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        sendmail();
                        Intent i = new Intent(v.getContext(), Online.class) ;
                        startActivity(i);
                    }
                }
        );
    }

    /**
     * sends data to email after button is pressed
     */
    private void sendmail(){
        String post = Userinput.getText().toString();
        post = Latex.latexInput + "\n" + post;
        SendMail sm = new SendMail(this, "lalulaluyadav12345@gmail.com", "Unsupported input", post);
        sm.execute();
    }
    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }


}
