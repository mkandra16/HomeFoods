package com.apps.b3bytes.homefoods.activities;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.adapters.DishOrdersListAdapter;
import com.apps.b3bytes.homefoods.models.OneDishOrder;
import com.apps.b3bytes.homefoods.utils.ListViewHelper;

import java.util.ArrayList;
import java.util.List;

public class OrderReview extends ActionBarActivity {
    private LinearLayout llRoot;
    private RelativeLayout rlAddNewChef;
    private int currentId;
    private Button bAddNewChef;
    private LayoutInflater inflater;

    private int chefIdx;
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
        setContentView(R.layout.activity_order_review);

        inflater = LayoutInflater.from(getApplicationContext());
        llRoot = (LinearLayout) findViewById(R.id.llRoot);
        currentId = (int) llRoot.getId();

        for (int i = 0; i < chefNamesArray.length; i++) {
            llRoot.addView(createOneChefOrderLayout(currentId, i));
            currentId++;
        }

    }

    private LinearLayout createOneChefOrderLayout(int currentId, int idx) {

        LinearLayout llOneChefOrder = (LinearLayout) inflater.inflate(R.layout.one_chef_order, null, false);
        RelativeLayout rlOneChefOrder = (RelativeLayout) llOneChefOrder.findViewById(R.id.rlOneChefOrder);

        TextView tvChefName = (TextView) rlOneChefOrder.findViewById(R.id.tvChefName);
        tvChefName.setText(chefNamesArray[idx]);
        tvChefName.setTextSize(24);
        tvChefName.setTextColor(Color.BLACK);

        List<OneDishOrder> list = new ArrayList<OneDishOrder>();
        ListView lvChefOrders = (ListView) rlOneChefOrder.findViewById(R.id.lvChefOrders);
        ArrayAdapter<OneDishOrder> aOneDishOrder = new DishOrdersListAdapter(OrderReview.this, list, lvChefOrders, llOneChefOrder);
        lvChefOrders.setAdapter(aOneDishOrder);

        int numDishes = dishNamesArray[idx].length;
        for (int i = 0; i < numDishes; i++) {
            list.add(new OneDishOrder(dishNamesArray[idx][i], dishQuantitiesArray[idx][i], dishUnitPriceArray[idx][i]));
        }
        aOneDishOrder.notifyDataSetChanged();
        ListViewHelper.setListViewHeightBasedOnChildren(lvChefOrders);

        return llOneChefOrder;
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
