package com.e.bluetoothpt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

/** 起始畫面
 *  檢查藍芽以及是否有配對到裝置
 * */
public class StartActivity extends AppCompatActivity{
//    public BluetoothHeadset bluetoothHeadset;
    BluetoothAdapter m_bluetoothAdapter                 = BluetoothAdapter.getDefaultAdapter();
    final int REQUEST_ENABLE_BT                         = 1;
    private Button m_btnBluetoothSearch                 = null;
    private Set<BluetoothDevice> m_bltDevices           = null;
    AlertDialog.Builder m_Builder                       = null;
    private View.OnClickListener m_clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(R.id.btn_bluetooth_search == v.getId())
            {
                searchBltDevices();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_btnBluetoothSearch = findViewById(R.id.btn_bluetooth_search);
        m_btnBluetoothSearch.setOnClickListener(m_clickListener);
        m_Builder = new AlertDialog.Builder(this);
        initBluetooth();
    }

    /** 初始化設定或檢查藍芽*/
    private void initBluetooth()
    {
        //TODO 如果已經連接到裝置，略過搜尋&配對步驟
        if (m_bluetoothAdapter == null) {
            // 如果此裝置不支援藍芽
            // 可能要跳出對話視窗
            return;
        }
        if (!m_bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);//REQUEST_ENABLE_BT為BT活動ID，可自己定義
            return;
        }
        m_btnBluetoothSearch.setEnabled(true);
    }

    /** 搜尋藍芽裝置*/
    private void searchBltDevices()
    {
        m_bltDevices = m_bluetoothAdapter.getBondedDevices();
        if(null != m_bltDevices)
        {
            showBltListWithDialog();
        }
    }

    /** 顯示藍芽裝置*/
    private void showBltListWithDialog()
    {
        // load data
        ArrayList<String> list = new ArrayList<>();
        for(BluetoothDevice bt : m_bltDevices)
        {
            list.add(bt.getName());
        }

        //show Dialog
        String[] bltNames = list.toArray(new String[list.size()]);
        m_Builder.setTitle(getResources().getString(R.string.app_name));
        m_Builder.setItems(bltNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO onClick
            }
        });
        m_Builder.show();
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
