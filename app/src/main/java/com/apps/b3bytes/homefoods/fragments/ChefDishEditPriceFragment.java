package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

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

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mDish = (DishOnSale) bundle.getParcelable("dish");
            mMode = bundle.getInt("mode", HomePage.DISH_SECTION_EDIT_ALL);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_chef_dish_edit_price, container, false);

        spDishUnit = (Spinner) rootView.findViewById(R.id.spDishUnit);
        etDishQtyPerUnit = (EditText) rootView.findViewById(R.id.etDishQtyPerUnit);
        etDishEditPrice = (EditText) rootView.findViewById(R.id.etDishEditPrice);
        etDishEditQuantity = (EditText) rootView.findViewById(R.id.etDishEditQuantity);
        llDishPriceNavigationButtons = (LinearLayout)rootView.findViewById(R.id.llDishPriceNavigationButtons);
        bDishPriceNext = (Button) rootView.findViewById(R.id.bDishPriceNext);
        bDishPriceBack = (Button) rootView.findViewById(R.id.bDishPriceBack);
        llDishPriceSaveButtons = (LinearLayout)rootView.findViewById(R.id.llDishPriceSaveButtons);
        bDishPriceSave = (Button) rootView.findViewById(R.id.bDishPriceSave);

        initFields();
        return rootView;
    }

    private void initEditTextView(EditText etView, String text) {
        if (text != null && !text.isEmpty()) {
            etView.setText(text);
        }
    }

    private void initFields() {
        //populate  fields if applicable. i.e. mDish != null
        if (mDish != null) {
            spDishUnit.setSelection(mDish.getmMeasure().ordinal());
            initEditTextView(etDishQtyPerUnit, String.valueOf(mDish.getmQtyPerUnit()));
            initEditTextView(etDishEditPrice, String.valueOf(mDish.getmUnitPrice()));
            initEditTextView(etDishEditQuantity, String.valueOf(mDish.getmUnitsOnSale()));
        }

        if (mMode == HomePage.DISH_SECTION_EDIT_ALL) {
            llDishPriceNavigationButtons.setVisibility(View.VISIBLE);
            llDishPriceSaveButtons.setVisibility(View.GONE);
        } else if (mMode == HomePage.DISH_SECTION_EDIT_SINGLE) {
            llDishPriceSaveButtons.setVisibility(View.VISIBLE);
            llDishPriceNavigationButtons.setVisibility(View.GONE);
        }

    }

    private String getPriceAsStting(String in) {
        String out;
        // Remove the currency symbol, so that we can use parseDouble
        // index 0 - currency char
        // index 1 - empty space
        out = in.substring( 2, in.length() );

        return out;
    }

    private void readFields() {
        if (mDish != null) {
            mDish.setmMeasure(spDishUnit.getSelectedItem().toString());
            mDish.setmQtyPerUnit(Double.parseDouble(etDishQtyPerUnit.getText().toString()));
            mDish.setmUnitPrice(Double.parseDouble(getPriceAsStting(etDishEditPrice.getText().toString())));
            mDish.setmUnitsOnSale(Integer.parseInt(etDishEditQuantity.getText().toString()));
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* Set Text Watcher listener */
        etDishEditPrice.addTextChangedListener(dishPriceWatcher);

        bDishPriceNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFields();
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
                mSaveCallback.onDishImageSaveSelected(mDish, HomePage.DISH_SECTION_EDIT_SINGLE);
            }
        });

    }
}

