package com.changan.lib_baseutil.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.changan.lib_baseutil.AppBean;
import com.changan.lib_baseutil.R;
import com.changan.lib_baseutil.util.FormatTools;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by 64553 on 2018-11-13.
 * Power By ChangnAutoMobile RCLink Team
 */

public class AppShowAdapter extends RecyclerView.Adapter<AppShowAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private List<AppBean> mData = new ArrayList<>();
    private Context mContext;
    private boolean isInDeleteMode;

    private OnItemClickListener onItemClickListener;

    public AppShowAdapter(List<AppBean> data, Context context) {
        mData.addAll(data);
        this.mContext = context;
    }


    public void registOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void unRegistListener() {
        onItemClickListener = null;
    }

    public List<AppBean>getData(){
        return mData;
    }

    public void setData(List<AppBean>beans){
        mData.clear();
        mData.addAll(beans);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_show, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(this);
        holder.itemView.setOnLongClickListener(this);
        holder.itemView.setTag(position);
        holder.icon.setImageBitmap(FormatTools.getInstance().Bytes2Bitmap(mData.get(position).icon));
        holder.appName.setText(mData.get(position).appName);
        holder.deleteIcon.setOnClickListener(this);
        holder.deleteIcon.setTag(position);
        if (mData.get(position).isInDeleteMode) {
            //shake
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.shake);
            holder.itemView.setAnimation(animation);
            holder.deleteIcon.setVisibility(View.VISIBLE);
        } else {
            Animation animation = holder.itemView.getAnimation();
            if (null != animation) {
                animation.cancel();
            }
            holder.deleteIcon.setVisibility(View.GONE);

        }


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (null != onItemClickListener) {
            switch (v.getId()) {
                case R.id.root_view:
                    if (isInDeleteMode) {
                        changeSelectMode(false);
                        Log.e("haha", "switch delete mode false");
                    } else {
                        //
                        onItemClickListener.onClick((int) v.getTag());
                        Log.e("haha", "dispach touch----");
                    }
                    break;
                case R.id.app_delte:
                    Log.e("haha", "app_delte click--");
                    onItemClickListener.onDeleteClick((int) v.getTag());
                    break;
            }
        }

    }

    @Override
    public boolean onLongClick(View v) {
        changeSelectMode(true);
        if (null != onItemClickListener) {
            onItemClickListener.onLongClick((int) v.getTag());
        }
        return false;
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView appName;
        private ImageView deleteIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.app_icon);
            appName = itemView.findViewById(R.id.app_name);
            deleteIcon = itemView.findViewById(R.id.app_delte);
        }
    }

    public void changeSelectMode(boolean delete) {
        isInDeleteMode = delete;
        for (AppBean bean : mData) {
            bean.isInDeleteMode = delete;
        }
        mData.get(mData.size()-1).isInDeleteMode = false;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onClick(int pos);

        void onLongClick(int pos);

        void onDeleteClick(int pos);
    }


}
