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
    private ImageView selectIcon;

    private boolean isPaused;
    private boolean isEditing;
    private boolean isSelected;

    public MyDownloadIncompleteItemHolder(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

        sum = (TextView) itemView.findViewById(R.id.my_download_incomplete_item_sum);
        download = (TextView) itemView.findViewById(R.id.my_download_incomplete_item_download);
        itemName = (TextView) itemView.findViewById(R.id.my_download_incomplete_item_name);
        downloadState = (TextView) itemView.findViewById(R.id.my_download_incomplete_item_state_text);

        stateIcon = (ImageView) itemView.findViewById(R.id.my_download_incomplete_item_state_icon);
        selectIcon = (ImageView) itemView.findViewById(R.id.my_download_incomplete_item_select_icon);
    }

    @Override
    public void onClick(View v) {
        if(!isEditing){
            changeDownloadState();
        } else {
            setSelected();
        }
    }

    public void changeToEditState(){
        isEditing = !isEditing;

        if(isEditing){
            selectIcon.setVisibility(View.VISIBLE);
        } else {
            selectIcon.setVisibility(View.GONE);
        }
    }

    public void changeDownloadState(){
        changeDownloadState(!isPaused);
    }

    public void changeDownloadState(boolean isPaused){
        this.isPaused = isPaused;

        if(isPaused){
            stateIcon.setImageResource(R.drawable.profile);
            downloadState.setText(R.string.my_download_incomplete_item_pause);
        } else {
            stateIcon.setImageResource(R.drawable.profile);
            downloadState.setText(R.string.my_download_incomplete_item_downloading);
        }
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

    private void setSelected(){
        isSelected = !isSelected;

        //修改图标
        if(isSelected){

        } else {

        }
    }

    public boolean isSelected() {
        return isSelected;
    }
}
