package com.xuemooc.edxapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.view.adapter.MyDownloadIncompleteAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaossss on 2015/10/10.
 */
public class MyDownloadIncompleteFragment extends Fragment implements View.OnClickListener{
    private Button allStart;
    private Button allPause;
    private RecyclerView incompleteTaskList;
    private MyDownloadIncompleteAdapter adapter;

    private List<List<String>> incompleteTasks = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_download_incomplete, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView){
        allStart = (Button) rootView.findViewById(R.id.my_download_incomplete_all_start);
        allPause = (Button) rootView.findViewById(R.id.my_download_incomplete_all_pause);

        allStart.setOnClickListener(this);
        allPause.setOnClickListener(this);

        incompleteTaskList = (RecyclerView) rootView.findViewById(R.id.my_download_incomplete_list);

        adapter = new MyDownloadIncompleteAdapter(incompleteTasks);
        incompleteTaskList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.my_download_incomplete_all_start:
                break;

            case R.id.my_download_incomplete_all_pause:
                break;
        }
    }
}
