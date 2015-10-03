package com.apps.b3bytes.homefoods.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.State.Constants;
import com.apps.b3bytes.homefoods.datalayer.common.DataLayer;
import com.apps.b3bytes.homefoods.fragments.ChefDishEditAvailFragment;
import com.apps.b3bytes.homefoods.fragments.ChefDishEditImageFragment;
import com.apps.b3bytes.homefoods.fragments.ChefDishEditInfoFragment;
import com.apps.b3bytes.homefoods.fragments.ChefDishEditPriceFragment;
import com.apps.b3bytes.homefoods.fragments.ChefDishReadonlyFragment;
import com.apps.b3bytes.homefoods.fragments.FragmentActionRequestHandler;
import com.apps.b3bytes.homefoods.models.DishOnSale;
import com.apps.b3bytes.homefoods.utils.Utils;

public class ChefDishDesc extends AppCompatActivity implements FragmentActionRequestHandler {
    public static final int DISH_SECTION_EDIT_SINGLE = 0;

    public static final int DISH_EDIT_SECTION_INFO = 0;
    public static final int DISH_EDIT_SECTION_PRICE = 1;
    public static final int DISH_EDIT_SECTION_AVAIL = 2;
    public static final int DISH_EDIT_SECTION_IMAGE = 3;

    private RelativeLayout rlDishInfo;
    private ImageView ivDishInfoEdit;
    private TextView tvDishEditDishName;
    private TextView tvDishEditDishInfo;
    private TextView tvDishEditDishPrep;
    private TextView tvDishEditCuisine;
    private CheckBox cbDishEditVegan;

    private RelativeLayout rlDishPrice;
    private ImageView ivDishPriceEdit;
    private TextView tvDishQtyPerUnit;
    private TextView tvDishEditPrice;
    private TextView tvDishEditQuantity;

    private RelativeLayout rlDishAvil;
    private ImageView ivDishAvailEdit;
    private RelativeLayout rlDishEditFromDatePicker;
    private TextView tvDishEditFromDatePicker;
    private TextView tvDishEditFromTimePicker;
    private RelativeLayout rlDishEditToDatePicker;
    private TextView tvDishEditToDatePicker;
    private TextView tvDishEditToTimePicker;
    private LinearLayout llDishEditPickupDelivery;
    private CheckBox cbDishEditPickUp;
    private CheckBox cbDishEditDelivery;

    private RelativeLayout rlDishImage;
    private ImageView ivDishImageEdit;
    private ImageView ivDishEditDishImage;
    private TextView tvDishAdditionalInfo;

    private DishOnSale mDish;

    private ChefDishReadonlyFragment readFragment;
    private ChefDishEditInfoFragment infoFragment;
    private ChefDishEditPriceFragment priceFragment;
    private ChefDishEditAvailFragment availFragment;
    private ChefDishEditImageFragment saveFragment;

