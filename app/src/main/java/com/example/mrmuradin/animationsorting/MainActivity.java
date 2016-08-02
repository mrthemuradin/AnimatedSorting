package com.example.mrmuradin.animationsorting;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ListAdapter mAdapter;
    private List<String> dataList;
    private TextView tvStart;
    private TextView tvShuffle;
    private ListIterator<String> iter;
    private String current = "";
    private String previous = "";
    private int iterationsCounter;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_List);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        String[] myResArray = getResources().getStringArray(R.array.list_Items);
        dataList = Arrays.asList(myResArray);
        iter = dataList.listIterator();
//        Log.d("myLogs", "iterations counter start= " + iterationsCounter);

        mAdapter = new ListAdapter(dataList);
        mRecyclerView.setAdapter(mAdapter);

        //TODO Fix situation with number of shown views and correct counting
        RecyclerView.ItemAnimator animator = new DefaultItemAnimator() {
            @Override
            public void onAnimationFinished(RecyclerView.ViewHolder viewHolder) {
                if (counter == dataList.size() - 1) {
                    counter = 0;
                    startSorting();
                } else counter++;

            }
        };

        animator.setMoveDuration(500);
        mRecyclerView.setItemAnimator(animator);

        tvStart = (TextView) findViewById(R.id.tv_Start);
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iterationsCounter = dataList.size() - 1;
                blockButtons(true);
                startSorting();
            }
        });

        tvShuffle = (TextView) findViewById(R.id.tv_Shuffle);
        tvShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.shuffle(dataList, new Random(System.nanoTime()));
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void blockButtons(boolean needToBlock) {
        tvShuffle.setEnabled(!needToBlock);
        tvStart.setEnabled(!needToBlock);
    }

    private void startSorting() {
//        Log.d("myLogs", "iterations counter = " + iterationsCounter + ", dataList.size = " + dataList.size());
        if (iterationsCounter < 1) {
            blockButtons(false);
            return;
        }

        if (previous.isEmpty()) current = iter.next();

        if (iter.hasNext()) {
            final String next = iter.next();
//            Log.d("myLogs", "===   current = " + current + ", next = " + next + ", previous =" + previous);
            if (Integer.valueOf(current) > Integer.valueOf(next)) {
                Collections.swap(dataList, dataList.indexOf(current), dataList.indexOf(next));
                mAdapter.notifyItemMoved(dataList.indexOf(current), dataList.indexOf(next));
                previous = next;
//                Log.d("myLogs", "===>   current = " + current + ", previous =" + previous);
            } else {
                previous = current;
                current = next;
                startSorting();
            }

        } else {
            iter = dataList.listIterator();
            previous = "";
            current = "";
            iterationsCounter--;
            startSorting();

        }

    }

}

