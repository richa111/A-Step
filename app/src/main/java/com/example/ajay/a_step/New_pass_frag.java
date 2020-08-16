package com.example.ajay.a_step;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class New_pass_frag extends Fragment {

    Button done;
    EditText pass,cpass;
    TextView email;
    SharedPreferences preferences;

    public New_pass_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_pass_frag, container, false);
        preferences=getActivity().getSharedPreferences("mypre", Context.MODE_PRIVATE);
        done=(Button)view.findViewById(R.id.btnnewpassdone);
        email=(TextView) view.findViewById(R.id.textViewemail);

        pass = (EditText)view.findViewById(R.id.editxtpassnewpass);
        cpass = (EditText)view.findViewById(R.id.editxtcopassnewpass);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NewPass newPass = new NewPass();

                String st2=preferences.getString("emailid","no data");
                email.setText(st2);

                 if(pass.getText().toString().length()<5 )
                {

                    pass.setError("pass >= 5");
                    return;
                }

                else if(!cpass.getText().toString().equals(pass.getText().toString()))
                {

                    cpass.setError("not match");
                    return;
                }
                 else {
                     ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
                     NetworkInfo ninfo = cm.getActiveNetworkInfo();


                     if (ninfo != null && ninfo.isConnected()) {
                         newPass.execute(email.getText().toString(), pass.getText().toString());
                         Toast.makeText(getActivity(), "Password Succesfully", Toast.LENGTH_LONG).show();
                         startActivity(new Intent(getActivity(), Registraction.class));

                     }
                     else
                     {

                         Toast.makeText(getActivity(), "Check Connectin....", Toast.LENGTH_LONG).show();

                     }
                 }
            }
        });

     return view;
    }

    class NewPass extends AsyncTask<String,Void,String>
    {


        @Override
        protected String doInBackground(String... params) {
            try {

                String emailid =params[0];
                String pass = params[1];


                String muUrlData = "email="+emailid+"&pass="+pass;

                URL url = new URL("http://ajaychoudhary.000webhostapp.com/updatepass.php");
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
                    StringBuffer buffer = new StringBuffer();

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


            return null;
        }
    }

}
