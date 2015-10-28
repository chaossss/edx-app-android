package com.xuemooc.edxapp.view.activity;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import com.xuemooc.edxapp.http.interfaces.IWebMessage;
import com.xuemooc.edxapp.utils.handler.WebHandler;
import com.xuemooc.edxapp.view.adapter.TestAdapter;
import com.xuemooc.edxapp.view.subview.TestHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaos on 2015/10/28.
 */
public class TestActivity extends AppCompatActivity implements IWebMessage, View.OnClickListener{
    // 固定下载的资源路径，这里可以设置网络上的地址
    private static final String URL = "http://download.haozip.com/";
    // 固定存放下载的音乐的路径：SD卡目录下
    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath().toString() + "/UESTC_MOOC/Download";
    // 存放各个下载器
    private Map<String, Downloader> downloaders = new HashMap<>();
    // 存放与下载器对应的进度条
    private Map<String, ProgressBar> ProgressBars = new HashMap<>();

    private WebHandler handler;

    private RecyclerView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

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
        TestAdapter adapter=new TestAdapter(data, this);
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
        String urlstr = URL + resouceName;
        String localfile = SD_PATH + "/" + resouceName;
        //设置下载线程数为4，这里是我为了方便随便固定的
        String threadcount = "4";
        DownloadTask downloadTask=new DownloadTask(v);
        downloadTask.execute(urlstr, localfile, threadcount);
    }

    @Override
    public void sendRequest(Message msg) {

    }

    @Override
    public void onMessageResponse(Message msg) {
        if(msg.what == DownloadConst.DOWNLOAD_UPDATE_UI){
            Log.v("TAG", "getUpdateUIInfo");
            String url = (String) msg.obj;
            int length = msg.arg1;
            ProgressBar bar = ProgressBars.get(url);
            if (bar != null) {
                Log.v("TAG", "updatePB");
                // 设置进度条按读取的length长度更新
                bar.incrementProgressBy(length);
                if (bar.getProgress() == bar.getMax()) {
                    LinearLayout layout = (LinearLayout) bar.getParent();
                    TextView resouceName=(TextView)layout.findViewById(R.id.tv_resouce_name);
                    Toast.makeText(TestActivity.this, "[" + resouceName.getText() + "]下载完成！", Toast.LENGTH_SHORT).show();
                    // 下载完成后清除进度条并将map中的数据清空
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

    class DownloadTask extends AsyncTask<String, Integer, LoadInfo> {
        FileDownloader downloader=null;
        View v=null;
        String urlstr=null;
        public DownloadTask(final View v){
            this.v=v;
        }
        @Override
        protected void onPreExecute() {
            Button btn_start=(Button)((View)v.getParent()).findViewById(R.id.btn_start);
            Button btn_pause=(Button)((View)v.getParent()).findViewById(R.id.btn_pause);
            btn_start.setVisibility(View.GONE);
            btn_pause.setVisibility(View.VISIBLE);
        }
        @Override
        protected LoadInfo doInBackground(String... params) {
            urlstr=params[0];
            String localfile=params[1];
            int threadcount=Integer.parseInt(params[2]);
            // 初始化一个downloader下载器
            downloader = (FileDownloader)downloaders.get(urlstr);
            if (downloader == null) {
                downloader = new FileDownloader(urlstr, localfile, threadcount, TestActivity.this, handler);
                downloaders.put(urlstr, downloader);
            }
            if (downloader.isDownloading())
                return null;
            // 得到下载信息类的个数组成集合
            return downloader.getDownloadInfo();
        }
        @Override
        protected void onPostExecute(LoadInfo loadInfo) {
            if(loadInfo!=null){
                // 显示进度条
                showProgress(loadInfo, urlstr, v);

                Log.v("TAG", "download");

                // 调用方法开始下载
                downloader.download();
            }
        }

    };
    /**
     * 显示进度条
     */
    private void showProgress(LoadInfo loadInfo, String url, View v) {
        ProgressBar bar = ProgressBars.get(url);
        if (bar == null) {
            TestHolder holder = (TestHolder)list.getChildViewHolder((View)v.getParent().getParent());
            bar = holder.getProgressBar();
            bar.setMax(loadInfo.getFileSize());
            bar.setProgress(loadInfo.getComplete());
            ProgressBars.put(url, bar);
        }
    }
    /**
     * 响应暂停下载按钮的点击事件
     */
    public void pauseDownload(View v) {
        TestHolder holder = (TestHolder) list.getChildViewHolder((View) v.getParent().getParent());
        String urlstr = URL + holder.getResourceName();
        downloaders.get(urlstr).pause();
        holder.pause();
    }
}
