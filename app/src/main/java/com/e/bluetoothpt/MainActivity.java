package com.e.bluetoothpt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity{
    public BluetoothHeadset bluetoothHeadset;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
   // Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
    final int REQUEST_ENABLE_BT = 1;




    /**
     * @param
     * @return
     *
     */
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bluetooth_search = findViewById(R.id.bluetooth_search);
        bluetooth_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (bluetoothAdapter == null) {
            // 如果此裝置不支援藍芽
            // 可能要跳出對話視窗

        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);//REQUEST_ENABLE_BT為BT活動ID，可自己定義
        }
    }

    private BluetoothProfile.ServiceListener profileListener = new BluetoothProfile.ServiceListener() {
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            if (profile == BluetoothProfile.HEADSET) {
                bluetoothHeadset = (BluetoothHeadset) proxy;
            }
        }
        public void onServiceDisconnected(int profile) {
            if (profile == BluetoothProfile.HEADSET) {
                bluetoothHeadset = null;
            }
        }
    };
    /**
     * 當藍芽未開啟確認得請求對話框 被啟動
     * 使用者可以選擇按ok或取消
     * 之後所執行的動作
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_ENABLE_BT)
        {
            if(resultCode == RESULT_OK){
                Toast.makeText(this, "Result OK", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Result Cancel", Toast.LENGTH_LONG).show();
            }
        }
    }


    //    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle(R.string.pick_color)
//                .setItems(R.array.colors_array, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // The 'which' argument contains the index position
//                        // of the selected item
//                    }
//                });
//        return builder.create();
//    }
}
