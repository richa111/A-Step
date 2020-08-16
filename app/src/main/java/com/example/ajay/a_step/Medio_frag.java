package com.example.ajay.a_step;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Medio_frag extends android.support.v4.app.Fragment {

    TextView zeenews,toi,dainik,aajtak,patrika;

    public Medio_frag() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.fragment_medio_frag, container, false);
        zeenews=(TextView) view.findViewById(R.id.zeenews);

        zeenews.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0141-2743866"));
                startActivity(callIntent);

            }

        });

        toi=(TextView) view.findViewById(R.id.toi);

        toi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0141-5191328"));
                startActivity(callIntent);

            }

        });

        patrika=(TextView) view.findViewById(R.id.patrika);

        patrika.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0141-39404142"));
                startActivity(callIntent);

            }

        });

        dainik=(TextView) view.findViewById(R.id.dainik);

        dainik.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0141-2711356"));
                startActivity(callIntent);

            }

        });

        aajtak=(TextView) view.findViewById(R.id.aajtak);

        aajtak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:141-2518804"));
                startActivity(callIntent);

            }

        });
        // Inflate the layout for this fragment
        return view;
    }

}
