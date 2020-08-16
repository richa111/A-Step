package com.example.ajay.a_step;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registraction extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraction);
        FragmentManager manager  = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Sign_up_frag sign_up_frag = new Sign_up_frag();
        transaction.replace(R.id.regfrag,sign_up_frag);
        transaction.commit();

    }

    public void log_In(View view)
    {
        FragmentManager manager  = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Login_frag login_frag = new Login_frag();
        transaction.replace(R.id.regfrag,login_frag);
        transaction.commit();
    }

    public void sign_Up(View view)
    {
        FragmentManager manager  = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Sign_up_frag sign_up_frag = new Sign_up_frag();
        transaction.replace(R.id.regfrag,sign_up_frag);
        transaction.commit();
    }
}
