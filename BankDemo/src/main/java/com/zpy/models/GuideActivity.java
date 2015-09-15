package com.zpy.models;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.Button;
import com.zpy.R;


public class GuideActivity extends BaseActivity  implements View.OnClickListener{
       private com.rey.material.widget.Button bt_1;
       private com.rey.material.widget.Button bt_2;
       private com.rey.material.widget.Button bt_3;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initToolbar(this,false,getResources().getString(R.string.app_name));
        findView();
    }

    private void findView() {
        bt_1 = (Button) findViewById(R.id.bt_1);
        bt_2 = (Button) findViewById(R.id.bt_2);
        bt_3 = (Button) findViewById(R.id.bt_3);
        bt_1.setOnClickListener(this);
        bt_2.setOnClickListener(this);
        bt_3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Dialog.Builder builder = null;
        switch (v.getId()){
            case R.id.bt_1:
               builder = new SimpleDialog.Builder(R.style.SimpleDialogLight ){
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        Toast.makeText(GuideActivity.this, "确认", Toast.LENGTH_SHORT).show();
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(GuideActivity.this, "", Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }
                };
                ( (SimpleDialog.Builder)builder).message(" 几乎每个项目都需要适当的使用style文件几乎每个项目都需要适当的使用style文件")
                        .title("确认解锁？")
                        .positiveAction("确定")
                        .negativeAction("取消");
                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(this.getSupportFragmentManager(), null);
            break;
            
            case R.id.bt_2:
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                break; case R.id.bt_3:
                Intent i3 = new Intent(GuideActivity.this, UsercenterActivity.class);
                startActivity(i3);
                break;
        }
    
        
    }
}
