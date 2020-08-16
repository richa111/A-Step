package com.example.ajay.a_step;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Videoupload_Frag extends Fragment implements View.OnClickListener {


    private Button buttonChoose;
    private Button buttonplay;
    private Button buttonUpload;
    private TextView textView;
    private TextView textViewResponse;
    private static final int SELECT_VIDEO = 3;
    private String selectedPath;

    public Videoupload_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videoupload_, container, false);
        buttonChoose = (Button)view.findViewById(R.id.buttonChoose);
        buttonUpload = (Button)view.findViewById(R.id.buttonUpload);
        buttonplay = (Button)view.findViewById(R.id.buttonplay);

        textView = (TextView)view.findViewById(R.id.textView);
        textViewResponse = (TextView)view.findViewById(R.id.textViewResponse);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        buttonplay.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    private void chooseVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);
    }

    private void playVideo()
    {

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        VideoPlay_frag videoPlay_frag = new VideoPlay_frag();
        transaction.replace(R.id.content_homepage, videoPlay_frag);
        //setTitle("Image List");
        //fab.setVisibility(View.INVISIBLE);
        transaction.commit();

        //startActivity(new Intent(getActivity(),VideoPlay.class));
    }

    @Override
    public void onClick(View v) {

        if (v == buttonChoose) {
            chooseVideo();
        }
        if (v == buttonUpload) {
            uploadVideo();
        }
        if (v == buttonplay) {
            playVideo();
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public String getPath(Uri uri)
    {
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getActivity().getApplicationContext().getContentResolver().query(
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;
    }




    private void uploadVideo() {
        class UploadVideo extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(getActivity(), "Uploading File", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
                textViewResponse.setText(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
                textViewResponse.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            protected String doInBackground(Void... params) {
                Upload u = new Upload();
                String msg = u.uploadVideo(selectedPath);
                return msg;
            }
        }
        UploadVideo uv = new UploadVideo();
        uv.execute();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_VIDEO) {
                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
                textView.setText(selectedPath);
            }
        }
    }
}
