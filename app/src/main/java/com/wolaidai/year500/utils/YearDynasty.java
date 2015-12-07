package com.wolaidai.year500.utils;

import android.content.Context;

import com.wolaidai.year500.R;
import com.wolaidai.year500.beans.DynastyBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielzhang on 15/12/3.
 */
public class YearDynasty {

    private YearDynasty() {
    }

    private static YearDynasty instance = new YearDynasty();
    private static List<DynastyBean> dynastys = new ArrayList<>();

    public static YearDynasty getInstance() {
        return instance;
    }

    public void init(Context context, String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            if (!object.getString(context.getString(R.string.json_error)).equals(context.getString(R.string.json_false))) {
                return;
            }
            JSONArray result = object.getJSONArray(context.getString(R.string.json_result));
            for (int i = 0; i < result.length(); i++) {
                DynastyBean dynasty = new DynastyBean();
                dynasty.setCode(result.getJSONObject(i).getString(context.getString(R.string.json_dynasty_code)));
                dynasty.setName(result.getJSONObject(i).getString(context.getString(R.string.json_dynasty_name)));
                dynastys.add(dynasty);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getNameByCode(String code) {
        String ret = null;
        for (int i = 0; i < dynastys.size(); i++) {
            if (dynastys.get(i).getCode().equals(code))
                ret = dynastys.get(i).getName();
        }
        return ret;
    }

    public String getNameByIndex(int index) {
        return dynastys.get(index).getName();
    }

    public String getCodeByName(String name) {
        String ret = null;
        for (int i = 0; i < dynastys.size(); i++) {
            if (dynastys.get(i).getName().equals(name))
                ret = dynastys.get(i).getCode();
        }
        return ret;
    }

    public List<String> getNameList() {
        List<String> names = new ArrayList<>();
        for (DynastyBean dynasty : dynastys) {
            names.add(dynasty.getName());
        }
        return names;
    }

}
