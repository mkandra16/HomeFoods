package com.apps.b3bytes.homefoods.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.fragments.ChefDishEditFragment;
import com.apps.b3bytes.homefoods.fragments.ChefDishReadonlyFragment;
import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.OneDishOrder;
import com.apps.b3bytes.homefoods.utils.Address;


public class ChefDishDesc extends AppCompatActivity {
    private boolean editMode = false;
    private ChefDishEditFragment editFragment = null;
    private ChefDishReadonlyFragment readonlyFragment = null;
    private OneDishOrder dish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_dish_desc);

        Intent in = getIntent();
        Bundle bundle = in.getExtras();
        editMode = bundle.getBoolean("mode");
        dish = (OneDishOrder) bundle.getParcelable("dish");

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        editFragment = new ChefDishEditFragment();
        readonlyFragment = new ChefDishReadonlyFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (editMode == false) {
            Bundle args = new Bundle();
            args.putParcelable("dish", dish);
            readonlyFragment.setArguments(args);
            ft.replace(R.id.flContainer, readonlyFragment);
            getSupportActionBar().setTitle("Dish Name");
        } else {
            Bundle args = new Bundle();
            args.putParcelable("dish", null);
            editFragment.setArguments(args);
            ft.replace(R.id.flContainer, editFragment);
            getSupportActionBar().setTitle("Edit Dish");
        }
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chef_dish_desc, menu);
        MenuItem item = menu.findItem(R.id.action_edit);

        if (editMode) {
            item.setIcon(R.mipmap.ic_action_save);
        } else {
            item.setIcon(R.mipmap.ic_action_edit);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_edit:
                switchEditMode();
                if (editMode) {
                    // TODO: set actual dish name
                    getSupportActionBar().setTitle("Edit Dish");
                    item.setIcon(R.mipmap.ic_action_save);
                } else {
                    // TODO: set actual dish name
                    getSupportActionBar().setTitle("New Dish Name");
                    item.setIcon(R.mipmap.ic_action_edit);
                }
                return true;
            case android.R.id.home:
                if (editMode) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
                    builder.setMessage("Discard changes?").setPositiveButton("YES", dialogClickListener)
                            .setNegativeButton("NO", dialogClickListener).show();
                } else {
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    private void switchEditMode() {
        editMode = !editMode;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (editMode) {
            dish = readonlyFragment.getDish();
            Bundle args = new Bundle();
            args.putParcelable("dish", dish);
            editFragment.setArguments(args);
            ft.replace(R.id.flContainer, editFragment);
        } else {
            editFragment.saveDish();
            ft.replace(R.id.flContainer, readonlyFragment);
        }
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
