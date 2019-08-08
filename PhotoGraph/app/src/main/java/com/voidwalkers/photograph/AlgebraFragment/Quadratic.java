package com.voidwalkers.photograph.AlgebraFragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.voidwalkers.photograph.Latex;
import com.voidwalkers.photograph.MainActivity;
import com.voidwalkers.photograph.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for Quadratic
 */
public class Quadratic extends AppCompatActivity {
    /*the class contains four data members , namely the coefficients*/

    private static double Coeffs[] = new double[4];
    public ListView steps ;
//    public RelativeLayout rl;

    // arrayadpter for the listview

    public ArrayList<String> listItems=new ArrayList<String>();
    public ArrayAdapter<String> adapter;

    /**
     * Handles all activity when Quadratic is called
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        // general oncreate method

        super.onCreate(savedInstanceState);

        // setting the view to activity_quadratic

        setContentView(R.layout.activity_quadratic);

        // Meet change this to the newer library

        // GraphView myGraph = (GraphView) findViewById(R.id.outputGraph);
        TextView input = (TextView) findViewById(R.id.input_latex);
        steps = (ListView) findViewById(R.id.quadratic_steps) ;

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);

        steps.setAdapter(adapter);

//        rl = (RelativeLayout) findViewById(R.id.quad_output);

        input.setText(Latex.latexInput);

        //  myGraph.removeAllSeries();

//        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();



        Latex.QuadTransform = Quadratic.parse(Latex.latexInput) ;

        //if (GlobalMemory.QuadraticData.containsKey(Latex.QuadTransform)) {
        // execute the function corresponding to the entry
        Log.i("TAG23", Latex.QuadTransform+"eqrwq");
        //  this.callByName(GlobalMemory.QuadraticData.get(Latex.QuadTransform));
        changecoeffs(Latex.QuadTransform);
        quad0(Coeffs);

        //  }

    }
    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
    /**
     * updates values of Coeffs array
     * @param quadTransform transformed latex string after parse
     */
    private void changecoeffs(String quadTransform) {
        Coeffs[0]=PolyCoeffs(quadTransform,"x^{2}");
        Coeffs[1]=PolyCoeffs(quadTransform,"x");
        Coeffs[2]=PolyCoeffs(quadTransform,"");
        Coeffs[3]=0;
    }

    /**
     * Main function of the Quadratic class
     * makes graph of the input equation and gives a detailed solution
     * @param coeffs Coefficient of input equation in standard form
     */
    private void quad0(double[] coeffs) {
        WebView webView = (WebView)findViewById(R.id.graphoutput);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.getSettings().setUseWideViewPort(true);

        String left = "y =" + Latex.QuadTransform.split("=")[0];
        String right = "y =" + Latex.QuadTransform.split("=")[1];

        String text = "<html style=\"height: 100%\">" +
                "<head>" +
                "    <title>calculator</title>" +
                "</head>" +
                "<body style=\"position: absolute;top:0; bottom:0; right:0; left:0;\">" +
                "<script src=\"https://www.desmos.com/api/v0.9/calculator.js?apiKey=dcb31709b452b1cf9dc26972add0fda6\"></script>" +
                "<div id=\"calculator\" style=\"width: 100%;height: 100%\"></div>" +
                "<script>" +
                "  var elt = document.getElementById('calculator');" +
                "  var calculator = Desmos.GraphingCalculator(elt);" +
                "  calculator.setExpression({id:'graph1', latex:'"+left+"'});" +
                "  calculator.setExpression({id:'graph2', latex:'"+right+"'});" +
                "</script>" +
                "</body>" +
                "</html>";

        webView.loadDataWithBaseURL("file:///android_asset/", text, "text/html", "UTF-8", null);

        String step1 = "This is the Standard form!" ;
        String step2 = "ax^2 + bx + c = 0" ;
        String step3 = "where a = " + String.valueOf(Coeffs[0]) ;
        String step4 = "where b = " + String.valueOf(Coeffs[1]) ;
        String step5 = "where c = " + String.valueOf(Coeffs[2]) ;
        String step6 = "Applying Standard formula for quadratic";
        double D = Coeffs[1]*Coeffs[1] - 4*Coeffs[0]*Coeffs[2];

        this.addItems(this.findViewById(R.id.quadratic_steps),step1);
        this.addItems(this.findViewById(R.id.quadratic_steps),step2);
        this.addItems(this.findViewById(R.id.quadratic_steps),step3);
        this.addItems(this.findViewById(R.id.quadratic_steps),step4);
        this.addItems(this.findViewById(R.id.quadratic_steps),step5);


        //knthis.addItems(this.findViewById(R.id.quadratic_steps),step6);
        if (D >= 0){
            double x1= (-Coeffs[1] + Math.sqrt(D))/(2*Coeffs[0]);
            double x2= (-Coeffs[1] - Math.sqrt(D))/(2*Coeffs[0]);
            String step7 = "Roots of equation are ";
            String step8 = "root 1 =" +String.valueOf(x1);
            String step9 = "root 2 =" +String.valueOf(x2);

            this.addItems(this.findViewById(R.id.quadratic_steps),step7);
            this.addItems(this.findViewById(R.id.quadratic_steps),step8);
            this.addItems(this.findViewById(R.id.quadratic_steps),step9);
        }
        else{
            String step10 = "The equation has imaginary roots.";
            this.addItems(this.findViewById(R.id.quadratic_steps),step10);
        }
        // this code basically adds steps, (lines) to the ListView



    }



