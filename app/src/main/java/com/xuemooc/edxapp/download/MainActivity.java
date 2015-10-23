package com.example.chaos.downloadlibrary;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String URL = "http://download.haozip.com/";
    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath();

    private Map<String, Downloader> downloaders = new HashMap<>();
    private Map<String, ProgressBar> progressbars = new HashMap<>();

    private ListView list;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                String url = (String) msg.obj;
                int length = msg.arg1;
                ProgressBar pb = progressbars.get(url);
                if(pb != null){
                    pb.incrementProgressBy(length);

                    if(pb.getProgress() == pb.getMax()){
                        LinearLayout layout = (LinearLayout) pb.getParent();
                        TextView resourceName = (TextView) layout.findViewById(R.id.tv_resource_name);
                        Toast.makeText(MainActivity.this, "[" + resourceName.getText() + "]下载完成！", Toast.LENGTH_SHORT).show();
                        layout.removeView(pb);
                        progressbars.remove(url);
                        downloaders.get(url).delete(url);
                        downloaders.get(url).reset();
                        downloaders.remove(url);
                        Button btn_start = (Button) layout.findViewById(R.id.btn_start);
                        Button btn_pause = (Button) layout.findViewById(R.id.btn_pause);
                        btn_pause.setVisibility(View.GONE);
                        btn_start.setVisibility(View.GONE);
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        list = (ListView) findViewById(android.R.id.list);
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("name", "haozip_v3.1.exe");
        data.add(map);
        map = new HashMap<>();
        map.put("name", "haozip_v3.1_hj.exe");
        data.add(map);
        map = new HashMap<>();
        map.put("name", "haozip_v2.8_x64_tiny.exe");
        data.add(map);
        map = new HashMap<>();
        map.put("name", "haozip_v2.8_tiny.exe");
        data.add(map);
        DownloadAdapter adapter = new DownloadAdapter(data, this);
        list.setAdapter(adapter);
    }

    public void startDownload(View v){
        LinearLayout layout = (LinearLayout) v.getParent();
        String resourceName = ((TextView) layout.findViewById(R.id.tv_resource_name)).getText().toString();
        String url = URL + resourceName;
        String localFile = SD_PATH + resourceName;
        String threadCount = "4";
        DownloadTask downloadTask = new DownloadTask(v);
        downloadTask.execute(url, localFile, threadCount);
    }

    class DownloadTask extends AsyncTask<String, Integer, LoadInfo>{
        Downloader downloader = null;
        View v = null;
        String url = null;

        public DownloadTask(View v) {
            this.v = v;
        }

        @Override
        protected void onPreExecute() {
            Button btn_start = (Button)((View) v.getParent()).findViewById(R.id.btn_start);
            Button btn_pause = (Button)((View) v.getParent()).findViewById(R.id.btn_pause);
            btn_start.setVisibility(View.GONE);
            btn_pause.setVisibility(View.VISIBLE);
        }

        @Override
        protected LoadInfo doInBackground(String... params) {
            url = params[0];
            String localFile = params[1];
            int threadCount = Integer.parseInt(params[2]);
            downloader = downloaders.get(url);

            if(downloader == null){
                downloader = new Downloader(url, localFile, threadCount, MainActivity.this, mHandler);
                downloaders.put(url, downloader);
            }

            if(downloader.isDownloading()){
                return null;
            }

            return downloader.getDownloadersInfo();
        }

        @Override
        protected void onPostExecute(LoadInfo loadInfo) {
            if(loadInfo != null){
                showProgress(loadInfo, url, v);
                downloader.download();
            }
        }
    }

    public void showProgress(LoadInfo loadInfo, String url, View v){
        ProgressBar pb = progressbars.get(url);

        if(pb == null){
            pb = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
            pb.setMax(loadInfo.getFileSize());
            pb.setProgress(loadInfo.getComplete());
            progressbars.put(url, pb);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 5);
            ((LinearLayout) (v.getParent())).addView(pb, params);
        }
    }

    public void pauseDownload(View v){
        LinearLayout layout = (LinearLayout) v.getParent();
        String resourceName = ((TextView) layout.findViewById(R.id.tv_resource_name)).getText().toString();
        String url = URL + resourceName;
        downloaders.get(url).pause();
        Button btn_start = (Button)((View) v.getParent()).findViewById(R.id.btn_start);
        Button btn_pause = (Button)((View) v.getParent()).findViewById(R.id.btn_pause);
        btn_start.setVisibility(View.GONE);
        btn_pause.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
