/**
 * Main Matrix Class
 */

package com.voidwalkers.photograph.MatrixFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.voidwalkers.photograph.GlobalValues;
import com.voidwalkers.photograph.MatrixFragment.OperationFragments.AdditionFragment;
import com.voidwalkers.photograph.MatrixFragment.OperationFragments.AdjointFragment;
import com.voidwalkers.photograph.MatrixFragment.OperationFragments.DeterminantFragment;
import com.voidwalkers.photograph.MatrixFragment.OperationFragments.EigenvalueFragment;
import com.voidwalkers.photograph.MatrixFragment.OperationFragments.EigenvectorFragment;
import com.voidwalkers.photograph.MatrixFragment.OperationFragments.ExponentFragment;
import com.voidwalkers.photograph.MatrixFragment.OperationFragments.InverseFragment;
import com.voidwalkers.photograph.MatrixFragment.OperationFragments.MultiplyFragment;
import com.voidwalkers.photograph.MatrixFragment.OperationFragments.SubtractionFragment;
import com.voidwalkers.photograph.MatrixFragment.OperationFragments.TraceFragment;
import com.voidwalkers.photograph.MatrixFragment.OperationFragments.TransposeFragment;
import com.voidwalkers.photograph.MatrixFragment.base_fragments.MatrixMainFragmentList;
import com.voidwalkers.photograph.R;
import com.voidwalkers.photograph.ScannerFragment.CameraActivity;


