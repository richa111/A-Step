package com.example.ajay.a_step;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User_Veryfi extends Activity {

     EditText emailvfy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__veryfi);
    }

    public void nextveryfi(View view)
    {
        emailvfy = (EditText) findViewById(R.id.editTxemailveryf);
        final String email = emailvfy.getText().toString();


        if (!isValidEmail(email)) {
            emailvfy.setError("Invalid Email");
        }
        else
        {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Otp_frag otp_frag = new Otp_frag();
            transaction.replace(R.id.userfrag, otp_frag);
            transaction.commit();
        }
    }

    private boolean isValidEmail(String email)
    {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }
}
