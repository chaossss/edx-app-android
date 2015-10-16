package com.xuemooc.edxapp.view.subview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuemooc.edxapp.R;

/**
 * Created by chaossss on 2015/10/10.
 */
public class MyDownloadIncompleteItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView sum;
    private TextView download;
    private TextView itemName;
    private TextView downloadState;

    private ImageView stateIcon;

    private boolean isPaused;

    public MyDownloadIncompleteItemHolder(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

        sum = (TextView) itemView.findViewById(R.id.my_download_incomplete_item_sum);
        download = (TextView) itemView.findViewById(R.id.my_download_incomplete_item_download);
        itemName = (TextView) itemView.findViewById(R.id.my_download_incomplete_item_name);
        downloadState = (TextView) itemView.findViewById(R.id.my_download_incomplete_item_state_text);

        stateIcon = (ImageView) itemView.findViewById(R.id.my_download_incomplete_item_state_icon);
    }

    public void setItemName(String itemName){
        this.itemName.setText(itemName);
    }

    public void setDownloadPercent(String downloadPercent){
        this.download.setText(downloadPercent);
    }

    public void setDownloadSum(String downloadSum){
        this.sum.setText(downloadSum);
    }

    public void changeDownloadState(){
        isPaused = !isPaused;

        if(isPaused){
            stateIcon.setImageResource(R.drawable.profile);
            downloadState.setText(R.string.my_download_incomplete_item_pause);
        } else {
            stateIcon.setImageResource(R.drawable.profile);
            downloadState.setText(R.string.my_download_incomplete_item_downloading);
        }
    }

    @Override
    public void onClick(View v) {
        changeDownloadState();
    }
}
