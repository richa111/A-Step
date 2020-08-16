package com.example.ajay.a_step;



import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Ngo_frag extends android.support.v4.app.Fragment {

    TextView txtudaan,txtsurman,txtpryatan,txtbodh,txtseva;

    public Ngo_frag() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_ngo_frag, container, false);

        txtudaan=(TextView) view.findViewById(R.id.txtudaan);

        txtsurman=(TextView) view.findViewById(R.id.txtsurman);
        txtpryatan=(TextView) view.findViewById(R.id.txtpryatan);
        txtbodh=(TextView) view.findViewById(R.id.txtbodh);
        txtseva=(TextView) view.findViewById(R.id.txtseva);

        txtudaan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9785729987"));
                startActivity(callIntent);

            }

        });

        txtsurman.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9828031708"));
                startActivity(callIntent);

            }

        });

        txtpryatan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9414028004"));
                startActivity(callIntent);

            }

        });

        txtbodh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9784000821"));
                startActivity(callIntent);

            }

        });

        txtseva.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9252560000"));
                startActivity(callIntent);

            }

        });

        return view;
    }

}
