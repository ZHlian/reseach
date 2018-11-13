package com.zhlian.lib_baseutil;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhlian.lib_baseutil.adapter.AppSelectAdapter;
import com.zhlian.lib_baseutil.adapter.AppShowAdapter;
import com.zhlian.lib_baseutil.adapter.AppShowAdapter.OnItemClickListener;
import com.zhlian.lib_baseutil.util.FormatTools;
import com.zhlian.lib_baseutil.util.ThirdAppLoader;
import com.zhlian.lib_baseutil.util.ThirdAppUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements OnItemClickListener, AppSelectAdapter.ItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyvlerView;
    private AppShowAdapter mShowAdapter;
    private AppBean addAppBean;
    private LinearLayout mAddPannel;
    private RecyclerView mSelectRecyvlerView;
    private TextView mConfirmTextView;
    private List<AppBean> selectApp;
    private List<AppBean> unSelectedApp;
    private AppSelectAdapter selectAdapter;
    private ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            } else {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            //得到当拖拽的viewHolder的Position
            int fromPosition = viewHolder.getAdapterPosition();
            //拿到当前拖拽到的item的viewHolder
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mShowAdapter.getData(), i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mShowAdapter.getData(), i, i - 1);
                }
            }
            mShowAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        /**
         * 手指松开的时候还原
         * @param recyclerView
         * @param viewHolder
         */
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(0);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addAppBean = new AppBean();
        addAppBean.icon = FormatTools.getInstance().Bitmap2Bytes(BitmapFactory.decodeResource(getResources(), R.drawable.icon_add));
        addAppBean.appName = "添加应用";
//        selectApp = ThirdAppUtil.getInstance().loadAllThirdApp();
        selectApp = new ArrayList<>();
        selectApp.addAll(GreenDaoUtil.getInstance().getAll());

        initUnSelectedApp();
        selectApp.add(addAppBean);
        mRecyvlerView = findViewById(R.id.rv_test);
        mShowAdapter = new AppShowAdapter(selectApp, getApplicationContext());
        mRecyvlerView.setLayoutManager(new GridLayoutManager(this, 5));
        mRecyvlerView.setAdapter(mShowAdapter);
        helper.attachToRecyclerView(mRecyvlerView);
        mShowAdapter.registOnItemClickListener(this);

//
        mAddPannel = findViewById(R.id.add_panel);
        mSelectRecyvlerView = findViewById(R.id.rv_add_app);
        mConfirmTextView = findViewById(R.id.tv_add_confirm);
        mConfirmTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddPannel.setVisibility(View.GONE);
                updateShowAppList();
            }
        });

        selectAdapter = new AppSelectAdapter(unSelectedApp);
        mSelectRecyvlerView.setLayoutManager(new LinearLayoutManager(this));
        mSelectRecyvlerView.setAdapter(selectAdapter);

    }

    private void initPrimaryData() {

    }

    private void initViewAndBind() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mShowAdapter.unRegistListener();
    }

    @Override
    public void onClick(int pos) {
        mAddPannel.setVisibility(View.GONE);
        if (pos == mShowAdapter.getData().size() - 1) {
            Log.e(TAG, "add btn clicked--");
            mAddPannel.setVisibility(View.VISIBLE);
            mAddPannel.requestFocus();
        } else {
            mAddPannel.setVisibility(View.GONE);
            AppBean bean = mShowAdapter.getData().get(pos);
            ThirdAppUtil.getInstance().jumpWithPkgName(bean.pkgName);
        }
    }

    @Override
    public void onLongClick(int pos) {
        mAddPannel.setVisibility(View.GONE);
    }

    @Override
    public void onDeleteClick(int pos) {

        AppBean deleteBean = mShowAdapter.getData().get(pos);
        deleteBean.isInSelectMode = false;
        updateUnSelectedApp(deleteBean);

        mShowAdapter.getData().remove(pos);
        mShowAdapter.notifyDataSetChanged();

        //delete in db
        GreenDaoUtil.getInstance().deleteData(deleteBean.getPkgName());
    }

    @Override
    public void onItemSelected(int pos) {

    }

    private void initUnSelectedApp() {
        unSelectedApp = new ArrayList<>();
        List<AppBean> allApps = ThirdAppUtil.getInstance().loadAllThirdApp();
        for (AppBean bean : allApps) {
            boolean canAdd = true;
            for (AppBean selectedBean : selectApp) {
                if (selectedBean.pkgName.equalsIgnoreCase(bean.pkgName)) {
                    canAdd = false;
                    break;
                }
            }
            if (canAdd) {
                unSelectedApp.add(bean);
            }
        }
        //加载数据库中保存的的app

    }

    /**
     * 点击确定按钮后 获取选中的数据,更新到选择列表
     *
     * @return
     */
    private List<AppBean> getSelectedApp() {
        List<AppBean> result = new ArrayList<>();
        List<AppBean> oldData = selectAdapter.getData();
        List<AppBean> newData = new ArrayList<>();
        if (oldData.size() > 0) {
            for (AppBean bean : oldData) {
                if (bean.isInSelectMode) {
                    result.add(bean);
                } else {
                    newData.add(bean);
                }
            }
        }
        selectAdapter.setData(newData);
        return result;
    }

    private void updateUnSelectedApp(AppBean bean) {
        List<AppBean> newData = new ArrayList<>();
        newData.addAll(selectAdapter.getData());
        newData.add(bean);
        selectAdapter.setData(newData);

    }

    /**
     * 点击确定按钮的时候调用
     */
    private void updateShowAppList() {
        List<AppBean> lastSelectedApp = getSelectedApp();
        selectApp.addAll(lastSelectedApp);
        List<AppBean> newData = new ArrayList<>();
        newData.addAll(mShowAdapter.getData());
        newData.remove(newData.size() - 1);
        newData.addAll(lastSelectedApp);
        newData.add(addAppBean);
        mShowAdapter.setData(newData);
        insertToDB(lastSelectedApp);
    }

    private void insertToDB(List<AppBean> beans) {
        for (AppBean appBean : beans) {
            GreenDaoUtil.getInstance().insertData(appBean);
        }
    }

    @Override
    public void onBackPressed() {
        mAddPannel.setVisibility(View.GONE);
        mShowAdapter.changeSelectMode(false);
        super.onBackPressed();
    }
}
