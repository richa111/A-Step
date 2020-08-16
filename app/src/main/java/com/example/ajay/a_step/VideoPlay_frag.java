package com.example.ajay.a_step;


import android.app.Fragment;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoPlay_frag extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    videoModel vm;
    RequestQueue requestQueue;
    String url="http://ajaychoudhary.000webhostapp.com/getvideo.php";

    ArrayList<videoModel> vlist=new ArrayList<>();


    public VideoPlay_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_play_frag, container, false);
        recyclerView= (RecyclerView)view.findViewById(R.id.rview);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        requestQueue= Volley.newRequestQueue(getActivity());
        getVideoList();
        // Inflate the layout for this fragment
        return view;
    }

    public void getVideoList(){
        final JsonObjectRequest js=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObj=new JSONObject(response.toString());
                    JSONArray jarray=jsonObj.getJSONArray("titlelink");
                    for(int i=0;i<jarray.length();i++){
                        vm=new videoModel();
                        JSONObject jObj= jarray.getJSONObject(i);
                        vm.setTitle(jObj.getString("title"));
                        vm.setLink(jObj.getString("link"));
                        vlist.add(vm);
                        adapter=new videoAdapter(getActivity(),vlist);
                        recyclerView.setAdapter(adapter);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(js);
    }


}

