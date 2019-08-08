package com.voidwalkers.photograph.MatrixFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.voidwalkers.photograph.GlobalMemory;
import com.voidwalkers.photograph.GlobalValues;

import org.apache.commons.math3.linear.* ;
/**
 * Matrix Class
 */

public class Matrix {

    private int NumberofRows, NumberofCols;

    private String name;

    private float Elements[][]=new float[9][9];

    /**
     * Constructor
     * @param r
     * @param c
     */
    public  Matrix(int r,int c) {
        this.NumberofRows=r;
        this.NumberofCols=c;
        this.name="New Normal 3";
    }

    /**
     * Constructor
     * @param p
     */
    public Matrix(int p) {
        NumberofRows=p;
        NumberofCols=p;
        name="New Square 1";

    }

    /**
     * Constructor
     */
    public Matrix() {
        NumberofRows=9;
        NumberofCols=9;
        name="New Matrix";
    }

    /**
     * Return number of rows of the matrix
     * @return
     */
    public int GetRow()
    {
        return NumberofRows;
    }

    /**
     * Return number of columns of the matrix
     * @return
     */
    public int GetCol()
    {
        return NumberofCols;
    }

    /**
     * Set number of rows to r
     * @param r
     */
    public void SetRow(int r)
    {
        NumberofRows=r;
    }

    /**
     * Set number of columns to c
     * @param c
     */
    public void SetCol(int c)
    {
        NumberofCols=c;
    }

    /**
     *
     * @return true if it is a square matrix
     */
    public boolean is_squareMatrix()
    {
        return (NumberofCols==NumberofRows);
    }

    /**
     * Getter for name of the matrix
     * @return name
     */
    public String GetName()
    {
        return this.name;
    }

    /**
     * Setter for name of the matrix
     * @param name
     */
    public void SetName(String name)
    {
        this.name=name;
    }

    /**
     * Add this matrix to m
     * sets this[i][j] = this[i][j] + m[i][j]
     * if not same order does nothing
     * @param m
     */
    public void AddtoThis(Matrix m) {
        if(isSameOrder(m)) {
            for (int i = 0; i < m.GetRow(); i++)
                for (int j = 0; j < m.GetCol(); j++)
                    this.Elements[i][j] = this.Elements[i][j] + m.Elements[i][j];
        }
    }

    /**
     *Subtracts m from this
     * sets this[i][j] = this[i][j] - m[i][j] ;
     * @param m
     */
    public void SubtoThis(Matrix m) {
        if(isSameOrder(m)) {
            for (int i = 0; i < m.GetRow(); i++)
                for (int j = 0; j < m.GetCol(); j++)
                    this.Elements[i][j] = this.Elements[i][j] - m.Elements[i][j];
        }
    }

    /**
     * Returns true of both have same number of rows and columns
     * @param a
     * @return
     */
    public boolean isSameOrder(Matrix a) {
        return (a.GetRow()==this.GetRow()&&a.GetCol()==this.GetCol());
    }

    /**
     * initialises values of p to values of this
     * if not same order does nothing
     * @param p
     */
    private void CopyThisto(Matrix p) {
        if(isSameOrder(p))
        {
            p.Elements = this.Elements.clone();
        }

    }

    /**
     * initialises elements of this as elements of p
     * if order is different, then does nothing
     * @param p
     */
    private void CopyFrom(Matrix p) {
        if(isSameOrder(p))
        {
            this.Elements = p.Elements.clone();
        }
    }

    /**
     * Gets Trace of the matrix
     * @return
     * @throws IllegalStateException
     */
    public float GetTrace() throws IllegalStateException{
        float trace=0;
        if(!this.is_squareMatrix())
            throw new IllegalStateException("Matrix must be Square");
        else {
            for(int i=0;i<this.GetRow();i++)
                for(int j=0;j<this.GetCol();j++)
                    if(i==j)
                        trace+=this.GetElementof(i,j);
            return trace;
        }
    }

