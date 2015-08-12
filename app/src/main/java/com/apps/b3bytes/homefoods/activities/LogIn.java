package com.apps.b3bytes.homefoods.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

public class LogIn extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Button bSignIn = (Button) findViewById(R.id.bSignIn);
        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = ((EditText) findViewById(R.id.etUserId)).getText().toString();
                String password = ((EditText) findViewById(R.id.etPassword)).getText().toString();
                AppGlobalState.gDataLayer.signInFoodie(uid, password, new DataLayer.SignInCallback() {
                    @Override
                    public void done(Foodie f, Exception e) {
                        if (e == null) {
                            Toast t = Toast.makeText(getApplication(),
                                    "Welcome " + f.getmUserName(), Toast.LENGTH_LONG);
                            t.show();
                            Intent i = new Intent(LogIn.this, WelcomeScreen.class);
                            LogIn.this.startActivity(i);
                        } else {
                            Toast t = Toast.makeText(getApplicationContext(), "SignIn failed", Toast.LENGTH_LONG);
                            t.show();
                        }
                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
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
