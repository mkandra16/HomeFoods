package com.apps.b3bytes.homefoods.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.models.OneDishOrder;

import java.util.List;

public class ChefHomePageOrdersListAdapter extends ArrayAdapter<OneDishOrder> {

    private final List<OneDishOrder> list;
    private final Activity context;
    private final ListView lvChefHomePagePendingOrders;

    public ChefHomePageOrdersListAdapter(Activity context, List<OneDishOrder> list, ListView lvChefHomePagePendingOrders) {
        super(context, R.layout.chef_deliver_dish_item, list);
        this.context = context;
        this.list = list;
        this.lvChefHomePagePendingOrders = lvChefHomePagePendingOrders;
    }

    static class ViewHolder {
        protected TextView tvOrderNumHdr;
        protected TextView tvOrderNumVal;
        protected ImageView ivPickupDelivery;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.chef_home_page_orders_listview_item, null);
            final ViewHolder viewHolder = new ViewHolder();
           // viewHolder.tvOrderNumHdr = (TextView) view.findViewById(R.id.tvOrderNumHdr);
            viewHolder.tvOrderNumVal = (TextView) view.findViewById(R.id.tvOrderNumVal);
            viewHolder.ivPickupDelivery = (ImageView) view.findViewById(R.id.ivPickupDelivery);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();

       // holder.tvOrderNumHdr.setText(list.get(position).getDishName());
       // holder.tvOrderNumVal.setText("" + list.get(position).getQuantity());







/*        if (position % 2 == 1) {
            view.setBackgroundColor(Color.BLUE);
        } else {
            view.setBackgroundColor(Color.CYAN);
        }*/

        return view;
    }

}


