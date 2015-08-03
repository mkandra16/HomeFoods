package com.apps.b3bytes.homefoods.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.Foodie;
import com.apps.b3bytes.homefoods.models.Listener;

import org.json.JSONException;
import org.json.JSONObject;



public class WelcomeScreen extends ActionBarActivity {

    private Button bDishDescScreenNavigate;
    private Button bCheckoutScreenNavigate;
    private DataLayer dataLayer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        // Current Foodie
        // RegisterFoodie // one time.
        // If not registered as chef, register as chef.
        // publishDish(); multiple of them.
        //
        if (dataLayer == null) {
//            Foodie f = Foodie.createDummyFoodie();
            dataLayer = new DataLayer(this);
/*            dataLayer.registerFoodie(f, new DataLayer.RegistrationCallback() {

                @Override
                public void done(Foodie f, Exception e) {
                    if (e == null) {
                        Toast t = Toast.makeText(getApplicationContext(),
                                "Registration successful, FoodieId" + f.getmFoodieId(), Toast.LENGTH_LONG);
                    } else {
                        Toast t = Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_LONG);

                    }
                }
            });
*/
            dataLayer.signInFoodie("Mohan", "welcome", new DataLayer.SignInCallback() {
                @Override
                public void done(Foodie f, Exception e) {
                    if (e == null) {
                        Toast t = Toast.makeText(getApplicationContext(),
                                "Welcome " + f.getmUserName(), Toast.LENGTH_LONG);
                        t.show();

 //                       dataLayer.addFewDishes(getApplicationContext());
                    } else {
                        Toast t = Toast.makeText(getApplicationContext(), "SignIn failed", Toast.LENGTH_LONG);
                        t.show();
                    }
                }
            });
        }
  //      if(f.getmChef() == null) {
  //          f.signUpAsChef();
  //          dataLayer.signUpAsChef(f);
  //      }
        bDishDescScreenNavigate = (Button) findViewById(R.id.bDishDescScreenNavigate);

        bDishDescScreenNavigate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent i = new Intent(WelcomeScreen.this, DishDesc.class);

                WelcomeScreen.this.startActivity(i);
            }
        });

        bCheckoutScreenNavigate = (Button) findViewById(R.id.bCheckoutScreenNavigate);

        bCheckoutScreenNavigate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent i = new Intent(WelcomeScreen.this, OrderReview.class);

                WelcomeScreen.this.startActivity(i);
            }
        });


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
}
