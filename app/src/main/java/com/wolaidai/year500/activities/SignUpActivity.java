package com.wolaidai.year500.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.wolaidai.year500.R;
import com.wolaidai.year500.protocols.YearsAPI;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by danielzhang on 15/9/30.
 */
public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_sign_up_account;
    private EditText et_sign_up_password;
    private Button btn_sign_up_done;
    private ProgressDialog signUpProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ((TextView) findViewById(R.id.tv_activity_title)).setText(R.string.sign_up_title);
        findViewById(R.id.iv_activity_back).setOnClickListener(this);
        et_sign_up_account = (EditText) findViewById(R.id.et_sign_up_account);
        et_sign_up_password = (EditText) findViewById(R.id.et_sign_up_password);
        btn_sign_up_done = (Button) findViewById(R.id.btn_sign_up_done);
        btn_sign_up_done.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_activity_back:
                onBackPressed();
                break;
            case R.id.btn_sign_up_done:
                signUp();
                break;
        }
    }

    private void signUp() {
        String account = et_sign_up_account.getText().toString();
        String password = et_sign_up_password.getText().toString();
        if (account.isEmpty())
            Toast.makeText(activityThis, R.string.sign_in_account_is_empty, Toast.LENGTH_SHORT).show();
        else if (!account.matches("^1[3|4|5|8][0-9]\\d{4,8}$"))
            Toast.makeText(activityThis, R.string.sign_in_account_not_match, Toast.LENGTH_SHORT).show();
        else if (password.isEmpty())
            Toast.makeText(activityThis, R.string.sign_in_password_is_empty, Toast.LENGTH_SHORT).show();
        else if (!password.matches("^[a-zA-Z0-9]{4,20}"))
            Toast.makeText(activityThis, R.string.sign_in_password_not_match, Toast.LENGTH_SHORT).show();
        else {
            signUpProgress = new ProgressDialog(activityThis);
            signUpProgress.setCancelable(false);
            signUpProgress.setMessage(getString(R.string.sign_up_loading));
            signUpProgress.show();
            YearsAPI.reg(activityThis, account, password, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    signUpProgress.dismiss();
                    Log.e("ZXD", response.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    signUpProgress.dismiss();
                    Log.e("ZXD", "error:" + responseString);
                }
            });
        }
    }
}
