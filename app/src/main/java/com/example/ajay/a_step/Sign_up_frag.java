package com.example.ajay.a_step;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static android.R.attr.button;
import static android.R.attr.name;
import static android.R.attr.password;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sign_up_frag extends Fragment {

    EditText uname,emailid,mobile,address,pass,cpass;
    Button submit;
    boolean status=false;

    public Sign_up_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_up_frag, container, false);

          submit = (Button)view.findViewById(R.id.btnsubmitsign);
        uname = (EditText)view.findViewById(R.id.editxtusernamsi);
        emailid = (EditText)view.findViewById(R.id.editxtemailidsign);
        mobile = (EditText)view.findViewById(R.id.editxtmobnosign);
        address = (EditText)view.findViewById(R.id.editxtAddsig);
        pass = (EditText)view.findViewById(R.id.editxtpasssign);
        cpass = (EditText)view.findViewById(R.id.editxtconfpasssig);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                GetInput getInput = new GetInput();
                //Validation for Blank Field
                if(uname.getText().toString().length()==0)
                {
                     uname.setError("Name cannot be Blank");
                    return;
                }
                 if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailid.getText().toString()).matches())
                {

                    emailid.setError("Invalid Email");
                    return;
                }
                 if(mobile.getText().toString().length()<10)
                {


                    mobile.setError("Fill the mobile no");
                    return;
                }

                 if(address.getText().toString().length()==0 )
                {


                    address.setError("Fill the address");
                    return;
                }
                 if(pass.getText().toString().length()<5 )
                {

                    pass.setError("pass >= 5");
                    return;
                }

              else if(!cpass.getText().toString().equals(pass.getText().toString()))
                {

                    cpass.setError("Password not matched");
                    return;
                }
                else {

                    ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);//
                    NetworkInfo ninfo = cm.getActiveNetworkInfo();




                    if(ninfo!=null && ninfo.isConnected())
                    {
                        getInput.execute(uname.getText().toString(), emailid.getText().toString(), mobile.getText().toString(), address.getText().toString(), pass.getText().toString());
                        Toast.makeText(getActivity(), "Recored Succesfully", Toast.LENGTH_LONG).show();

                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        Login_frag login_frag = new Login_frag();
                        transaction.replace(R.id.regfrag, login_frag);
                        transaction.commit();

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Check Connectin..", Toast.LENGTH_LONG).show();
                    }


                }


            }
        });

        return view;
    }

    class GetInput extends AsyncTask<String,Void,String>
    {
        ProgressDialog dialog=new ProgressDialog(getActivity());


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog.setMessage("Registration in Process...");
            this.dialog.show();
            dialog.setCanceledOnTouchOutside(false);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            this.dialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String uname = params[0];
                String emailid = params[1];
                String mobile = params[2];
                String address = params[3];
                String pass = params[4];


                String muUrlData = "uname="+uname+"&emailid="+emailid+"&mobile="+mobile+"&address="+address+"&pass="+pass;

                URL url = new URL("http://ajaychoudhary.000webhostapp.com/insert.php");
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

                    while ((line=reader.readLine())!=null) {

                        buffer.append(line);

                    }
                    Log.d("result is",buffer.toString());

                    if(buffer.toString().equals("success"))
                    {
                        status= true;

                    }
                    else
                    {
                        status=false;
                    }


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
