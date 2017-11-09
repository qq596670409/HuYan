package me.peng.huyan.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import me.peng.huyan.Activity.ActivityInform;
import me.peng.huyan.Activity.ActivityTime;
import me.peng.huyan.R;

/**
 * Created by Administrator on 2017/11/10.
 */

public class MenuLeftFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_menu, container, false);

        //绑定两个button，并且设置点击效果
        Button bt_time_control = (Button)view.findViewById(R.id.bt_time_control);
        Button bt_inform_control = (Button)view.findViewById(R.id.bt_inform_control);

        bt_time_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(),ActivityTime.class);
                startActivity(intent);
            }
        });
        bt_inform_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getApplicationContext(),ActivityInform.class);
                startActivity(intent);
            }
        });
        return view;

    }
}
