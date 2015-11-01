package com.xuemooc.edxapp.view.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.chaos.downloadlibrary.http.download.Downloader;
import com.chaos.downloadlibrary.http.download.FileDownloader;
import com.chaos.downloadlibrary.http.module.LoadInfo;
import com.chaos.downloadlibrary.util.state.DownloadConst;
import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.utils.handler.WebHandler;
import com.xuemooc.edxapp.utils.interfaces.WebCommunication;
import com.xuemooc.edxapp.utils.thread.DownloadPretreatmentTask;
import com.xuemooc.edxapp.view.adapter.MyDownloadIncompleteAdapter;
import com.xuemooc.edxapp.view.subview.MyDownloadIncompleteItemHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ...
 * Created by chaossss on 2015/10/10.
 */
public class MyDownloadIncompleteFragment extends Fragment implements WebCommunication, View.OnClickListener{
    private Button allStart;
    private Button allPause;
    private ExpandableListView incompleteTaskList;
    private MyDownloadIncompleteAdapter adapter;

    private static final String URL = "http://download.haozip.com/";
    private static final String DOWNLOAD_PATH_SUFFIX = "/UESTC_MOOC/Download/";
    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath();

    private Map<String, Downloader> downloaders;
    private Map<String, MyDownloadIncompleteItemHolder> myDownloadIncompleteItemHolderMap;

    private WebHandler handler;

    public MyDownloadIncompleteFragment() {
        initParam();
    }

    private void initParam(){
        checkDownloadDir();

        handler = new WebHandler(this);

        downloaders = new HashMap<>();
        myDownloadIncompleteItemHolderMap = new HashMap<>();
    }

    private void checkDownloadDir(){
        File file = new File(SD_PATH + DOWNLOAD_PATH_SUFFIX);
        if(!file.exists()){
            file.mkdirs();
        }
    }

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
            temp.add("haozip_v3.1.exe");
            temp.add("haozip_v3.1_hj.exe");
            temp.add("haozip_v2.8_x64_tiny.exe");
            temp.add("haozip_v2.8_tiny.exe");
            incompleteTasksInfo.put(incompleteTasksName.get(i), temp);
        }

        adapter = new MyDownloadIncompleteAdapter(this);
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
                for(int i = 0; i < adapter.getSubView().size(); i++){
                    adapter.getSubView().get(i).changeDownloadState(false);
                }
                break;

            case R.id.my_download_incomplete_all_pause:
                for(int i = 0; i < adapter.getSubView().size(); i++){
                    adapter.getSubView().get(i).changeDownloadState(true);
                }
                break;

            default:
                MyDownloadIncompleteItemHolder holder = (MyDownloadIncompleteItemHolder) v.getTag();
                if(holder != null){
                    if(holder.isPaused()){
                        startDownload(holder);
                    } else {
                        pauseDownload(holder);
                    }
                }
                break;
        }
    }

    public MyDownloadIncompleteAdapter getAdapter() {
        return adapter;
    }

    private void showProgress(LoadInfo loadInfo) {
        Log.v("DATA", "fileSize" + loadInfo.getFileSize());
        Log.v("DATA", "completeSize" + loadInfo.getComplete());
        MyDownloadIncompleteItemHolder holder = myDownloadIncompleteItemHolderMap.get(loadInfo.getUrlstring());
        holder.setDownloadSum(loadInfo.getFileSize());
        holder.setDownloadPercent(loadInfo.getComplete());
    }

    public void startDownload(MyDownloadIncompleteItemHolder holder) {
        Log.v("TAG", "startDownload");

        holder.changeDownloadState();

        String resourceName = holder.getItemName();
        String urlStr = URL + resourceName;
        String filePath = SD_PATH + DOWNLOAD_PATH_SUFFIX + resourceName;
        myDownloadIncompleteItemHolderMap.put(urlStr, holder);

        FileDownloader downloader = (FileDownloader)downloaders.get(urlStr);
        if(downloader == null){
            downloader = new FileDownloader(urlStr, filePath, this.getActivity(), handler);
            downloaders.put(urlStr, downloader);
        }
        DownloadPretreatmentTask downloadPretreatmentTask = new DownloadPretreatmentTask(downloaders.get(urlStr), handler);
        new Thread(downloadPretreatmentTask).start();
    }

    public void pauseDownload(MyDownloadIncompleteItemHolder holder) {
        String urlStr = URL + holder.getItemName();
        downloaders.get(urlStr).pause();
        holder.changeDownloadState();
    }

    @Override
    public void sendRequest(Message msg) {

    }

    @Override
    public void onMessageResponse(Message msg) {
        switch(msg.what){
            case DownloadConst.DOWNLOAD_UPDATE_UI:
                Log.v("TAG", "getUpdateUIInfo");

                int length = msg.arg1;
                String url = (String) msg.obj;
                MyDownloadIncompleteItemHolder holder = myDownloadIncompleteItemHolderMap.get(url);

                if (holder != null) {
                    Log.v("TAG", "updatePB");
                    Log.v("DATA", "update pb length" + length);
                    holder.setDownloadPercent(length);

                    if (holder.getDownloadSum() == holder.getDownloadPercent()) {
                        String resourceName = holder.getItemName();
                        holder.complete();

                        Toast.makeText(this.getActivity(), "[" + resourceName + "]下载完成！", Toast.LENGTH_SHORT).show();

                        downloaders.get(url).delete(url);
                        downloaders.get(url).reset();
                        downloaders.remove(url);

                        myDownloadIncompleteItemHolderMap.remove(url);
                    }
                }
                break;

            case DownloadConst.DOWNLOAD_INIT:
                LoadInfo loadInfo = (LoadInfo)msg.obj;
                showProgress(loadInfo);
                break;
        }
    }
}
