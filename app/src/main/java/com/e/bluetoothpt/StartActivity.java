package com.e.bluetoothpt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/** 起始畫面*/
public class StartActivity extends AppCompatActivity{
//    public BluetoothHeadset bluetoothHeadset;
    BluetoothAdapter m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    final int REQUEST_ENABLE_BT = 1;
    private Button m_btnBluetoothSearch;
    private View.OnClickListener m_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(R.id.btn_bluetooth_search == v.getId())
            {
                //TODO search bluetooth
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_btnBluetoothSearch = findViewById(R.id.btn_bluetooth_search);
        m_btnBluetoothSearch.setOnClickListener(m_clickListener);
        initBluetooth();
    }

    /** 初始化設定或檢查藍芽*/
    private void initBluetooth()
    {
        if (m_bluetoothAdapter == null) {
            // 如果此裝置不支援藍芽
            // 可能要跳出對話視窗
        }
        if (!m_bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);//REQUEST_ENABLE_BT為BT活動ID，可自己定義
        }
    }

//    private BluetoothProfile.ServiceListener profileListener = new BluetoothProfile.ServiceListener() {
//        public void onServiceConnected(int profile, BluetoothProfile proxy) {
//            if (profile == BluetoothProfile.HEADSET) {
//                bluetoothHeadset = (BluetoothHeadset) proxy;
//            }
//        }
//        public void onServiceDisconnected(int profile) {
//            if (profile == BluetoothProfile.HEADSET) {
//                bluetoothHeadset = null;
//            }
//        }
//    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_ENABLE_BT)
        {
            // 當藍芽未開啟確認得請求對話框 被啟動 使用者可以選擇按ok或取消 之後所執行的動作
            if(resultCode == RESULT_OK){
                Toast.makeText(this, "Result OK", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Result Cancel", Toast.LENGTH_LONG).show();
            }
        }
    }
}