public class MatrixMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final int RESULT=1;
    Menu ActionbarMenu; //the Menu items in the top 3 dots
    ActionBar actionBar; //the Main Activity Actionbar
    TextView t; // opening hint
    Menu NavMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // onCreate
        super.onCreate(savedInstanceState);
        // setting View to activity_main
        setContentView(R.layout.activity_main);

        // suggests the user to click the + button
        t = (TextView)findViewById(R.id.OpeningHint);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // if there are entries (one or more variables stored, set the hint = ""
        if(!((GlobalValues)getApplication()).GetCompleteList().isEmpty())
            t.setText(null);

        // extracting the drawer layout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        // setting an event listener on Add Variable Button
        Button b = (Button) findViewById(R.id.Main_Add_Variable) ;
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On click Start MakeNewMatrix Activity
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivityForResult(intent, RESULT);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View v = navigationView.getHeaderView(0);
        navigationView.setCheckedItem(R.id.Home);
        NavMenuItem = navigationView.getMenu();


        // TODO Add comment
        if(savedInstanceState==null)
        {
            MatrixMainFragmentList mh=new MatrixMainFragmentList();
            getSupportFragmentManager().beginTransaction().add(R.id.MainContent,mh,"MAIN_LIST").commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        // this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.ActionbarMenu = menu;
        this.actionBar = getSupportActionBar();
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // on click event for ClearVar
        int id = item.getItemId();
        if (id == R.id.ClearAllVar)
            if(!((GlobalValues)getApplication()).GetCompleteList().isEmpty()){
                // create an dialog box
                AlertDialog.Builder builder= new AlertDialog.Builder(MatrixMain.this);
                builder.setMessage("Clear all Variables?");
                builder.setTitle("Clear All");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // On Clicking yes, matrices cleared from globalvalues
                        ((GlobalValues) getApplication()).ClearAllMatrix();
                        actionBar.setSubtitle(null);
                        // setting autosaved to 1
                        ((GlobalValues)getApplication()).AutoSaved=1;
                        // reset the opening hint
                        TextView t = (TextView) findViewById(R.id.OpeningHint);
                        t.setText(R.string.OpenHint);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
            else {
                Log.v("TAG2","Nothing") ;
                Toast.makeText(getApplication(), "Nothing to clear", Toast.LENGTH_SHORT).show();
            }
        return super.onOptionsItemSelected(item);
    }

    /**
     * On Selecting various Navigation bar items, replacing fragments
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // event handler for the navigation menu
        int id = item.getItemId();
        Button b = (Button) findViewById(R.id.Main_Add_Variable);

        Log.v("TAG2","i") ;

        switch (id)
        {
            case R.id.Home:
                Log.v("TAG2","HomePressed") ;
                // setting the fragment
                MatrixMainFragmentList fragment_home=new MatrixMainFragmentList();
                // replacing the fragment with home fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.MainContent,fragment_home,"MAIN_LIST").commit();
                ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(true);
                actionBar.setTitle(R.string.app_name);
                if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                    actionBar.setSubtitle(null);
                else
                    actionBar.setSubtitle(R.string.MainSubtitle);
                // set button visible
                b.setVisibility(View.VISIBLE);

                // setting the hint
                if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                    t.setText(R.string.OpenHint);
                else
                    t.setText(null);
                break;
            case R.id.add_sub :
                Log.v("TAG2","Add_sub_pressed") ;
//                setting fragment
                FragmentTransaction AdditionTransaction = getSupportFragmentManager().beginTransaction();
                AdditionFragment additionFragment = new AdditionFragment();
                AdditionTransaction.replace(R.id.MainContent, additionFragment,"ADDITION_FRAGMENT");
                AdditionTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                AdditionTransaction.commit();
//                setting actionbar
                ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
                actionBar.setTitle(R.string.ShortAddSub);
                actionBar.setSubtitle(null);
                if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                    t.setText(R.string.OpenHint2);
                else
                    t.setText(null);
                b.setVisibility(View.GONE);
                break;
            case R.id.only_sub:
//                setting fragment
                FragmentTransaction SubtractionTransaction = getSupportFragmentManager().beginTransaction();
                SubtractionFragment subtractionFragment = new SubtractionFragment();
                SubtractionTransaction.replace(R.id.MainContent, subtractionFragment,"SUBTRACTION_FRAGMENT");
                SubtractionTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                SubtractionTransaction.commit();
//                setting actionbar
                ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
                actionBar.setTitle(R.string.ShortOnlySub);
                actionBar.setSubtitle(null);
                if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                    t.setText(R.string.OpenHint2);
                else
                    t.setText(null);
                b.setVisibility(View.GONE);
                break;
            case R.id.Transpose:
                FragmentTransaction  transposeTransaction = getSupportFragmentManager().beginTransaction();
                TransposeFragment transposeFragment = new TransposeFragment();
                transposeTransaction.replace(R.id.MainContent, transposeFragment,"TRANSPOSE_FRAGMENT");
                transposeTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transposeTransaction.commit();
                ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
                actionBar.setTitle(R.string.transpose);
                actionBar.setSubtitle(null);
                if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                    t.setText(R.string.OpenHint2);
                else
                    t.setText(null);
                b.setVisibility(View.GONE);
                break;
            case R.id.multiply:
                FragmentTransaction  multiplyTransaction = getSupportFragmentManager().beginTransaction();
                MultiplyFragment multiplyFragment = new MultiplyFragment();
                multiplyTransaction.replace(R.id.MainContent, multiplyFragment,"MULTIPLY_FRAGMENT");
                multiplyTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                multiplyTransaction.commit();
                ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
                actionBar.setSubtitle(null);
                actionBar.setTitle(R.string.multiply);
                if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                    t.setText(R.string.OpenHint2);
                else
                    t.setText(null);
                b.setVisibility(View.GONE);
                break;
            case R.id.exponent:
                FragmentTransaction ExponentTransaction = getSupportFragmentManager().beginTransaction();
                ExponentFragment ef = new ExponentFragment();
                ExponentTransaction.replace(R.id.MainContent, ef,"EXPONENT_FRAGMENT");
                ExponentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ExponentTransaction.commit();
//                Modify the Actionbar
                ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
                actionBar.setTitle(R.string.exponent);
                actionBar.setSubtitle(null);
                if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                    t.setText(R.string.OpenHint2);
                else
                {
                    if(isAnyVariableSquare())
                        t.setText(null);
                    else
                        t.setText(R.string.NoSupport);
                }
                b.setVisibility(View.GONE);
                break;
            case R.id.determinant:
                FragmentTransaction DeterminantTransaction= getSupportFragmentManager().beginTransaction();
                DeterminantFragment df = new DeterminantFragment();
                DeterminantTransaction.replace(R.id.MainContent, df,"DETERMINANT_FRAGMENT");
                DeterminantTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                DeterminantTransaction.commit();
//                Modify the Actionbar
                ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
                actionBar.setTitle(R.string.determinant);
                actionBar.setSubtitle(null);
                if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                    t.setText(R.string.OpenHint2);
                else
                {
                    if(isAnyVariableSquare())
                        t.setText(null);
                    else
                        t.setText(R.string.NoSupport);
                }
                b.setVisibility(View.GONE);
                break;
            case R.id.trace:
                FragmentTransaction TraceTrasaction = getSupportFragmentManager().beginTransaction();
                TraceFragment traceFragment = new TraceFragment();
                TraceTrasaction.replace(R.id.MainContent,traceFragment,"TRACE_FRAGMENT");
                TraceTrasaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                TraceTrasaction.commit();
                //Modify Actionbar
                ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
                actionBar.setTitle(R.string.trace_text);
                actionBar.setSubtitle(null);
                if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                    t.setText(R.string.OpenHint2);
                else {
                    if (isAnyVariableSquare())
                        t.setText(null);
                    else
                        t.setText(R.string.NoSupport);
                }
                b.setVisibility(View.GONE);
                break;
            case R.id.eigenvectors:
                FragmentTransaction eigenvectors = getSupportFragmentManager().beginTransaction();
                EigenvectorFragment eigen = new EigenvectorFragment();
                eigenvectors.replace(R.id.MainContent,eigen,"EIGEN_VECTORS");
                eigenvectors.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                eigenvectors.commit();
                //Modify Actionbar
                ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
                actionBar.setTitle("EigenVectors");
                actionBar.setSubtitle(null);
                if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                    t.setText(R.string.OpenHint2);
                else {
                    if (isAnyVariableSquare())
                        t.setText(null);
                    else
                        t.setText(R.string.NoSupport);
                }
                b.setVisibility(View.GONE);
                break;
            case R.id.eigenvalue:
                FragmentTransaction eigenvalues = getSupportFragmentManager().beginTransaction();
                EigenvalueFragment eigenval = new EigenvalueFragment();
                eigenvalues.replace(R.id.MainContent,eigenval,"EIGEN_VALUES");
                eigenvalues.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                eigenvalues.commit();
                //Modify Actionbar
                ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
                actionBar.setTitle("EigenValues");
                actionBar.setSubtitle(null);
                if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                    t.setText(R.string.OpenHint2);
                else {
                    if (isAnyVariableSquare())
                        t.setText(null);
                    else
                        t.setText(R.string.NoSupport);
                }
                b.setVisibility(View.GONE);
                break;
            case R.id.inverse:
                FragmentTransaction InverseTransaction= getSupportFragmentManager().beginTransaction();
                InverseFragment inv = new InverseFragment();
                InverseTransaction.replace(R.id.MainContent, inv,"INVERSE_FRAGMENT");
                InverseTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                InverseTransaction.commit();
                //Modify the Actionbar
                ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
                actionBar.setTitle(R.string.inverse);
                actionBar.setSubtitle(null);
                if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                    t.setText(R.string.OpenHint2);
                else
                {
                    if(isAnyVariableSquare())
                        t.setText(null);
                    else
                        t.setText(R.string.NoSupport);
                }
                b.setVisibility(View.GONE);
                break;
            case R.id.adjoint:
                FragmentTransaction AdjointTransaction= getSupportFragmentManager().beginTransaction();
                AdjointFragment af = new AdjointFragment();
                AdjointTransaction.replace(R.id.MainContent, af,"ADJOINT_FRAGMENT");
                AdjointTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                AdjointTransaction.commit();
                //Modify the Actionbar
                ActionbarMenu.findItem(R.id.ClearAllVar).setVisible(false);
                actionBar.setTitle("Adjoint");
                actionBar.setSubtitle(null);
                if(((GlobalValues)getApplication()).GetCompleteList().isEmpty())
                    t.setText(R.string.OpenHint2);
                else
                {
                    if(isAnyVariableSquare())
                        t.setText(null);
                    else
                        t.setText(R.string.NoSupport);
                }
                b.setVisibility(View.GONE);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Add Matrix if found in bundle else repeat activity
     * @param requestcode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestcode,int resultCode,Intent data)
    {
        super.onActivityResult(requestcode,resultCode,data);
        if(resultCode==RESULT)
        {
            if(data!=null) {
                Bundle AllData=new Bundle();
                AllData.putAll(data.getExtras());
                Matrix m=new Matrix();
                try{
                    m.SetFromBundle(AllData);
                    ((GlobalValues) getApplication()).AddToGlobal(m); //Sending the things to Global Reference
                    if(actionBar.getSubtitle()==null)
                        actionBar.setSubtitle("All Variables");
                    t.setText(null);
                    Toast.makeText(getApplicationContext(), "Variable Created", Toast.LENGTH_SHORT).show();
                }  catch (NullPointerException e2){
                    e2.printStackTrace();
                }
            }
        }
        // Recreate this Activity if none added
        if(resultCode==100)
            this.recreate();
    }

    /**
     * returns true if any of the variables is square
     * calls is_squareMatrix on all input matrices
     * @return
     */
    protected boolean isAnyVariableSquare()
    {
        for(int i = 0; i <((GlobalValues)getApplication()).GetCompleteList().size(); i++)
            if(((GlobalValues)getApplication()).GetCompleteList().get(i).is_squareMatrix())
                return true;
        return false;
    }
}