    /**
     * Returns a matrix transpose of the matrix
     * @return
     */
    public Matrix Transpose() {
        Matrix p = new Matrix(this.GetCol(),this.GetRow());
        for(int i=0;i<this.GetRow();i++)
            for (int j=0;j<this.GetCol();j++)
                p.Elements[j][i]=this.Elements[i][j];
        return p;
    }

    /**
     * Initialises this as transpose of this
     */
    public void SquareTranspose() {
        Matrix p = new Matrix(this.GetCol());
        for(int i=0;i<this.GetRow();i++)
            for (int j=0;j<this.GetCol();j++)
                p.Elements[j][i]=this.Elements[i][j];
        this.CopyFrom(p);
    }

    /**
     * swaps elements of h and this
     * @param h
     */
    public void SwapWith(Matrix h) {
        if(isSameOrder(h))
        {
            Matrix buffer = new Matrix(this.GetRow(),this.GetCol());
            buffer.Elements = this.Elements.clone();
            this.Elements = h.Elements.clone();
            h.Elements =buffer.Elements.clone();

        }

    }

    /**
     * Returns true if the matrices are multipliable
     * @param h
     * @return
     */
    private boolean AreMultipliabe(Matrix h)
    {
        return this.GetCol()==h.GetRow();
    }


    /**
     * Multiply this with j
     * Checks if they are multipliable
     * performs standard O(n^3) algorithm
     * @param j
     * @return product of the matrices
     * @throws Exception
     */
    private Matrix MultipyWith(Matrix j) throws Exception{
        if(AreMultipliabe(j))
        {
            Matrix m= new Matrix(this.GetRow(),j.GetCol());
            for(int i=0;i<this.GetRow();i++)
                for(int js=0;js<m.GetCol();js++)
                {
                    m.Elements[i][js]=0;
                    for(int k=0;k<this.GetCol();k++)
                    {
                        m.Elements[i][js]+=this.Elements[i][k]*j.Elements[k][js];
                    }
                }

            return m;
        }
        else {
            throw new Exception("Matrix Could not be multiplied still called MultiplyMethod");
        }

    }


    /**
     *  Multiply two matrices
     * @param m
     * @throws ExceptionInInitializerError
     */
    public void MultiplytoThis(Matrix m) throws ExceptionInInitializerError{
        if (this.AreMultipliabe(m)) {
            Matrix mh = new Matrix(this.GetRow(), m.GetCol());
            for (int i = 0; i < this.GetRow(); i++)
                for (int js = 0; js < m.GetCol(); js++) {
                    mh.Elements[i][js] = 0;
                    for (int k = 0; k < this.GetCol(); k++) {
                        mh.Elements[i][js] += this.Elements[i][k] * m.Elements[k][js];
                    }
                }
            this.CloneFrom(mh);
        }
        else{
            throw new ExceptionInInitializerError();
        }
    }

    /**
     * setvalue of element i,j
     * @param row
     * @param column
     * @param element
     */
    private void setValue(int row,int column,float element)
    {
        this.Elements[row][column]=element;
    }

    /**
     *
     * @return Determinant of the matrix
     */
    public float GetDeterminant() {
        float  Result=0;
        int flag=0,a=0,b=0;
        int Order =this.GetRow();
        if(Order==1)
        {
            Result= this.Elements[0][0];
            return Result;
        }
        if(Order==2)
        {
            float l=this.Elements[0][0]*this.Elements[1][1];
            float m=this.Elements[1][0]*this.Elements[0][1];
            Result=l-m;
            return Result;

        }
        else
        {
            for(;flag<Order;flag++)
            {
                Matrix pointer= new Matrix(Order-1);
                for(int i=1;i<Order;i++)
                {
                    for(int j=0;j< Order;j++)
                    {
                        if(flag!=j)
                        {
                            float pg=this.Elements[i][j];
                            pointer.setValue(a,b,pg);
                            b++;
                        }
                    }
                    a++;
                    b=0;

                }
                a=0;
                b=0;
                float z=pointer.GetDeterminant();
                Result +=Math.pow(-1,flag)*(this.Elements[0][flag]*z);
            }
        }

        return Result;
    }

