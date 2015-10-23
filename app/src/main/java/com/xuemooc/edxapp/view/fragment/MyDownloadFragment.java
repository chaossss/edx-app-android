package com.xuemooc.edxapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.xuemooc.edxapp.R;

/**
 *
 * Created by chaossss on 2015/9/4.
 */
public class MyDownloadFragment extends Fragment{
    private ViewPager pager;
    private SmartTabLayout tab;
    private FragmentPagerItemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_download, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView){
        pager = (ViewPager) rootView.findViewById(R.id.my_download_pager);
        tab = (SmartTabLayout) rootView.findViewById(R.id.my_download_tab);

         adapter = new FragmentPagerItemAdapter(
                this.getFragmentManager(), FragmentPagerItems.with(this.getActivity())
                .add(R.string.my_download_incomplete, MyDownloadIncompleteFragment.class)
                .add(R.string.my_download_complete, MyDownloadCompleteFragment.class)
                .create()
        );

        pager.setAdapter(adapter);
        tab.setViewPager(pager);
    }

    public Fragment getSubFragment(){
        return adapter.getPage(pager.getCurrentItem());
    }

    public boolean isIncompleteShowing() {
        return pager.getCurrentItem() == 0;
    }
}
