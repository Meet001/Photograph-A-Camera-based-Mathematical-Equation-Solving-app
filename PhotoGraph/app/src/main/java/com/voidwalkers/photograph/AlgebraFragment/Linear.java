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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.voidwalkers.photograph.GlobalMemory;
import com.voidwalkers.photograph.Latex;
import com.voidwalkers.photograph.MainActivity;
import com.voidwalkers.photograph.R;

import org.w3c.dom.Text;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Main class for linear equation input
 */
public class Linear extends AppCompatActivity {
    // the class contains four data members , namely the coefficients
    private static double[] Eq1 = new double[4];
    private static double[] Eq2 = new double[4];
    public ListView steps;
    public WebView webView;
    private boolean state ;
//    public RelativeLayout rl;

    // arrayadpter for the listview

    public ArrayList<String> listItems = new ArrayList<String>();
    public ArrayAdapter<String> adapter;

    /**
     *Handles all activities when Linear is called
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // general oncreate method
        state = false ;

        super.onCreate(savedInstanceState);

        // setting the view to activity_quadratic

        setContentView(R.layout.activity_quadratic);

        // Meet change this to the newer library

        webView = (WebView) findViewById(R.id.graphoutput);


//        GraphView myGraph = (GraphView) findViewById(R.id.outputGraph);
        TextView input = (TextView) findViewById(R.id.input_latex);
        steps = (ListView) findViewById(R.id.quadratic_steps);

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        steps.setAdapter(adapter);


//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);

//        steps.setAdapter(adapter);

//        rl = (RelativeLayout) findViewById(R.id.quad_output);

        while (Latex.latexInput.contains("frac")){
            Log.i("TAG23","in frac");
            Log.i("TAG23",Latex.latexInput);
            String mypattern = "\\\\frac\\{([+-]?\\d*\\.?\\d+)\\}\\{([+-]?\\d*\\.?\\d+)\\}";
            String p = "";
            String q = "";
            Pattern pattern = Pattern.compile(mypattern);
            Matcher matcher = pattern.matcher(Latex.latexInput);
            if (matcher.find()){
                Log.i("TAG23","found");
                p = matcher.group(1);
                q = matcher.group(2);
                Log.i("TAG23",p);
                Log.i("TAG23",q);
                double replace = Double.parseDouble(p)/Double.parseDouble(q);


                Latex.latexInput = Latex.latexInput.replaceAll("\\\\frac\\{"+p+"\\}\\{"+q+"\\}", String.valueOf(replace));
                Log.i("TAG23",String.valueOf(replace));
                Log.i("TAG23",Latex.latexInput);

            }
            else break;

        }

        input.setText(Latex.latexInput);


        Latex.Linear1 = Linear.parse(Latex.latexInput.split(",")[0]) ;
        Latex.Linear2 = Linear.parse(Latex.latexInput.split(",")[1]) ;
//
//        Linear.Eq1[0] = 1;
//        Linear.Eq1[1] = 1;
//        Linear.Eq1[2] = 0;
//        Linear.Eq1[3] = 3;
//        Linear.Eq2[0] = 1;
//        Linear.Eq2[1] = -1;
//        Linear.Eq2[2] = 0;
//        Linear.Eq2[3] = 1;

        this.MakeLinearGraph();
        if (GlobalMemory.LinearData.containsKey(Latex.Linear1) && GlobalMemory.LinearData.containsKey(Latex.Linear2)) {
//             execute the function corresponding to the entry
            Log.v("Tag2","g1") ;
            this.callByName(GlobalMemory.LinearData.get(Latex.Linear1));
            Log.v("Tag2","g2") ;
            state = true ;
            Log.v("Tag2","g3") ;
            this.callByName(GlobalMemory.LinearData.get(Latex.Linear2));
            Log.v("Tag2","g4") ;
            state = false ;
            this.Linear0();
            Log.v("Tag2","g5") ;

        }

        else {
//             just make graph.
            Intent i = new Intent(this, Forum.class);
            startActivity(i);

        }


    }

    /**
     * function for adding items to a ListView
     */
    public void addItems(View v, String step) {
        listItems.add(step);
        adapter.notifyDataSetChanged();
    }

