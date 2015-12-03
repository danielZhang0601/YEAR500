package com.wolaidai.year500.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.wolaidai.year500.R;
import com.wolaidai.year500.beans.ImageBean;
import com.wolaidai.year500.beans.RecordBean;
import com.wolaidai.year500.protocols.YearsAPI;
import com.wolaidai.year500.widgets.LoadingImageView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielzhang on 15/11/10.
 */
public class CollectionDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView et_collection_detail_record;
    private TextView et_collection_detail_story;
    private TextView tv_collection_detail_dynasty;
    private TextView tv_collection_detail_length;
    private TextView tv_collection_detail_width;
    private TextView tv_collection_detail_height;
    private TextView tv_collection_detail_weight;

    private GridView gv_collection_detail_images;
    private GoodsImageAdapter imageAdapter;

    private LinearLayout ll_collection_detail_scroll_root;

    private ProgressDialog loadDialog;

    private boolean isCreate = true;
    private String collectionId;

    private List<ImageBean> images = new ArrayList<>();
    private List<RecordBean> records = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_detail);

        findViewById(R.id.iv_activity_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_activity_title)).setText(R.string.detail_title);

        ll_collection_detail_scroll_root = (LinearLayout) findViewById(R.id.ll_collection_detail_scroll_root);
        et_collection_detail_record = (TextView) findViewById(R.id.et_collection_detail_record);
        et_collection_detail_story = (TextView) findViewById(R.id.et_collection_detail_story);

        gv_collection_detail_images = (GridView) findViewById(R.id.gv_collection_detail_images);
        imageAdapter = new GoodsImageAdapter(activityThis);
        imageAdapter.setDatas(images);
        gv_collection_detail_images.setAdapter(imageAdapter);

        findViewById(R.id.rl_collection_detail_modify_base_info).setOnClickListener(this);
        findViewById(R.id.rl_collection_detail_modify_record).setOnClickListener(this);
        findViewById(R.id.rl_collection_detail_modify_story).setOnClickListener(this);

        tv_collection_detail_dynasty = (TextView) findViewById(R.id.tv_collection_detail_dynasty);
        tv_collection_detail_length = (TextView) findViewById(R.id.tv_collection_detail_length);
        tv_collection_detail_width = (TextView) findViewById(R.id.tv_collection_detail_width);
        tv_collection_detail_height = (TextView) findViewById(R.id.tv_collection_detail_height);
        tv_collection_detail_weight = (TextView) findViewById(R.id.tv_collection_detail_weight);
        collectionId = getIntent().getStringExtra(getString(R.string.collection_id));
        collectionId = "S1440165538460";
        isCreate = getIntent().getBooleanExtra(getString(R.string.create_collection), true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ll_collection_detail_scroll_root.requestFocus();
        if (null != collectionId && collectionId.isEmpty())
            return;
        loadDialog = new ProgressDialog(activityThis);
        loadDialog.setMessage(getString(R.string.detail_item_info_loading));
        loadDialog.setCancelable(false);
        loadDialog.show();
        YearsAPI.getCollectionDetail(activityThis, collectionId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getString(getString(R.string.json_error)).equals(getString(R.string.json_false)))
                        updateView(response.getJSONObject(getString(R.string.json_result)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onFinish() {
                super.onFinish();
                loadDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_activity_back:
                onBackPressed();
                break;
            case R.id.rl_collection_detail_modify_base_info:
                break;
            case R.id.rl_collection_detail_modify_record:
                Intent recordIntent = new Intent(activityThis, ModifyRecordActivity.class);
                recordIntent.putExtra(getString(R.string.collection_record), et_collection_detail_record.getText().toString());
                startActivity(recordIntent);
                break;
            case R.id.rl_collection_detail_modify_story:
                Intent storyIntent = new Intent(activityThis, ModifyStoryActivity.class);
                storyIntent.putExtra(getString(R.string.collection_story), et_collection_detail_story.getText().toString());
                startActivity(storyIntent);
                break;
        }
    }


    private void updateView(JSONObject response) throws JSONException {
        JSONObject baseInfo = response.getJSONObject(getString(R.string.json_sou_base_info));
        String length = baseInfo.has(getString(R.string.json_length)) ? ("  " + baseInfo.getString(getString(R.string.json_length))) : "    ";
        String width = baseInfo.has(getString(R.string.json_width)) ? ("  " + baseInfo.getString(getString(R.string.json_width))) : "    ";
        String height = baseInfo.has(getString(R.string.json_height)) ? ("  " + baseInfo.getString(getString(R.string.json_height))) : "    ";
        String weight = baseInfo.has(getString(R.string.json_weight)) ? ("  " + baseInfo.getString(getString(R.string.json_weight))) : "    ";
        String dynastyname = baseInfo.has(getString(R.string.json_dynasty_name)) ? ("  " + baseInfo.getString(getString(R.string.json_dynasty_name)) + "  ") : "    ";
        images.clear();
        images.addAll(JSON.parseArray(response.getString(getString(R.string.json_sou_images_list)), ImageBean.class));
        images.add(new ImageBean(true));
        records.clear();
        records.addAll(JSON.parseArray(response.getString(getString(R.string.json_user_records)), RecordBean.class));

        //更新view
        tv_collection_detail_dynasty.setText(getString(R.string.detail_item_detail_dynasty) + dynastyname);
        tv_collection_detail_length.setText(getString(R.string.detail_item_detail_length) + length + getString(R.string.detail_item_detail_cm));
        tv_collection_detail_width.setText(getString(R.string.detail_item_detail_width) + getString(R.string.detail_item_detail_cm));
        tv_collection_detail_height.setText(getString(R.string.detail_item_detail_height) + height + getString(R.string.detail_item_detail_cm));
        tv_collection_detail_weight.setText(getString(R.string.detail_item_detail_height) + weight + getString(R.string.detail_item_detail_g));

        String recordStr = "", storyStr = "";
        for (RecordBean record : records) {
            if (record.getTitle().equals(getString(R.string.detail_item_record)))
                recordStr = record.getContent();
            if (record.getTitle().equals(getString(R.string.detail_item_story))
                    || record.getTitle().equals("心得体悟"))    //数据问题
                storyStr += storyStr.isEmpty() ? record.getContent() : ("\n" + record.getContent());
        }
        et_collection_detail_record.setText(recordStr);
        et_collection_detail_story.setText(storyStr);

        imageAdapter.notifyDataSetChanged();
    }

    class GoodsImageAdapter extends BaseAdapter {

        private Context mContext;
        private List<ImageBean> datas;

        public GoodsImageAdapter(Context context) {
            mContext = context;
        }

        public void setDatas(List<ImageBean> images) {
            datas = images;
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
            ViewHolder holder = null;
            if (null == convertView) {
                if (!datas.get(position).isAdd()) {
                    holder = new ViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.goods_image_grid_item, null);
                    convertView.setTag(holder);
                } else {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.goods_image_grid_add, null);
                }
            } else {
                if (!datas.get(position).isAdd()) {
                    holder = (ViewHolder) convertView.getTag();
                }
            }

            if (!datas.get(position).isAdd() && null != holder) {
                holder.liv_image_item = (LoadingImageView) convertView.findViewById(R.id.liv_image_item);
                String url = "http://image5.huangye88.com/2014/11/12/120b3529e7eb3c55.jpg";
                YearsAPI.getImage(mContext, url, holder.liv_image_item.getAsyncHttpResponseHandler());
            }
            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            return datas.get(position).isAdd() ? 0 : 1;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        class ViewHolder {
            LoadingImageView liv_image_item;
        }

    }
}
