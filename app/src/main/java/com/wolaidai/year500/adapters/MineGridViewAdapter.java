package com.wolaidai.year500.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wolaidai.year500.R;

import java.util.List;

import com.wolaidai.year500.beans.GoodsBean;
import com.wolaidai.year500.widgets.stickygridheaders.StickyGridHeadersSimpleAdapter;

/**
 * Created by danielzhang on 15/9/18.
 */
public class MineGridViewAdapter extends BaseAdapter implements StickyGridHeadersSimpleAdapter {

    private List<GoodsBean> hasHeaderList;
    private Context mContext;

    public MineGridViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setHasHeaderList(List<GoodsBean> hasHeaderList) {
        this.hasHeaderList = hasHeaderList;
    }

    @Override
    public long getHeaderId(int position) {
        return hasHeaderList.get(position).getHeaderId();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        final HeadViewHolder headViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.goods_grid_head, null);
            headViewHolder = new HeadViewHolder();
            headViewHolder.tv_goods_head = (TextView) convertView.findViewById(R.id.tv_goods_head);
            convertView.setTag(headViewHolder);
        } else {
            headViewHolder = (HeadViewHolder) convertView.getTag();
        }
        headViewHolder.tv_goods_head.setText(hasHeaderList.get(position).getTypeName());
        return convertView;
    }

    @Override
    public int getCount() {
        return hasHeaderList.size();
    }

    @Override
    public Object getItem(int position) {
        return hasHeaderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.goods_grid_item, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_goods_icon = (ImageView) convertView.findViewById(R.id.iv_goods_icon);
            viewHolder.tv_goods_text = (TextView) convertView.findViewById(R.id.tv_goods_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (hasHeaderList.get(position).getImageUrl().equals("0")) {
            viewHolder.iv_goods_icon.setImageResource(R.mipmap.pic1);
        }else if (hasHeaderList.get(position).getImageUrl().equals("1")) {
            viewHolder.iv_goods_icon.setImageResource(R.mipmap.pic2);
        }else if (hasHeaderList.get(position).getImageUrl().equals("2")) {
            viewHolder.iv_goods_icon.setImageResource(R.mipmap.pic3);
        }else if (hasHeaderList.get(position).getImageUrl().equals("3")) {
            viewHolder.iv_goods_icon.setImageResource(R.mipmap.pic4);
        }
        viewHolder.tv_goods_text.setText(hasHeaderList.get(position).getType());
        return convertView;
    }

    class ViewHolder {
        ImageView iv_goods_icon;
        TextView tv_goods_text;
    }

    class HeadViewHolder {
        TextView tv_goods_head;
    }

}
