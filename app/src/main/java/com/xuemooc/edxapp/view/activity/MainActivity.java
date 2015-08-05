package com.xuemooc.edxapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.view.BezelImageView;
import com.xuemooc.edxapp.R;

/**
 * 首页
 * Created by chaossss on 28.07.15.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Bundle savedInstanceState;//保存当前 Activity 的状态

    //UI 相关
    private Drawer drawer = null;//抽屉
    private Toolbar toolbar = null;
    private FrameLayout header = null;//抽屉中的头部
    private LinearLayout footer = null;//抽屉中的尾部

    //抽屉头部组件
    private Button userLogin = null;
    private TextView userName = null;
    private BezelImageView userImg = null;

    //抽屉尾部组件
    private RelativeLayout settings = null;
    private RelativeLayout encourage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.savedInstanceState = savedInstanceState;
        initView();
    }

    /**
     * 初始化 Activity 要显示的 View
     */
    private void initView(){
        initDrawer();
    }

    private void initDrawer(){
        initHeader();
        initToolBar();
        initFooter();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(header) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new SecondaryDrawerItem().withName(R.string.drawer_item_my_course).withIcon(FontAwesome.Icon.faw_home),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_discover_course).withIcon(FontAwesome.Icon.faw_eye),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_download).withIcon(FontAwesome.Icon.faw_arrow_circle_o_down)
                )
                .withStickyFooter(footer)
                .withAnimateDrawerItems(true)
                .withSavedInstance(savedInstanceState)
                .build();
    }

    private void initHeader(){
        header = (FrameLayout) LayoutInflater.from(this.getApplication()).inflate(R.layout.drawer_header,null);

        userName = (TextView) header.findViewById(R.id.header_user_name);
        userLogin = (Button) header.findViewById(R.id.header_user_login);
        userImg = (BezelImageView) header.findViewById(R.id.header_user_icon);

        userLogin.setOnClickListener(this);
    }

    private void initToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initFooter(){
        footer = (LinearLayout)LayoutInflater.from(this.getApplication()).inflate(R.layout.drawer_footer,null);

        settings = (RelativeLayout) footer.findViewById(R.id.drawer_footer_setting);
        encourage = (RelativeLayout) footer.findViewById(R.id.drawer_footer_encourage);

        settings.setOnClickListener(this);
        encourage.setOnClickListener(this);
    }

    /**
     * 处理 View 的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.header_user_login:
                break;

            case R.id.drawer_footer_setting:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                this.startActivity(intent);
                break;

            case R.id.drawer_footer_encourage:
                break;
        }
    }

    /**
     * 用于显示“编辑”按钮，只有在选择了抽屉中的我的下载才会显示
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        menu.setGroupVisible(0, false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 保存当前 Activity 和 drawer 的状态
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = drawer.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    /**
     * 按后退按钮后，如果抽屉处于打开状态，则关闭抽屉；否则退出应用
     */
    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
