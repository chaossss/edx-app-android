package com.xuemooc.edxapp.view.activity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.savedInstanceState = savedInstanceState;
        initView();
    }

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
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = drawer.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
