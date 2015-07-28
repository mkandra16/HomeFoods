package com.apps.b3bytes.homefoods.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;

public class OrderReview extends ActionBarActivity {

    Context context = this;

    /* TODO: TEST DATA */
    String[] chefNamesArray = {"Chef Name1", "Chef Name2", "Chef Name 3", "Chef Name 4"};
    String[][] dishNamesArray = {{"Roti Paratha", "Curd Rice"},
            {"South Indian Breakfast"},
            {"Salad", "Chicken Tikka", "Biryani"},
            {"Pizza", "Cupcakes", "Sandwhich", "Burger", "PanCake"}};
    int[][] dishQuantitiesArray = {{2, 1},
            {3},
            {1, 2, 4},
            {1, 12, 3, 4, 10}};
    double[][] dishUnitPriceArray = {{75, 120},
            {175},
            {90, 125, 150},
            {250, 25, 75, 80, 40}};
    /* TODO: END TEST DATA */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int currentId = 10;

        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout rlParent = new RelativeLayout(this);
        ScrollView svParent = new ScrollView(this);

        for (int i = 0; i < chefNamesArray.length; i++) {
            rlParent.addView(createChefNameLayout(currentId, chefNamesArray[i]));
            currentId++;

            int numDishes = dishNamesArray[i].length;
            for (int j = 0; j < numDishes; j++) {
                rlParent.addView(createOneDishOrderLayout(inflater, dishNamesArray[i][j], dishQuantitiesArray[i][j], dishUnitPriceArray[i][j], currentId));
                currentId++;
            }
        }

        svParent.addView(rlParent);

        setContentView(svParent);

    }

    private TextView createChefNameLayout(int currentId, String chefName) {
        TextView textView = new TextView(this);
        textView.setText(chefName);
        textView.setTextColor(Color.BLACK);
        textView.setId(currentId);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (currentId == 10) {
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 1);
        }
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1);
        params.setMargins(5, 0, 0, 8);
        params.addRule(RelativeLayout.BELOW, currentId - 1);
        textView.setLayoutParams(params);

        return textView;
    }

    private RelativeLayout createOneDishOrderLayout(LayoutInflater inflater, String dishName, int quantity, double pricePerUnit, int currentId) {

        RelativeLayout rlOneDish = (RelativeLayout) inflater.inflate(R.layout.one_dish_order, null, false);
        TextView t1 = (TextView) rlOneDish.findViewById(R.id.tvOrderReviewDishName);
        t1.setText(dishName);
        t1.setTextSize(17);
        t1.setTextColor(Color.BLACK);

        TextView t2 = (TextView) rlOneDish.findViewById(R.id.tvOrderReviewDishQuantityNum);
        t2.setText("" + quantity);
        t2.setTextColor(Color.BLACK);

        TextView t3 = (TextView) rlOneDish.findViewById(R.id.tvOrderReviewDishPrice);
        t3.setText("Rs " + pricePerUnit * quantity);
        t3.setTextColor(Color.BLACK);

        rlOneDish.setId(currentId);
        RelativeLayout.LayoutParams rlc3 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlc3.addRule(RelativeLayout.BELOW, currentId - 1);
        rlOneDish.setLayoutParams(rlc3);
        //rlOneDish.setBackgroundColor(0XF5F5F5);

        return rlOneDish;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_checkout, menu);
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
