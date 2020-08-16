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
public class Account_frg extends Fragment {

    EditText auname,aemailid,amobile,aaddress,apass;
    Button acedit;
    TextView aid;
    SharedPreferences preferences;

    public Account_frg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_account_frg, container, false);
        preferences=getActivity().getSharedPreferences("mypre", Context.MODE_PRIVATE);
        aid = (TextView)view.findViewById(R.id.accid);
        acedit = (Button)view.findViewById(R.id.accountedit);
        auname = (EditText)view.findViewById(R.id.accountname);
        aemailid = (EditText)view.findViewById(R.id.accountemail);
        amobile = (EditText)view.findViewById(R.id.accountmob);
        aaddress = (EditText)view.findViewById(R.id.accountaddres);
        apass = (EditText)view.findViewById(R.id.accountpass);

        String st=preferences.getString("id", "");
        aid.setText(st);
        String st1=preferences.getString("uname", "no data");
        auname.setText(st1);
        String st2=preferences.getString("emailid","no data");
        aemailid.setText(st2);

        String st3=preferences.getString("mobile","no data");
        amobile.setText(st3);
        String st4=preferences.getString("address", "no data");
        aaddress.setText(st4);
        String st5=preferences.getString("pass", "no data");
        apass.setText(st5);


        acedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               AcEdit acEdit = new AcEdit();



                //Validation for Blank Field
                if(auname.getText().toString().length()==0)
                {
                    auname.setError("Name cannot be Blank");
                    return;
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(aemailid.getText().toString()).matches())
                {

                    aemailid.setError("Invalid Email");
                    return;
                }
                else if(amobile.getText().toString().length()<10)
                {


                    amobile.setError("Invalid mobile no");
                    return;
                }

                else if(aaddress.getText().toString().length()==0 )
                {


                    aaddress.setError("Please enter your address");
                    return;
                }
                else if(apass.getText().toString().length()<5 )
                {

                    apass.setError("password >= 5");
                    return;
                }


                else
                {
                    ConnectivityManager cm = (ConnectivityManager)  getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
                    NetworkInfo ninfo = cm.getActiveNetworkInfo();

                    if(ninfo!=null && ninfo.isConnected()) {
                        Toast.makeText(getActivity(), "Edit Details Succesfully", Toast.LENGTH_SHORT).show();
                        acEdit.execute(aid.getText().toString(), auname.getText().toString(), aemailid.getText().toString(), amobile.getText().toString(), aaddress.getText().toString(), apass.getText().toString());
                        startActivity(new Intent(getActivity(), Home_page.class));

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Check Connectin..", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });



       // return inflater.inflate(R.layout.fragment_account_frg, container, false);
        return view;

    }

    class AcEdit extends AsyncTask<String,Void,String>
    {


        @Override
        protected String doInBackground(String... params) {
            try {
                String id=params[0];
                String uname = params[1];
                String emailid = params[2];
                String mobile = params[3];
                String address = params[4];
                String pass = params[5];


                String muUrlData = "id="+id+"&uname="+uname+"&emailid="+emailid+"&mobile="+mobile+"&address="+address+"&pass="+pass;

                URL url = new URL("http://ajaychoudhary.000webhostapp.com/acedit.php");
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
