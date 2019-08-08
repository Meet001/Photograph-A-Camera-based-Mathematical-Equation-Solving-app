package com.voidwalkers.photograph;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.voidwalkers.photograph.AlgebraFragment.Quadratic;
import com.voidwalkers.photograph.MatrixFragment.Matrix;
import com.voidwalkers.photograph.MatrixFragment.MatrixAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores standard templates of linear class in hashmap corresponding to their function call name
 */
public class GlobalMemory {

    // public static HashMap<String, String> QuadraticData;
    public static HashMap<String, String> LinearData;

    static {
        LinearData = new HashMap<String, String>() ;
        LinearData.put("Vx+Vy+V=V","Linear1") ;
        LinearData.put("Vy+Vx+V=V","Linear2") ;
        LinearData.put("Vx+Vy=V","Linear3");
        LinearData.put("Vy+Vx=V","Linear4");
    }
}