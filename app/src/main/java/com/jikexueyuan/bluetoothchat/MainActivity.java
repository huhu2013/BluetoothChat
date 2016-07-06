package com.jikexueyuan.bluetoothchat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLED_BLUETOOTH = 1000;
    private BluetoothAdapter bluetoothAdapter;
    private ListView lvDevices;
    private DevicesListAdapter devicesListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvDevices = (ListView) findViewById(R.id.lvDeviecs);
        devicesListAdapter = new DevicesListAdapter(this,android.R.layout.simple_list_item_1);
        lvDevices.setAdapter(devicesListAdapter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null){
            Toast.makeText(MainActivity.this,"您的设备不支持蓝牙",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            if (!bluetoothAdapter.isEnabled()){
                requestEnableBlueTooth();
            }else{
               loadBondedDevices();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_ENABLED_BLUETOOTH:
                switch(requestCode){
                    case RESULT_OK:
                        loadBondedDevices();
                        break;
                    default:
                        new AlertDialog.Builder(this).setTitle("提醒").setMessage("您拒绝启用蓝牙")
                                .setPositiveButton("再次启用", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                      requestEnableBlueTooth();
                                    }
                                }).setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    finish();
                            }
                        }).setCancelable(false).show();
                        break;
                }
                break;
        }
    }

    void requestEnableBlueTooth(){
        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(i, REQUEST_ENABLED_BLUETOOTH);
    }

    void loadBondedDevices(){
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device :
                bondedDevices) {
            devicesListAdapter.add(new BluetoothDeviceWrapper(device));
        }
    }

    public void btnDiscoverableClicked(View view) {
        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        i.putExtra(bluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,120);
        startActivity(i);
    }
}
