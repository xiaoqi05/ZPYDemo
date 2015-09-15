package com.zpy.models;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.zpy.R;


/*********************************************************************************
 * Project Name  : PhotoPicker
 * Package       : me.iwf.zpybankdemo.models
 *
 * @AUTHOR by 肖齐
 * 邮箱：xiaoqi262@163.com
 * Created by 2015/9/14 19:05.
 * <p/>
 * <p/>
 * ********************************************************************************
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    public void initToolbar(Activity activity, boolean isBack,String title){
        Toolbar mToolbar = (Toolbar) activity.findViewById(R.id.ly_toolbar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        if (isBack){
            ActionBar actionBar = getSupportActionBar();
            assert actionBar != null;
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }
    
}