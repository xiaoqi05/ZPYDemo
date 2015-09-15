package com.zpy.models;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zpy.R;
import com.zpy.Util.okhttp.OkHttpClientManager;
import com.zpy.adapter.PhotoAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.iwf.libs.PhotoPickerActivity;
import me.iwf.libs.utils.PhotoPickerIntent;

public class MainActivity extends BaseActivity {


    RecyclerView recyclerView;
    PhotoAdapter photoAdapter;

    ArrayList<String> selectedPhotos = new ArrayList<>();

    public final static int REQUEST_CODE = 1;
    private TextView tv_path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar(this,true,"选择图片上传");
        OkHttpClientManager.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(this, selectedPhotos);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);
        tv_path= (TextView) findViewById(R.id.tv_path);


        findViewById(R.id.button_one_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
                intent.setPhotoCount(1);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        findViewById(R.id.button_upload_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    uploadFile(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        
        

  /*  findViewById(R.id.button_photo_gif).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
        intent.setPhotoCount(4);
        intent.setShowCamera(true);
        intent.setShowGif(true);
        startActivityForResult(intent, REQUEST_CODE);
      }
    });*/


    }


    public void uploadFile(View view) throws IOException {
        if (selectedPhotos.size() == 0 ) {
            Toast.makeText(MainActivity.this, "请先选择图片", Toast.LENGTH_LONG).show();
            return;
        }
        File file = new File(selectedPhotos.get(0));
        Toast.makeText(MainActivity.this, "上传图片中", Toast.LENGTH_SHORT).show();
       
        OkHttpClientManager.postAsyn("http://211.149.197.241:8080/bank/upload.do",//
                new OkHttpClientManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String filePath) {
                        Log.e("path", "成功" + filePath);
                        Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                        tv_path.setText("图片地址path：" + filePath);
                    }
                },//
                file,//
                "file"//

        );
    }

  /*  public void byGet() {
        //直接返回对象
        OkHttpClientManager.getAsyn("http://211.149.197.241:8080/bank/change.do",
                new OkHttpClientManager.ResultCallback<ResultBean>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        e.printStackTrace();

                    }

                    @Override
                    public void onResponse(ResultBean u) {
                      //  Toast.makeText(MainActivity.this, "上传"+u.getResult(), Toast.LENGTH_LONG).show();
                            if (ifUpLoadPic==1){
                                ifUpLoadPic = 0;
                            }else {
                                ifUpLoadPic=1;
                            }
                            tv_flag.setText("ifUpLoadPic : "+u.getResult());
                    }
                });

    }*/

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

    public void previewPhoto(Intent intent) {
        startActivityForResult(intent, REQUEST_CODE);
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
        }
    }


}
