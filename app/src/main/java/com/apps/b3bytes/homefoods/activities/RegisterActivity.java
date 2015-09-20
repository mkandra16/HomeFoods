package com.apps.b3bytes.homefoods.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.models.Foodie;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private EditText etFirstName;
    private EditText etMiddleName;
    private EditText etLastName;
    private EditText etUserId;
    private EditText etPassword;
    private EditText etStreetAddress;
    private EditText etState;
    private EditText etZip;
    private EditText etEmailId;
    private EditText etHomePhnone;
    private EditText etMobile;
    private EditText etFavFoods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Button bRegister = (Button) findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bRegister.setEnabled(false);
                // Register Foodie
                // Create Foodie
                JSONObject addr =  new JSONObject();

                try {
                    EditText etAddr = (EditText) findViewById(R.id.etStreetAddress);
                    Editable e = etAddr.getText();
                    addr.put("AddrLine1", e.toString())
                            .put("AddrZip", Integer.valueOf(((EditText) findViewById(R.id.etZip)).getText().toString()))
                            .put("AddrState", ((EditText) findViewById(R.id.etState)).getText().toString())
                            .put("AddrCountry", "US");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject contact = new JSONObject();
                try {
                    contact.put("HomePh", ((EditText)findViewById(R.id.etHomePhone)).getText().toString())
                            .put("Mobile", ((EditText)findViewById(R.id.etMobile)).getText().toString())
                            .put("EmailId", ((EditText)findViewById(R.id.etEmailId)).getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject foodie = new JSONObject();
                try {
                    foodie.put("FoodieId", -1)
                            .put("FirstName", ((EditText) findViewById(R.id.etFirstName)).getText().toString())
                            .put("LastName", ((EditText) findViewById(R.id.etLastName)).getText().toString())
                            .put("UserName", ((EditText) findViewById(R.id.etUserId)).getText().toString())
                            .put("Password", ((EditText) findViewById(R.id.etPassword)).getText().toString())
                            .put("Address", addr)
                            .put("ContactDetails", contact)
                            .put("FavoriteFoods", ((EditText) findViewById(R.id.etFavFoods)).getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Foodie f = new Foodie(foodie);
                AppGlobalState.gDataLayer.registerFoodie(f, new DataLayer.RegistrationCallback() {
                    @Override
                    public void done(Foodie f, Exception e) {
                        Toast.makeText(getApplicationContext(), "Resgistered Foodie" + f.getmUserName(), Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
