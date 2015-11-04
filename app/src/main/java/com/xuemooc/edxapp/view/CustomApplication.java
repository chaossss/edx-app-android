package com.xuemooc.edxapp.view;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;
import com.xuemooc.edxapp.http.Api;
import com.xuemooc.edxapp.http.IApi;

/**
 * CustomApplication helps init the Main page Drawer
 * Created by chaossss on 28.07.15.
 */
public class CustomApplication extends Application {
    public static IApi api;

    @Override
    public void onCreate() {
        super.onCreate();

        api = new Api(getApplicationContext());

        DrawerImageLoader.init(new DrawerImageLoader.IDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx) {
                return null;
            }
        });
    }
}
