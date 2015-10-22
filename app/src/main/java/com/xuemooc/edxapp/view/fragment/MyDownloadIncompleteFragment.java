package com.xuemooc.edxapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.view.adapter.MyDownloadIncompleteAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaossss on 2015/10/10.
 */
public class MyDownloadIncompleteFragment extends Fragment implements View.OnClickListener{
    private Button allStart;
    private Button allPause;
    private ExpandableListView incompleteTaskList;
    private MyDownloadIncompleteAdapter adapter;

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

        incompleteTaskList = (ExpandableListView) rootView.findViewById(R.id.my_download_incomplete_list);
        incompleteTaskList.setGroupIndicator(null);

        List<String> incompleteTasksName = new ArrayList<>();
        incompleteTasksName.add("title1");
        incompleteTasksName.add("title2");
        incompleteTasksName.add("title3");
        Map<String, List<String>> incompleteTasksInfo = new HashMap<>();
        for(int i = 0; i < incompleteTasksName.size(); i++){
            List<String> temp = new ArrayList<>();
            temp.add("data");
            temp.add("data");
            temp.add("data");
            temp.add("data");
            incompleteTasksInfo.put(incompleteTasksName.get(i), temp);
        }

        adapter = new MyDownloadIncompleteAdapter();
        adapter.setIncompleteTasksName(incompleteTasksName);
        adapter.setIncompleteTasksInfo(incompleteTasksInfo);
        incompleteTaskList.setAdapter(adapter);
        for(int i = 0; i < incompleteTasksName.size(); i++){
            incompleteTaskList.expandGroup(i);
        }
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
