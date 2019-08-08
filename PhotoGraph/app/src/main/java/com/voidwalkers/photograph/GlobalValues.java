/**
 * Global Values
 */
package com.voidwalkers.photograph;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.voidwalkers.photograph.MatrixFragment.Matrix;
import com.voidwalkers.photograph.MatrixFragment.MatrixAdapter ;
import java.util.ArrayList;
import java.util.Objects;

public class GlobalValues extends Application {

    // Array that stores the created matrices
    private ArrayList<Matrix> createdValues = new ArrayList<>();
    public ArrayList<Matrix> MatrixQueue = new ArrayList<>();

    public  Matrix current_editing = null;

    private int LAST_INDEX = 0; //LastIndex of ArrayList

    public int AutoSaved = 1; //To automatically name the saved result

    // Matrix adapter
    public MatrixAdapter matrixAdapter;

    public void AddToGlobal(Matrix mk)
    {
        createdValues.add(mk);
        if(matrixAdapter!=null)
            matrixAdapter.notifyDataSetChanged();
        LAST_INDEX++;
    }

    public void AddResultToGlobal(Matrix mk) {
        AddToGlobal(mk);
        AutoSaved++;
    }

    public ArrayList<Matrix> GetCompleteList() {
        return createdValues;
    }

    /**
     * Clears all matrices
     */
    public void ClearAllMatrix() {
        createdValues.clear();
        matrixAdapter.notifyDataSetChanged();
        LAST_INDEX = 0;
    }

    // change this to valid matrices
    public boolean CanCreateVariable(){
            return true;
    }

}