    /**
     * make adjoint matrix
     */
    private void MakeAdjoint() {
        int Order=this.GetCol();
        Matrix base= new Matrix(Order);
        int flag,a=0,b=0;
        if(Order==2)
        {
            float x= this.Elements[0][0];
            this.Elements[0][0]=this.Elements[1][1];
            this.Elements[1][1]=x;
            this.Elements[1][0]*=(-1);
            this.Elements[0][1]*=(-1);
            x=this.Elements[1][0];
            this.Elements[1][0]=this.Elements[0][1];
            this.Elements[0][1]=x;
            this.SquareTranspose();
        }
        else
        {
            for(int k=0;k<Order;k++)
            {
                for(flag=0;flag<Order;flag++)
                {
                    Matrix pointer=new Matrix(Order-1);
                    for(int i=0;i<Order;i++)
                    {
                        for (int j = 0; j < Order; j++)
                        {
                            if ((flag != j) && (k != i))
                            {
                                float pg = this.Elements[i][j];
                                pointer.setValue(a, b, pg);
                                b++;
                            }
                        }
                        if (k != i)
                            a++;
                        b = 0;
                    }
                    float z=(float) pointer.GetDeterminant();
                    int variable= k+flag;
                    base.Elements[k][flag]= (float) Math.pow((-1),variable)*z;
                    a=0;
                    b=0;
                }
            }
            this.CopyFrom(base);
            this.SquareTranspose();
        }

    }

    /**
     * Returns adjoint of matrix
     * @return
     */
    public Matrix ReturnAdjoint() {
        Matrix Result= new Matrix(this.GetRow());
        this.CopyThisto(Result);
        Result.MakeAdjoint();
        return  Result;
    }

    /**
     * Calculates inverse from adjoint
     * @return
     */
    public Matrix Inverse(){
        float determinant= (float) this.GetDeterminant();
        if(determinant==0) //if determinant was to be zero
            return null;
        else {
            Matrix Result = this.ReturnAdjoint();
            for (int i = 0; i < this.GetRow(); i++)
                for (int j = 0; j < this.GetCol(); j++)
                    Result.Elements[i][j] /= determinant;
            return Result;
        }
    }

    /**
     * Makes matrix identity matrix
     */
    public void MakeIdentity(){
        for (int i = 0 ; i < this.GetRow() ; i++){
            for (int j = 0 ; j < this.GetCol() ; j++){
                if (i==j) this.SetElementof(1,i,j);
                else this.SetElementof(0,i,j);
            }
        }
    }

    /**
     * Exponent of matrix
     * @param a
     */
    public void Raiseto(int a){
        Matrix pi= new Matrix(this.GetRow());
        pi.MakeIdentity();
        if(a==0)
            this.MakeIdentity();
        else
        {
            try {
                for (int i = 0; i < a; i++) {
                    assert pi != null;
                    pi = pi.MultipyWith(this);
                }

            }catch (Exception e){
                Log.d("RaiseToError :","Non Square Matrix called the function raiseto()");
                e.printStackTrace();
                return;
            }
            this.CopyFrom(pi);
        }

    }

    /**
     * bundle the data for intent
     * @return
     */
    public Bundle GetDataBundled() {
        Bundle AllInfo= new Bundle();
        AllInfo.putInt("ROW",this.GetRow());
        AllInfo.putInt("COL",this.GetCol());
        AllInfo.putString("NAME",name);
        AllInfo.putFloatArray("VALUES",Compress(this.Elements,this.GetRow(),this.GetCol()));
        return AllInfo;
    }

    /**
     * Get element array
     * @param elements
     * @param row
     * @param col
     * @return
     */
    public float[] Compress(float[][] elements,int row, int col) {
        float resultant[] = new float[row*col];
        int a=0;
        for(int i=0;i<row;i++) {
            for (int j = 0; j < col; j++)
                resultant[a++] = elements[i][j];
        }
        return resultant;
    }

