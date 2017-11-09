package me.peng.huyan.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import me.peng.huyan.Bean.GlobalData;
import me.peng.huyan.R;
import me.peng.huyan.View.TitleLayout;

public class ActivityTime extends AppCompatActivity {

    private EditText et_inform_time;
    private Button bt_time_confirm;

    private Spinner mSpinner;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        InitView();
    }

    private void InitView() {
        TitleLayout title = (TitleLayout)findViewById(R.id.time_title);
        mSpinner = (Spinner)findViewById(R.id.sp_vibrate_control);
        et_inform_time = (EditText)findViewById(R.id.et_inform_time);
        bt_time_confirm = (Button)findViewById(R.id.bt_time_confirm);

        mEditor = getSharedPreferences("GlobalData", MODE_PRIVATE).edit();

        title.setTitleText("设置时间和震动");
        //设置界面中的spinner
        setSpinner();
        et_inform_time.setText(GlobalData.inform_time + "");
        //设置确认button的点击效果
        bt_time_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置提醒时间
                //设置振动类型
                setMyTime();
                setMyVibrateType();
                Toast.makeText(ActivityTime.this, "设置成功！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSpinner() {
        //给单选spinner设置adapter，内容为GlobalData.vibrate_type_name
        ArrayAdapter<String> mSpAdapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_singlechoice, GlobalData.vibrate_type_name);
        mSpinner.setAdapter(mSpAdapter);
        //setSelection这句话一定要放在setAdapter之后，否则无效，并且不会提示错误
        mSpinner.setSelection(GlobalData.vibrate_type_number, true);
    }

    private void setMyTime() {
        String time = et_inform_time.getText().toString();
        //由于GlobalData.inform_time是int类型的，此处有要转换一下
        GlobalData.inform_time = Integer.parseInt(time);
        mEditor.putInt("inform_time", Integer.parseInt(time));
        //提交数据
        mEditor.commit();
    }

    private void setMyVibrateType() {
        int position = mSpinner.getSelectedItemPosition();
        GlobalData.vibrate_type_number = position;
        mEditor.putInt("vibrate_type_number", position);
        //提交数据
        mEditor.commit();
    }
}
