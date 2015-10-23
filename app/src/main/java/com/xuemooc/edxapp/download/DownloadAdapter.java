package com.example.chaos.downloadlibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by chaos on 2015/10/23.
 */
public class DownloadAdapter extends BaseAdapter {
    private List<Map<String, String>> data;
    private Context context;
    private View.OnClickListener click;

    public DownloadAdapter(List<Map<String, String>> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void refresh(List<Map<String, String>> data){
        this.data = data;
        this.notifyDataSetChanged();
    }

    public void setClick(View.OnClickListener click) {
        this.click = click;
    }

    public View.OnClickListener getClick() {
        return click;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Map<String, String> bean = data.get(position);
        ViewHolder holder = null;

        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.resourceName = (TextView) convertView.findViewById(R.id.tv_resource_name);
            holder.startDownload = (Button) convertView.findViewById(R.id.btn_start);
            holder.pauseDownload = (Button) convertView.findViewById(R.id.btn_pause);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.resourceName.setText(bean.get("name"));

        return convertView;
    }

    private class ViewHolder {
        public TextView resourceName;
        public Button startDownload;
        public Button pauseDownload;
    }
}
