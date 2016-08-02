package com.example.mrmuradin.animationsorting.presenter;


import android.content.Context;

import com.example.mrmuradin.animationsorting.adapter.ListAdapter;
import com.example.mrmuradin.animationsorting.R;
import com.example.mrmuradin.animationsorting.view.SortingView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SortingPresenterImpl implements SortingPresenter {

    private SortingView sortingView;
    private List<String> dataList;
    private Iterator<String> iter;
    private int iterationsCounter;
    private String currentIterPosition = "";
    private String previousIterPosition = "";
    private int animatedViewsCounter;
    private ListAdapter mAdapter;

    public SortingPresenterImpl(SortingView sortingView, Context context, ListAdapter adapter) {
        this.sortingView = sortingView;
        this.mAdapter = adapter;
        String[] myResArray = context.getResources().getStringArray(R.array.list_Items);
        dataList = Arrays.asList(myResArray);
        mAdapter.setData(dataList);
        iter = dataList.iterator();
    }

    @Override
    public void onStartBubbleSortingClick() {
        iterationsCounter = dataList.size() - 1;
        sortingView.blockButtons(true);
        startSorting();
    }

    @Override
    public void onShuffleClick() {
        Collections.shuffle(dataList, new Random(System.nanoTime()));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAnimationFinished() {
        if (animatedViewsCounter == dataList.size() - 1) {
            animatedViewsCounter = 0;
            startSorting();
        }
        else animatedViewsCounter++;
    }

    @Override
    public void onDestroy() {
        sortingView = null;
    }

    private void startSorting() {
//        Log.d("myLogs", "iterations counter = " + iterationsCounter + ", dataList.size = " + dataList.size());
        if (iterationsCounter < 1) {
            sortingView.blockButtons(false);
            return;
        }
        if (previousIterPosition.isEmpty()) currentIterPosition = iter.next();

        if (iter.hasNext()) {
            final String next = iter.next();
//            Log.d("myLogs", "===   current = " + current + ", next = " + next + ", previous =" + previous);
            if (Integer.valueOf(currentIterPosition) > Integer.valueOf(next)) {
                Collections.swap(dataList, dataList.indexOf(currentIterPosition), dataList.indexOf(next));
                mAdapter.notifyItemMoved(dataList.indexOf(currentIterPosition), dataList.indexOf(next));
                previousIterPosition = next;
//                Log.d("myLogs", "===>   current = " + current + ", previous =" + previous);
            } else {
                previousIterPosition = currentIterPosition;
                currentIterPosition = next;
                startSorting();
            }

        } else {
            iter = dataList.listIterator();
            previousIterPosition = "";
            currentIterPosition = "";
            iterationsCounter--;
            startSorting();
        }
    }
}
