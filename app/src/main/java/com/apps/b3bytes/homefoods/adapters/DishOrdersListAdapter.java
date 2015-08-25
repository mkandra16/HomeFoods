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

import com.apps.b3bytes.homefoods.State.AppGlobalState;
import com.apps.b3bytes.homefoods.models.DishOnSale;
import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.models.FoodieDishOrder;
import com.apps.b3bytes.homefoods.utils.ListViewHelper;

import java.util.List;

public class DishOrdersListAdapter extends ArrayAdapter<FoodieDishOrder> {

    private final List<FoodieDishOrder> list;
    private final Activity context;
    private final ListView lvChefOrders;
    private final LinearLayout llOneChefOrder;

    public DishOrdersListAdapter(Activity context, List<FoodieDishOrder> list, ListView lvChefOrders, LinearLayout llOneChefOrder) {
        super(context, R.layout.one_dish_order_item, list);
        this.context = context;
        this.list = list;
        this.lvChefOrders = lvChefOrders;
        this.llOneChefOrder = llOneChefOrder;
    }

    static class ViewHolder {
        protected TextView tvOrderReviewDishName;
        protected TextView tvOrderReviewDishQuantityNum;
        protected TextView tvOrderReviewDishPrice;
        protected TextView tvOrderReviewDishQuantityChange;
        protected EditText etOrderReviewDishQuantityNum;
        protected TextView tvOrderReviewDishQuantityUpdate;
        protected TextView tvOrderReviewDishQuantityDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.one_dish_order_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvOrderReviewDishName = (TextView) view.findViewById(R.id.tvOrderReviewDishName);
            viewHolder.tvOrderReviewDishQuantityNum = (TextView) view.findViewById(R.id.tvOrderReviewDishQuantityNum);
            viewHolder.tvOrderReviewDishPrice = (TextView) view.findViewById(R.id.tvOrderReviewDishPrice);
            viewHolder.tvOrderReviewDishQuantityChange = (TextView) view.findViewById(R.id.tvOrderReviewDishQuantityChange);
            viewHolder.etOrderReviewDishQuantityNum = (EditText) view.findViewById(R.id.etOrderReviewDishQuantityNum);
            viewHolder.tvOrderReviewDishQuantityUpdate = (TextView) view.findViewById(R.id.tvOrderReviewDishQuantityUpdate);
            viewHolder.tvOrderReviewDishQuantityDelete = (TextView) view.findViewById(R.id.tvOrderReviewDishQuantityDelete);

            viewHolder.tvOrderReviewDishQuantityChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(view.getContext(), "Link1 : " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                    viewHolder.tvOrderReviewDishQuantityChange.setVisibility(View.GONE);
                    viewHolder.tvOrderReviewDishQuantityNum.setVisibility(View.GONE);

                    viewHolder.etOrderReviewDishQuantityNum.setText(viewHolder.tvOrderReviewDishQuantityNum.getText());
                    viewHolder.etOrderReviewDishQuantityNum.setVisibility(View.VISIBLE);
                    viewHolder.tvOrderReviewDishQuantityUpdate.setVisibility(View.VISIBLE);
                    viewHolder.tvOrderReviewDishQuantityDelete.setVisibility(View.VISIBLE);

                }
            });

            viewHolder.tvOrderReviewDishQuantityUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewHolder.etOrderReviewDishQuantityNum.setVisibility(View.GONE);
                    viewHolder.tvOrderReviewDishQuantityUpdate.setVisibility(View.GONE);
                    viewHolder.tvOrderReviewDishQuantityDelete.setVisibility(View.GONE);

                    String quan = viewHolder.etOrderReviewDishQuantityNum.getText().toString();
                    int q = Integer.parseInt(quan);
                    if (q == 0) {
                        AppGlobalState.gCart.setQty(list.get(position).getmDishOnSale(), 0);
                        list.remove(position);
                        notifyDataSetChanged();
                        notifyDataSetInvalidated();
                        viewHolder.etOrderReviewDishQuantityNum.setVisibility(View.GONE);
                        viewHolder.tvOrderReviewDishQuantityUpdate.setVisibility(View.GONE);
                        viewHolder.tvOrderReviewDishQuantityDelete.setVisibility(View.GONE);
                        viewHolder.tvOrderReviewDishQuantityChange.setVisibility(View.VISIBLE);
                        viewHolder.tvOrderReviewDishQuantityNum.setVisibility(View.VISIBLE);
                        ListViewHelper.setListViewHeightBasedOnChildren(lvChefOrders);
                        if (lvChefOrders.getAdapter().getCount() == 0) {
                            llOneChefOrder.setVisibility(View.GONE);
                        }
                    } else {
                        viewHolder.tvOrderReviewDishQuantityNum.setText(quan);
                        AppGlobalState.gCart.setQty(list.get(position).getmDishOnSale(), q);
                        list.get(position).setmQty(q);
                        viewHolder.tvOrderReviewDishPrice.setText(context.getString(R.string.Rs) + " "
                                + AppGlobalState.gCart.getGrandTotalByDish(list.get(position).getmDishOnSale()));

                        viewHolder.tvOrderReviewDishQuantityChange.setVisibility(View.VISIBLE);
                        viewHolder.tvOrderReviewDishQuantityNum.setVisibility(View.VISIBLE);
                    }
                    /* TODO: update total price */
                }
            });

            viewHolder.tvOrderReviewDishQuantityDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppGlobalState.gCart.setQty(list.get(position).getmDishOnSale(), 0);
                    list.remove(position);
                    notifyDataSetChanged();
                    notifyDataSetInvalidated();
                    viewHolder.etOrderReviewDishQuantityNum.setVisibility(View.GONE);
                    viewHolder.tvOrderReviewDishQuantityUpdate.setVisibility(View.GONE);
                    viewHolder.tvOrderReviewDishQuantityDelete.setVisibility(View.GONE);
                    viewHolder.tvOrderReviewDishQuantityChange.setVisibility(View.VISIBLE);
                    viewHolder.tvOrderReviewDishQuantityNum.setVisibility(View.VISIBLE);
                    ListViewHelper.setListViewHeightBasedOnChildren(lvChefOrders);
                    if (lvChefOrders.getAdapter().getCount() == 0) {
                        llOneChefOrder.setVisibility(View.GONE);
                    }
                    /* TODO: update total price */
                }
            });

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();

        holder.tvOrderReviewDishName.setText(list.get(position).getmDishOnSale().getmDish().getmDishName());
        holder.tvOrderReviewDishQuantityNum.setText(""
                + AppGlobalState.gCart.dishQtyInCart(list.get(position).getmDishOnSale()));
        holder.tvOrderReviewDishPrice.setText(context.getString(R.string.Rs) + " " +
                AppGlobalState.gCart.getGrandTotalByDish(list.get(position).getmDishOnSale()));

/*        if (position % 2 == 1) {
            view.setBackgroundColor(Color.BLUE);
        } else {
            view.setBackgroundColor(Color.CYAN);
        }*/

        return view;
    }

}


