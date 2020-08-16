package com.example.ajay.a_step;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Poolice_frag extends android.support.v4.app.Fragment {

  TextView callpolic;
    public Poolice_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_poolice_frag, container, false);

        callpolic=(TextView) view.findViewById(R.id.txtpoloccall);

        callpolic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0141-2362100"));
                startActivity(callIntent);

            }

        });
        // Inflate the layout for this fragment
        return view;
    }

}
