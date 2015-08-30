package com.apps.b3bytes.homefoods.activities;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.models.ChefOrder;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.apps.b3bytes.homefoods.models.FoodieOrder;

import java.util.ArrayList;


public class WelcomeScreen extends ActionBarActivity {

    private Button bDishDescScreenNavigate;
    private Button bChefDeliveryScreenNavigate;
    private Button bHomePageScreenNavigate;

    // nav drawer title
    private CharSequence mPageTitle;
    // used to store app title
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolBar);
        //Toolbar will now take on default actionbar characteristics
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("Home Foods");
        mTitle = mPageTitle = getTitle();

        // enabling action bar app icon and behaving it as toggle button
/*        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);*/
        // This is first Screen, initialize global state of the App.
        AppGlobalState.initialize(getApplicationContext());

        bDishDescScreenNavigate = (Button) findViewById(R.id.bDishDescScreenNavigate);

        bDishDescScreenNavigate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent i = new Intent(WelcomeScreen.this, DishDesc.class);

                WelcomeScreen.this.startActivity(i);
            }
        });

        bChefDeliveryScreenNavigate = (Button) findViewById(R.id.bChefDeliveryScreenNavigate);

        bChefDeliveryScreenNavigate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent i = new Intent(WelcomeScreen.this, ChefOrderDelivery.class);

                WelcomeScreen.this.startActivity(i);
            }
        });

        bHomePageScreenNavigate = (Button) findViewById(R.id.bHomePageScreenNavigate);

        bHomePageScreenNavigate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent i = new Intent(WelcomeScreen.this, HomePage.class);

                WelcomeScreen.this.startActivity(i);
            }
        });


        Button bRegister = (Button) findViewById(R.id.bRegisterFoodie);

        bRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Toast.makeText(getApplicationContext(), "Request to register foodie", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(WelcomeScreen.this, RegisterActivity.class);
                WelcomeScreen.this.startActivity(i);
            }
        });


        Button bSignIn = (Button) findViewById(R.id.bSignInFoodie);

        bSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent i = new Intent(WelcomeScreen.this, LogIn.class);
                WelcomeScreen.this.startActivity(i);
            }
        });

        Button bTester = (Button) findViewById(R.id.bTester);

        bTester.setOnClickListener(new View.OnClickListener() {
            String foodieOrderNo = "zKEsTyKqms";

            public void onClick(View v) {
                AppGlobalState.gDataLayer.getFoodieOrder(foodieOrderNo, new DataLayer.GetFoodieOrderCallback() {
                    @Override
                    public void done(FoodieOrder foodieOrder, Exception e) {
                        if (e != null) {
                            Toast.makeText(getApplicationContext(), "Failed to retrie foodie order " + e.toString(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Intent i = new Intent();
                            i.putExtra("FoodieOrder", foodieOrder);
                            FoodieOrder fo = i.getParcelableExtra("FoodieOrder");
                            assert fo.equals(foodieOrder);
                        }
                    }
                });
            }
        });

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle("Home Foods");
    }
}
