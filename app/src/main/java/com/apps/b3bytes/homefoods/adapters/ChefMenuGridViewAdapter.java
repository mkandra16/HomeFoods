package com.apps.b3bytes.homefoods.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.models.DishOnSale;

import java.util.List;

public class ChefMenuGridViewAdapter extends ArrayAdapter<DishOnSale> {

    private final List<DishOnSale> list;
    private final Activity context;
    private final GridView gvChefMenu;

    public ChefMenuGridViewAdapter(Activity context, List<DishOnSale> list, GridView gvChefMenu) {
        super(context, R.layout.chef_menu_grid_item, list);
        this.context = context;
        this.list = list;
        this.gvChefMenu = gvChefMenu;
    }

    static class ViewHolder {
        protected TextView tvMenuGridDishName;
        protected TextView tvMenuGridDishQuantityPosted;
        protected TextView tvMenuGridDishQuantityDelivered;
        protected TextView tvMenuGridDishQuantityPending;
        protected TextView tvMenuGridDishPrice;
        protected ImageView ivMenuGridDishImage;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int halfScreenWidth = (int)(screenWidth *0.5);

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.chef_menu_grid_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvMenuGridDishName = (TextView) view.findViewById(R.id.tvMenuGridDishName);
            viewHolder.tvMenuGridDishQuantityPosted = (TextView) view.findViewById(R.id.tvMenuGridDishQuantityPosted);
            viewHolder.tvMenuGridDishQuantityDelivered = (TextView) view.findViewById(R.id.tvMenuGridDishQuantityDelivered);
            viewHolder.tvMenuGridDishQuantityPending = (TextView) view.findViewById(R.id.tvMenuGridDishQuantityPending);
            viewHolder.tvMenuGridDishPrice = (TextView) view.findViewById(R.id.tvMenuGridDishPrice);
            viewHolder.ivMenuGridDishImage = (ImageView) view.findViewById(R.id.ivMenuGridDishImage);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();

        holder.tvMenuGridDishName.setText(list.get(position).getmDish().getmDishName());
        holder.tvMenuGridDishQuantityPosted.setText("Posted " + list.get(position).getmQtyPerUnit());
        holder.tvMenuGridDishQuantityDelivered.setText("Delivered " + list.get(position).getmUnitsDelivered());
        holder.tvMenuGridDishQuantityPending.setText("Pending " + list.get(position).getmUnitsOrdered());
        holder.tvMenuGridDishPrice.setText(context.getString(R.string.Rs) + " " + (list.get(position).getmUnitPrice()));
        holder.ivMenuGridDishImage.setImageResource(R.drawable.south_indian_breakfast_01);

        return view;
    }

}


