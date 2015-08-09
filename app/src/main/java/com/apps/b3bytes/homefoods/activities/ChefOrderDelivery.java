package com.apps.b3bytes.homefoods.activities;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.adapters.ChefDeliveryOrdersListAdapter;
import com.apps.b3bytes.homefoods.models.OneDishOrder;
import com.apps.b3bytes.homefoods.utils.ListViewHelper;

import java.util.ArrayList;
import java.util.List;

public class ChefOrderDelivery extends ActionBarActivity {
    Context context = this;

    /* TODO: TEST DATA */
    String[] dishNamesArray = {"Roti Paratha", "Curd Rice", "South Indian Breakfast", "Salad", "Chicken Tikka", "Biryani", "Pizza", "Cupcakes", "Sandwhich", "Burger", "PanCake"};
    int[] dishQuantitiesArray = {2, 1, 3, 1, 2, 4, 1, 12, 3, 4, 10};
    double[] dishUnitPriceArray = {75, 120, 175, 90, 125, 150, 250, 25, 75, 80, 40};
    /* TODO: END TEST DATA */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_order_delivery);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("Order Deliver");
        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        TextView tvChefDeliveryTotalNumDishes = (TextView) findViewById(R.id.tvChefDeliveryTotalNumDishes);
        TextView tvChefDeliveryTotalPriceVal = (TextView) findViewById(R.id.tvChefDeliveryTotalPriceVal);

        List<OneDishOrder> list = new ArrayList<OneDishOrder>();
        ListView lvChefDeliveryOrders = (ListView) findViewById(R.id.lvChefDeliveryOrders);
        ArrayAdapter<OneDishOrder> aOneDishOrder = new ChefDeliveryOrdersListAdapter(ChefOrderDelivery.this, list, lvChefDeliveryOrders);
        lvChefDeliveryOrders.setAdapter(aOneDishOrder);

        int numOrders = dishNamesArray.length;
        int numDishes = 0;
        double totalPrice = 0;
        for (int i = 0; i < numOrders; i++) {
            list.add(new OneDishOrder(dishNamesArray[i], dishQuantitiesArray[i], dishUnitPriceArray[i]));
            numDishes += dishQuantitiesArray[i];
            totalPrice += (dishQuantitiesArray[i] * dishUnitPriceArray[i]);
        }
        aOneDishOrder.notifyDataSetChanged();
        ListViewHelper.setListViewHeightBasedOnChildren(lvChefDeliveryOrders);

        tvChefDeliveryTotalNumDishes.setText("" + numDishes);
        tvChefDeliveryTotalPriceVal.setText(context.getString(R.string.Rs) + " " + totalPrice);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chef_order_delivery, menu);
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

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
