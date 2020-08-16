package com.example.ajay.a_step;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_frag extends Fragment {


    public Home_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
  View view =  inflater.inflate(R.layout.fragment_home_frag, container, false);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ImageList_frag imageList_frag = new ImageList_frag();
        transaction.replace(R.id.content_homepage, imageList_frag);
        //setTitle("Image List");
        //fab.setVisibility(View.INVISIBLE);
        transaction.commit();
        // Inflate the layout for this fragment
        return view;
    }

}
