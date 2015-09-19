package com.xuemooc.edxapp.view.subview;

/**
 * Created by chaossss on 2015/9/19.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.R.attr;
import com.mikepenz.materialdrawer.R.color;
import com.mikepenz.materialdrawer.R.layout;
import com.mikepenz.materialdrawer.model.BaseDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.ColorfulBadgeable;
import com.mikepenz.materialdrawer.util.PressedEffectStateListDrawable;
import com.mikepenz.materialdrawer.util.UIUtils;
import com.xuemooc.edxapp.R;

public class MyDrawerItem extends BaseDrawerItem<MyDrawerItem> implements ColorfulBadgeable<MyDrawerItem> {
    private String badge;
    private int badgeTextColor = 0;
    private int badgeBackgroundRes = 0;

    public MyDrawerItem() {
    }

    public MyDrawerItem withBadge(String badge) {
        this.badge = badge;
        return this;
    }

    public String getBadge() {
        return this.badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public MyDrawerItem withBadgeTextColor(int color) {
        this.badgeTextColor = color;
        return this;
    }

    public int getBadgeTextColor() {
        return this.badgeTextColor;
    }

    public void setBadgeTextColor(int color) {
        this.badgeTextColor = color;
    }

    public void setBadgeBackgroundResource(int res) {
        this.badgeBackgroundRes = res;
    }

    public int getBadgeBackgroundResource() {
        return this.badgeBackgroundRes;
    }

    public MyDrawerItem withBadgeBackgroundResource(int res) {
        this.badgeBackgroundRes = res;
        return this;
    }

    public String getType() {
        return "SECONDARY_ITEM";
    }

    public int getLayoutRes() {
        return layout.material_drawer_item_secondary;
    }

    public View convertView(LayoutInflater inflater, View convertView, ViewGroup parent) {
        Context ctx = parent.getContext();
        MyDrawerItem.ViewHolder viewHolder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.layout_drawer_item, parent, false);
            viewHolder = new MyDrawerItem.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyDrawerItem.ViewHolder)convertView.getTag();
        }

        int selectedColor = UIUtils.decideColor(ctx, this.getSelectedColor(), this.getSelectedColorRes(), attr.material_drawer_selected, color.material_drawer_selected);
        int color;
        if(this.isEnabled()) {
            color = UIUtils.decideColor(ctx, this.getTextColor(), this.getTextColorRes(), attr.material_drawer_secondary_text, R.color.material_drawer_secondary_text);
        } else {
            color = UIUtils.decideColor(ctx, this.getDisabledTextColor(), this.getDisabledTextColorRes(), attr.material_drawer_hint_text, R.color.material_drawer_hint_text);
        }

        int selectedTextColor = UIUtils.decideColor(ctx, this.getSelectedTextColor(), this.getSelectedTextColorRes(), attr.material_drawer_selected_text, R.color.material_drawer_selected_text);
        int iconColor;
        if(this.isEnabled()) {
            iconColor = UIUtils.decideColor(ctx, this.getIconColor(), this.getIconColorRes(), attr.material_drawer_primary_icon, R.color.material_drawer_primary_icon);
        } else {
            iconColor = UIUtils.decideColor(ctx, this.getDisabledIconColor(), this.getDisabledIconColorRes(), attr.material_drawer_hint_text, R.color.material_drawer_hint_text);
        }

        int selectedIconColor = UIUtils.decideColor(ctx, this.getSelectedIconColor(), this.getSelectedIconColorRes(), attr.material_drawer_selected_text, R.color.material_drawer_selected_text);
        UIUtils.setBackground(viewHolder.view, UIUtils.getDrawerItemBackground(selectedColor));
        if(this.getNameRes() != -1) {
            viewHolder.name.setText(this.getNameRes());
        } else {
            viewHolder.name.setText(this.getName());
        }

        viewHolder.name.setTextColor(UIUtils.getTextColorStateList(color, selectedTextColor));

        if(this.getTypeface() != null) {
            viewHolder.name.setTypeface(this.getTypeface());
        }

        Drawable icon = UIUtils.decideIcon(ctx, this.getIcon(), this.getIIcon(), this.getIconRes(), iconColor, this.isIconTinted());
        Drawable selectedIcon = UIUtils.decideIcon(ctx, this.getSelectedIcon(), this.getIIcon(), this.getSelectedIconRes(), selectedIconColor, this.isIconTinted());
        if(icon != null) {
            if(selectedIcon != null) {
                viewHolder.icon.setImageDrawable(UIUtils.getIconStateList(icon, selectedIcon));
            } else if(this.isIconTinted()) {
                viewHolder.icon.setImageDrawable(new PressedEffectStateListDrawable(icon, iconColor, selectedIconColor));
            } else {
                viewHolder.icon.setImageDrawable(icon);
            }

            viewHolder.icon.setVisibility(View.VISIBLE);
        } else {
            viewHolder.icon.setVisibility(View.GONE);
        }

        return convertView;
    }

    private static class ViewHolder {
        private View view;
        private ImageView icon;
        private TextView name;

        private ViewHolder(View view) {
            this.view = view;
            this.name = (TextView)view.findViewById(R.id.drawer_item_name);
            this.icon = (ImageView)view.findViewById(R.id.drawer_item_icon);
        }
    }
}

