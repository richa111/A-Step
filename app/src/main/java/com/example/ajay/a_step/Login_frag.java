package com.example.ajay.a_step;


import android.app.Dialog;
import android.app.Fragment;
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
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Login_frag extends Fragment {

Button done,forg;
    EditText uname,pass;
    boolean status=false;
    String id,name,emailid,mobile,address,passw;
    CheckBox cb;
    SharedPreferences preferences;
    Context context;
    ProgressDialog progressDialog;
    public Login_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_login_frag, container, false);
     preferences=getActivity().getSharedPreferences("mypre", Context.MODE_PRIVATE);

        done=(Button)view.findViewById(R.id.btndone);
        forg=(Button)view.findViewById(R.id.btnforgpasslogin);


        uname = (EditText)view.findViewById(R.id.editxtusernmlogin);
        pass = (EditText)view.findViewById(R.id.editxtpasslogin);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LoggIn log =new LoggIn();

                if(uname.getText().toString().length()==0)
                {
                    uname.setError("Name cannot be Blank");

                }
                else if(pass.getText().toString().length()<5 )
                {

                    pass.setError("pass >= 5");
                }

                else
                {

                    ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
                    NetworkInfo ninfo = cm.getActiveNetworkInfo();
                    log.execute(uname.getText().toString(),pass.getText().toString());


                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run() {
                            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
                            NetworkInfo ninfo = cm.getActiveNetworkInfo();

                            if (ninfo!=null && ninfo.isConnected()) {
                                if (status) {
                                    preferences.edit().putString("name", name).commit();
                                    Toast.makeText(getActivity(), "Sucess", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getActivity(), Home_page.class));

                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Donot Match User Name & Password.....", Toast.LENGTH_LONG).show();
                                }
                            }

                            else
                            {

                                        Toast.makeText(getActivity(), "Check Connectin....", Toast.LENGTH_LONG).show();

                            }

                        }
                    },2000);

                }

            }
        });




        forg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          startActivity(new Intent(getActivity(),Forgat_pass.class));

            }
        });


        return view;
    }
    public void checkbox(View view)
    {
        if (cb.isChecked()) {
            pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

    }



    class LoggIn extends AsyncTask<String,Void,String>
    {

        private final ProgressDialog dialog = new ProgressDialog(getActivity());

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
                String uname = params[0];
                String pass = params[1];

                String muUrlData = "uname="+uname+"&pass="+pass;

                URL url = new URL("http://ajaychoudhary.000webhostapp.com/login.php");
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

                    id = object.getString("id");
                    name = object.getString("uname");
                    emailid = object.getString("emailid");
                    mobile = object.getString("mobile");
                    address = object.getString("address");
                    passw = object.getString("pass");
                    Log.d("Id is ",id);
                    if(id!=null) {
                        status=true;
                        preferences.edit().putString("id", id).commit();
                        preferences.edit().putString("uname", name).commit();
                        preferences.edit().putString("emailid", emailid).commit();
                        preferences.edit().putString("mobile", mobile).commit();
                        preferences.edit().putString("address", address).commit();
                        preferences.edit().putString("pass", passw).commit();
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
