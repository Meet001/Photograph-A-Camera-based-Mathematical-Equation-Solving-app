package com.voidwalkers.photograph.MatrixFragment.base_classes ;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.voidwalkers.photograph.GlobalValues;
import com.voidwalkers.photograph.R;
import com.voidwalkers.photograph.MatrixFragment.base_fragments.ViewMatrixFragment;
import com.voidwalkers.photograph.MatrixFragment.base_fragments.EditFragment ;

/**
 * View the created matrix
 */
public class ViewCreatedMatrix extends AppCompatActivity {

    Menu ActionMenu;
    final int ChangedOrder=12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_created_matrix);

        int index =getIntent().getIntExtra("INDEX",-1);
        if(getSupportActionBar()!=null && index != (-1))
            getSupportActionBar().setTitle(((GlobalValues) getApplication()).GetCompleteList().get(index).GetName());
        ViewMatrixFragment viewMatrixFragment = new ViewMatrixFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("INDEX",getIntent().getIntExtra("INDEX",-1));
        viewMatrixFragment.setArguments(bundle);
        if(savedInstanceState==null){
            FragmentTransaction  transaction= getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.FragmentContainer,viewMatrixFragment,"VIEW_TAG").commit();

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.onclick_view,menu);
        ActionMenu=menu;
        return true;
    }
    @Override
    protected void onActivityResult(int requestcode,int resultCode,Intent data)
    {
        super.onActivityResult(requestcode,resultCode,data);
        if(resultCode==100)
        {
            int index =getIntent().getExtras().getInt("INDEX",-1);
            if(index!=(-1))
            {
                ((GlobalValues)getApplication()).GetCompleteList().get(index).
                        SetName(data.getStringExtra("NEW_NAME_FOR_THIS_MATRIX"));
                ((GlobalValues)getApplication()).matrixAdapter.notifyDataSetChanged();
                finish();
            }


        }
        if(resultCode==ChangedOrder)
        {
            Toast.makeText(getApplication(),R.string.ChangedOrder,Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}