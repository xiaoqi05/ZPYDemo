package com.zpy.models;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;
import com.rey.material.widget.SnackBar;
import com.squareup.okhttp.Request;
import com.zpy.R;
import com.zpy.Util.okhttp.OkHttpClientManager;
import com.zpy.Util.okhttp.Url;
import com.zpy.entity.UnionResultBean;

import java.util.HashMap;
import java.util.Map;

public class CheckActivity extends BaseActivity implements View.OnClickListener{
    private SnackBar mSnackBar;
    private Button bt_check;
    private EditText et_card_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        initToolbar(this, true, "查询状态");
        findView();
    }

    private void findView() {
        bt_check = (Button) findViewById(R.id.bt_check);
        bt_check.setOnClickListener(this);
        et_card_num = (EditText) findViewById(R.id.et_card_num);
    }

    private void checkState() {
        Map<String ,String> params = new HashMap<>();
        //params.put("locked",1+"");
       /* OkHttpClientManager.postAsyn(Url.CHECK, new OkHttpClientManager.ResultCallback<UnionResultBean>() {

            @Override
            public void onError(Request request, Exception e) {
                
            }

            @Override
            public void onResponse(UnionResultBean response) {
                showLongToast(response.getMessage());
            }
        },params);*/
        OkHttpClientManager.getAsyn(Url.CHECK, new OkHttpClientManager.ResultCallback<UnionResultBean>() {

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(UnionResultBean response) {
                showLongToast(response.getMessage());
                
            }
        });
        

     

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_check:
                if (TextUtils.isEmpty(et_card_num.getText().toString().trim())){
                    showLongToast("请填写银行卡号");
                }else {
                    checkState();
                }
                break;
            
            
            
        }
    }
}
