package com.example.asus.externalstorage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_save, btn_read;
    private TextView tv_show;
    private  final String fileName="huydeptrai";
    private final String content="du lieu de luu";
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        checkAndRequestPermissions();
    }

    private void initView() {
        btn_save = (Button) findViewById(R.id.btn_save_data);
        btn_save.setOnClickListener(this);
        btn_read = (Button) findViewById(R.id.btn_read_data);
        btn_read.setOnClickListener(this);
        tv_show = (TextView) findViewById(R.id.tv_show_data);
    }
//xin 2 quyen
    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save_data:
                saveData();
//                saveData2();
                break;
            case R.id.btn_read_data:
                readData();
                break;
            default:
                break;
        }

    }
    //kiểm tra bộ nhớ
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


    private void saveData() {
        //mở luồn lên = null
        FileOutputStream fileOutputStream = null;
        File file;
        if (isExternalStorageReadable()){
        try {
            //tạo 1 file ở thư mục Environment.getExternalStorageDirectory(), ten file
            //có thể thuyền đường dần File("/SDcard0/,fileName);
            //bắt buộc phải đúng tên
            file = new File(Environment.getExternalStorageDirectory(),fileName);
            //show duong dan
            Log.d(TAG, "saveData: "+Environment.getExternalStorageDirectory().getAbsolutePath());
//            khởi tại cái luồn
            fileOutputStream = new FileOutputStream(file);
            //ghi xún thư mục
            fileOutputStream.write(content.getBytes());
            Toast.makeText(this, "Thành cmn công", Toast.LENGTH_SHORT).show();
            //đóng luồn
            //lưu mặc định ngoài cùng
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }else{
            Toast.makeText(this, "Đầy Bộ Nhớ", Toast.LENGTH_SHORT).show();
        }
    }
    private void saveData2() {
        //mở luồn lên = null
        FileOutputStream fileOutputStream = null;
        File file;
        try {
            //tạo 1 file ở thư mục getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), ten file
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),fileName);
            //show duong dan
            Log.d(TAG, "saveData: "+Environment.getExternalStorageDirectory().getAbsolutePath());
//            khởi tại cái luồn
            fileOutputStream = new FileOutputStream(file);
            //ghi xún thư mục
            fileOutputStream.write(content.getBytes());
            Toast.makeText(this, "Thành cmn công", Toast.LENGTH_SHORT).show();
            //đóng luồn
            //lưu mặc định ngoài cùng
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readData() {
        BufferedReader bf= null;
        File file  = null;
        try {
            file = new File(Environment.getExternalStorageDirectory(),fileName);

            bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            //dung buffer de doc tung dong
            StringBuffer buffer = new StringBuffer();
            while ((line =bf.readLine()) != null){
                buffer.append(line);
            }
            tv_show.setText(buffer.toString());
            Log.d(TAG, "readData: "+buffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