    /**
     * calles function corresponding to string name given as input
     */
    public void callByName(String funcName) {
        try {
            Method method = getClass().getDeclaredMethod(funcName);
            method.invoke(this, new Object[]{});
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function equation as input and converts it to it standrard template formate
     * @param latexInput the equation String entered by user
     * @return
     */
    public static String parse(String latexInput) {

        Log.v("TAG2", "Transforming from Latex");
        String answer = latexInput.replaceAll(" ", "");
        answer = answer.replaceAll("x|X", "Vx");

        answer = answer.replaceAll("y|Y", "Vy");

        answer = answer.replaceAll("[0-9]+", "V");
        answer = answer.replaceAll("[V]+", "V");

        answer = answer.replaceAll("[-]+", "+");

        Log.v("TAG2", "LATEX WAS " + latexInput + " Transformed " + answer);

        return answer;
    }

    /**
     * Makes the graph for two linear equation in xy plane
     */
    public void MakeLinearGraph() {
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
                "  calculator.setExpression({id:'graph1', latex:'" + Latex.latexInput.split(",")[0] + "'});" +
                "  calculator.setExpression({id:'graph2', latex:'" + Latex.latexInput.split(",")[1] + "'});" +
                "</script>" +
                "</body>" +
                "</html>";

        webView.loadDataWithBaseURL("file:///android_asset/", text, "text/html", "UTF-8", null);
    }

    public boolean notAllZero() {
        return true;
    }

    /**
     * if the template already exists in the gloabal memory then linear0 gives detailed solution of the
     * input equations
     */
    public void Linear0() {
        if (notAllZero()) {
            String step1 = "This is the Standard form!";
            String step2 = "Equation 1 is :" + String.valueOf(Eq1[0]) + "x +(" + String.valueOf(Eq1[1]) + "y) = " + String.valueOf(Eq1[3] - Eq1[2]);
            String step3 = "Equation 2 is :" + String.valueOf(Eq2[0]) + "x +(" + String.valueOf(Eq2[1]) + "y) = " + String.valueOf(Eq2[3] - Eq2[2]);
            String step4 = "Multiply equation 1 by " + String.valueOf(Eq2[0]);
            String step5 = "Now equation 1 is :" + String.valueOf(Eq1[0] * Eq2[0]) + "x +(" + String.valueOf(Eq1[1] * Eq2[0]) + "y) = " + String.valueOf((Eq1[3] - Eq1[2]) * Eq2[0]);
            String step6 = "Multiply equation 2 by " + String.valueOf(Eq1[0]);
            String step7 = "Now equation 2 is :" + String.valueOf(Eq2[0] * Eq1[0]) + "x +(" + String.valueOf(Eq2[1] * Eq1[0]) + "y) = " + String.valueOf((Eq2[3] - Eq2[2]) * Eq1[0]);
            // whichever is greater
            String step8 = "Now subtract 1 from 2";

            String step9 = String.valueOf(Eq1[1] * Eq2[0] - Eq2[1] * Eq1[0]) + "y = " + String.valueOf((Eq1[3] - Eq1[2]) * Eq2[0] - (Eq2[3] - Eq2[2]) * Eq1[0]);
            Double y_val = ((Eq1[3] - Eq1[2]) * Eq2[0] - (Eq2[3] - Eq2[2]) * Eq1[0]) / (Eq1[1] * Eq2[0] - Eq2[1] * Eq1[0]);
            String step10 = "y = " + String.valueOf(y_val);

            String step11 = "Substitute y in equation 1";
            String step12 = String.valueOf(Eq1[0]) + "x +(" + String.valueOf(Eq1[1]) + "*" + String.valueOf(y_val) + ") = " + String.valueOf(Eq1[3] - Eq1[2]);
            Double x_val = ((Eq1[3] - Eq1[2]) - Eq1[1] * y_val) / Eq1[0];
            String step13 = "Therefore x = " + String.valueOf(x_val);
            String step14 = "Point of intersection is :";
            String step15 = "(" + String.valueOf(x_val) + " ," + String.valueOf(y_val) + ")";

            this.addItems(steps, step1);//
            this.addItems(steps, step2);//
            this.addItems(steps, step3);//
            this.addItems(steps, step4);//
            this.addItems(steps, step5);//
            this.addItems(steps, step6);//
            this.addItems(steps, step7);//
            this.addItems(steps, step8);//
            this.addItems(steps, step9);//
            this.addItems(steps, step10);//
            this.addItems(steps, step11);//
            this.addItems(steps, step12);//
            this.addItems(steps, step13);//
            this.addItems(steps, step14);//
            this.addItems(steps, step15);//

        }
    }

    /**
     * finds coefficients out of standard template1 type of input linear equations
     */
    public void Linear1(){
        Log.e("TAG","Linear1called") ;
        String local="";
        if (!state){
            local = Latex.latexInput.split(",")[0];
        }
        else{
            local = Latex.latexInput.split(",")[1];
        }
        Log.e("TAG",local+"1");
        String Vars[] = new String[4];
        local = local.replaceAll(" ","");
        Log.e("TAG",local+"2");

        String pattern = "(.*)x(.*)y(.*)=(.*)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(local);

        if (m.find()){
            Log.v("TAG2","in matcher");
            for (int i=0;i<4;i++){
                Vars[i]=m.group(i+1);
                Log.v("TAG2",Vars[i]) ;
            }
            if (Vars[0].equals("")) Vars[0] = "1";
            if (Vars[0].equals("-")) Vars[0] = "-1";
            if (Vars[1].equals("+")) Vars[1] = "1";
            if (Vars[1].equals("-")) Vars[1] = "-1";
            if (Vars[2].equals("")) Vars[2] = "0";
        }
        if (!state){
            Eq1[0] = Double.parseDouble(Vars[0]) ;
            Eq1[1] = Double.parseDouble(Vars[1]) ;
            Eq1[2] = Double.parseDouble(Vars[2]) ;
            Eq1[3] = Double.parseDouble(Vars[3]) ;
        }
        if (state){
            Eq2[0] = Double.parseDouble(Vars[0]) ;
            Eq2[1] = Double.parseDouble(Vars[1]) ;
            Eq2[2] = Double.parseDouble(Vars[2]) ;
            Eq2[3] = Double.parseDouble(Vars[3]) ;
        }
    }

    /**
     *  finds coefficients out of standard template2 type of input linear equations
     */
    public void Linear2(){
        Log.e("TAG","Linear2called") ;
        String local="";
        if (!state){
            local = Latex.latexInput.split(",")[0];
        }
        else{
            local = Latex.latexInput.split(",")[1];
        }
        Log.e("TAG",local+"1");
        String Vars[] = new String[4];
        local = local.replaceAll(" ","");
        Log.e("TAG",local+"2");

        String pattern = "(.*)y(.*)x(.*)=(.*)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(local);

        if (m.find()){
            Log.v("TAG2","in matcher");
            for (int i=0;i<4;i++){
                Vars[i]=m.group(i+1);
                Log.v("TAG2",Vars[i]) ;
            }
            if (Vars[0].equals("")) Vars[0] = "1";
            if (Vars[0].equals("-")) Vars[0] = "-1";
            if (Vars[1].equals("+")) Vars[1] = "1";
            if (Vars[1].equals("-")) Vars[1] = "-1";
            if (Vars[2].equals("")) Vars[2] = "0";
        }
        if (!state){
            Eq1[0] = Double.parseDouble(Vars[1]) ;
            Eq1[1] = Double.parseDouble(Vars[0]) ;
            Eq1[2] = Double.parseDouble(Vars[2]) ;
            Eq1[3] = Double.parseDouble(Vars[3]) ;
            String step1 = "Equation 1 is :" + String.valueOf(Eq1[1]) + "y +(" + String.valueOf(Eq1[0]) + "x) = " + String.valueOf(Eq1[3] - Eq1[2]);
            String step2 = "Conversion to standard form is trivial!";
            this.addItems(this.findViewById(R.id.quadratic_steps), step1);//
            this.addItems(this.findViewById(R.id.quadratic_steps), step2);//
        }
        if (state){
            Eq2[0] = Double.parseDouble(Vars[1]) ;
            Eq2[1] = Double.parseDouble(Vars[0]) ;
            Eq2[2] = Double.parseDouble(Vars[2]) ;
            Eq2[3] = Double.parseDouble(Vars[3]) ;
            String step1 = "Equation 2 is :" + String.valueOf(Eq2[1]) + "y +(" + String.valueOf(Eq2[0]) + "x) = " + String.valueOf(Eq2[3] - Eq2[2]);
            String step2 = "Conversion to standard form is trivial!" ;
            this.addItems(this.findViewById(R.id.quadratic_steps), step1);//
            this.addItems(this.findViewById(R.id.quadratic_steps), step2);//
        }
    }
    /**
     * finds coefficients out of standard template2 type of input linear equations
     */
    public void Linear3(){
        Log.e("TAG","Linear3called") ;
        String local="";
        if (!state){
            local = Latex.latexInput.split(",")[0];
        }
        else{
            local = Latex.latexInput.split(",")[1];
        }
        Log.e("TAG",local+"1");
        String Vars[] = new String[4];
        local = local.replaceAll(" ","");
        Log.e("TAG",local+"2");

        String pattern = "(.*)x(.*)y=(.*)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(local);

        if (m.find()){
            Log.v("TAG2","in matcher");
            for (int i=0;i<3;i++){
                Vars[i]=m.group(i+1);
                Log.v("TAG2",Vars[i]) ;
            }
            if (Vars[0].equals("")) Vars[0] = "1";
            if (Vars[0].equals("-")) Vars[0] = "-1";
            if (Vars[1].equals("+")) Vars[1] = "1";
            if (Vars[1].equals("-")) Vars[1] = "-1";
            if (Vars[2].equals("")) Vars[2] = "0";
        }
        if (!state){
            Eq1[0] = Double.parseDouble(Vars[0]) ;
            Eq1[1] = Double.parseDouble(Vars[1]) ;
            Eq1[2] = 0 ;
            Eq1[3] = Double.parseDouble(Vars[2]) ;
            String step1 = "Equation 1 is :" + String.valueOf(Eq1[1]) + "y +(" + String.valueOf(Eq1[0]) + "x) = " + String.valueOf(Eq1[3] - Eq1[2]);
            String step2 = "Conversion to standard form is trivial!";
            this.addItems(this.findViewById(R.id.quadratic_steps), step1);//
            this.addItems(this.findViewById(R.id.quadratic_steps), step2);//
        }
        if (state){
            Eq2[0] = Double.parseDouble(Vars[0]) ;
            Eq2[1] = Double.parseDouble(Vars[1]) ;
            Eq2[2] = 0 ;
            Eq2[3] = Double.parseDouble(Vars[2]) ;
            String step1 = "Equation 2 is :" + String.valueOf(Eq2[1]) + "y +(" + String.valueOf(Eq2[0]) + "x) = " + String.valueOf(Eq2[3] - Eq2[2]);
            String step2 = "Conversion to standard form is trivial!" ;
            this.addItems(this.findViewById(R.id.quadratic_steps), step1);//
            this.addItems(this.findViewById(R.id.quadratic_steps), step2);//
        }
    }
    /**
     * finds coefficients out of standard template2 type of input linear equations
     */
    public void Linear4(){
        Log.e("TAG","Linear4called") ;
        String local="";
        if (!state){
            local = Latex.latexInput.split(",")[0];
        }
        else{
            local = Latex.latexInput.split(",")[1];
        }
        Log.e("TAG",local+"1");
        String Vars[] = new String[4];
        local = local.replaceAll(" ","");
        Log.e("TAG",local+"2");

        String pattern = "(.*)y(.*)x=(.*)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(local);

        if (m.find()){
            Log.v("TAG2","in matcher");
            for (int i=0;i<3;i++){
                Vars[i]=m.group(i+1);
                Log.v("TAG2",Vars[i]) ;
            }
            if (Vars[0].equals("")) Vars[0] = "1";
            if (Vars[0].equals("-")) Vars[0] = "-1";
            if (Vars[1].equals("+")) Vars[1] = "1";
            if (Vars[1].equals("-")) Vars[1] = "-1";
            if (Vars[2].equals("")) Vars[2] = "0";
        }
        if (!state){
            Eq1[0] = Double.parseDouble(Vars[1]) ;
            Eq1[1] = Double.parseDouble(Vars[0]) ;
            Eq1[2] = 0 ;
            Eq1[3] = Double.parseDouble(Vars[2]) ;
            String step1 = "Equation 1 is :" + String.valueOf(Eq1[1]) + "y +(" + String.valueOf(Eq1[0]) + "x) = " + String.valueOf(Eq1[3] - Eq1[2]);
            String step2 = "Conversion to standard form is trivial!";
            this.addItems(this.findViewById(R.id.quadratic_steps), step1);//
            this.addItems(this.findViewById(R.id.quadratic_steps), step2);//
        }
        if (state){
            Eq2[0] = Double.parseDouble(Vars[1]) ;
            Eq2[1] = Double.parseDouble(Vars[0]) ;
            Eq2[2] = 0 ;
            Eq2[3] = Double.parseDouble(Vars[2]) ;
            String step1 = "Equation 2 is :" + String.valueOf(Eq2[1]) + "y +(" + String.valueOf(Eq2[0]) + "x) = " + String.valueOf(Eq2[3] - Eq2[2]);
            String step2 = "Conversion to standard form is trivial!" ;
            this.addItems(this.findViewById(R.id.quadratic_steps), step1);//
            this.addItems(this.findViewById(R.id.quadratic_steps), step2);//
        }
    }


}