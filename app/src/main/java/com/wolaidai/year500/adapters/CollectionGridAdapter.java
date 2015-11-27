package com.wolaidai.year500.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wolaidai.year500.R;
import com.wolaidai.year500.widgets.LoadingImageView;

import java.util.List;

/**
 * Created by danielzhang on 15/11/27.
 */
public class CollectionGridAdapter extends BaseAdapter {

    private Context context;
    private List<String> images;

    public CollectionGridAdapter(Context context) {
        this.context = context;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.images_grid_item, parent, true);
            holder = new ViewHolder();
            holder.liv_image_item = (LoadingImageView) convertView.findViewById(R.id.liv_image_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {
        LoadingImageView liv_image_item;
    }
}
