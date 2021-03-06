package com.example.mrmuradin.animationsorting.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mrmuradin.animationsorting.adapter.ListAdapter;
import com.example.mrmuradin.animationsorting.R;
import com.example.mrmuradin.animationsorting.presenter.SortingPresenter;
import com.example.mrmuradin.animationsorting.presenter.SortingPresenterImpl;

import java.util.List;
import java.util.ListIterator;

public class SortingActivity extends AppCompatActivity implements SortingView{

    private ListAdapter mAdapter;
    private List<String> dataList;
    private TextView tvStart;
    private TextView tvShuffle;
    private ListIterator<String> iter;
    private String current = "";
    private String previous = "";
    private int iterationsCounter;
    private int counter;

    private SortingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_List);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        presenter = new SortingPresenterImpl(this, getApplicationContext(), mAdapter);

        //TODO Fix situation with number of shown views and correct counting
        RecyclerView.ItemAnimator animator = new DefaultItemAnimator() {
            @Override
            public void onAnimationFinished(RecyclerView.ViewHolder viewHolder) {
                presenter.onAnimationFinished();
            }
        };

        animator.setMoveDuration(500);
        mRecyclerView.setItemAnimator(animator);

        tvStart = (TextView) findViewById(R.id.tv_Start);
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onStartBubbleSortingClick();
            }
        });

        tvShuffle = (TextView) findViewById(R.id.tv_Shuffle);
        tvShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onShuffleClick();
            }
        });
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void blockButtons(boolean needToBlock) {
        tvShuffle.setEnabled(!needToBlock);
        tvStart.setEnabled(!needToBlock);
    }

}

