package com.zpy.models;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

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

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    public void showShortToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }



}