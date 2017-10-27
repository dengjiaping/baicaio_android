package com.cnxxp.cabbagenet.activity;

import android.app.Notification;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cnxxp.cabbagenet.R;
import com.cnxxp.cabbagenet.base.BaseActivity;
import com.cnxxp.cabbagenet.utils.SPUtils;
import com.cnxxp.cabbagenet.view.PickerView;
import com.kyleduo.switchbutton.SwitchButton;
import com.yuyh.library.imgsel.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class MyPushActivity extends BaseActivity {

    @Bind(R.id.sb_open)
    SwitchButton sbOpen;
    @Bind(R.id.sb_voice)
    SwitchButton sbVoice;
    @Bind(R.id.sb_quake)
    SwitchButton sbQuake;
    @Bind(R.id.sb_quiet)
    SwitchButton sbQuiet;
    @Bind(R.id.tv_time_period)
    TextView tvTimePeriod;
    @Bind(R.id.rl_time_quiet)
    RelativeLayout rlTimeQuiet;
    @Bind(R.id.ll_ablow_layout)
    LinearLayout llAblow;
    private PickerView startPick;
    private PickerView endPick;
    private String startTime;
    private String endTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_push);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setTitleText("推送设置");
        initTimePicker();
    }

    @Override
    protected void initEvents() {

    }

    private PopupWindow timeSelect;

    private void initTimePicker() {
        View pickView = LayoutInflater.from(this).inflate(R.layout.year_month_pop, null);
        pickView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
        startPick = (PickerView) pickView.findViewById(R.id.year_pick);
        endPick = (PickerView) pickView.findViewById(R.id.month_pick);
        pickView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSelect.dismiss();
            }
        });
        pickView.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSelect.dismiss();
                //确认
                tvTimePeriod.setText("从" + startTime + ":00到" + endTime + ":00");
                HashSet<Integer> set = new HashSet<Integer>();
                set.add(0);
                set.add(1);
                set.add(2);
                set.add(3);
                set.add(4);
                set.add(5);
                set.add(6);
                if(Integer.parseInt(endTime)>Integer.parseInt(startTime)){
                    JPushInterface.setPushTime(getApplicationContext(), set, Integer.parseInt(endTime),23);
                    JPushInterface.setPushTime(getApplicationContext(), set, 0,Integer.parseInt(startTime));
                }else {
                    JPushInterface.setPushTime(getApplicationContext(), set, Integer.parseInt(endTime), Integer.parseInt(startTime));
                }
                SPUtils.put(MyPushActivity.this, "silence_start", startTime);
                SPUtils.put(MyPushActivity.this, "silence_end", endTime);
            }
        });
        List<String> startList = new ArrayList<String>();
        final List<String> endList = new ArrayList<String>();
        for (int i = 0; i <= 23; i++) {
            startList.add(i + "");
        }
        for (int i = 0; i <= 23; i++) {
            endList.add(i + "");
        }
        startPick.setData(startList);
        endPick.setData(endList);
        startPick.setSelected(startList.size() - 1);
        startTime = startList.get(startList.size() - 1);
        endPick.setSelected(endList.size() - 1);
        endTime = endList.get(endList.size() - 1);
        startPick.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                startTime = text;
            }
        });
        endPick.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                endTime = text;
            }
        });
        timeSelect = new PopupWindow(pickView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        timeSelect.setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT)
        );
        timeSelect.setFocusable(true);
        timeSelect.setOutsideTouchable(true);
    }

    @Override
    protected void initData() {
        sbOpen.setChecked((Boolean) SPUtils.get(MyPushActivity.this, "openPush", true));
        if (sbOpen.isChecked()) {
            llAblow.setVisibility(View.GONE);
            llAblow.setClickable(false);
        } else {
            llAblow.setVisibility(View.VISIBLE);
            llAblow.setClickable(true);
        }
        sbVoice.setChecked((Boolean) SPUtils.get(MyPushActivity.this, "push_voice", true));
        sbQuake.setChecked((Boolean) SPUtils.get(MyPushActivity.this, "push_quake", true));
        sbQuiet.setChecked((Boolean) SPUtils.get(MyPushActivity.this, "push_quiet", false));
        if (sbQuiet.isChecked()) {
            rlTimeQuiet.setClickable(true);
        } else {
            rlTimeQuiet.setClickable(false);
        }
        startTime = (String) SPUtils.get(MyPushActivity.this, "silence_start", "0");
        endTime = (String) SPUtils.get(MyPushActivity.this, "silence_end", "07");
        tvTimePeriod.setText("从" + startTime + ":00到" + endTime + ":00");
    }

    @OnClick({R.id.sb_open, R.id.sb_voice, R.id.sb_quake, R.id.sb_quiet, R.id.rl_time_quiet})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sb_open:
                //此处是状态改变之后的状态，例如之前没有选中，点击之后状态就变成了checked
                if (sbOpen.isChecked()) {
                    SPUtils.put(MyPushActivity.this, "openPush", true);
                    llAblow.setVisibility(View.GONE);
                    llAblow.setClickable(false);
                    JPushInterface.onResume(getApplicationContext());
                } else {
                    SPUtils.put(MyPushActivity.this, "openPush", false);
                    llAblow.setVisibility(View.VISIBLE);
                    llAblow.setClickable(true);
                    JPushInterface.stopPush(getApplicationContext());
                }
                break;
            case R.id.sb_voice:
                //声音
                if (sbVoice.isChecked()) {
                    SPUtils.put(MyPushActivity.this, "push_voice", true);
                    if ((Boolean) SPUtils.get(MyPushActivity.this, "push_quake", true)) {
                        setNotification(1);
                    } else {
                        setNotification(2);
                    }
                } else {
                    SPUtils.put(MyPushActivity.this, "push_voice", false);
                    if ((Boolean) SPUtils.get(MyPushActivity.this, "push_quake", true)) {
                        setNotification(3);
                    } else {
                        setNotification(4);
                    }
                }
                break;
            case R.id.sb_quake:
                if (sbVoice.isChecked()) {
                    SPUtils.put(MyPushActivity.this, "push_quake", true);
                    if ((Boolean) SPUtils.get(MyPushActivity.this, "push_voice", true)) {
                        setNotification(1);
                    } else {
                        setNotification(3);
                    }
                } else {
                    SPUtils.put(MyPushActivity.this, "push_quake", false);
                    if ((Boolean) SPUtils.get(MyPushActivity.this, "push_voice", true)) {
                        setNotification(2);
                    } else {
                        setNotification(4);
                    }
                }
                break;
            case R.id.sb_quiet:
                //安静时段是否开启
                HashSet<Integer> set = new HashSet<Integer>();
                set.add(0);
                set.add(1);
                set.add(2);
                set.add(3);
                set.add(4);
                set.add(5);
                set.add(6);
                if (sbQuiet.isChecked()) {
                    SPUtils.put(MyPushActivity.this, "push_quiet", true);
                    rlTimeQuiet.setClickable(true);
                    if(Integer.parseInt(endTime)>Integer.parseInt(startTime)){
                        JPushInterface.setPushTime(getApplicationContext(), set, Integer.parseInt(endTime),23);
                        JPushInterface.setPushTime(getApplicationContext(), set, 0,Integer.parseInt(startTime));
                    }else {
                        JPushInterface.setPushTime(getApplicationContext(), set, Integer.parseInt(endTime), Integer.parseInt(startTime));
                    }
                } else {
                    LogUtils.e("设置推送时段0-23");
                    SPUtils.put(MyPushActivity.this, "push_quiet", false);
                    rlTimeQuiet.setClickable(false);
                    JPushInterface.setPushTime(getApplicationContext(), set, 0, 23);
                }
                break;
            case R.id.rl_time_quiet:
                timeSelect.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    private void setNotification(int type) {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(MyPushActivity.this);
        builder.statusBarDrawable = R.mipmap.ic_logo;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        switch (type) {
            case 1:
                //声音+震动
                builder.notificationDefaults = Notification.DEFAULT_SOUND
                        | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS;

                break;
            case 2:
                //声音
                builder.notificationDefaults = Notification.DEFAULT_SOUND
                        | Notification.DEFAULT_LIGHTS;
                break;
            case 3:
                //震动
                builder.notificationDefaults = Notification.DEFAULT_VIBRATE
                        | Notification.DEFAULT_LIGHTS;
                break;
            case 4:
                //无声，无振动
                builder.notificationDefaults =
                        Notification.DEFAULT_LIGHTS;
                break;
        }
        // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(1, builder);
    }
}
