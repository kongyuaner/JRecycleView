package com.zinc.jrecycleview.stick.content;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.zinc.jrecycleview.JRecycleView;
import com.zinc.jrecycleview.R;
import com.zinc.jrecycleview.adapter.JRefreshAndLoadMoreAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author       : zinc
 * time         : 2019-07-26 14:09
 * desc         :
 * version      :
 */
public class StickContentActivity extends AppCompatActivity {

    private JRecycleView mJRecycleView;

    private JRefreshAndLoadMoreAdapter mAdapter;
    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_and_load);

        mJRecycleView = findViewById(R.id.j_recycle_view);

        data = getInitData();

        RecyclerView.Adapter adapter = new StickContentAdapter(this, data);
        this.mAdapter = new JRefreshAndLoadMoreAdapter(this, adapter);

        this.mAdapter.setIsOpenLoadMore(false);
        this.mAdapter.setIsOpenRefresh(false);

        mJRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mJRecycleView.setAdapter(mAdapter);

    }

    public List<String> getInitData() {
        this.data.clear();
        for (int i = 1; i <= 20; ++i) {
            data.add("zinc Power" + i);
        }
        return data;
    }

}