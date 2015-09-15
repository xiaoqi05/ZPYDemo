package com.zpy.models;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;
import com.rey.material.widget.Spinner;
import com.squareup.okhttp.Request;
import com.zpy.R;
import com.zpy.Util.okhttp.OkHttpClientManager;
import com.zpy.Util.okhttp.Url;
import com.zpy.adapter.PhotoAdapter;
import com.zpy.entity.ResultBean;
import com.zpy.entity.UnionResultBean;
import com.zpy.entity.UserInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.libs.PhotoPickerActivity;
import me.iwf.libs.utils.PhotoPickerIntent;


public class UsercenterActivity extends BaseActivity implements View.OnClickListener {
    private Spinner spn_label;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_card_num;
    private Button bt_commit;
    private ImageView iv_photo;
    private final static int REQUEST_CODE = 1;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    private UserInfo userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercenter);
        initToolbar(this, true, "个人设置");
        findView();
        initData();
    }


    private void findView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        spn_label = (Spinner) findViewById(R.id.spinner_label);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_card_num = (EditText) findViewById(R.id.et_card_num);
        bt_commit = (Button) findViewById(R.id.bt_commit);
        iv_photo = (ImageView) findViewById(R.id.iv_photo);
        bt_commit.setOnClickListener(this);
        iv_photo.setOnClickListener(this);

    }

    private void initData() {
        String[] items = new String[20];
        for (int i = 0; i < items.length; i++)
            items[i] = "内蒙古银行 " + String.valueOf(i + 1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(UsercenterActivity.this, R.layout.row_spn, items);
        spn_label.setAdapter(adapter);
        photoAdapter = new PhotoAdapter(this, selectedPhotos);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_commit:
                userInfo = SetUserInfo();
                boolean isAll = checkInfoIsAll(userInfo);
                if (isAll) {
                    try {
                        uploadFile(v);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showLongToast("请填写完整信息");
                }


                break;

            case R.id.iv_photo:
                PhotoPickerIntent intent = new PhotoPickerIntent(UsercenterActivity.this);
                intent.setPhotoCount(1);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
                break;


        }
    }

    private void commitUserInfoByHttp(String name, String phone, String card, String bankName, String imageAddress, String locked) {

        Map<String, String> params = new HashMap<>();
        //// TODO: 2015/9/15  姓名暂时写死为admin 
        params.put("name", "admin");
        params.put("phone", phone);
        params.put("card", card);
        params.put("bankname", bankName);
        params.put("fingerprint", imageAddress);
        params.put("locked", locked);

        OkHttpClientManager.postAsyn(Url.SETTING, new OkHttpClientManager.ResultCallback<UnionResultBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(UnionResultBean response) {
                showLongToast(response.getMessage());
                bt_commit.setText(response.getMessage());
            }
        }, params);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<String> photos = null;
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {

                selectedPhotos.addAll(photos);
                Log.i("path", selectedPhotos.get(0));
            }
            photoAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);

        }
    }

    public void uploadFile(View view) throws IOException {
        if (selectedPhotos.size() == 0) {
            showShortToast("请选择图片");
            return;
        }
        final File file = new File(selectedPhotos.get(0));
        showLongToast("上传图片中");
        OkHttpClientManager.postAsyn(Url.UPLOAD,
                new OkHttpClientManager.ResultCallback<ResultBean>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(ResultBean filePath) {
                        showLongToast("图片上传成功");
                        String path = filePath.getResult().getPath();
                        //{"code":1001,"result":{"path":"http://211.149.197.241:8080/bank/attach/7e5f69ae6fc8476eb94158085292fbf9.jpg"}}
                        //上传图片成功后，提交信息
                        if (!TextUtils.isEmpty(path)) {
                            commitInfo(path);
                        }
                    }
                },//
                file,//
                "file"//
        );
    }

    public UserInfo SetUserInfo() {
        String name = et_name.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();
        String cardNum = et_card_num.getText().toString().trim();
        String bankName = spn_label.getSelectedItem().toString();
        UserInfo info = new UserInfo();
        info.setName(name);
        info.setPhone(phone);
        info.setBankName(bankName);
        info.setCardNum(cardNum);
        return info;
    }

    public boolean checkInfoIsAll(UserInfo info) {
        if (TextUtils.isEmpty(info.getBankName()) || TextUtils.isEmpty(info.getCardNum()) || TextUtils.isEmpty(info.getName()) || TextUtils.isEmpty(info.getPhone())) {
            return false;
        } else {
            return true;
        }
    }


    private void commitInfo(String path) {
        commitUserInfoByHttp(userInfo.getName(), userInfo.getPhone(), userInfo.getCardNum(), userInfo.getBankName(), path, "");
    }
}
