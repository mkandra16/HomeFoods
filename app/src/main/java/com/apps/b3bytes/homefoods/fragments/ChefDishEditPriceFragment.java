package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.activities.HomePage;
import com.apps.b3bytes.homefoods.models.DishOnSale;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class ChefDishEditPriceFragment extends Fragment {
    private FragmentActivity mContext;
    private View rootView;
    private DishOnSale mDish;
    private int mMode;
    private boolean mAlertDiscardChanges;

    private Spinner spDishUnit;
    private EditText etDishQtyPerUnit;
    private EditText etDishEditPrice;
    private EditText etDishEditQuantity;
    private LinearLayout llDishPriceNavigationButtons;
    private Button bDishPriceNext;
    private Button bDishPriceBack;
    private LinearLayout llDishPriceSaveButtons;
    private Button bDishPriceSave;

    OnDishPriceNextSelectedListener mNextCallback;
    OnDishPriceBackSelectedListener mBackCallback;
    OnDishImageSaveSelectedListener mSaveCallback;
    OnDishEditCancelSelectedListener mCancelCallback;


    // Container Activity must implement this interface
    public interface OnDishPriceNextSelectedListener {
        public void onDishPriceNextSelected(DishOnSale mDish);
    }

    // Container Activity must implement this interface
    public interface OnDishPriceBackSelectedListener {
        public void onDishPriceBackSelected(DishOnSale mDish);
    }

    public interface OnDishImageSaveSelectedListener {
        public void onDishImageSaveSelected(DishOnSale mDish, int mode);
    }

    public interface OnDishEditCancelSelectedListener {
        public void OnDishEditCancelSelected(boolean changed, int mode);
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;

        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mNextCallback = (OnDishPriceNextSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDishPriceNextSelectedListener");
        }

        try {
            mBackCallback = (OnDishPriceBackSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDishPriceBackSelectedListener");
        }

        try {
            mSaveCallback = (OnDishImageSaveSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDishImageSaveSelectedListener");
        }

        try {
            mCancelCallback = (OnDishEditCancelSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDishEditCancelSelectedListener");
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mMode == HomePage.DISH_SECTION_EDIT_ALL)
            getActivity().setTitle("Add Dish");
        else
            getActivity().setTitle(mDish.getmDish().getmDishName());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mDish = (DishOnSale) bundle.getParcelable("dish");
            mMode = bundle.getInt("mode", HomePage.DISH_SECTION_EDIT_ALL);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_chef_dish_edit_price, container, false);

        spDishUnit = (Spinner) rootView.findViewById(R.id.spDishUnit);
        etDishQtyPerUnit = (EditText) rootView.findViewById(R.id.etDishQtyPerUnit);
        etDishEditPrice = (EditText) rootView.findViewById(R.id.etDishEditPrice);
        etDishEditQuantity = (EditText) rootView.findViewById(R.id.etDishEditQuantity);
        llDishPriceNavigationButtons = (LinearLayout) rootView.findViewById(R.id.llDishPriceNavigationButtons);
        bDishPriceNext = (Button) rootView.findViewById(R.id.bDishPriceNext);
        bDishPriceBack = (Button) rootView.findViewById(R.id.bDishPriceBack);
        llDishPriceSaveButtons = (LinearLayout) rootView.findViewById(R.id.llDishPriceSaveButtons);
        bDishPriceSave = (Button) rootView.findViewById(R.id.bDishPriceSave);

        initFields();
        // previous fragments has data
        if (mMode == HomePage.DISH_SECTION_EDIT_ALL)
            mAlertDiscardChanges = true;
        else
            mAlertDiscardChanges = false;

        return rootView;
    }

    private void initEditTextView(EditText etView, String text) {
        if (text != null && !text.isEmpty()) {
            etView.setText(text);
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            mAlertDiscardChanges = true;
            Toast.makeText(mContext, "Content Changed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void initFields() {
        //populate  fields if applicable. i.e. mDish != null
        if (mDish != null) {
            spDishUnit.setSelection(mDish.getmMeasure().ordinal());
            // not sure of the exact reason, somehow these fields are getting initialized with zeros
            // which makes these fileds to have some value
            if (mDish.getmQtyPerUnit() != 0.0)
                initEditTextView(etDishQtyPerUnit, String.valueOf(mDish.getmQtyPerUnit()));
            if (mDish.getmUnitPrice() != 0.0)
                initEditTextView(etDishEditPrice, String.valueOf(mDish.getmUnitPrice()));
            if (mDish.getmUnitsOnSale() != 0)
                initEditTextView(etDishEditQuantity, String.valueOf(mDish.getmUnitsOnSale()));
        }

        etDishQtyPerUnit.addTextChangedListener(textWatcher);
        //etDishEditPrice.addTextChangedListener(textWatcher);
        etDishEditQuantity.addTextChangedListener(textWatcher);

        if (mMode == HomePage.DISH_SECTION_EDIT_ALL) {
            llDishPriceNavigationButtons.setVisibility(View.VISIBLE);
            llDishPriceSaveButtons.setVisibility(View.GONE);
        } else if (mMode == HomePage.DISH_SECTION_EDIT_SINGLE) {
            llDishPriceSaveButtons.setVisibility(View.VISIBLE);
            llDishPriceNavigationButtons.setVisibility(View.GONE);
        }

    }

    private String getPriceAsString(String in) {
        // Remove the currency symbol, so that we can use parseDouble
        // delimiter: empty space
        String[] splited = in.split("\\s+");

        // the last entry will be the price
        return splited[splited.length - 1];
    }

    private void readFields() {
        if (mDish != null) {
            mDish.setmMeasure(spDishUnit.getSelectedItem().toString());
            try {
                mDish.setmQtyPerUnit(Double.parseDouble(etDishQtyPerUnit.getText().toString()));
                mDish.setmUnitPrice(Double.parseDouble(getPriceAsString(etDishEditPrice.getText().toString())));
                mDish.setmUnitsOnSale(Integer.parseInt(etDishEditQuantity.getText().toString()));
            } catch (NumberFormatException e) {
                // Nothing
            }
        }
    }

    private final TextWatcher dishPriceWatcher = new TextWatcher() {
        DecimalFormat dec = new DecimalFormat("0.00");

        @Override
        public void afterTextChanged(Editable arg0) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
        }

        private String current = "";

        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {
            mAlertDiscardChanges = true;
            if (!s.toString().equals(current)) {
                etDishEditPrice.removeTextChangedListener(this);

                /*String replaceable = String.format("[%s,.]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());*/
                String cleanString = s.toString().replaceAll("[^0-9]", "");

                double parsed = Double.parseDouble(cleanString);
                String formatted = NumberFormat.getCurrencyInstance(new Locale("en", "in")).format((parsed / 100));
                /* String formatted = NumberFormat.getCurrencyInstance().format((parsed / 100)); */

                current = formatted;
                etDishEditPrice.setText(formatted);
                etDishEditPrice.setSelection(formatted.length());

                etDishEditPrice.addTextChangedListener(this);
            }
        }
    };

    private boolean checkForMustData() {
        String qty = etDishQtyPerUnit.getText().toString();
        String price = getPriceAsString(etDishEditPrice.getText().toString());
        String units = etDishEditQuantity.getText().toString();

        if (qty == null || qty.isEmpty()) {
            Toast.makeText(mContext, "Please Enter Quantity per Unit", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (price == null || price.isEmpty()) {
            Toast.makeText(mContext, "Please Enter Price per unit", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (units == null || units.isEmpty()) {
            Toast.makeText(mContext, "Please Enter Units Available", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* Set Text Watcher listener */
        etDishEditPrice.addTextChangedListener(dishPriceWatcher);

        bDishPriceNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFields();
                boolean gotAllData = checkForMustData();
                if (gotAllData)
                    mNextCallback.onDishPriceNextSelected(mDish);
            }
        });

        bDishPriceBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFields();
                mBackCallback.onDishPriceBackSelected(mDish);
            }
        });

        bDishPriceSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFields();
                boolean gotAllData = checkForMustData();
                if (gotAllData)
                    mSaveCallback.onDishImageSaveSelected(mDish, HomePage.DISH_SECTION_EDIT_SINGLE);
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_chef_fragment_dish_edit, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancel_edit:
                mCancelCallback.OnDishEditCancelSelected(mAlertDiscardChanges, mMode);
                return true;
            default:
                break;
        }
        return false;
    }

}

