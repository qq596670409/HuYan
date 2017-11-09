package me.peng.huyan.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;

import java.io.File;
import java.io.FileNotFoundException;

import me.peng.huyan.Bean.GlobalData;
import me.peng.huyan.R;
import me.peng.huyan.Service.LongRunningService;

import static android.support.v4.widget.DrawerLayout.DrawerListener;

public class MainActivity extends AppCompatActivity
                    implements View.OnClickListener{

    private Button bt_start_inform;
    private Button bt_stop_inform;
    private TextView tv_inform_time;
    private TextView tv_inform_vibrate_type;
    private TextView tv_inform_title;
    private TextView tv_inform_content;

    private DrawerLayout mDrawerLayout;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化界面
        //初始化drawerlayout
        //从系统获取信息存储到GlobalData的全局变量中
        InitView();
        initDrawerLayout();
        InitGlobalData();
    }

    private void InitView() {
        Button bt_menu=(Button)findViewById(R.id.bt_menu);
        bt_start_inform=(Button)findViewById(R.id.bt_start_inform);
        bt_stop_inform=(Button)findViewById(R.id.bt_stop_inform);
        tv_inform_time=(TextView)findViewById(R.id.tv_inform_time);
        tv_inform_vibrate_type=(TextView)findViewById(R.id.tv_inform_vibrate_type);
        tv_inform_title=(TextView)findViewById(R.id.tv_inform_title);
        tv_inform_content=(TextView)findViewById(R.id.tv_inform_content);
        mSharedPreferences = getSharedPreferences("GlobalData", MODE_PRIVATE);

        bt_menu.setOnClickListener(this);
        bt_start_inform.setOnClickListener(this);
        bt_stop_inform.setOnClickListener(this);
        //设置不允许点击“关闭提示”按钮
        bt_stop_inform.setEnabled(false);
    }

    private void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);

        mDrawerLayout.setDrawerListener(new DrawerListener() {

            //当产生抽屉滑动时
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //获取mDrawerLayout中的第一个子布局，也就是布局中的Relativelayt
                //获取抽屉的view
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals("LEFT")){
                    float leftScale = 1 - 0.3f * scale;

                    //设置左边菜单滑动后的占据屏幕大小
                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    //设置菜单透明度
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));

                    //设置内容界面水平和垂直方向偏转量
                    //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                    ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * (1 - scale));
                    //设置内容界面操作无效（比如有button就会点击无效）
                    mContent.invalidate();
                    //设置右边菜单滑动后的占据屏幕大小
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);

                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    //使用sharepreference读取信息并且设置到全局变量中
    private void InitGlobalData() {
        int inform_time = mSharedPreferences.getInt("inform_time", 55);
        int vibrate_type_number = mSharedPreferences.getInt("vibrate_type_number",0);
        String inform_title = mSharedPreferences.getString("inform_title","时间到啦，注意用眼");
        String inform_content = mSharedPreferences.getString("inform_content","关爱眼睛，从我做起。");

        GlobalData.inform_time = inform_time;
        GlobalData.vibrate_type_number = vibrate_type_number;
        GlobalData.inform_title = inform_title;
        GlobalData.inform_content = inform_content;

        //查看SD卡中是否有已存的提示图片，如果有就把它设为全局变量，如果没有就把默认的老鼠图片设为全局变量
        File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");

        try {
            if (outputImage.exists()){
                GlobalData.imageUri = Uri.fromFile(outputImage);
                GlobalData.inform_bitmap = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(GlobalData.imageUri));
            } else {
                Resources resources = getResources();
                GlobalData.inform_bitmap = BitmapFactory.decodeResource(resources, R.drawable.mice);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        tv_inform_time.setText(GlobalData.inform_time+"分钟");
        tv_inform_vibrate_type.setText(
                GlobalData.vibrate_type_name[GlobalData.vibrate_type_number]);
        tv_inform_title.setText(GlobalData.inform_title);
        tv_inform_content.setText(GlobalData.inform_content);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, LongRunningService.class);

        switch (view.getId()){
            case R.id.bt_menu:
                //打开左边的抽屉
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.bt_start_inform:
                startService(intent);
                //当提示开启后 “开启提示”不可点击，“关闭提示”可以点击
                bt_start_inform.setEnabled(false);
                bt_stop_inform.setEnabled(true);
                Toast.makeText(MainActivity.this, "提醒功能已经开启。\nAPP关闭了仍然能够提醒哦！",
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.bt_stop_inform:
                stopService(intent);
                bt_start_inform.setEnabled(true);
                bt_stop_inform.setEnabled(false);
                Toast.makeText(MainActivity.this, "提醒功能已经关闭！", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
