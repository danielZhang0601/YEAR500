package com.wolaidai.year500.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    private RelativeLayout rl_sign_in_root;
    private EditText et_sign_in_account;
    private EditText et_sign_in_password;
    private Button btn_sign_in_sign_in;
    private Button btn_sign_in_sign_up;
    private Button btn_sign_in_forget_password;
    private ProgressDialog loginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //init view
        rl_sign_in_root = (RelativeLayout) findViewById(R.id.rl_sign_in_root);
        et_sign_in_account = (EditText) findViewById(R.id.et_sign_in_account);
        et_sign_in_password = (EditText) findViewById(R.id.et_sign_in_password);
        btn_sign_in_sign_in = (Button) findViewById(R.id.btn_sign_in_sign_in);
        btn_sign_in_sign_up = (Button) findViewById(R.id.btn_sign_in_sign_up);
        btn_sign_in_forget_password = (Button) findViewById(R.id.btn_sign_in_forget_password);

        et_sign_in_account.setOnFocusChangeListener(this);
        et_sign_in_password.setOnFocusChangeListener(this);
        rl_sign_in_root.setOnClickListener(this);
        btn_sign_in_sign_in.setOnClickListener(this);
        btn_sign_in_sign_up.setOnClickListener(this);
        btn_sign_in_forget_password.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 100:
                if (resultCode == Activity.RESULT_OK) {
                    et_sign_in_account.setText(data.getStringExtra("account"));
                    et_sign_in_password.setText(data.getStringExtra("password"));
                    login();
                }
                break;
            case 101:
                if (resultCode == Activity.RESULT_OK)
                    et_sign_in_account.setText(data.getStringExtra("account"));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            Toast.makeText(activityThis,R.string.sign_in_account_is_empty,Toast.LENGTH_SHORT).show();
        else if (!account.matches("^1[3|4|5|8][0-9]\\d{4,8}$"))
            Toast.makeText(activityThis,R.string.sign_in_account_not_match,Toast.LENGTH_SHORT).show();
        else if (password.isEmpty())
            Toast.makeText(activityThis,R.string.sign_in_password_is_empty,Toast.LENGTH_SHORT).show();
        else if (!password.matches("^[a-zA-Z0-9]{4,20}"))
            Toast.makeText(activityThis,R.string.sign_in_password_not_match,Toast.LENGTH_SHORT).show();
        else {
            loginProgress = new ProgressDialog(activityThis);
            loginProgress.setCancelable(false);
            loginProgress.setMessage(getString(R.string.sign_in_loading));
            loginProgress.show();
            YearsAPI.login(activityThis, account, password,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    loginProgress.dismiss();
                    SharedPreferencesHelper.putString(activityThis, getString(R.string.app_name), getString(R.string.account), account);
                    SharedPreferencesHelper.putString(activityThis, getString(R.string.app_name), getString(R.string.password), password);
                    startActivity(MainActivity.class);
                    activityThis.finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Toast.makeText(activityThis, String.format(getString(R.string.response_format), statusCode, errorResponse!=null?errorResponse.toString():""), Toast.LENGTH_SHORT).show();
                    loginProgress.dismiss();
                }
            });
        }
    }
}
