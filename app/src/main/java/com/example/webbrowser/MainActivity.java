package com.example.webbrowser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Controller controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new Controller(this);

    }



    public void onBackPressed(){
        Log.d("ONBACKPRESSED","Calling controller on back");
        controller.onBackPress();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Log.d("MAKEMENU", "create option menu");
        controller.createMenu(menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean returnValue = controller.onOptionsItemSelected(item);

        if(!returnValue){
            return super.onOptionsItemSelected(item);
              }
        else{
            return returnValue;
             }
        }
    }