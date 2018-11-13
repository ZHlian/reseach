package com.zhlian.lib_baseutil.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhlian.lib_baseutil.AppBean;
import com.zhlian.lib_baseutil.R;
import com.zhlian.lib_baseutil.util.FormatTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 64553 on 2018-11-13.
 * Power By ChangnAutoMobile RCLink Team
 */

public class AppSelectAdapter extends RecyclerView.Adapter<AppSelectAdapter.ViewHolder>implements View.OnClickListener{
    private List<AppBean>mData = new ArrayList<>();
    private ItemSelectedListener itemSelectedListener;

    public AppSelectAdapter(List<AppBean>data){
        this.mData = data;
    }


    public void registOnSelectListener(ItemSelectedListener listener) {
        itemSelectedListener = listener;
    }

    public void unregistOnSelectListener(){
        itemSelectedListener = null;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item_select,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
        holder.appName.setText(mData.get(position).appName);
        holder.icon.setImageBitmap(FormatTools.getInstance().Bytes2Bitmap(mData.get(position).icon));
        if (mData.get(position).isInSelectMode){
            holder.selectedBox.setChecked(true);
        }else {
            holder.selectedBox.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
//        if (null != itemSelectedListener){
//            notifyDataSetChanged();
        if(!((CheckBox)v.findViewById(R.id.cb_select)).isChecked()){
            ((CheckBox)v.findViewById(R.id.cb_select)).setChecked(true);
            mData.get((int)v.getTag()).isInSelectMode =true;

        }else {
            ((CheckBox)v.findViewById(R.id.cb_select)).setChecked(false);
            mData.get((int)v.getTag()).isInSelectMode =false;

        }

//            itemSelectedListener.onItemSelected((int)v.getTag());
//        }
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView appName;
        public final ImageView icon;
        public final CheckBox selectedBox;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.select_app_icon);
            appName = itemView.findViewById(R.id.select_app_name);
            selectedBox = itemView.findViewById(R.id.cb_select);
        }
    }

    public List<AppBean> getData(){
        return mData;
    }

    public void setData(List<AppBean>beans){
        mData.clear();
        mData.addAll(beans);
        notifyDataSetChanged();
    }

    public interface ItemSelectedListener{
        void onItemSelected(int pos);
    }
}
