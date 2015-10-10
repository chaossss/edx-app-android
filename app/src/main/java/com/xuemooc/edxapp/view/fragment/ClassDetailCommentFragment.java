package com.xuemooc.edxapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.view.adapter.ClassDetailCommentAdapter;

/**
 * Created by chaossss on 2015/10/9.
 */
public class ClassDetailCommentFragment extends Fragment {
    private RecyclerView scrollView;
    private ClassDetailCommentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_class_detail_list, container, false);

        scrollView = (RecyclerView) rootView.findViewById(R.id.class_detail_list);
        scrollView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        scrollView.setAdapter(adapter);
        return rootView;
    }
}
