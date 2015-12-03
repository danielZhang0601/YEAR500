package com.wolaidai.year500.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.wolaidai.year500.R;
import com.wolaidai.year500.beans.DynastyBean;
import com.wolaidai.year500.beans.GoodsBean;
import com.wolaidai.year500.adapters.MineGridViewAdapter;
import com.wolaidai.year500.adapters.MineListAdapter;
import com.wolaidai.year500.beans.TypeBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.wolaidai.year500.protocols.YearsAPI;
import com.wolaidai.year500.utils.SharedPreferencesHelper;
import com.wolaidai.year500.utils.YearDynasty;
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
    private ProgressDialog netDialog;

    private int currentTypePosition = 0;

    private List<DynastyBean> heads = new ArrayList<>();

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
        findViewById(R.id.iv_activity_back).setOnClickListener(this);
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

        getDynasts();

        getCollections(SharedPreferencesHelper.getString(activityThis, getString(R.string.app_name), getString(R.string.user_id), ""),
                SharedPreferencesHelper.getString(activityThis, getString(R.string.app_name), getString(R.string.account), ""), 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_activity_back:
                onBackPressed();
                break;
            case R.id.btn_mine_add_goods:
                startActivity(CollectionDetailActivity.class);
                break;
            case R.id.btn_mine_add_types:
                showAddTypeDialog();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == lv_mine_button_group) {
            for (int i = 0; i < typeList.size(); i++) {
                if (i == position) typeList.get(i).setIsSelected(true);
                else typeList.get(i).setIsSelected(false);
            }
            goodsList.clear();
            try {
                JSONArray array = new JSONArray(typeList.get(position).getGoodsDetailJson());
                for (int i = 0; i < array.length(); i++) {
                    String headCode = array.getJSONObject(i).getString(getString(R.string.json_dynasty_code));
                    boolean headContains = false;
                    int index = -1;
                    for (int j = 0; j < heads.size(); j++) {
                        if (heads.get(j).getCode().equals(headCode)) {
                            headContains = true;
                            index = j;
                            break;
                        }
                    }
                    if (!headContains) {
                        heads.add(new DynastyBean(headCode, array.getJSONObject(i).getString(getString(R.string.json_dynasty_name))));
                        index = heads.size() - 1;
                    }
                    GoodsBean goods = new GoodsBean();
                    goods.setHeaderId(index);
                    goods.setTypeName(heads.get(index).getName());
                    goods.setImageUrl(array.getJSONObject(i).getString(getString(R.string.json_main_image_url)));
                    goods.setId(array.getJSONObject(i).getString(getString(R.string.json_id)));
                    if (goodsList.size() > 0) {
                        GoodsBean lastGoods = goodsList.get(goodsList.size() - 1);
                        if (index == lastGoods.getHeaderId()) {
                            if (lastGoods.getColumn() == 0) {
                                goods.setRow(lastGoods.getRow());
                                goods.setColumn(1);
                            } else {
                                goods.setRow(lastGoods.getRow() + 1);
                                goods.setColumn(0);
                            }
                        } else {
                            goods.setRow(lastGoods.getRow() + 2);
                            goods.setColumn(0);
                        }
                    } else {
                        goods.setRow(1);
                        goods.setColumn(0);
                    }
                    int positionCount = goods.getRow() * 2 + goods.getColumn();
                    goods.setPressPosition(positionCount);
                    goods.setType(typeList.get(position).getType() + String.format("%02d", i + 1));
//                    goods.setType(typeList.get(position).getType() + String.format("%03d", positionCount));
                    goodsList.add(goods);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            currentTypePosition = position;
            typeAdapter.notifyDataSetChanged();
            goodsAdapter.notifyDataSetChanged();
        } else if (parent == dsghgv_mine_collections) {
            Intent intent = new Intent(activityThis, CollectionDetailActivity.class);
            intent.putExtra(getString(R.string.collection_id), goodsList.get(position).getId());
            intent.putExtra(getString(R.string.create_collection), false);
            startActivity(intent);
        }
    }

    @Override
    public void onUpOutside(AdapterView<?> parent, View view, int position, long id, float rawX, float rawY) {
        if (parent == dsghgv_mine_collections) {
            final int listPosition = lv_mine_button_group.pointToPosition(
                    (int) rawX - lv_mine_button_group.getLeft(),
                    (int) rawY - lv_mine_button_group.getTop()
                            - getStatusHeight(parent.getContext()));
            if (listPosition != ListView.INVALID_POSITION && listPosition != currentTypePosition) {
                netDialog = new ProgressDialog(activityThis);
                netDialog.setMessage(getString(R.string.mine_loading));
                netDialog.setCancelable(false);
                netDialog.show();
                //添加到相应的list
                String collectionId = "";
                for (GoodsBean goodsBean : goodsList) {
                    if (goodsBean.getPressPosition() == position) {
                        collectionId = goodsBean.getId();
                        break;
                    }
                }
                if (collectionId.isEmpty())
                    return;
                YearsAPI.updateCollectionByType(activityThis, collectionId, typeList.get(listPosition).getId(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        netDialog.dismiss();
                        Toast.makeText(activityThis, R.string.mine_load_done, Toast.LENGTH_SHORT).show();
                        getCollections(SharedPreferencesHelper.getString(activityThis, getString(R.string.app_name), getString(R.string.user_id), ""),
                                SharedPreferencesHelper.getString(activityThis, getString(R.string.app_name), getString(R.string.account), ""), listPosition);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        netDialog.dismiss();
                        Toast.makeText(activityThis, R.string.mine_load_error, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }

    private void getDynasts() {
        YearsAPI.getDynasts(activityThis, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                YearDynasty.getInstance().init(getApplicationContext(), s);
            }
        });
    }

    private void getCollections(String userId, String account, final int position) {
        YearsAPI.getCollections(activityThis, userId, account, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getString(getString(R.string.json_error)).equals(getString(R.string.json_false))) {
                        typeList = getTypeList(response.getJSONObject(getString(R.string.json_result)).toString());
                        typeAdapter.setDatas(typeList);
                        lv_mine_button_group.performItemClick(null, position, position);
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

    private DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                
            }
        }
    };

    private void showAddTypeDialog() {
        AlertDialog addTypeDialog = new AlertDialog.Builder(activityThis).create();
        View layout = LayoutInflater.from(activityThis).inflate(R.layout.dialog_add_type, null);
        addTypeDialog.setView(layout);
        addTypeDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.add_type_accept), dialogClickListener);
        addTypeDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.add_type_cancel), dialogClickListener);
        addTypeDialog.setCancelable(false);
        addTypeDialog.show();
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
