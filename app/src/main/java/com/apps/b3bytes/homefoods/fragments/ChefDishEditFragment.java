package com.apps.b3bytes.homefoods.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.models.Dish;
import com.apps.b3bytes.homefoods.models.DishOnSale;
import com.apps.b3bytes.homefoods.models.OneDishOrder;

import org.w3c.dom.Text;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChefDishEditFragment extends Fragment {
    private FragmentActivity mContext;
    private View rootView;

    private EditText etDishEditDishName;
    private AutoCompleteTextView acTvDishEditCuisine;
    private CheckBox cbVegitarian;
    private EditText etDishEditPrice;
    private EditText etDishEditQuantity;
    private RelativeLayout rlDishEditFromDatePicker;
    private RelativeLayout rlDishEditToDatePicker;
    private LinearLayout llDishEditPickupDelivery;
    private CheckBox cbDishEditPickUp;
    private CheckBox cbDishEditDelivery;
    private TextView tvDishEditDishImage;
    private ImageView ivDishEditDishImage;

    private int datePickerInput;
    private int timePickerInput;
    private Uri outputFileUri;
    private boolean mexistingDish;
    private EditText etDishEditDishInfo;
    private EditText etDishEditDishPrepMethod;
    private EditText etDishQtyPerUnit;
    private Spinner spDishUnit;
    private DishOnSale mDish;

    public ChefDishEditFragment() {
        mexistingDish = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
            mDish = (DishOnSale) bundle.getParcelable("dish");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_chef_dish_edit, container, false);
        acTvDishEditCuisine = (AutoCompleteTextView) rootView.findViewById(R.id.acTvDishEditCuisine);
        etDishEditDishName = (EditText) rootView.findViewById(R.id.etDishEditDishName);
        etDishEditDishInfo = (EditText) rootView.findViewById(R.id.etDishEditDishInfo);
        etDishEditDishPrepMethod = (EditText) rootView.findViewById(R.id.etDishEditDishPrepMethod);

        cbVegitarian = (CheckBox) rootView.findViewById(R.id.cbDishEditVegan);
        etDishEditPrice = (EditText) rootView.findViewById(R.id.etDishEditPrice);
        etDishEditQuantity = (EditText) rootView.findViewById(R.id.etDishEditQuantity);
        rlDishEditFromDatePicker = (RelativeLayout) rootView.findViewById(R.id.rlDishEditFromDatePicker);
        rlDishEditToDatePicker = (RelativeLayout) rootView.findViewById(R.id.rlDishEditToDatePicker);
        llDishEditPickupDelivery = (LinearLayout) rootView.findViewById(R.id.llDishEditPickupDelivery);
        cbDishEditPickUp = (CheckBox) rootView.findViewById(R.id.cbDishEditPickUp);
        cbDishEditDelivery = (CheckBox) rootView.findViewById(R.id.cbDishEditDelivery);
        etDishQtyPerUnit = (EditText) rootView.findViewById(R.id.etDishQtyPerUnit);
        spDishUnit = (Spinner) rootView.findViewById(R.id.spDishUnit);
        tvDishEditDishImage = (TextView) rootView.findViewById(R.id.tvDishEditDishImage);
        ivDishEditDishImage = (ImageView) rootView.findViewById(R.id.ivDishEditDishImage);
        initFields();
        return rootView;
    }

    public DishOnSale getDishDetails() {
        // for now assume it's a new dish.
        assert mexistingDish == false;

        DishOnSale dishOnSale = new DishOnSale();
        String dishName = etDishEditDishName.getText().toString();
        String dishInfo = etDishEditDishInfo.getText().toString();
        String dishPrepMethod = etDishEditDishPrepMethod.getText().toString();

        String dishCusine = acTvDishEditCuisine.getText().toString();
        boolean vegitarian = cbVegitarian.isChecked();
        String dishPriceStr = etDishEditPrice.getText().toString().replaceAll("[^0-9]", "");
        double dishPrice = 0.0;
        if (dishPriceStr != "") {
            dishPrice = Double.valueOf(dishPriceStr) / 100;
        }
        Double dishQty = 0.0;
        String quantity = etDishEditQuantity.getText().toString();
        if (quantity != null && !quantity.isEmpty()) {
            dishQty = Double.valueOf(quantity);
        }
        boolean pickup = cbDishEditPickUp.isChecked();
        boolean delivery = cbDishEditDelivery.isChecked();
        String mToDate = ((TextView) rlDishEditToDatePicker.findViewById(R.id.tvDishEditDatePicker)).getText().toString();
        String mToTime = ((TextView) rlDishEditToDatePicker.findViewById(R.id.tvDishEditTimePicker)).getText().toString();
        double qtyPerUnit = 0;
        String qPerUnit = etDishQtyPerUnit.getText().toString();
        if (qPerUnit != null && !qPerUnit.isEmpty()) {
            qtyPerUnit = Double.valueOf(qPerUnit);
        }
        String mUnit = spDishUnit.getSelectedItem().toString();

//        Date date = lDishEditToDatePicker.get
        dishOnSale.setmUnitPrice(dishPrice);
        dishOnSale.setmUnitsOnSale(dishQty.intValue()); // TODO.... maintain int for all quantities
        dishOnSale.setmPickUp(pickup);
        dishOnSale.setmDelivery(delivery);
        dishOnSale.setmToDate(mToDate);
        dishOnSale.setmToTime(mToTime);
        dishOnSale.setmMeasure(mUnit);
        dishOnSale.setmQtyPerUnit(qtyPerUnit);


        Dish dish = dishOnSale.getmDish();
        dish.setmChef(AppGlobalState.getmCurrentFoodie());
        dish.setmDishName(dishName);
        dish.setmCusine(dishCusine);
        dish.setmDishInfo(dishInfo);
        dish.setmPrepMethod(dishPrepMethod);
        dish.setmVegitarian(vegitarian);

        return dishOnSale;
    }

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

    public void initFields() {
        //TODO: populate  fields if applicable. i.e. mDish != null
        TextView tvDishEditFromDateHdr = (TextView) (rlDishEditFromDatePicker.findViewById(R.id.tvDishEditDateHdr));
        tvDishEditFromDateHdr.setText("FROM");
        TextView tvDishEditToDateHdr = (TextView) (rlDishEditToDatePicker.findViewById(R.id.tvDishEditDateHdr));
        tvDishEditToDateHdr.setText("TO");
        //populate  fields if applicable. i.e. mDish != null
        if (mDish != null) {
            initEditTextView(etDishEditDishName, mDish.getmDish().getmDishName());
            initAutoCompleteTextView(acTvDishEditCuisine, mDish.getmDish().getmCusine());
            //if (mDish.getmDish().getmIsVegan() == true) //TODO: enable this after Dish model is updated
            cbVegitarian.setChecked(true);
            initEditTextView(etDishEditPrice, "" + mDish.getmDish().getmPrice());
            initEditTextView(etDishEditQuantity, "" + mDish.getmDish().getmQty());

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

            initEditTextView(etDishQtyPerUnit, "" + mDish.getmQtyPerUnit());
            if (mDish.getmMeasure() != null)
                spDishUnit.setSelection((mDish.getmMeasure().equals(DishOnSale.Measure.Grams)) ? 0 : 1);

            // TODO: set image from read only to edit mode
            /*private TextView tvDishEditDishImage;
            private ImageView ivDishEditDishImage;*/

        }
    }

    @Override
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<CharSequence> aCuisine = ArrayAdapter.createFromResource(
                mContext, R.array.cuisine_picker_array, android.R.layout.simple_dropdown_item_1line);
        acTvDishEditCuisine.setAdapter(aCuisine);

        /* Set Text Watcher listener */
        etDishEditPrice.addTextChangedListener(dishPriceWatcher);

        TextView tvDishEditFromDateHdr = (TextView) rlDishEditFromDatePicker.findViewById(R.id.tvDishEditDateHdr);
        tvDishEditFromDateHdr.setText("AVILABLE FROM");

        TextView tvDishEditFromDatePicker = (TextView) rlDishEditFromDatePicker.findViewById(R.id.tvDishEditDatePicker);
        tvDishEditFromDatePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerInput = rlDishEditFromDatePicker.getId();
                DatePickerDialogFragment newFragment = new DatePickerDialogFragment();
                newFragment.setCallback(ondate);
                newFragment.show(mContext.getSupportFragmentManager(), "DatePicker");
            }
        });

        TextView tvDishEditFromTimePicker = (TextView) rlDishEditFromDatePicker.findViewById(R.id.tvDishEditTimePicker);
        tvDishEditFromTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerInput = rlDishEditFromDatePicker.getId();
                TimePickerDialogFragment newFragment = new TimePickerDialogFragment();
                newFragment.setCallback(onTime);
                newFragment.show(mContext.getSupportFragmentManager(), "TimePicker");
            }
        });


        TextView tvDishEditToDateHdr = (TextView) rlDishEditToDatePicker.findViewById(R.id.tvDishEditDateHdr);
        tvDishEditToDateHdr.setText("TO");
        TextView tvDishEditToDatePicker = (TextView) rlDishEditToDatePicker.findViewById(R.id.tvDishEditDatePicker);
        tvDishEditToDatePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerInput = rlDishEditToDatePicker.getId();
                DatePickerDialogFragment newFragment = new DatePickerDialogFragment();
                newFragment.setCallback(ondate);
                newFragment.show(mContext.getSupportFragmentManager(), "DatePicker");
            }
        });

        TextView tvDishEditToTimePicker = (TextView) rlDishEditToDatePicker.findViewById(R.id.tvDishEditTimePicker);
        tvDishEditToTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerInput = rlDishEditToDatePicker.getId();
                TimePickerDialogFragment newFragment = new TimePickerDialogFragment();
                newFragment.setCallback(onTime);
                newFragment.show(mContext.getSupportFragmentManager(), "TimePicker");
            }
        });


        tvDishEditDishImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DishImageHelper.setDishImage(ivDishEditDishImage, getApplicationContext());
