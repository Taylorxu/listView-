package com.xuzhiguang.scrollviewnest;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xuzhiguang.scrollviewnest.databinding.ActivityMainBinding;
import com.xuzhiguang.scrollviewnest.databinding.ItemJiZuZhiBiaoBinding;
import com.xuzhiguang.scrollviewnest.databinding.ItemNameListBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private List<SimpleListTextItem> list = new ArrayList<>();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
        binding.zhiBiaoListId.setAdapter(new Adapter(list));

        binding.zhiBiaoListId.setOnScrollListener(new MyScrollListener());
        for (int i = 0; i < binding.contentJiZu.getChildCount(); i++) {
            LinearLayout view = (LinearLayout) binding.contentJiZu.getChildAt(i);
            ListView listview = (ListView) view.getChildAt(2);
            listview.setOnScrollListener(new MyScrollListener());
        }
    }

    private class MyScrollListener implements AbsListView.OnScrollListener {


        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
// 关键代码
            if (scrollState == SCROLL_STATE_IDLE
                    || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                View subView = view.getChildAt(0);
                if (subView != null) {
                    final int top = subView.getTop();
                    final int position = view.getFirstVisiblePosition();

                    for (int i = 0; i < binding.contentJiZu.getChildCount(); i++) {
                        LinearLayout view1 = (LinearLayout) binding.contentJiZu.getChildAt(i);
                        ListView listview = (ListView) view1.getChildAt(2);
                        listview.setSelectionFromTop(position,top);
                    }
                    binding.zhiBiaoListId.setSelectionFromTop(position,top);
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            // 关键代码
            View subView = view.getChildAt(0);
            if (subView != null) {
                final int top = subView.getTop();
                for (int i = 0; i < binding.contentJiZu.getChildCount(); i++) {
                    LinearLayout view1 = (LinearLayout) binding.contentJiZu.getChildAt(i);
                    ListView listview = (ListView) view1.getChildAt(2);
                    listview.setSelectionFromTop(firstVisibleItem,top);
                }
                binding.zhiBiaoListId.setSelectionFromTop(firstVisibleItem,top);
            }
        }
    }

    private void initView() {
        for (int j = 0; j < 70; j++) {
            SimpleListTextItem textItem = new SimpleListTextItem();
            textItem.setTitle(j + "指标");
            list.add(textItem);
        }


        WindowManager wm1 = this.getWindowManager();
        int width1 = wm1.getDefaultDisplay().getWidth() / 2 - 200;
        for (int i = 0; i < 70; i++) {
            LinearLayout view = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_ji_zu_zhi_biao, binding.contentJiZu, false);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(width1,
                    LinearLayout.LayoutParams.FILL_PARENT, 1.0f);
            view.setLayoutParams(param);
            ItemJiZuZhiBiaoBinding itemBiaoBinding = DataBindingUtil.bind(view.getRootView());
            itemBiaoBinding.zhiBiaoListId2.setAdapter(new Adapter1(list));
            SimpleListTextItem textItem = new SimpleListTextItem();
            textItem.setContent("100%");
            itemBiaoBinding.setItemZhiBiao(textItem);
            binding.contentJiZu.addView(view);
        }

        for (int n = 0; n < 70; n++) {
            FrameLayout view = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.item_name_list, binding.nameListLayout, false);
          /*  LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(width1,
                    LinearLayout.LayoutParams.FILL_PARENT, 1.0f);
            view.setLayoutParams(param);*/
            ItemNameListBinding itemBiaoBinding = DataBindingUtil.bind(view.getRootView());
            SimpleListTextItem textItem = new SimpleListTextItem();
            textItem.setTitle(n + "#机组");
            itemBiaoBinding.setItemZhiBiao(textItem);
            binding.nameListLayout.addView(view);
        }


    }

    ItemNameListBinding nameListBinding;

    class Adapter extends BaseAdapter {
        List<SimpleListTextItem> list;

        public Adapter(List<SimpleListTextItem> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                nameListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_name_list, parent, false);
                convertView = nameListBinding.getRoot();
                convertView.setTag(nameListBinding);
            } else {
                nameListBinding = (ItemNameListBinding) convertView.getTag();
            }
            nameListBinding.setItemZhiBiao(list.get(position));
            return convertView;
        }
    }


    class Adapter1 extends BaseAdapter {
        List<SimpleListTextItem> list;

        public Adapter1(List<SimpleListTextItem> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                nameListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_name_list, parent, false);
                convertView = nameListBinding.getRoot();
                convertView.setTag(nameListBinding);
            } else {
                nameListBinding = (ItemNameListBinding) convertView.getTag();
            }
            nameListBinding.setItemZhiBiao(list.get(position));
            return convertView;
        }
    }

}
