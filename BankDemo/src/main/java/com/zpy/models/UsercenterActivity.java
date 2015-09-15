package com.zpy.models;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.rey.material.widget.Spinner;
import com.zpy.R;


public class UsercenterActivity extends BaseActivity {
    private Spinner spn_label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercenter);
        initToolbar(this, true, "个人设置");
        findView();
        initData();
    }

   
    private void findView() {
         spn_label = (Spinner)findViewById(R.id.spinner_label);
    }

    private void initData() {
        String[] items = new String[20];
        for(int i = 0; i < items.length; i++)
            items[i] = "内蒙古银行 " + String.valueOf(i + 1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(UsercenterActivity.this, R.layout.row_spn, items);
        spn_label.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_usercenter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
