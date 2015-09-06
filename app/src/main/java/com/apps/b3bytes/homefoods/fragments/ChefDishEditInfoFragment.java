package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.activities.HomePage;
import com.apps.b3bytes.homefoods.models.DishOnSale;

public class ChefDishEditInfoFragment extends Fragment {
    private FragmentActivity mContext;
    private View rootView;
    private DishOnSale mDish;
    private int mMode;
    private boolean mAlertDiscardChanges;

    private EditText etDishEditDishName;
    private EditText etDishEditDishInfo;
    private EditText etDishEditDishPrepMethod;
    private AutoCompleteTextView acTvDishEditCuisine;
    private CheckBox cbDishEditVegan;
    private Button bDishInfoNext;
    private Button bDishInfoSave;

    OnDishInfoNextSelectedListener mNextCallback;
    OnDishImageSaveSelectedListener mSaveCallback;
    OnDishEditCancelSelectedListener mCancelCallback;

    // Container Activity must implement this interface
    public interface OnDishInfoNextSelectedListener {
        public void onDishInfoNextSelected(DishOnSale mDish);
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
            mNextCallback = (OnDishInfoNextSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDishInfoNextSelectedListener");
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
    public void onResume() {
        super.onResume();
        if (mMode == HomePage.DISH_SECTION_EDIT_ALL)
            getActivity().setTitle("Add Dish");
        else
            getActivity().setTitle(mDish.getmDish().getmDishName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_chef_dish_edit_info, container, false);

        etDishEditDishName = (EditText) rootView.findViewById(R.id.etDishEditDishName);
        etDishEditDishInfo = (EditText) rootView.findViewById(R.id.etDishEditDishInfo);
        etDishEditDishPrepMethod = (EditText) rootView.findViewById(R.id.etDishEditDishPrepMethod);
        acTvDishEditCuisine = (AutoCompleteTextView) rootView.findViewById(R.id.acTvDishEditCuisine);
        cbDishEditVegan = (CheckBox) rootView.findViewById(R.id.cbDishEditVegan);
        bDishInfoNext = (Button) rootView.findViewById(R.id.bDishInfoNext);
        bDishInfoSave = (Button) rootView.findViewById(R.id.bDishInfoSave);

        initFields();
        mAlertDiscardChanges = false;

        return rootView;
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

    private void initAutoCompleteTextView(AutoCompleteTextView acTvView, String text) {
        if (text != null && !text.isEmpty()) {
            acTvView.setText(text);
        }
    }

    private void initEditTextView(EditText etView, String text) {
        if (text != null && !text.isEmpty()) {
            etView.setText(text);
        }
    }

    private void initFields() {
        //populate  fields if applicable. i.e. mDish != null
        if (mDish != null) {
            initEditTextView(etDishEditDishName, mDish.getmDish().getmDishName());
            initEditTextView(etDishEditDishInfo, mDish.getmDish().getmDishInfo());
            initEditTextView(etDishEditDishPrepMethod, mDish.getmDish().getmPrepMethod());
            initAutoCompleteTextView(acTvDishEditCuisine, mDish.getmDish().getmCusine());
            cbDishEditVegan.setChecked(mDish.getmDish().ismVegan());
        }

        etDishEditDishName.addTextChangedListener(textWatcher);
        etDishEditDishInfo.addTextChangedListener(textWatcher);
        etDishEditDishPrepMethod.addTextChangedListener(textWatcher);
        acTvDishEditCuisine.addTextChangedListener(textWatcher);
        cbDishEditVegan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mAlertDiscardChanges = true;
                Toast.makeText(mContext, "Checkbox Changed", Toast.LENGTH_SHORT).show();
            }
        });

        if (mMode == HomePage.DISH_SECTION_EDIT_ALL) {
            bDishInfoNext.setVisibility(View.VISIBLE);
            bDishInfoSave.setVisibility(View.GONE);
        } else if (mMode == HomePage.DISH_SECTION_EDIT_SINGLE) {
            bDishInfoSave.setVisibility(View.VISIBLE);
            bDishInfoNext.setVisibility(View.GONE);
        }
    }

    private void readFields() {
        if (mDish != null) {
            mDish.getmDish().setmDishName(etDishEditDishName.getText().toString());
            mDish.getmDish().setmDishInfo(etDishEditDishInfo.getText().toString());
            mDish.getmDish().setmPrepMethod(etDishEditDishPrepMethod.getText().toString());
            mDish.getmDish().setmCusine(acTvDishEditCuisine.getText().toString());
            mDish.getmDish().setmVegan(cbDishEditVegan.isChecked());
        }
    }

    private boolean checkForMustData() {
        String name = etDishEditDishName.getText().toString();
        String info = etDishEditDishInfo.getText().toString();

        if (name == null || name.isEmpty()) {
            Toast.makeText(mContext, "Please Enter Dish Name", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (info == null || info.isEmpty()) {
            Toast.makeText(mContext, "Please Enter Dish Information", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<CharSequence> aCuisine = ArrayAdapter.createFromResource(
                mContext, R.array.cuisine_picker_array, android.R.layout.simple_dropdown_item_1line);
        acTvDishEditCuisine.setAdapter(aCuisine);

        bDishInfoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFields();
                boolean gotAllData = checkForMustData();
                if (gotAllData)
                    mNextCallback.onDishInfoNextSelected(mDish);
            }
        });

        bDishInfoSave.setOnClickListener(new View.OnClickListener() {
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
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false).setEnabled(false);
        return;
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

