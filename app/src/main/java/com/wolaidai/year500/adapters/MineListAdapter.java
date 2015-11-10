package com.wolaidai.year500.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wolaidai.year500.R;
import com.wolaidai.year500.beans.TypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielzhang on 15/9/22.
 */
public class MineListAdapter extends BaseAdapter {

    private Context mContext;
    private List<TypeBean> datas;

    public MineListAdapter(Context context) {
        mContext = context;
    }

    public void setDatas(List<TypeBean> datalist) {
        this.datas = datalist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.group_button, null);
            viewHolder = new ViewHolder();
            viewHolder.rl_group_button_root = (RelativeLayout) convertView.findViewById(R.id.rl_group_button_root);
            viewHolder.tv_group_button_title_left = (TextView) convertView.findViewById(R.id.tv_group_button_title_left);
            viewHolder.tv_group_button_title_right = (TextView) convertView.findViewById(R.id.tv_group_button_title_right);
            viewHolder.tv_group_button_count = (TextView) convertView.findViewById(R.id.tv_group_button_count);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.rl_group_button_root.setBackgroundResource(datas.get(position).isSelected() ? R.drawable.button_orange_check : R.drawable.button_orange_press);
        viewHolder.tv_group_button_title_left.setText(datas.get(position).getTitle());
        viewHolder.tv_group_button_title_right.setText(datas.get(position).getType());
        viewHolder.tv_group_button_count.setText(datas.get(position).getCount() + "件");
        return convertView;
    }

    class ViewHolder {
        RelativeLayout rl_group_button_root;
        TextView tv_group_button_title_left;
        TextView tv_group_button_title_right;
        TextView tv_group_button_count;
    }
}
