package com.example.ajay.a_step;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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
public class Feedback_frg extends Fragment {

   RatingBar bar;
    EditText comnt;
    Button canc,subm;
    TextView fid;
    SharedPreferences preferences;
    public Feedback_frg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_feedback_frg, container, false);

        preferences=getActivity().getSharedPreferences("mypre", Context.MODE_PRIVATE);

        bar=(RatingBar)view.findViewById(R.id.ratingBar);
        comnt=(EditText)view.findViewById(R.id.edittxtfeed);
        canc=(Button)view.findViewById(R.id.buttconclefeed);
        subm=(Button)view.findViewById(R.id.buttsubmitfeed);
        fid = (TextView)view.findViewById(R.id.textviewfeedid);

        String st=preferences.getString("id", "");
        fid.setText(st);



        canc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Home_page.class));
            }
        });
        subm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Feedback feedback = new Feedback();

                String rating=String.valueOf(bar.getRating());
                //Toast.makeText(getActivity(), rating, Toast.LENGTH_LONG).show();

                if(rating.length()==0)
                {
                    Toast.makeText(getActivity(), "Fill Rating", Toast.LENGTH_LONG).show();

                }
               else if(comnt.getText().toString().length()==0)
                {
                    comnt.setError("Fill the comment");
                    return;
                }
                else
                {
                    ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
                    NetworkInfo ninfo = cm.getActiveNetworkInfo();
                    if(ninfo!=null && ninfo.isConnected()) {
                        Toast.makeText(getActivity(), "Feedback Succesfully", Toast.LENGTH_LONG).show();
                        feedback.execute(fid.getText().toString(), rating.toString(), comnt.getText().toString());
                        Intent intent = new Intent(getActivity(), Home_page.class);

                        startActivity(intent);
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

    class Feedback extends AsyncTask<String,Void,String>
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
        protected String doInBackground(String... params) {
            try {

                String id =params[0];
                String bar = params[1];
                String comnt = params[2];


                String muUrlData = "fid="+id+"&fbar="+bar+"&fcomnt="+comnt;

                URL url = new URL("http://ajaychoudhary.000webhostapp.com/feedback.php");
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