/*                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image*//*");
                startActivityForResult(photoPickerIntent, 1);*/

/*                Intent pickIntent = new Intent();
                pickIntent.setType("image*//*");
                pickIntent.setAction(Intent.ACTION_GET_CONTENT);

                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                String pickTitle = "Select or take a new Picture"; // Or get from strings.xml
                Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
                chooserIntent.putExtra
                        (
                                Intent.EXTRA_INITIAL_INTENTS,
                                new Intent[] { takePhotoIntent }
                        );

                startActivityForResult(chooserIntent, 1);*/


                // Determine Uri of camera image to save.
                final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
                root.mkdirs();
                final String fname = "img_" + System.currentTimeMillis() + ".jpg"; //Utils.getUniqueImageFilename();
                final File sdImageMainDirectory = new File(root, fname);
                outputFileUri = Uri.fromFile(sdImageMainDirectory);

                // Camera.
                final List<Intent> cameraIntents = new ArrayList<Intent>();
                final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                final PackageManager packageManager = mContext.getPackageManager();
                final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
                for (ResolveInfo res : listCam) {
                    final String packageName = res.activityInfo.packageName;
                    final Intent intent = new Intent(captureIntent);
                    intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    intent.setPackage(packageName);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    cameraIntents.add(intent);
                }

                // Filesystem.
                final Intent galleryIntent = new Intent();
                //galleryIntent.setType("image*/*");
                //galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setAction(Intent.ACTION_PICK);

                // Chooser of filesystem options.
                final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

                // Add the camera options.
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

                startActivityForResult(chooserIntent, 120);
            }
        });
    }

    private EditText initTextView(View view, int id, String text) {
        EditText etView = (EditText) view.findViewById(id);
        if (text != null && !text.isEmpty()) {
            etView.setText(text);
        }
        return etView;
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

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            cal.set(Calendar.MONTH, monthOfYear);
            String formattedDate = new SimpleDateFormat("E, MMM d, yyyy").format(cal.getTime());

            if (datePickerInput == rlDishEditFromDatePicker.getId())
                ((TextView) rlDishEditFromDatePicker.findViewById(R.id.tvDishEditDatePicker)).setText(formattedDate);
            else if (datePickerInput == rlDishEditToDatePicker.getId())
                ((TextView) rlDishEditToDatePicker.findViewById(R.id.tvDishEditDatePicker)).setText(formattedDate);

        }
    };

    TimePickerDialog.OnTimeSetListener onTime = new TimePickerDialog.OnTimeSetListener() {
        //onTimeSet() callback method
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //Get the AM or PM for current time
            String aMpM = "AM";
            if (hourOfDay > 11) {
                aMpM = "PM";
            }

            //Make the 24 hour time format to 12 hour time format
            int currentHour;
            if (hourOfDay > 11) {
                currentHour = hourOfDay - 12;
            } else {
                currentHour = hourOfDay;
            }

            //Display the user changed time on TextView
            if (timePickerInput == rlDishEditFromDatePicker.getId())
                ((TextView) rlDishEditFromDatePicker.findViewById(R.id.tvDishEditTimePicker)).setText(String.valueOf(currentHour)
                        + " : " + String.valueOf(minute) + " " + aMpM);
            else if (timePickerInput == rlDishEditToDatePicker.getId())
                ((TextView) rlDishEditToDatePicker.findViewById(R.id.tvDishEditTimePicker)).setText(String.valueOf(currentHour)
                        + " : " + String.valueOf(minute) + " " + aMpM);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 120) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                Uri selectedImageUri;
                if (isCamera) {
                    selectedImageUri = outputFileUri;
                } else {
                    selectedImageUri = data == null ? null : data.getData();
                }
                if (selectedImageUri != null) {
                    ivDishEditDishImage.setImageURI(null);
                    ivDishEditDishImage.setImageURI(selectedImageUri);
                }
            }
        }
    }

    public void saveDish() {

    }
}

