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
    private static final int VIEW_TYPE_HEADER = 0; // first view in the list is header
    private static final int VIEW_TYPE_ITEM = 1; // first view in the list is header

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
        return items.size()+1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        if (viewType == VIEW_TYPE_HEADER) {
            View parent = LayoutInflater.from(context).inflate(R.layout.slider_menu_header, viewGroup, false);
            return ViewHolder.newInstance(parent, viewType);
        } else if (viewType == VIEW_TYPE_ITEM) {
            View parent = LayoutInflater.from(context).inflate(R.layout.drawer_list_item, viewGroup, false);
            return ViewHolder.newInstance(parent, viewType);
        }
        return null;
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final NavDrawerItem item;
        final int pos = position;
        if (viewHolder.holderId == 0) {

        } else if (viewHolder.holderId == 1) {
            item = items.get(position - 1);
            viewHolder.setTitle(item.getTitle());
            viewHolder.setimgIcon(item.getIcon());
            viewHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(item, pos);
                }
            });
        }
    }

    // With the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return VIEW_TYPE_HEADER;

        return VIEW_TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private  View parent;
        private  int holderId;
        private  TextView txtTitle;
        private  ImageView imgIcon;
        private  TextView tvUserName;
        private  TextView tvUserEmail;
        private  ImageView ivUserProfile;


        public static ViewHolder newInstance(View parent, int viewType) {
            if (viewType == VIEW_TYPE_HEADER) {
                ImageView ivUserProfile = (ImageView) parent.findViewById(R.id.ivUserProfile);
                TextView tvUserName = (TextView) parent.findViewById(R.id.tvUserName);
                TextView tvUserEmail = (TextView) parent.findViewById(R.id.tvUserEmail);
                return new ViewHolder(parent, tvUserName, tvUserEmail, ivUserProfile, 0);
            } else if (viewType == VIEW_TYPE_ITEM) {
                ImageView imgIcon = (ImageView) parent.findViewById(R.id.icon);
                TextView txtTitle = (TextView) parent.findViewById(R.id.title);
                return new ViewHolder(parent, txtTitle, imgIcon, 1);
            }
            return null;
        }

        private ViewHolder(View parent, TextView tvUserName, TextView tvUserEmail, ImageView ivUserProfile, int holderId) {
            super(parent);
            this.holderId = holderId;
            this.parent = parent;
            this.tvUserName = tvUserName;
            this.tvUserEmail = tvUserEmail;
            this.ivUserProfile = ivUserProfile;
        }

        private ViewHolder(View parent, TextView txtTitle, ImageView imgIcon, int holderId) {
            super(parent);
            this.holderId = holderId;
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