    public void addItems(View v, String step) {
        listItems.add(step);
        adapter.notifyDataSetChanged();
    }

//    public void callByName(String funcName) {
//        try {
//            Method method = getClass().getDeclaredMethod(funcName);
//            method.invoke(this, new Object[] {});
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * takes latexInput and converts it to solvable string by PolyCoeffs
     * i.e. converts all fraction with their corresponding double values
     * @return a string form of input equation with all unsolvable forms removed
     */
    public static String parse(String latexInput) {
        Log.i("TAG23","Transforming from Latex") ;
        String answer = latexInput.replaceAll("\\s+", "");

        while (answer.contains("frac")){
            Log.i("TAG23","in frac");
            Log.i("TAG23",answer);
            String mypattern = "\\\\frac\\{([+-]?\\d*\\.?\\d+)\\}\\{([+-]?\\d*\\.?\\d+)\\}";
            String p = "";
            String q = "";
            Pattern pattern = Pattern.compile(mypattern);
            Matcher matcher = pattern.matcher(answer);
            if (matcher.find()){
                Log.i("TAG23","found");
                p = matcher.group(1);
                q = matcher.group(2);
                Log.i("TAG23",p);
                Log.i("TAG23",q);
                double replace = Double.parseDouble(p)/Double.parseDouble(q);
                replace = Math.round(replace*100);
                replace = replace/100;

                answer = answer.replaceAll("\\\\frac\\{"+p+"\\}\\{"+q+"\\}", String.valueOf(replace));
                Log.i("TAG23",String.valueOf(replace));
                Log.i("TAG23",answer);

            }}
        Latex.latexInput = answer;
        // answer = answer.replaceAll("x","x ");
        //answer = answer.replaceAll("x ^{2}","x^{2} ");
        answer = answer.replaceAll("\\+"," +");
        answer = answer.replaceAll("-"," -");
        answer = answer.replaceAll("="," = ");
//        Latex.latexInput = answer;
//
//        answer = answer.replaceAll("x|X","Vx") ;
//
//        answer = answer.replaceAll("y|Y","Vy") ;
//        answer = answer.replaceAll("\\.","");
//        answer = answer.replaceAll("[0-9]+","V") ;
//        answer = answer.replaceAll("[V]+","V") ;
//
//        answer = answer.replaceAll("[-]+","+") ;
//
//        Log.v("TAG2","LATEX WAS "+ latexInput + " Transformed " + answer) ;
//        Log.i("TAG23",answer);
        return answer ;

    }



//    public void quad1(){
//        // ax^2 + bx + c = d ;
//
//        String latexInput = Latex.latexInput ;
//        String transformedLatex = Latex.QuadTransform ;
//
//        Log.i("TAG23", "I am in quadratic1");
//        Log.e("TAG2", "LatexInput = " + latexInput);
//
//        String[] Vars = new String[4];
//
//        latexInput = latexInput.replaceAll(" ", "");
//        //   latexInput = latexInput.replaceAll("\\.", "");
//
//        String pattern = "(.*)x\\^\\{.*\\}(.*)x(.*)=(.*)";
//
//        Log.e("TAG2", "LatexInput = " + latexInput);
//        Log.e("TAG2", "TransformedLatex = " + transformedLatex);
//
//        Pattern r = Pattern.compile(pattern);
//        Matcher m = r.matcher(latexInput);
//
//        // if I find a pattern in the latex string
//
//        if (m.find()) {
//
//            for (int i = 0; i < 4; i++) {
//                Vars[i] = m.group(i + 1);
//                Log.i("TAG23","Found value: " + Vars[i]);
//            }
//            if (Vars[0].equals("")) Vars[0] = "1";
//            if (Vars[0].equals("-")) Vars[0] = "-1";
//            if (Vars[1].equals("+")) Vars[1] = "1";
//            if (Vars[1].equals("-")) Vars[1] = "-1";
//            if (Vars[2].equals("")) Vars[2] = "0";
//
//            for (int i = 0; i < 4; i++) {
//                Log.i("TAG23", "Found value: " + String.valueOf(Double.parseDouble(Vars[i])));
//            }
//
//            Coeffs[0] = Double.parseDouble(Vars[0]) ;
//            Coeffs[1] = Double.parseDouble(Vars[1]) ;
//            Coeffs[2] = Double.parseDouble(Vars[2]) ;
//            Coeffs[3] = Double.parseDouble(Vars[3]) ;
//
//            Log.i("TAG23","Adding") ;
//
//            // steps, lol.
//
//
//
////            TextView t = new TextView(this) ;
////            t.setText("This is the Standard form!");
////            rl.addView(t);
////            t.setText("ax^2 + bx + c = d");
////            rl.addView(t);
////            t.setText("where a = " + String.valueOf(a));
////            rl.addView(t);
//
//        } else {
//
//            // else I will just draw a graph
//
//            Log.e("TAG2", "NOT FOUND");
//        }
//
//    }
//
//    public void quad2(){
//        // bx + ax^2 + c = d ;
//
//        String latexInput = Latex.latexInput ;
//        String transformedLatex = Latex.QuadTransform ;
//
//        Log.i("TAG23", "I am in quadratic1");
//        Log.e("TAG2", "LatexInput = " + latexInput);
//
//        String[] Vars = new String[4];
//
//        latexInput = latexInput.replaceAll(" ", "");
//        //   latexInput = latexInput.replaceAll("\\.", "");
//
//        String pattern = "(.*)x(.*)x\\^\\{.*\\}(.*)=(.*)";
//
//        Log.e("TAG2", "LatexInput = " + latexInput);
//        Log.e("TAG2", "TransformedLatex = " + transformedLatex);
//
//        Pattern r = Pattern.compile(pattern);
//        Matcher m = r.matcher(latexInput);
//
//        // if I find a pattern in the latex string
//
//        if (m.find()) {
//
//            for (int i = 0; i < 4; i++) {
//                Vars[i] = m.group(i + 1);
//                Log.i("TAG23","Found value: " + Vars[i]);
//            }
//            if (Vars[0].equals("")) Vars[0] = "1";
//            if (Vars[0].equals("-")) Vars[0] = "-1";
//            if (Vars[1].equals("+")) Vars[1] = "1";
//            if (Vars[1].equals("-")) Vars[1] = "-1";
//            if (Vars[2].equals("")) Vars[2] = "0";
//
//            for (int i = 0; i < 4; i++) {
//                Log.i("TAG23", "Found value: " + String.valueOf(Double.parseDouble(Vars[i])));
//            }
//
//            Coeffs[0] = Double.parseDouble(Vars[1]) ;
//            Coeffs[1] = Double.parseDouble(Vars[0]) ;
//            Coeffs[2] = Double.parseDouble(Vars[2]) ;
//            Coeffs[3] = Double.parseDouble(Vars[3]) ;
//
//            Log.i("TAG23","Adding") ;
//
//            // steps, lol.
//
//
//
////            TextView t = new TextView(this) ;
////            t.setText("This is the Standard form!");
////            rl.addView(t);
////            t.setText("ax^2 + bx + c = d");
////            rl.addView(t);
////            t.setText("where a = " + String.valueOf(a));
////            rl.addView(t);
//
//        } else {
//
//            // else I will just draw a graph
//
//            Log.e("TAG2", "NOT FOUND");
//        }
//
//    }

//    public void quad3(){
//        // c + ax^2 + bx = d ;
//
//        String latexInput = Latex.latexInput ;
//        String transformedLatex = Latex.QuadTransform ;
//
//        Log.i("TAG23", "I am in quadratic1");
//        Log.e("TAG2", "LatexInput = " + latexInput);
//
//        String[] Vars = new String[4];
//
//        latexInput = latexInput.replaceAll(" ", "");
//        //   latexInput = latexInput.replaceAll("\\.", "");
//
//        String pattern = "(.*)([+-].*)x\\^\\{.*\\}(.*)x=(.*)";
//
//        Log.e("TAG2", "LatexInput = " + latexInput);
//        Log.e("TAG2", "TransformedLatex = " + transformedLatex);
//
//        Pattern r = Pattern.compile(pattern);
//        Matcher m = r.matcher(latexInput);
//
//        // if I find a pattern in the latex string
//
//        if (m.find()) {
//
//            for (int i = 0; i < 4; i++) {
//                Vars[i] = m.group(i + 1);
//                Log.i("TAG23","Found value: " + Vars[i]);
//            }
//            if (Vars[0].equals("")) Vars[0] = "0";
//            if (Vars[0].equals("-")) Vars[0] = "-1";
//            if (Vars[1].equals("+")) Vars[1] = "1";
//            if (Vars[1].equals("-")) Vars[1] = "-1";
//            if (Vars[1].equals("+")) Vars[2] = "1";
//            if (Vars[1].equals("-")) Vars[2] = "-1";
//
//
//            for (int i = 0; i < 4; i++) {
//                Log.i("TAG23", "Found value: " + String.valueOf(Double.parseDouble(Vars[i])));
//            }
//
//            Coeffs[0] = Double.parseDouble(Vars[1]) ;
//            Coeffs[1] = Double.parseDouble(Vars[2]) ;
//            Coeffs[2] = Double.parseDouble(Vars[0]) ;
//            Coeffs[3] = Double.parseDouble(Vars[3]) ;
//
//            Log.i("TAG23","Adding") ;
//
//            // steps, lol.
//
//
//
////            TextView t = new TextView(this) ;
////            t.setText("This is the Standard form!");
////            rl.addView(t);
////            t.setText("ax^2 + bx + c = d");
////            rl.addView(t);
////            t.setText("where a = " + String.valueOf(a));
////            rl.addView(t);
//
//        } else {
//
//            // else I will just draw a graph
//
//            Log.e("TAG2", "NOT FOUND");
//        }
//
//    }

