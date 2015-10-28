package com.xuemooc.edxapp.view.subview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xuemooc.edxapp.R;

/**
 * Created by chaos on 2015/10/28.
 */
public class TestHolder extends RecyclerView.ViewHolder{
    private Button start;
    private Button pause;
    private TextView resourceName;
    private ProgressBar progressBar;
    private View.OnClickListener onClickListener;

    private boolean isPause;

    public TestHolder(View itemView, View.OnClickListener onClickListener) {
        super(itemView);

        this.onClickListener = onClickListener;

        start = (Button) itemView.findViewById(R.id.btn_start);
        pause = (Button) itemView.findViewById(R.id.btn_pause);

        start.setVisibility(View.VISIBLE);
        pause.setVisibility(View.INVISIBLE);

        resourceName = (TextView) itemView.findViewById(R.id.tv_resouce_name);
        progressBar = (ProgressBar) itemView.findViewById(R.id.test_pb);

        start.setOnClickListener(this.onClickListener);
        pause.setOnClickListener(this.onClickListener);
    }

    public void setResourceName(String resourceName){
        this.resourceName.setText(resourceName);
    }

    public String getResourceName(){
        return resourceName.getText().toString();
    }

    public ProgressBar getProgressBar(){
        return progressBar;
    }

    public void pause(){
        isPause = !isPause;

        if(isPause){
            start.setVisibility(View.INVISIBLE);
            pause.setVisibility(View.VISIBLE);
        } else {
            start.setVisibility(View.VISIBLE);
            pause.setVisibility(View.INVISIBLE);
        }
    }

    public void complete(){
        start.setVisibility(View.GONE);
        pause.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }
}
