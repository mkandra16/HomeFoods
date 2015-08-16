package com.apps.b3bytes.homefoods.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.b3bytes.homefoods.R;
import com.apps.b3bytes.homefoods.models.NavDrawerItem;

import java.util.List;


public class NavDrawerRVAdapter extends RecyclerView.Adapter<NavDrawerRVAdapter.ViewHolder> {
    private List<NavDrawerItem> items;
    private ItemClickListener itemClickListener;

    public NavDrawerRVAdapter(List<NavDrawerItem> objects) {
        this.items = objects;
    }

    public void SetOnItemClickListener(final ItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public interface ItemClickListener {
        public void onItemClick(NavDrawerItem item, int position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        Context context = viewGroup.getContext();

        View parent = LayoutInflater.from(context).inflate(R.layout.drawer_list_item, viewGroup, false);
        return ViewHolder.newInstance(parent);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final NavDrawerItem item = items.get(position);
        final int pos = position;
        viewHolder.setTitle(item.getTitle());
        viewHolder.setimgIcon(item.getIcon());
        viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(item, pos);
            }
        });

    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private View parent;
        private TextView txtTitle;
        private ImageView imgIcon;

        public static ViewHolder newInstance(View parent) {
                ImageView imgIcon = (ImageView) parent.findViewById(R.id.icon);
                TextView txtTitle = (TextView) parent.findViewById(R.id.title);
                return new ViewHolder(parent, txtTitle, imgIcon);
        }

        private ViewHolder(View parent, TextView txtTitle, ImageView imgIcon) {
            super(parent);
            this.parent = parent;
            this.txtTitle = txtTitle;
            this.imgIcon = imgIcon;
        }

        public void setTitle(CharSequence text) {
            txtTitle.setText(text);
        }

        public void setimgIcon(int position) {
            imgIcon.setImageResource(position);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }
    }
}