    /**
     * Function takes a solvable latex String of equation and a variable returns the coeffs of the varible in that latex
     * @param latex Solvable latex string of equation
     * @param variable Variable whose Coefficient is to be found
     * @return coeffs of the varible in that latex
     */
    public double PolyCoeffs(String latex,String variable) {

        String[] splitted = latex.split("\\s+");
        int n = splitted.length;
        int val = 1;
        String ans = "";
        double constant = 0;
        if (variable.equals("")) {
            //    String decimalPattern = "([+]*)([-]*)([0-9]*)\\.([0-9]*)";
            for (int i = 0; i < n; i++) {
                //    String number = splitted[i];
                if (splitted[i].equals("=")) {
                    val = -1;
                }
                //    boolean match = Pattern.matches(decimalPattern, number);
                if (isDouble(splitted[i])) {
                    double newcon = Double.parseDouble(splitted[i]);
                    constant = constant + newcon * val;
                    Log.i("TAG23", splitted[i]);
                }
            }

            return constant;
        } else {
            for (int i = 0; i < n; i++) {
                if (splitted[i].equals("=")) {
                    val = -1;
                }
                if (splitted[i].contains(variable)) {
                    ans = splitted[i].replace(variable, "");
                    if (ans.equals("+")){
                        ans = "1";
                    }
                    if (ans.equals("-")){
                        ans = "-1";
                    }
                    if (ans.equals("")){
                        ans = "1";
                    }

                    if (isDouble(ans)) {
                        double newcon = Double.parseDouble(ans);
                        constant = constant + newcon * val;
                        Log.i("TAG23", splitted[i]);
                    }
                }
                // Log.i("TAG23",splitted[i]);
            }



            return constant;
        }

    }

    /**
     * Checks and Gives output if given string is a Double or not
     * @return true if String can be converted to double otherwise false
     */
    boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



}