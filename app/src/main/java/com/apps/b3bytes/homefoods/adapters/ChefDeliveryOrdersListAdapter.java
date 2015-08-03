package com.apps.b3bytes.homefoods.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.models.OneDishOrder;
import com.apps.b3bytes.homefoods.utils.ListViewHelper;

import java.util.List;

public class ChefDeliveryOrdersListAdapter extends ArrayAdapter<OneDishOrder> {

    private final List<OneDishOrder> list;
    private final Activity context;
    private final ListView lvChefDeliveryOrders;

    public ChefDeliveryOrdersListAdapter(Activity context, List<OneDishOrder> list, ListView lvChefDeliveryOrders) {
        super(context, R.layout.chef_deliver_dish_item, list);
        this.context = context;
        this.list = list;
        this.lvChefDeliveryOrders = lvChefDeliveryOrders;
    }

    static class ViewHolder {
        protected TextView tvChefDeliverDishName;
        protected TextView tvChefDeliverDishQuantity;
        protected TextView tvChefDeliverDishPrice;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.chef_deliver_dish_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvChefDeliverDishName = (TextView) view.findViewById(R.id.tvChefDeliverDishName);
            viewHolder.tvChefDeliverDishQuantity = (TextView) view.findViewById(R.id.tvChefDeliverDishQuantity);
            viewHolder.tvChefDeliverDishPrice = (TextView) view.findViewById(R.id.tvChefDeliverDishPrice);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();

        holder.tvChefDeliverDishName.setText(list.get(position).getDishName());
        holder.tvChefDeliverDishQuantity.setText("" + list.get(position).getQuantity());
        holder.tvChefDeliverDishPrice.setText(context.getString(R.string.Rs) + " " + (list.get(position).getQuantity() * list.get(position).getUnitPrice()));

/*        if (position % 2 == 1) {
            view.setBackgroundColor(Color.BLUE);
        } else {
            view.setBackgroundColor(Color.CYAN);
        }*/

        return view;
    }

}


