package com.voidwalkers.photograph.AlgebraFragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.voidwalkers.photograph.GlobalMemory;
import com.voidwalkers.photograph.GlobalValues;
import com.voidwalkers.photograph.Latex;
import com.voidwalkers.photograph.MatrixFragment.Matrix;
import com.voidwalkers.photograph.MatrixFragment.MatrixMain;
import com.voidwalkers.photograph.R;
import org.apache.commons.lang3.StringUtils;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This activity identifies the type of expression given
 * as input and tries to match it with a supported template.
 *
 */
public class EquationSolver extends AppCompatActivity {

    @Override
    /**
     * Creates the activity
     */
    protected void onCreate(Bundle savedInstanceState) {

        Intent i = getIntent();
        Bundle b = i.getExtras();

        super.onCreate(savedInstanceState);

        // change it to random .

//        if (Latex.latexInput.contains(",")){
//            Log.e("TaG2", "LinearEquation");
//            this.SolveMatrix();
//            setContentView(R.layout.activity_matrix_main);
//        }
//
        if (Latex.latexInput.contains("array")) {
            Log.e("TaG2", "Matrix called");
            this.SolveMatrix();
            setContentView(R.layout.activity_matrix_main);
            this.finish();
        } else {
            this.Solve();
        }
    }

    /**
     * Removes any fractional terms present in the string.
     * @param a The numerator of the fraction.
     * @param b The denominator of the fraction.
     * @return A decimal value of the fractional term.
     */
    private double RemoveFrac(String a, String b) {

//        String pattern = "\\\\frac\\{(.*)\\}\\{(.*)\\}" ;
//
//        Pattern r = Pattern.compile(pattern);
//        Matcher m = r.matcher(frac);
//
//        if (m.find()){
//            if (!(m.group(2) == "0"))
//            return Double.parseDouble(m.group(1)) / Double.parseDouble(m.group(2)) ;
//
// }
        return Math.round(Double.parseDouble(a) / Double.parseDouble(b));
        // denominator zero error is to be taken care of.
    }

    /**
     * Removes fractional terms with the numerator and the denominator in the same line,
     * separated by a slash.
     * @param a The numerator of the term
     * @param b The denominator of the term
     * @return The decimal representation of the string.
     */
    private double RemoveSlash(String a, String b) {
        return Math.round(Double.parseDouble(a) / Double.parseDouble(b));
    }

    /**
     * Processes the input string representing the matrix into
     * an appropriate form, i.e. without fractional numbers,
     * extra spaces etc. The improved matrix is then passed on to the
     * MatrixMain activity for further processing.
     */
    private void SolveMatrix() {

        String latexInput = Latex.latexInput;

        latexInput = latexInput.replaceAll(" ", "");

        int rows = StringUtils.countMatches(latexInput, "\\\\") + 1;
        int columns = StringUtils.countMatches(latexInput, "&")/(rows-1) ;
        Log.v("Tag2", Integer.toString(rows) + " " + Integer.toString(columns));
        Log.e("TAG2", latexInput);

//        String pattern = "\\\\frac\\{([+-]?]\\d*\\.?\\d+)\\}\\{([+-]?\\d*\\.?\\d+}\\}" ;
//        Pattern p = Pattern.compile(pattern) ;
//        Matcher m = p.matcher(latexInput) ;
//
//        if (m.find()) {
//            String a = m.group(1) ;
//        }
        if (latexInput.contains("frac")) {
            latexInput = latexInput.replaceAll("\\\\frac\\{(.*)\\}\\{(.*)\\}", String.valueOf(Float.parseFloat("$1") / Float.parseFloat("$2")));
        }
        Log.e("TAG2", latexInput);
//        latexInput = latexInput.replaceAll("\\{(.*)/(.*)\\}", Double.toString(RemoveSlash("$1", "$2")));
        Log.v("Tag2", Integer.toString(rows) + " " + Integer.toString(columns));
        latexInput = latexInput.replaceAll("[a-z\\&\\\\]", "");
        Log.e("TAG2", latexInput);
        String[] coeffs = latexInput.split("\\}\\{");


        Matrix input_matrix = new Matrix(rows, columns);

        float[][] variable = new float[3][3];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Log.v("Tag2",String.valueOf(rows*i+j+2) );
                if(coeffs[columns*i+j+2].contains("-")) input_matrix.SetElementof(-Float.parseFloat(coeffs[columns*i + j + 2].substring(1)), i, j);
                input_matrix.SetElementof(Float.parseFloat(coeffs[columns*i + j + 2]), i, j);
            }
        }

        int name = ((GlobalValues)getApplication()).GetCompleteList().size() + 1 ;

        input_matrix.SetName (Integer.toString(name)) ;

        ((GlobalValues) getApplication()).AddResultToGlobal(input_matrix);

        Intent i = new Intent(this, MatrixMain.class);
        startActivity(i);
    }


    /**
     * Filters the input string to determine the type of expression
     * it represents. After the input is suitably classified, it is then passed
     * on to the appropriate activity for evaluation. In case no known template matches
     * the input, the question is passed on to the Online activity to be solved
     * by Wolfram Alpha.
     */
    private void Solve() {
        String latexInput = Latex.latexInput;

        if (latexInput.contains(",")){
            Log.v("TAG2", "CALLED Linear Solver");
            Intent i = new Intent(this, Linear.class);
            startActivity(i);
            Log.v("TAG2", "SOLVED Linear Solver");

        }

        else if (latexInput.replaceAll(" ","").contains("^{2}")) {

            // So a comma is not found in the string, it is a quadratic
            // very vague right now

            Log.v("TAG2", "CALLED Quadratic Solver");

            Intent i = new Intent(this, Quadratic.class);
            startActivity(i);

            Log.v("TAG2", "SOLVED Quadratic Solver");

        }

        else if (!Latex.latexInput.contains("array")){
            Log.v("TAG2", "Checking online");
            Intent i = new Intent(this, Forum.class);
            startActivity(i);

        }
    }

}