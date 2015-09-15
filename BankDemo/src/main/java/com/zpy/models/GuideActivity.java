package com.zpy.models;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.Button;
import com.squareup.okhttp.Request;
import com.zpy.R;
import com.zpy.Util.okhttp.OkHttpClientManager;
import com.zpy.Util.okhttp.Url;
import com.zpy.entity.UnionResultBean;


public class GuideActivity extends BaseActivity implements View.OnClickListener {
    private com.rey.material.widget.Button bt_1;
    private com.rey.material.widget.Button bt_2;
    private com.rey.material.widget.Button bt_3;
    private com.rey.material.widget.Button bt_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initToolbar(this, false, getResources().getString(R.string.app_name));
        findView();
    }

    private void findView() {
        bt_1 = (Button) findViewById(R.id.bt_1);
        bt_2 = (Button) findViewById(R.id.bt_2);
        bt_3 = (Button) findViewById(R.id.bt_3);
        bt_4 = (Button) findViewById(R.id.bt_4);
        bt_1.setOnClickListener(this);
        bt_2.setOnClickListener(this);
        bt_3.setOnClickListener(this);
        bt_4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Dialog.Builder builder = null;
        switch (v.getId()) {
            case R.id.bt_1:
                builder = new SimpleDialog.Builder(R.style.SimpleDialogLight) {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        //锁卡
                        unLockByHttp();
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        super.onNegativeActionClicked(fragment);
                    }
                };
                ((SimpleDialog.Builder) builder).message(" 几乎每个项目都需要适当的使用style文件几乎每个项目都需要适当的使用style文件")
                        .title("确认锁卡？")
                        .positiveAction("确定")
                        .negativeAction("取消");
                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(this.getSupportFragmentManager(), null);
                break;

            case R.id.bt_2:
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_3:
                Intent i3 = new Intent(GuideActivity.this, UsercenterActivity.class);
                startActivity(i3);
                break;
            case R.id.bt_4:
                Intent i4 = new Intent(GuideActivity.this, CheckActivity.class);
                startActivity(i4);
                break;
        }


    }

    private void unLockByHttp() {
        /*Map<String ,String> params = new HashMap<>();
        //params.put("locked",0+"");
        OkHttpClientManager.postAsyn(Url.LOCK, new OkHttpClientManager.ResultCallback<UnionResultBean>() {
            @Override
            public void onError(Request request, Exception e) {
                showLongToast("锁卡失败，请重试");
            }

            @Override
            public void onResponse(UnionResultBean response) {
                showLongToast(response.getMessage());
            }
        },params);
        */
        OkHttpClientManager.getAsyn(Url.LOCK, new OkHttpClientManager.ResultCallback<UnionResultBean>() {
            @Override
            public void onError(Request request, Exception e) {
                showLongToast("锁卡失败，请重试");
            }

            @Override
            public void onResponse(UnionResultBean response) {
                showLongToast(response.getMessage());
            }
        });
    }
}
