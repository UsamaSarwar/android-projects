package com.example.aamir.doctor.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.Classes.CustomTypefaceSpan;
import com.example.aamir.doctor.Database.DbHelper;
import com.example.aamir.doctor.GridMainList.DrCategoryAdapter;
import com.example.aamir.doctor.R;

import java.security.KeyRep;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.recyclerView)
    RecyclerView catListRecyclerView;
    @BindView(R.id.search_dr)
    LinearLayout searchAnyDoctor;
    @BindView(R.id.select_city)
    public Button selectCity;
    @BindView(R.id.linearLayout_wellcom)
    LinearLayout welCome;
    @BindView(R.id.textView_welcome)
    TextView wellCome;
    @BindView(R.id.button_see_lebortary)
    Button searchLab;


    @BindView(R.id.toolBar)
    Toolbar toolbar;


    Typeface typefaceRegular;
    Typeface typefaceBold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        typefaceRegular = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf");
        typefaceBold = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");


        SharedPreferences sp = getSharedPreferences("City",MODE_PRIVATE);
        if(sp.getInt("city_id",0)==-1){

            startActivity(new Intent(MainActivity.this,ChooseLocation.class));
        }





        setSupportActionBar(toolbar);

        //hide the default toolbar tilte
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //add custom toolbar tilte
        TextView toolBarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolBarTitle.setTypeface(typefaceBold);

    // navigation drawer view
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set custom font to Drawer view items
        Menu menu = navigationView.getMenu();
        for (int i=0; i<menu.size(); i++){

            MenuItem MenuItem = menu.getItem(i);
            applyFontToMenuItem(MenuItem);

        }



        setListAdapter();

        initListner();

    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();



        if (id == R.id.change_location_drawer) {
            startActivity(new Intent(MainActivity.this,ChooseLocation.class));
            // Handle the camera action
        } else if (id == R.id.share_drawer) {

           shareIntent();


        } else if (id == R.id.contact_us_drawer) {
            startActivity(new Intent(MainActivity.this,ContactUs.class));

        } else if (id == R.id.about_us_drawer){

            startActivity(new Intent(MainActivity.this,AboutUs.class));

        }else if(id == R.id.blog_drawer){
            startActivity(new Intent(MainActivity.this,Blog.class));
        }
        else if(id == R.id.lab_drawer){

            SharedPreferences sp = getSharedPreferences("City",MODE_PRIVATE);
            Intent intent = new Intent(MainActivity.this,LabPage.class);
            intent.putExtra("city_id",sp.getInt("city_id",0));
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void shareIntent() {

        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String sAux = "\nLet me recommend you this application\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=com.DigitalDroid.Tabeeb \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }


    private void initListner() {

        selectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(MainActivity.this,ChooseLocation.class));
            }
        });

        searchLab.setTypeface(typefaceRegular);
       /* searchLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("City",MODE_PRIVATE);
                cityName = sp.getString("city_name","");
                if(cityName.equals("Sahiwal") || cityName.equals("sahiwal")){
                    Intent intent = new Intent(MainActivity.this,LabPage.class);
                    intent.putExtra("city_id",sp.getInt("city_id",0));
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "This feature is currently not available in your city", Toast.LENGTH_SHORT).show();
                }

            }
        });*/

       searchLab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SharedPreferences sp = getSharedPreferences("City",MODE_PRIVATE);
               Intent intent =  new Intent(MainActivity.this,HospitalList.class);
               intent.putExtra("city_id",sp.getInt("city_id",0));
               startActivity(intent);
           }
       });
    }

    private void setListAdapter() {



        DbHelper dbHelper = new DbHelper(MainActivity.this);
        DrCategoryAdapter drCategoryAdapter = new DrCategoryAdapter(dbHelper.allCategories(),MainActivity.this);
        dbHelper.close();
        catListRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);
        catListRecyclerView.setLayoutManager(layoutManager);
        catListRecyclerView.setAdapter(drCategoryAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences sp = getSharedPreferences("City",MODE_PRIVATE);
        SharedPreferences sp1 = getSharedPreferences("User",MODE_PRIVATE);
        selectCity.setText(sp.getString("city_name",""));
        selectCity.setTypeface(typefaceRegular);

        if(sp1.getString("name","").equals("e")){
            welCome.setVisibility(View.GONE);
        }else {
            welCome.setVisibility(View.VISIBLE);
            wellCome.setText("Welcome : "+sp1.getString("name",""));
            wellCome.setTypeface(typefaceRegular);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        SharedPreferences sp1 = getSharedPreferences("User",MODE_PRIVATE);


        getMenuInflater().inflate(R.menu.options,menu);

        if(sp1.getString("name","").equals("e")){
            menu.findItem(R.id.log_in).setVisible(true);
        }else {
            menu.findItem(R.id.log_out).setVisible(true);
        }




        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.log_out:

                setPreftoNew();
                startActivity(new Intent(MainActivity.this,LoginSignup.class));
                onBackPressed();
                return true;

            case R.id.log_in:
                startActivity(new Intent(MainActivity.this,LoginSignup.class));
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void setPreftoNew() {

        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name","e");
        editor.putString("user_name","e");
        editor.putString("email","e");
        editor.apply();
    }


}
