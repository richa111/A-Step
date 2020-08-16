package com.example.ajay.a_step;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Home_page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final int CAMERA_PIC_REQUEST = 1337;
    SharedPreferences preferences;
    FloatingActionButton fab;
    TextView txtheader,txtemail;
    private int PICK_IMAGE_REQUEST = 1;
    ImageView profile_pic;

   // private ShareActionProvider mShareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        preferences=getSharedPreferences("mypre", Context.MODE_PRIVATE);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

            }
        });

      /*  FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Home_frag home_frag = new Home_frag();
        transaction.replace(R.id.content_homepage, home_frag);
        setTitle("A-STEP");
        transaction.commit();*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        txtheader = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtnameheader);
        txtemail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtemailhader);
        String st1=preferences.getString("uname", "no data");
        txtheader.setText(st1);
        String st2=preferences.getString("emailid","no data");
        txtemail.setText(st2);

        profile_pic = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.txtpicheader);

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);



            }
        });


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST&& resultCode == RESULT_OK && data != null && data.getData() != null) {
            Bitmap bitmap;
             Uri filePath;
            filePath = data.getData();
            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_pic.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Bitmap bitmap;
            Uri filePath;
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_pic.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            /*super.onBackPressed();
            *///startActivity(new Intent(this,Home_page.class));
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
           // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
           // finish();
            return true;
        }
        if (id == R.id.logout) {

            ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo ninfo = cm.getActiveNetworkInfo();

            if(ninfo!=null && ninfo.isConnected()) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("uname").commit();
                editor.remove("pass").commit();

                Intent intent = new Intent(Home_page.this, Registraction.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();

            }
            else
            {

                Toast.makeText(this, "Please check your internet connection ", Toast.LENGTH_SHORT).show();




            }


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.nav_home){
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Home_frag home_frag = new Home_frag();
            transaction.replace(R.id.content_homepage, home_frag);
            setTitle("A-STEP");
            transaction.commit();

        }

       else if (id == R.id.nav_image) {

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Image_frag image_frag = new Image_frag();
            transaction.replace(R.id.content_homepage, image_frag);
            setTitle("Image Upload");
            fab.setVisibility(View.INVISIBLE);
            transaction.commit();



        } else if (id == R.id.nav_video) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Videoupload_Frag videoupload_frag= new Videoupload_Frag();
            transaction.replace(R.id.content_homepage, videoupload_frag);
            setTitle("Video Upload");
            fab.setVisibility(View.INVISIBLE);
            transaction.commit();




        }  else if (id == R.id.nav_Services)
        {
          startActivity(new Intent(Home_page.this,Services.class));
        }
        else if (id == R.id.nav_Account) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Account_frg account_frg = new Account_frg();
            transaction.replace(R.id.content_homepage, account_frg);
            setTitle("Account");
            fab.setVisibility(View.INVISIBLE);
            transaction.commit();

        }
        else if (id == R.id.nav_share) {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBodyText = "Our Application in testing Process checkout from the following link:https://drive.google.com/file/d/0BxXCRbeoNxgsWFI0Tm5RUERlN2c/view?usp=sharing";
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(intent, "Choose sharing method"));

        }

        else if (id == R.id.nav_Feedback) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Feedback_frg feedback_frg = new Feedback_frg();
            transaction.replace(R.id.content_homepage, feedback_frg);
            setTitle("Feedback");
            fab.setVisibility(View.INVISIBLE);
            transaction.commit();

        }
        else if (id == R.id.nav_Contactus) {

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Contact_frg contact_frg = new Contact_frg();
          transaction.replace(R.id.content_homepage, contact_frg);
            setTitle("Contact");
            fab.setVisibility(View.INVISIBLE);
            transaction.commit();

        }
        else if (id == R.id.nav_Aboutus) {

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            About_us_frg about_us_frg = new About_us_frg();
            transaction.replace(R.id.content_homepage, about_us_frg);
            setTitle("About App");
            fab.setVisibility(View.INVISIBLE);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
