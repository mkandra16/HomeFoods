package com.apps.b3bytes.homefoods.activities;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.adapters.ChefDeliveryOrdersListAdapter;
import com.apps.b3bytes.homefoods.adapters.ChefHomePageOrdersListAdapter;
import com.apps.b3bytes.homefoods.adapters.viewPagerChefHomeAdapter;
import com.apps.b3bytes.homefoods.models.OneDishOrder;
import com.apps.b3bytes.homefoods.utils.ListViewHelper;
import com.apps.b3bytes.homefoods.widgets.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class ChefHomePage extends AppCompatActivity {
    Context context = this;
    ViewPager pager;
    viewPagerChefHomeAdapter viewPagerAdapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Orders", "Snapshot"};
    int Numboftabs = 2;

    /* TODO: TEST DATA */
    String[] dishNamesArray = {"Roti Paratha", "Curd Rice", "South Indian Breakfast", "Salad", "Chicken Tikka", "Biryani", "Pizza", "Cupcakes", "Sandwhich", "Burger", "PanCake"};
    int[] dishQuantitiesArray = {2, 1, 3, 1, 2, 4, 1, 12, 3, 4, 10};
    double[] dishUnitPriceArray = {75, 120, 175, 90, 125, 150, 250, 25, 75, 80, 40};
    /* TODO: END TEST DATA */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_home_page);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("Chef Home");
        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        viewPagerAdapter = new viewPagerChefHomeAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.viewPagerChefHome);
        pager.setAdapter(viewPagerAdapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.slidingTabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.KPTheme_AdriftInDreams_colorAccent);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

/*        List<OneDishOrder> list = new ArrayList<OneDishOrder>();
        ListView lvChefHomePagePendingOrders = (ListView) findViewById(R.id.lvChefHomePagePendingOrders);
        ArrayAdapter<OneDishOrder> aOneDishOrder = new ChefHomePageOrdersListAdapter(ChefHomePage.this, list, lvChefHomePagePendingOrders);
        lvChefHomePagePendingOrders.setAdapter(aOneDishOrder);

        int numOrders = dishNamesArray.length;
        for (int i = 0; i < numOrders; i++) {
            list.add(new OneDishOrder(dishNamesArray[i], dishQuantitiesArray[i], dishUnitPriceArray[i]));
        }

        aOneDishOrder.notifyDataSetChanged();
        ListViewHelper.setListViewHeightBasedOnChildren(lvChefHomePagePendingOrders);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chef_home_page, menu);
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
