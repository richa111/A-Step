package com.example.ajay.a_step;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Forgat_pass extends Activity {

    EditText emailvfy;
    SharedPreferences preferences;
    String emailid;
    boolean status=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgat_pass);
        preferences=getSharedPreferences("mypre", Context.MODE_PRIVATE);

        emailvfy = (EditText)findViewById(R.id.editxtforgemail);

    }

    public void email_ok(View view)
    {

        Emailveryfy emailveryfy = new Emailveryfy();

         if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailvfy.getText().toString()).matches())
    {

        emailvfy.setError("Invalid Email");

    }

         else
         {

             ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
             NetworkInfo ninfo = cm.getActiveNetworkInfo();

             emailveryfy.execute(emailvfy.getText().toString());

             new Handler().postDelayed(new Runnable()
             {


                 @Override
                 public void run() {
                     ConnectivityManager cm = (ConnectivityManager) getSystemService(Forgat_pass.CONNECTIVITY_SERVICE);
                     NetworkInfo ninfo = cm.getActiveNetworkInfo();

                     if (ninfo!=null && ninfo.isConnected()) {

                         if (status) {


                             Toast.makeText(Forgat_pass.this, "Sucess...", Toast.LENGTH_LONG).show();
                             preferences.edit().putString("emailid", emailid).commit();
                             FragmentManager manager = getFragmentManager();
                             FragmentTransaction transaction = manager.beginTransaction();
                             New_pass_frag new_pass_frag = new New_pass_frag();
                             transaction.replace(R.id.forgfram, new_pass_frag);
                             transaction.commit();

                         }
                         else
                         {
                             Toast.makeText(Forgat_pass.this, "Invalid Email Id", Toast.LENGTH_LONG).show();
                         }
                     }
                     else
                     {
                         Toast.makeText(Forgat_pass.this, "Check Connectin....", Toast.LENGTH_LONG).show();
                     }

                 }
             },2000);


         }

    }

    class Emailveryfy extends AsyncTask<String,Void,String>
    {

        private final ProgressDialog dialog = new ProgressDialog(Forgat_pass.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog.setMessage("Loading...");
            this.dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected String doInBackground(String... params)
        {
            StringBuffer buffer = new StringBuffer();
            try {
                String emaill = params[0];


                String muUrlData = "emaill="+emaill;

                URL url = new URL("http://ajaychoudhary.000webhostapp.com/emailpass.php");
                HttpURLConnection connection =(HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");

                connection.getOutputStream().write(muUrlData.getBytes());

                int response =connection.getResponseCode();

                Log.d("respose code is ",""+response);

                if(response==HttpURLConnection.HTTP_OK)
                {
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                    BufferedReader reader = new BufferedReader(inputStreamReader);


                    String line;


                    while ((line=reader.readLine())!=null)
                    {
                        buffer.append(line);
                    }
                    Log.d("result is",buffer.toString());



                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            this.dialog.dismiss();
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);

                JSONArray jsonArray = jsonObject.getJSONArray("result");

                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);


                    emailid = object.getString("emailid");

                    Log.d("Id is ",emailid);
                    if(emailid!=null) {
                        status=true;

                        preferences.edit().putString("emailid", emailid).commit();

                    }
                    else
                    {
                        status=false;
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