    /**
     * Take from bundle
     * @param bundle
     * @throws ClassCastException
     */
    public void SetFromBundle(Bundle bundle) throws ClassCastException {
        this.name=bundle.getString("NAME");
        this.SetRow(bundle.getInt("ROW"));
        this.SetCol(bundle.getInt("COL"));
        this.Elements=Expand(bundle.getInt("ROW"),bundle.getInt("COL"),bundle.getFloatArray("VALUES"));
    }

    /**
     *
     * @param row
     * @param col
     * @param values
     * @return
     */
    public float[][] Expand(int row, int col, float[] values) {
        float Values[][] =  new float[9][9];
        int a=0;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++)
                Values[i][j] = values[a++];
        }
        return Values;
    }

    /**
     *
     * @return true if matrix is null
     */
    public boolean isNull() {
        for(int i=0;i<this.GetRow();i++)
        {
            for(int j=0;j<this.GetCol();j++)
                if(this.Elements[i][j]!=0)
                    return false;
        }
        return true;
    }


    public float GetElementof(int r,int c) throws ExceptionInInitializerError{
        if(this.GetRow()<r || this.GetCol() < c || r < 0 || c < 0)
            throw new ExceptionInInitializerError();
        else
            return this.Elements[r][c];
    }

    /**
     * Set element of matrix
     * @param val
     * @param r
     * @param c
     * @throws ExceptionInInitializerError
     */
    public void SetElementof(float val,int r,int c) throws ExceptionInInitializerError{
        if(this.GetCol()<c || this.GetRow()<r || c < 0 || r < 0 )
            throw new ExceptionInInitializerError();
        else
            this.Elements[r][c]=val;
    }

    /**
     * Copy
     * @param p
     */
    public void CloneFrom(Matrix p) {
        this.SetCol(p.GetCol());
        this.SetRow(p.GetRow());
        this.CopyFrom(p);
        this.SetName(p.GetName());
    }

    /**
     * Conversion to String
     * @return
     */
    @Override
    public String toString(){
        String s = "--->";
        for(int i=0;i<this.GetRow();i++) {
            for (int j = 0; j < this.GetCol(); j++)
                s += String.valueOf(this.GetElementof(i, j)) + " : ";
            s += "->";
        }
        return s;
    }

    /**
     * Return EigenValues of a square matrix
     * @return
     */
    public String GetEigenValues() {
        double[][] m = new double[this.GetRow()][this.GetCol()] ;

        for (int i = 0 ; i < this.GetRow() ; i++){
            for (int j =0 ; j < this.GetCol() ; j++){
                m[i][j] = (double) Elements[i][j] ;
            }
        }

        RealMatrix m1 = MatrixUtils.createRealMatrix(m);

        EigenDecomposition evd = new EigenDecomposition(m1);

        double[] rev = evd.getRealEigenvalues();

        String ans = String.valueOf(rev[0]) ;

        for (int i = 1 ; i < rev.length ; i++){
            ans = ans + "," + rev[i] ;
        }

        return ans ;
    }

    /**
     * Return EigenVectors of the matrix
     */
    public Matrix GetEigenVector(){
        Matrix Result= new Matrix(this.GetRow());

        double[][] m = new double[this.GetRow()][this.GetCol()] ;

        for (int i = 0 ; i < this.GetRow() ; i++){
            for (int j =0 ; j < this.GetCol() ; j++){
                m[i][j] = (double) Elements[i][j] ;
            }
        }

        RealMatrix m1 = MatrixUtils.createRealMatrix(m);
        EigenDecomposition evd = new EigenDecomposition(m1);

        for (int i = 0 ; i < this.GetCol() ; i++){
            m[i] = evd.getEigenvector(i).toArray();
        }

        Matrix ans = new Matrix(this.GetRow(),this.GetCol()) ;

        for (int i = 0 ; i < this.GetRow() ; i++){
            for (int j = 0 ; j < this.GetCol() ; j++){
                ans.SetElementof((float)m[i][j], i, j);
            }
        }
        ans.SetName("EigenVector") ;
        return ans ;
    }

}