    private int mBackPressMode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_dish_desc);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            mDish = (DishOnSale) bundle.getParcelable("dish");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(mDish.getmDish().getmDishName());

        readFragment = new ChefDishReadonlyFragment();

        Bundle args = readFragment.getArguments();
        //http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
        if (args == null) {
            args = new Bundle();
            args.putParcelable("dish", mDish);
            readFragment.setArguments(args);
        } else {
            args.putParcelable("dish", mDish);
        }
        Utils.replaceFragment(getSupportFragmentManager(), R.id.frame_container, readFragment);
    }

    @Override
    public void onBackPressed() {
        if (mBackPressMode == 1) {
            mBackPressMode = 0;
            superOnBackPressed();
            return;
        }

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (currentFragment instanceof ChefDishEditInfoFragment) {
            if (((ChefDishEditInfoFragment) currentFragment).getmAlertDiscardChanges()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ChefDishDesc.this, R.style.myDialog));
                builder.setMessage("Discard changes?").setPositiveButton("YES", dialogToolbarBackClickListener)
                        .setNegativeButton("NO", dialogToolbarBackClickListener).show();
            } else {
                superOnBackPressed();
            }
        } else if (currentFragment instanceof ChefDishEditPriceFragment) {
            if (((ChefDishEditPriceFragment) currentFragment).getmAlertDiscardChanges()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ChefDishDesc.this, R.style.myDialog));
                builder.setMessage("Discard changes?").setPositiveButton("YES", dialogToolbarBackClickListener)
                        .setNegativeButton("NO", dialogToolbarBackClickListener).show();
            } else {
                superOnBackPressed();
            }
        } else if (currentFragment instanceof ChefDishEditAvailFragment) {
            if (((ChefDishEditAvailFragment) currentFragment).getmAlertDiscardChanges()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ChefDishDesc.this, R.style.myDialog));
                builder.setMessage("Discard changes?").setPositiveButton("YES", dialogToolbarBackClickListener)
                        .setNegativeButton("NO", dialogToolbarBackClickListener).show();
            } else {
                superOnBackPressed();
            }
        } else if (currentFragment instanceof ChefDishEditImageFragment) {
            if (((ChefDishEditImageFragment) currentFragment).getmAlertDiscardChanges()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ChefDishDesc.this, R.style.myDialog));
                builder.setMessage("Discard changes?").setPositiveButton("YES", dialogToolbarBackClickListener)
                        .setNegativeButton("NO", dialogToolbarBackClickListener).show();
            } else {
                superOnBackPressed();
            }
        } else {
            superOnBackPressed();
        }
    }

    DialogInterface.OnClickListener dialogToolbarBackClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    mBackPressMode = 1;
                    onBackPressed();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    // http://stackoverflow.com/questions/18305945/how-to-resume-fragment-from-backstack-if-exists
    private void superOnBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    public void fragmentActionRequestHandler(int fragment_id, int action_id, Bundle bundle) {
        switch (fragment_id) {
            case Constants.FRAGMENT_ChefDishReadonlyFragment_ID: {
                ChefDishReadonlyFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_ChefDishEditPriceFragment_ID: {
                ChefDishEditPriceFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_ChefDishEditImageFragment_ID: {
                ChefDishEditImageFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_ChefDishEditAvailFragment_ID: {
                ChefDishEditAvailFragmentRequestHandler(action_id, bundle);
                break;
            }
            case Constants.FRAGMENT_ChefDishEditInfoFragment_ID: {
                ChefDishEditInfoFragmentRequestHandler(action_id, bundle);
                break;
            }
        }
    }

    public void ChefDishReadonlyFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_EDIT_ChefDishReadonlyFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                int section = bundle.getInt("section");
                OnDishReadOnlyEditSelected(dish, section);
                break;
            }
        }
    }

    public void OnDishReadOnlyEditSelected(DishOnSale dish, int section) {
        mDish = dish;

        Fragment fragment = null;

        switch (section) {
            case ChefDishReadonlyFragment.DISH_EDIT_SECTION_INFO:
                fragment = new ChefDishEditInfoFragment();
                infoFragment = (ChefDishEditInfoFragment) fragment;
                break;
            case ChefDishReadonlyFragment.DISH_EDIT_SECTION_PRICE:
                fragment = new ChefDishEditPriceFragment();
                priceFragment = (ChefDishEditPriceFragment) fragment;
                break;
            case ChefDishReadonlyFragment.DISH_EDIT_SECTION_AVAIL:
                fragment = new ChefDishEditAvailFragment();
                availFragment = (ChefDishEditAvailFragment) fragment;
                break;
            case ChefDishReadonlyFragment.DISH_EDIT_SECTION_IMAGE:
                fragment = new ChefDishEditImageFragment();
                saveFragment = (ChefDishEditImageFragment) fragment;
                break;
            default:
        }

        if (fragment != null) {
            Bundle args = fragment.getArguments();
            //http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
            if (args == null) {
                args = new Bundle();
                args.putParcelable("dish", mDish);
                args.putInt("mode", DISH_SECTION_EDIT_SINGLE);
                fragment.setArguments(args);
            } else {
                args.putInt("mode", DISH_SECTION_EDIT_SINGLE);
                args.putParcelable("dish", mDish);
            }
            Utils.replaceFragment(getSupportFragmentManager(), R.id.frame_container, fragment);
        }
    }


    public void ChefDishEditInfoFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_SAVE_ChefDishEditInfoFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                int mode = bundle.getInt("mode");
                onDishEditSaveSelected(dish, mode);
                break;
            }
            case Constants.ACTION_CANCEL_ChefDishEditInfoFragment_ID: {
                boolean onChanged = bundle.getBoolean("onChanged");
                int mode = bundle.getInt("mode");
                OnDishEditCancelSelected(onChanged, mode);
                break;
            }
        }
    }

    public void ChefDishEditPriceFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_SAVE_ChefDishEditPriceFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                int mode = bundle.getInt("mode");
                onDishEditSaveSelected(dish, mode);
                break;
            }
            case Constants.ACTION_CANCEL_ChefDishEditPriceFragment_ID: {
                boolean onChanged = bundle.getBoolean("onChanged");
                int mode = bundle.getInt("mode");
                OnDishEditCancelSelected(onChanged, mode);
                break;
            }
        }
    }

    public void ChefDishEditAvailFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_SAVE_ChefDishEditAvailFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                int mode = bundle.getInt("mode");
                onDishEditSaveSelected(dish, mode);
                break;
            }
            case Constants.ACTION_CANCEL_ChefDishEditAvailFragment_ID: {
                boolean onChanged = bundle.getBoolean("onChanged");
                int mode = bundle.getInt("mode");
                OnDishEditCancelSelected(onChanged, mode);
                break;
            }
        }
    }

    public void ChefDishEditImageFragmentRequestHandler(int action_id, Bundle bundle) {
        switch (action_id) {
            case Constants.ACTION_SAVE_ChefDishEditImageFragment_ID: {
                DishOnSale dish = (DishOnSale) bundle.getParcelable("dish");
                int mode = bundle.getInt("mode");
                onDishEditSaveSelected(dish, mode);
                break;
            }
            case Constants.ACTION_CANCEL_ChefDishEditImageFragment_ID: {
                boolean onChanged = bundle.getBoolean("onChanged");
                int mode = bundle.getInt("mode");
                OnDishEditCancelSelected(onChanged, mode);
                break;
            }
        }
    }

    public void onDishEditSaveSelected(DishOnSale dish, int mode) {
        mDish = dish;
        AppGlobalState.gDataLayer.putDishOnSale(dish, new DataLayer.PublishCallback() {
            @Override
            public void done(Exception e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(),
                            "Posted Dish for sale. Name : " + mDish.getmDish().getmDishName(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Failed to post Dish for sale. Name : " + mDish.getmDish().getmDishName(),
                            Toast.LENGTH_SHORT).show();
                }

                Bundle args = readFragment.getArguments();
                //http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
                if (args == null) {
                    args = new Bundle();
                    args.putParcelable("dish", mDish);
                    readFragment.setArguments(args);
                } else {
                    args.putParcelable("dish", mDish);
                }
                Utils.replaceFragment(getSupportFragmentManager(), R.id.frame_container, readFragment);
            }
        });
    }

    public void OnDishEditCancelSelected(boolean isChanged, int mode) {
        if (isChanged) {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
            builder.setMessage("Discard changes?").setPositiveButton("YES", dialogToolbarBackClickListener)
                    .setNegativeButton("NO", dialogToolbarBackClickListener).show();
        } else {
            Bundle args = readFragment.getArguments();
            //http://stackoverflow.com/questions/10364478/got-exception-fragment-already-active
            if (args == null) {
                args = new Bundle();
                args.putParcelable("dish", mDish);
                readFragment.setArguments(args);
            } else {
                args.putParcelable("dish", mDish);
            }
            Utils.replaceFragment(getSupportFragmentManager(), R.id.frame_container, readFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chef_dish_desc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
