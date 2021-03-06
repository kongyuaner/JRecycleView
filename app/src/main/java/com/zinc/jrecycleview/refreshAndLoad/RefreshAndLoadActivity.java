package com.zinc.jrecycleview.refreshAndLoad;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zinc.jrecycleview.JRecycleView;
import com.zinc.jrecycleview.R;
import com.zinc.jrecycleview.adapter.JRefreshAndLoadMoreAdapter;
import com.zinc.jrecycleview.widget.MyRefreshView;

import java.util.ArrayList;
import java.util.List;

/**
 * author       : Jiang Pengyong
 * time         : 2018-03-17 21:59
 * email        : 56002982@qq.com
 * desc         : 下拉刷新、上拉加载
 * version      : 1.0.0
 */
public class RefreshAndLoadActivity extends AppCompatActivity {

    private static final int PAGE_SIZE = 20;

    public static final String IS_DIY = "isDiy";

    private JRecycleView mJRecycleView;

    private JRefreshAndLoadMoreAdapter mAdapter;

    private final List<String> mData = new ArrayList<>();

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_and_load);

        boolean isDiy = getIntent().getBooleanExtra(IS_DIY, false);

        mJRecycleView = findViewById(R.id.j_recycle_view);

        getInitData();

        // 原始 Adapter
        RecyclerView.Adapter adapter = new RefreshAndLoadAdapter(this, mData);
        // 使用 JRefreshAndLoadMoreAdapter 进行包装
        this.mAdapter = new JRefreshAndLoadMoreAdapter(this, adapter);

        this.mAdapter.setOnLoadMoreListener(() -> {
            Toast.makeText(RefreshAndLoadActivity.this,
                    "触发加载更多",
                    Toast.LENGTH_SHORT)
                    .show();

            mHandler.postDelayed(() -> {

                if (mData.size() > 2 * PAGE_SIZE) {
                    // 错误
                    mAdapter.setLoadError();
                } else {
                    int size = mData.size();

                    addData();
                    // 加载成功
                    mAdapter.setLoadComplete();
                    // 刷新
                    mAdapter.notifyItemRangeInserted(mAdapter.getRealPosition(size), PAGE_SIZE);
                }

            }, 2000);

        });

        this.mAdapter.setOnRefreshListener(() -> {
            mHandler.postDelayed(() -> {

                getRefreshData();
                // 将 "上拉加载更多" 重置
                mAdapter.resetLoadMore();
                // 将 "下拉刷新" 状态置为刷新完成
                mAdapter.setRefreshComplete();

            }, 2000);
        });

        if (isDiy) {
            mAdapter.setRefreshLoadView(new MyRefreshView(getBaseContext()));
        }

        mJRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mJRecycleView.setAdapter(mAdapter);

    }

    public void getInitData() {
        mData.clear();
        for (int i = 1; i <= PAGE_SIZE; ++i) {
            mData.add("zinc Power" + i);
        }
    }

    public void getRefreshData() {
        mData.clear();
        for (int i = 1; i <= 15; ++i) {
            mData.add("zinc Power" + i);
        }
    }

    public void addData() {
        int size = mData.size();
        for (int i = 1; i <= PAGE_SIZE; ++i) {
            mData.add("zinc Power" + (size + i));
        }
    }

}
