package me.peng.huyan.Bean;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * 保存振动类型、文段内容
 * Created by Administrator on 2017/11/9.
 */

public class GlobalData {
    //时间默认设置为55分钟
    public static int inform_time = 55;
    //当前震动类型的序号
    public static int vibrate_type_number = 4;
    //振动的类型为long数组
    public static long[][] all_vibrate_type = {
            {0, 5000},
            {0, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300},
            {0, 400, 400, 800, 400, 400, 400, 800, 400, 400, 400, 800, 400, 400, 400, 800},
            {0, 300, 300, 600, 300, 900, 300, 1200, 300, 2400, 300, 3000},
            {0, 2000, 1000, 1000, 500, 3000, 800, 1000, 500, 500, 200, 2000},
            {0, 200, 200, 200, 200, 1000, 200, 200, 200, 200, 200, 1000, 200, 200, 200, 200, 200, 1000}
    };
    //震动类型的名字
    public static String[] vibrate_type_name = {
            "魔性振动一",
            "魔性振动二",
            "魔性振动三",
            "魔性振动四",
            "魔性振动五",
            "魔性振动六"
    };
    //提示的标题
    public static String inform_title = "时间到，注意用眼";
    //提示的内容
    public static String inform_content = "关爱眼睛，从我做起";
    //提示的图片Uri
    public static Uri imageUri;
    //用来存储提示图片的Bitmap
    public static Bitmap inform_bitmap;
}
