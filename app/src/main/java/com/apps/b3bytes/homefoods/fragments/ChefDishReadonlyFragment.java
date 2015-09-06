package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.models.DishOnSale;


public class ChefDishReadonlyFragment extends Fragment {
    public static final int DISH_EDIT_SECTION_INFO = 0;
    public static final int DISH_EDIT_SECTION_PRICE = 1;
    public static final int DISH_EDIT_SECTION_AVAIL = 2;
    public static final int DISH_EDIT_SECTION_IMAGE = 3;

    private FragmentActivity mContext;

    private View rootView;

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

    OnDishReadOnlyEditSelectedListener mEditCallback;


    public ChefDishReadonlyFragment(){
        mDish = null;
    }

    // Container Activity must implement this interface
    public interface OnDishReadOnlyEditSelectedListener {
        public void OnDishReadOnlyEditSelected(DishOnSale mDish, int section);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null)
            mDish = (DishOnSale) bundle.getParcelable("dish");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(mDish.getmDish().getmDishName());
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;

        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mEditCallback = (OnDishReadOnlyEditSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDishReadOnlyEditSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_chef_dish_read_only, container, false);

        rlDishInfo = (RelativeLayout)rootView.findViewById(R.id.rlDishInfo);
        ivDishInfoEdit = (ImageView)rlDishInfo.findViewById(R.id.ivDishInfoEdit);
        tvDishEditDishName = (TextView) rlDishInfo.findViewById(R.id.tvDishEditDishName);
        tvDishEditDishInfo = (TextView) rlDishInfo.findViewById(R.id.tvDishEditDishInfo);
        tvDishEditDishPrep = (TextView) rlDishInfo.findViewById(R.id.tvDishEditDishPrep);
        tvDishEditCuisine = (TextView) rlDishInfo.findViewById(R.id.tvDishEditCuisine);
        cbDishEditVegan = (CheckBox) rlDishInfo.findViewById(R.id.cbDishEditVegan);

        rlDishPrice = (RelativeLayout)rootView.findViewById(R.id.rlDishPrice);
        ivDishPriceEdit = (ImageView)rlDishPrice.findViewById(R.id.ivDishPriceEdit);
        tvDishQtyPerUnit = (TextView) rlDishPrice.findViewById(R.id.tvDishQtyPerUnit);
        tvDishEditPrice = (TextView) rlDishPrice.findViewById(R.id.tvDishEditPrice);
        tvDishEditQuantity = (TextView) rlDishPrice.findViewById(R.id.tvDishEditQuantity);

        rlDishAvil = (RelativeLayout)rootView.findViewById(R.id.rlDishAvil);
        ivDishAvailEdit = (ImageView)rlDishAvil.findViewById(R.id.ivDishAvailEdit);
        rlDishEditFromDatePicker = (RelativeLayout) rlDishAvil.findViewById(R.id.rlDishEditFromDatePicker);
        tvDishEditFromDatePicker = (TextView)rlDishEditFromDatePicker.findViewById(R.id.tvDishEditDatePicker);
        tvDishEditFromTimePicker = (TextView)rlDishEditFromDatePicker.findViewById(R.id.tvDishEditTimePicker);
        rlDishEditToDatePicker = (RelativeLayout) rlDishAvil.findViewById(R.id.rlDishEditToDatePicker);
        tvDishEditToDatePicker = (TextView)rlDishEditToDatePicker.findViewById(R.id.tvDishEditDatePicker);
        tvDishEditToTimePicker = (TextView)rlDishEditToDatePicker.findViewById(R.id.tvDishEditTimePicker);
        llDishEditPickupDelivery = (LinearLayout) rlDishAvil.findViewById(R.id.llDishEditPickupDelivery);
        cbDishEditPickUp = (CheckBox) llDishEditPickupDelivery.findViewById(R.id.cbDishEditPickUp);
        cbDishEditDelivery = (CheckBox) llDishEditPickupDelivery.findViewById(R.id.cbDishEditDelivery);

        rlDishImage = (RelativeLayout)rootView.findViewById(R.id.rlDishImage);
        ivDishImageEdit = (ImageView)rlDishImage.findViewById(R.id.ivDishImageEdit);
        ivDishEditDishImage = (ImageView) rlDishImage.findViewById(R.id.ivDishEditDishImage);
        tvDishAdditionalInfo = (TextView) rlDishImage.findViewById(R.id.tvDishAdditionalInfo);

        ivDishInfoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditCallback.OnDishReadOnlyEditSelected(mDish, DISH_EDIT_SECTION_INFO);
            }
        });

        ivDishPriceEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditCallback.OnDishReadOnlyEditSelected(mDish, DISH_EDIT_SECTION_PRICE);
            }
        });

        ivDishAvailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditCallback.OnDishReadOnlyEditSelected(mDish, DISH_EDIT_SECTION_AVAIL);
            }
        });

        ivDishImageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditCallback.OnDishReadOnlyEditSelected(mDish, DISH_EDIT_SECTION_IMAGE);
            }
        });

        updateFields(rootView);



        return rootView;
    }

    private void initTextView(TextView tvView, String text) {
        if (text != null && !text.isEmpty()) {
            tvView.setText(text);
        }
    }

    public void updateFields(View rootView) {

        TextView tvDishEditFromDateHdr = (TextView) (rlDishEditFromDatePicker.findViewById(R.id.tvDishEditDateHdr));
        tvDishEditFromDateHdr.setText("FROM");
        TextView tvDishEditToDateHdr = (TextView) (rlDishEditToDatePicker.findViewById(R.id.tvDishEditDateHdr));
        tvDishEditToDateHdr.setText("TO");
        //populate  fields if applicable. i.e. mDish != null
        if (mDish != null) {
            initTextView(tvDishEditDishName, mDish.getmDish().getmDishName());
            initTextView(tvDishEditDishInfo, mDish.getmDish().getmDishInfo());
            initTextView(tvDishEditDishPrep, mDish.getmDish().getmPrepMethod());
            initTextView(tvDishEditCuisine, mDish.getmDish().getmCusine());
            cbDishEditVegan.setChecked(mDish.getmDish().ismVegan());

            initTextView(tvDishQtyPerUnit, String.valueOf(mDish.getmQtyPerUnit()));
            initTextView(tvDishEditPrice, String.valueOf(mDish.getmUnitPrice()));
            initTextView(tvDishEditQuantity, String.valueOf(mDish.getmUnitsOnSale()));

            initTextView(tvDishEditFromDatePicker, String.valueOf(mDish.getmFromDate()));
            initTextView(tvDishEditFromTimePicker, String.valueOf(mDish.getmFromTime()));
            initTextView(tvDishEditToDatePicker, String.valueOf(mDish.getmToDate()));
            initTextView(tvDishEditToTimePicker, String.valueOf(mDish.getmToTime()));
            //TODO: On Emulator, sometimes the checkbox won't display false as unchecked.
            cbDishEditPickUp.setChecked(mDish.ismPickUp());
            cbDishEditDelivery.setChecked(mDish.ismDelivery());

            // TODO: init the dish image
            initTextView(tvDishAdditionalInfo, String.valueOf(mDish.getmDishAddInfo()));
        }
    }

}
