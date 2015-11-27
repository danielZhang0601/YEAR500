package com.wolaidai.year500.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.wolaidai.year500.R;
import com.wolaidai.year500.protocols.YearsAPI;
import com.wolaidai.year500.utils.SharedPreferencesHelper;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by danielzhang on 15/9/30.
 */
public class SignInActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText et_sign_in_account;
    private EditText et_sign_in_password;
    private Button btn_sign_in_sign_up;
    private Button btn_sign_in_forget_password;
    private ProgressDialog loginProgress;
    private EditText et_net_config_url;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //init view
        et_sign_in_account = (EditText) findViewById(R.id.et_sign_in_account);
        et_sign_in_password = (EditText) findViewById(R.id.et_sign_in_password);
        btn_sign_in_sign_up = (Button) findViewById(R.id.btn_sign_in_sign_up);
        btn_sign_in_forget_password = (Button) findViewById(R.id.btn_sign_in_forget_password);

        et_sign_in_account.setOnFocusChangeListener(this);
        et_sign_in_password.setOnFocusChangeListener(this);
        findViewById(R.id.rl_sign_in_root).setOnClickListener(this);
        findViewById(R.id.btn_sign_in_sign_in).setOnClickListener(this);
        btn_sign_in_sign_up.setOnClickListener(this);
        btn_sign_in_forget_password.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        count = 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 100:
                if (resultCode == Activity.RESULT_OK) {
                    et_sign_in_account.setText(data.getStringExtra(getString(R.string.account)));
                    et_sign_in_password.setText(data.getStringExtra(getString(R.string.password)));
                    login();
                }
                break;
            case 101:
                if (resultCode == Activity.RESULT_OK)
                    et_sign_in_account.setText(data.getStringExtra(getString(R.string.account)));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_sign_in_root:
                showNetworkConfig();
                break;
            case R.id.btn_sign_in_sign_in:
                login();
                break;
            case R.id.btn_sign_in_sign_up:
                startActivityForResult(SignUpActivity.class, 100);
                break;
            case R.id.btn_sign_in_forget_password:
                startActivityForResult(ForgetPasswordActivity.class, 101);
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (et_sign_in_account.isFocused() || et_sign_in_password.isFocused()) {
            btn_sign_in_sign_up.setVisibility(View.GONE);
            btn_sign_in_forget_password.setVisibility(View.GONE);
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            btn_sign_in_sign_up.setVisibility(View.VISIBLE);
            btn_sign_in_forget_password.setVisibility(View.VISIBLE);
        }
    }

    private void login() {
        final String account = et_sign_in_account.getText().toString();
        final String password = et_sign_in_password.getText().toString();
        if (account.isEmpty())
            Toast.makeText(activityThis, R.string.sign_in_account_is_empty, Toast.LENGTH_SHORT).show();
        else if (!account.matches("^1[3|4|5|8][0-9]\\d{4,8}$"))
            Toast.makeText(activityThis, R.string.sign_in_account_not_match, Toast.LENGTH_SHORT).show();
        else if (password.isEmpty())
            Toast.makeText(activityThis, R.string.sign_in_password_is_empty, Toast.LENGTH_SHORT).show();
        else if (!password.matches("^[a-zA-Z0-9]{4,20}"))
            Toast.makeText(activityThis, R.string.sign_in_password_not_match, Toast.LENGTH_SHORT).show();
        else {
            loginProgress = new ProgressDialog(activityThis);
            loginProgress.setCancelable(false);
            loginProgress.setMessage(getString(R.string.sign_in_loading));
            loginProgress.show();
            YearsAPI.login(activityThis, account, password, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    loginProgress.dismiss();
                    try {
                        JSONObject result = response.getJSONObject(getString(R.string.json_result));
                        String userId = result.getString(getString(R.string.json_id));
                        SharedPreferencesHelper.putString(activityThis, getString(R.string.app_name), getString(R.string.account), account);
                        SharedPreferencesHelper.putString(activityThis, getString(R.string.app_name), getString(R.string.password), password);
                        SharedPreferencesHelper.putString(activityThis, getString(R.string.app_name), getString(R.string.user_id), userId);
                        startActivity(MainActivity.class);
                        activityThis.finish();
                    } catch (JSONException e) {
                        Toast.makeText(activityThis, R.string.response_error, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Toast.makeText(activityThis, String.format(getString(R.string.response_format), statusCode, errorResponse != null ? errorResponse.toString() : ""), Toast.LENGTH_SHORT).show();
                    loginProgress.dismiss();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(activityThis, String.format(getString(R.string.response_format), statusCode, ""), Toast.LENGTH_SHORT).show();
                    loginProgress.dismiss();
                }
            });
        }
    }

    private DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                YearsAPI.BASE_URL = et_net_config_url.getText().toString();
            }
        }
    };

    private void showNetworkConfig() {
        if (count++ > 5) {
            count = 0;
            AlertDialog networkConfigDialog = new AlertDialog.Builder(activityThis).create();
            View view = LayoutInflater.from(activityThis).inflate(R.layout.dialog_network_config, null);
            et_net_config_url = (EditText) view.findViewById(R.id.et_net_config_url);
            et_net_config_url.setText(YearsAPI.BASE_URL);
            networkConfigDialog.setView(view);
            networkConfigDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.net_accept), dialogClickListener);
            networkConfigDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.net_cancel), dialogClickListener);
            networkConfigDialog.setCancelable(false);
            networkConfigDialog.show();
        }
    }
}
