package com.xuemooc.edxapp.view.activity;

import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.downloadlibrary.DownloadConst;
import com.chaos.downloadlibrary.http.download.Downloader;
import com.chaos.downloadlibrary.http.download.FileDownloader;
import com.chaos.downloadlibrary.http.module.LoadInfo;
import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.utils.handler.WebHandler;
import com.xuemooc.edxapp.utils.interfaces.WebCommunication;
import com.xuemooc.edxapp.utils.thread.DownloadPretreatmentTask;
import com.xuemooc.edxapp.view.adapter.TestAdapter;
import com.xuemooc.edxapp.view.subview.TestHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaos on 2015/10/28.
 */
public class TestActivity extends AppCompatActivity implements WebCommunication, View.OnClickListener{
    private static final String URL = "http://download.haozip.com/";
    private static final String DOWNLOAD_PATH_SUFFIX = "/UESTC_MOOC/Download/";
    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath();

    private Map<String, Downloader> downloaders = new HashMap<>();
    private Map<String, ProgressBar> ProgressBars = new HashMap<>();

    private WebHandler handler;

    private RecyclerView list;
    private TestAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        File file = new File(SD_PATH + DOWNLOAD_PATH_SUFFIX);
        if(!file.exists()){
            file.mkdirs();
        }

        handler = new WebHandler(this);
        list = (RecyclerView) findViewById(R.id.test_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        showListView();
    }

    // 显示listView，这里可以随便添加
    private void showListView() {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "haozip_v3.1.exe");
        data.add(map);
        map = new HashMap<String, String>();
        map.put("name", "haozip_v3.1_hj.exe");
        data.add(map);
        map = new HashMap<String, String>();
        map.put("name", "haozip_v2.8_x64_tiny.exe");
        data.add(map);
        map = new HashMap<String, String>();
        map.put("name", "haozip_v2.8_tiny.exe");
        data.add(map);
        adapter = new TestAdapter(data, this);
        list.setAdapter(adapter);
    }

    /**
     * 响应开始下载按钮的点击事件
     */
    public void startDownload(View v) {
        Log.v("TAG", "startDownload");

        // 得到textView的内容
        LinearLayout layout = (LinearLayout) v.getParent();
        String resouceName = ((TextView) layout.findViewById(R.id.tv_resouce_name)).getText().toString();
        String urlStr = URL + resouceName;
        String localfile = SD_PATH + DOWNLOAD_PATH_SUFFIX + resouceName;
        showProgress(urlStr, v);

        FileDownloader downloader = (FileDownloader)downloaders.get(urlStr);
        if(downloader == null){
            downloader = new FileDownloader(urlStr, localfile, 4, this, handler);
            downloaders.put(urlStr, downloader);
        }
        DownloadPretreatmentTask downloadPretreatmentTask = new DownloadPretreatmentTask(downloaders.get(urlStr), handler);
        new Thread(downloadPretreatmentTask).start();
    }

    @Override
    public void sendRequest(Message msg) {

    }

    @Override
    public void onMessageResponse(Message msg) {
        switch(msg.what){
            case DownloadConst.DOWNLOAD_UPDATE_UI:
                Log.v("TAG", "getUpdateUIInfo");
                String url = (String) msg.obj;
                int length = msg.arg1;
                ProgressBar bar = ProgressBars.get(url);
                if (bar != null) {
                    Log.v("TAG", "updatePB");
                    bar.incrementProgressBy(length);
                    if (bar.getProgress() == bar.getMax()) {
                        LinearLayout layout = (LinearLayout) bar.getParent();
                        TextView resouceName=(TextView)layout.findViewById(R.id.tv_resouce_name);
                        Toast.makeText(TestActivity.this, "[" + resouceName.getText() + "]下载完成！", Toast.LENGTH_SHORT).show();
                        layout.removeView(bar);
                        ProgressBars.remove(url);
                        downloaders.get(url).delete(url);
                        downloaders.get(url).reset();
                        downloaders.remove(url);

                        Button btn_start=(Button)layout.findViewById(R.id.btn_start);
                        Button btn_pause=(Button)layout.findViewById(R.id.btn_pause);
                        btn_pause.setVisibility(View.GONE);
                        btn_start.setVisibility(View.GONE);
                    }
                }
                break;

            case DownloadConst.DOWNLOAD_INIT:
                LoadInfo loadInfo = (LoadInfo)msg.obj;
                showProgress(loadInfo);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                startDownload(v);
                break;

            case R.id.btn_pause:
                pauseDownload(v);
                break;
        }
    }

    private void showProgress(String urlStr, View v) {
        ProgressBar bar = ProgressBars.get(urlStr);
        if (bar == null) {
            TestHolder holder = (TestHolder)list.getChildViewHolder((View)v.getParent().getParent());
            bar = holder.getProgressBar();
            ProgressBars.put(urlStr, bar);
        }
    }

    private void showProgress(LoadInfo loadInfo) {
        ProgressBar bar = ProgressBars.get(loadInfo.getUrlstring());
        bar.setMax(loadInfo.getFileSize());
        bar.setProgress(loadInfo.getComplete());
    }

    public void pauseDownload(View v) {
        TestHolder holder = (TestHolder) list.getChildViewHolder((View) v.getParent().getParent());
        String urlStr = URL + holder.getResourceName();
        downloaders.get(urlStr).pause();
        holder.pause();
    }
}
