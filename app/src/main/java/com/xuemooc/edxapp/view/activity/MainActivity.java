package com.xuemooc.edxapp.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.view.BezelImageView;
import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.http.interfaces.IWebMessage;
import com.xuemooc.edxapp.utils.loader.ImageLoader;
import com.xuemooc.edxapp.utils.util.MessageConst;
import com.xuemooc.edxapp.view.fragment.DiscoverFragment;
import com.xuemooc.edxapp.view.fragment.MyCourseFragment;
import com.xuemooc.edxapp.view.fragment.MyDownloadFragment;
import com.xuemooc.edxapp.view.subview.MyDrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Page Activity
 * Created by chaossss on 28.07.15.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, Drawer.OnDrawerListener, Drawer.OnDrawerItemClickListener, IWebMessage{
    private static final int MY_COURSE = 0;
    private static final int DISCOVER = 1;
    private static final int MY_DOWNLOAD = 2;

    private Bundle savedInstanceState;//save activity's state

    //drawer stuff
    private Drawer drawer = null;//drawer
    private Toolbar toolbar = null;
    private FrameLayout header = null;//drawer header
    private LinearLayout footer = null;//drawer footer

    //drawer header stuff
    private TextView userName = null;
    private BezelImageView userImg = null;

    //drawer footer stuff
    private RelativeLayout settings = null;
    private RelativeLayout encourage = null;

    //main page view
    private List<Fragment> fragments;

    //menu's state
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        this.savedInstanceState = savedInstanceState;
    }

    /**
     * Init Activity's View
     */
    private void initView(){
        initMainPage();
        initDrawer();
    }

    /**
     * Init Main Page
     */
    private void initMainPage(){
        fragments = new ArrayList<>();
        fragments.add(new MyCourseFragment());
        fragments.add(new DiscoverFragment());
        fragments.add(new MyDownloadFragment());
        getSupportFragmentManager().beginTransaction().add(R.id.main_page_container, fragments.get(MY_COURSE)).commit();
    }

    /**
     * Init Drawer
     */
    private void initDrawer(){
        initHeader();
        initFooter();
        initToolBar();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withHeader(header)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new MyDrawerItem().withName(R.string.drawer_item_my_course).withIcon(FontAwesome.Icon.faw_home).withIdentifier(MY_COURSE),
                        new MyDrawerItem().withName(R.string.drawer_item_discover_course).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(DISCOVER),
                        new MyDrawerItem().withName(R.string.drawer_item_download).withIcon(FontAwesome.Icon.faw_arrow_circle_o_down).withIdentifier(MY_DOWNLOAD)
                )
                .withOnDrawerListener(this)
                .withStickyFooter(footer)
                .withAnimateDrawerItems(true)
                .withOnDrawerItemClickListener(this)
                .withSavedInstance(savedInstanceState)
                .build();
    }

    /**
     * Init Drawer Header, and set relating listener
     */
    private void initHeader(){
        header = (FrameLayout) LayoutInflater.from(this.getApplication()).inflate(R.layout.drawer_header,null);

        userName = (TextView) header.findViewById(R.id.header_user_name);
        userImg = (BezelImageView) header.findViewById(R.id.header_user_icon);
    }

    private void initToolBar(){
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.drawer_toolbar_my_course);
        setSupportActionBar(toolbar);
    }

    /**
     * Init Drawer footer, and set relating listener
     */
    private void initFooter(){
        footer = (LinearLayout)LayoutInflater.from(this.getApplication()).inflate(R.layout.drawer_footer,null);

        settings = (RelativeLayout) footer.findViewById(R.id.drawer_footer_setting);
        encourage = (RelativeLayout) footer.findViewById(R.id.drawer_footer_encourage);

        settings.setOnClickListener(this);
        encourage.setOnClickListener(this);
    }

    /**
     * Response to onClick()
     * @param v clicked view
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.drawer_footer_setting:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                this.startActivity(intent);
                break;

            case R.id.drawer_footer_encourage:
                break;
        }
    }

    /**
     * Show "Edit" Button, only visiable while displaying my download page
     * @param menu display menu
     * @return true - create successfully, false - did not
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        menu.setGroupVisible(0, false);
        this.menu = menu;
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
     * Save current Activity's and Drawer's state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = drawer.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    /**
     * Deal with "back"
     */
    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Response to Drawer item's onClick()
     * @return true - conumed, false - did not consume
     */
    @Override
    public boolean onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
        menu.setGroupVisible(0, iDrawerItem.getIdentifier() == MY_DOWNLOAD);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_page_container, fragments.get(iDrawerItem.getIdentifier())).commit();

        switch(iDrawerItem.getIdentifier()){
            case MY_COURSE:
                toolbar.setTitle(R.string.drawer_toolbar_my_course);
                break;

            case DISCOVER:
                toolbar.setTitle(R.string.drawer_toolbar_discover);
                break;

            case MY_DOWNLOAD:
                toolbar.setTitle(R.string.drawer_toolbar_my_download);
                break;
        }
        return false;
    }

    @Override
    public void sendRequest(Message msg) {

    }

    @Override
    public void onMessageResponse(Message msg) {
        if(msg.what == MessageConst.DRAWER_HEADER_USER_IMG){
            userImg.setImageBitmap((Bitmap) msg.obj);
        }
    }

    @Override
    public void onDrawerOpened(View view) {
        Message msg = Message.obtain();
        msg.what = MessageConst.DRAWER_HEADER_USER_IMG;
        msg.obj = "http://www.hinews.cn/pic/0/16/57/20/16572013_223861.jpg";
        ImageLoader.getImageLoader(this).load(msg);
    }

    @Override
    public void onDrawerClosed(View view) {

    }

    @Override
    public void onDrawerSlide(View view, float v) {

    }
}
