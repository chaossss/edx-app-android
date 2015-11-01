package com.xuemooc.edxapp.view.subview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuemooc.edxapp.R;

import java.text.DecimalFormat;

/**
 * Created by chaossss on 2015/10/10.
 */
public class MyDownloadIncompleteItemHolder extends RecyclerView.ViewHolder{
    private static final int MB = 1024 * 1024;

    private TextView sum;
    private TextView download;
    private TextView itemName;
    private TextView downloadState;

    private ImageView stateIcon;
    private ImageView selectIcon;

    private int sumNum;
    private int currDownloadPercent;

    private boolean isPaused;
    private boolean isEditing;
    private boolean isSelected;

    public MyDownloadIncompleteItemHolder(View itemView, View.OnClickListener onClickListener) {
        super(itemView);

        isPaused = true;
        itemView.setOnClickListener(onClickListener);

        sum = (TextView) itemView.findViewById(R.id.my_download_incomplete_item_sum);
        download = (TextView) itemView.findViewById(R.id.my_download_incomplete_item_download);
        itemName = (TextView) itemView.findViewById(R.id.my_download_incomplete_item_name);
        downloadState = (TextView) itemView.findViewById(R.id.my_download_incomplete_item_state_text);

        stateIcon = (ImageView) itemView.findViewById(R.id.my_download_incomplete_item_state_icon);
        selectIcon = (ImageView) itemView.findViewById(R.id.my_download_incomplete_item_select_icon);
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

    public boolean isPaused() {
        return isPaused;
    }

    public void setItemName(String itemName){
        this.itemName.setText(itemName);
    }

    public void setDownloadPercent(int downloadPercent){
        currDownloadPercent += downloadPercent;
        float res = (float)currDownloadPercent / MB;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        this.download.setText(decimalFormat.format(res) + "MB" + " / ");
    }

    public void setDownloadSum(int downloadSum){
        sumNum = downloadSum;
        float res = (float)sumNum / MB;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        this.sum.setText(decimalFormat.format(res) + "MB");
    }

    public String getItemName(){
        return itemName.getText().toString();
    }

    public int getDownloadSum(){
        return sumNum;
    }

    public int getDownloadPercent(){
        return currDownloadPercent;
    }

    public void complete(){
        stateIcon.setImageResource(R.drawable.profile);
        downloadState.setText(R.string.my_download_complete);
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
