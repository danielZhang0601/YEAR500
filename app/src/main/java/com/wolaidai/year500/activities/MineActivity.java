package com.wolaidai.year500.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.wolaidai.year500.R;
import com.wolaidai.year500.beans.GoodsBean;
import com.wolaidai.year500.adapters.MineGridViewAdapter;
import com.wolaidai.year500.adapters.MineListAdapter;
import com.wolaidai.year500.beans.TypeBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.wolaidai.year500.protocols.YearsAPI;
import com.wolaidai.year500.utils.SharedPreferencesHelper;
import com.wolaidai.year500.widgets.stickygridheaders.DragStickyGridHeadersGridView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by danielzhang on 15/9/18.
 */
public class MineActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, DragStickyGridHeadersGridView.onUpOutsideListener {

    private ListView lv_mine_button_group;
    private Button btn_mine_add_goods;
    private Button btn_mine_add_types;
    private DragStickyGridHeadersGridView dsghgv_mine_collections;

    private List<TypeBean> typeList;
    private MineListAdapter typeAdapter;
    private List<GoodsBean> goodsList;
    private MineGridViewAdapter goodsAdapter;

    private int currentTypePosition = 0;

    //    private final String[] heads = {"战国", "秦朝", "宋朝"};
    private List<String> heads = new ArrayList<>();

    public static void launch(Context context, Bundle bundle) {
        Intent intent = new Intent(context, MineActivity.class);
        if (null != bundle)
            intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ((TextView) findViewById(R.id.tv_activity_title)).setText(R.string.mine_title);
        btn_mine_add_goods = (Button) findViewById(R.id.btn_mine_add_goods);
        btn_mine_add_goods.setOnClickListener(this);
        btn_mine_add_types = (Button) findViewById(R.id.btn_mine_add_types);
        btn_mine_add_types.setOnClickListener(this);
        lv_mine_button_group = (ListView) findViewById(R.id.lv_mine_button_group);
        dsghgv_mine_collections = (DragStickyGridHeadersGridView) findViewById(R.id.dsghgv_mine_collections);
        typeList = new ArrayList<TypeBean>();
        typeAdapter = new MineListAdapter(this);
        typeAdapter.setDatas(typeList);
        lv_mine_button_group.setAdapter(typeAdapter);
        lv_mine_button_group.setOnItemClickListener(this);
        goodsList = new ArrayList<GoodsBean>();
        goodsAdapter = new MineGridViewAdapter(this);
        goodsAdapter.setHasHeaderList(goodsList);
        dsghgv_mine_collections.setAdapter(goodsAdapter);
        dsghgv_mine_collections.setOnItemClickListener(this);
        dsghgv_mine_collections.setOnUpOutsideListener(this);
//        // 模拟获取数据
//        for (int i = 0; i < 15; i++) {
//            TypeBean type = new TypeBean("类型" + i, (char) ('A' + i) + "", (int) (Math.random() * 100));
//            typeList.add(type);
//        }
        getCollections(SharedPreferencesHelper.getString(activityThis, getString(R.string.app_name), getString(R.string.user_id), ""),
                SharedPreferencesHelper.getString(activityThis, getString(R.string.app_name), getString(R.string.account), ""));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_mine_add_goods:
                break;
            case R.id.btn_mine_add_types:
                TypeBean type = new TypeBean("类型" + typeList.size(), (char) ('A' + typeList.size()) + "", (int) (Math.random() * 100));
                typeList.add(type);
                typeAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == lv_mine_button_group) {
            goodsList.clear();
            try {
                JSONArray array = new JSONArray(typeList.get(position).getGoodsDetailJson());
                for (int i = 0; i < array.length(); i++) {
                    String head = array.getJSONObject(i).getString(getString(R.string.json_dynasty_code));
                    if (!heads.contains(head)) {
                        heads.add(head);
                    }
                    GoodsBean goods = new GoodsBean();
                    int index = heads.indexOf(head);
                    goods.setHeaderId(index);
                    goods.setTypeName(head);
                    goods.setImageUrl(array.getJSONObject(i).getString(getString(R.string.json_main_image_url)));
                    goods.setId(array.getJSONObject(i).getString(getString(R.string.json_id)));
                    goods.setType(typeList.get(position).getType() + String.format("%02d", i + 1));
                    goodsList.add(goods);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            currentTypePosition = position;
            goodsAdapter.notifyDataSetChanged();
        } else if (parent == dsghgv_mine_collections) {

        }
    }

    @Override
    public void onUpOutside(AdapterView<?> parent, View view, int position, long id, float rawX, float rawY) {
        if (parent == dsghgv_mine_collections) {
            int listPosition = lv_mine_button_group.pointToPosition(
                    (int) rawX - lv_mine_button_group.getLeft(),
                    (int) rawY - lv_mine_button_group.getTop()
                            - getStatusHeight(parent.getContext()));
            if (listPosition != ListView.INVALID_POSITION && listPosition != currentTypePosition) {
                Log.e("ZXD", "listPosition" + listPosition);
                //添加到相应的list 这里只改变数字
                typeList.get(listPosition).setCount(typeList.get(listPosition).getCount() + 1);
                typeList.get(currentTypePosition).setCount(typeList.get(currentTypePosition).getCount() - 1);
                goodsList.remove(position - 2); //原因可能是增了了header造成的
                typeAdapter.notifyDataSetChanged();
                goodsAdapter.notifyDataSetChanged();
            }
        }
    }

    private void getCollections(String userId, String account) {
        YearsAPI.getCollections(activityThis, userId, account, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getString(getString(R.string.json_error)).equals(getString(R.string.json_false))) {
                        typeList = getTypeList(response.getJSONObject(getString(R.string.json_result)).toString());
                        typeAdapter.setDatas(typeList);
                        lv_mine_button_group.performItemClick(null, 0, 0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });
    }

    private List<TypeBean> getTypeList(String json) throws JSONException {
        List<TypeBean> list = new ArrayList<TypeBean>();
        JSONObject object = new JSONObject(json);
        JSONArray listArray = object.getJSONArray(getString(R.string.json_sou_type_list));
        for (int i = 0; i < listArray.length(); i++) {
            TypeBean type = new TypeBean();
            type.setId(listArray.getJSONObject(i).getString(getString(R.string.json_id)));
            type.setTitle(listArray.getJSONObject(i).getString(getString(R.string.json_type_name)));
            type.setType((char) ('A' + i) + "");
            //数据有问题 暂时做替代方案
//            type.setCount(Integer.valueOf(listArray.getJSONObject(i).getString(getString(R.string.json_type_count))));
            type.setGoodsDetailJson(object.getString(type.getId()));
            //替代方案
            type.setCount(object.getJSONArray(type.getId()).length());
            list.add(type);
        }
        return list;
    }

    private int getStatusHeight(Context context) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = context.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }
}
