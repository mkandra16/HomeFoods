package com.apps.b3bytes.homefoods.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.models.DishOnSale;
import com.apps.b3bytes.homefoods.models.OneDishOrder;


public class ChefDishReadonlyFragment extends Fragment {
    private View rootView;

    private TextView tvDishEditDishName;
    private TextView tvDishEditCuisine;
    private CheckBox cbDishEditVegan;
    private TextView tvDishEditPrice;
    private TextView tvDishEditQuantity;
    private RelativeLayout rlDishEditFromDatePicker;
    private RelativeLayout rlDishEditToDatePicker;
    private LinearLayout llDishEditPickupDelivery;
    private CheckBox cbDishEditPickUp;
    private CheckBox cbDishEditDelivery;
    private TextView tvDishEditDishImage;
    private ImageView ivDishEditDishImage;
    //TODO update with more fields that got added like qty per unit, and grams/litres

    private DishOnSale mDish;


    public ChefDishReadonlyFragment(){
        mDish = null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
            mDish = (DishOnSale) bundle.getParcelable("dish");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_chef_dish_read_only, container, false);

        tvDishEditDishName = (TextView) rootView.findViewById(R.id.tvDishEditDishName);
        tvDishEditCuisine = (TextView) rootView.findViewById(R.id.tvDishEditCuisine);
        cbDishEditVegan = (CheckBox) rootView.findViewById(R.id.cbDishEditVegan);
        tvDishEditPrice = (TextView) rootView.findViewById(R.id.tvDishEditPrice);
        tvDishEditQuantity = (TextView) rootView.findViewById(R.id.tvDishEditQuantity);
        rlDishEditFromDatePicker = (RelativeLayout) rootView.findViewById(R.id.rlDishEditFromDatePicker);
        rlDishEditToDatePicker = (RelativeLayout) rootView.findViewById(R.id.rlDishEditToDatePicker);
        llDishEditPickupDelivery = (LinearLayout) rootView.findViewById(R.id.llDishEditPickupDelivery);
        cbDishEditPickUp = (CheckBox) llDishEditPickupDelivery.findViewById(R.id.cbDishEditPickUp);
        cbDishEditDelivery = (CheckBox) llDishEditPickupDelivery.findViewById(R.id.cbDishEditDelivery);
        tvDishEditDishImage = (TextView) rootView.findViewById(R.id.tvDishEditDishImage);
        ivDishEditDishImage = (ImageView) rootView.findViewById(R.id.ivDishEditDishImage);

        updateFields(rootView);

        return rootView;
    }

    private void initTextView(TextView tvView, String text) {
        if (text != null && !text.isEmpty()) {
            tvView.setText(text);
        }
    }

    public void updateFields(View rootView) {

        //TODO: populate  fields if applicable. i.e. mDish != null
        TextView tvDishEditFromDateHdr = (TextView) (rlDishEditFromDatePicker.findViewById(R.id.tvDishEditDateHdr));
        tvDishEditFromDateHdr.setText("FROM");
        TextView tvDishEditToDateHdr = (TextView) (rlDishEditToDatePicker.findViewById(R.id.tvDishEditDateHdr));
        tvDishEditToDateHdr.setText("TO");
        //populate  fields if applicable. i.e. mDish != null
        if (mDish != null) {
            initTextView(tvDishEditDishName, mDish.getmDish().getmDishName());
            initTextView(tvDishEditCuisine, mDish.getmDish().getmCusine());
            //if (mDish.getmDishOnSale().getmIsVegan() == true) //TODO: enable this after Dish model is updated
            cbDishEditVegan.setChecked(mDish.getmDish().ismVegan());
            initTextView(tvDishEditPrice, "" + mDish.getmUnitPrice());
            initTextView(tvDishEditQuantity, "" + mDish.getmUnitsOnSale());

            // TODO: set date and time fileds.
            // Need to convert the formats from parse and into the parse
            TextView tvDishEditFromDatePicker = (TextView) (rlDishEditFromDatePicker.findViewById(R.id.tvDishEditDatePicker));
            tvDishEditFromDatePicker.setText(mDish.getmToDate()); //TODO need from date also
            TextView tvDishEditFromTimePicker = (TextView) (rlDishEditFromDatePicker.findViewById(R.id.tvDishEditTimePicker));
            tvDishEditFromTimePicker.setText(mDish.getmToTime()); //TODO need from Time also

            TextView tvDishEditToDatePicker = (TextView) (rlDishEditToDatePicker.findViewById(R.id.tvDishEditDatePicker));
            tvDishEditToDatePicker.setText(mDish.getmToDate());
            TextView tvDishEditToTimePicker = (TextView) (rlDishEditToDatePicker.findViewById(R.id.tvDishEditTimePicker));
            tvDishEditToTimePicker.setText(mDish.getmToTime());

            if (mDish.ismPickUp()) {
                cbDishEditPickUp.setChecked(true);
            } else {
                cbDishEditPickUp.setChecked(false);
            }

            if (mDish.ismDelivery()) {
                cbDishEditDelivery.setChecked(true);
            } else {
                cbDishEditDelivery.setChecked(false);
            }

            //TODO
/*            initTextView(etDishQtyPerUnit, "" + mDish.getmQtyPerUnit());
            if (mDish.getmMeasure() != null)
                spDishUnit.setSelection((mDish.getmMeasure().equals(DishOnSale.Measure.Grams)) ? 0 : 1);*/

            // TODO: set image from read only to edit mode
            /*private TextView tvDishEditDishImage;
            private ImageView ivDishEditDishImage;*/

        }
    }

    public DishOnSale getDish() {
        return mDish;
    }
}
