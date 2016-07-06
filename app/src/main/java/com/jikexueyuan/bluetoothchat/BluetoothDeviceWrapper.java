package com.jikexueyuan.bluetoothchat;


import android.bluetooth.BluetoothDevice;

public class BluetoothDeviceWrapper {

    private final BluetoothDevice device;

    public BluetoothDeviceWrapper(BluetoothDevice device) {
        this.device = device;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    @Override
    public boolean equals(Object o) {
        if (o!=null){
            assert o instanceof BluetoothDeviceWrapper;//执行断言 即o必须是该类型

            return ((BluetoothDeviceWrapper) o).getDevice().getAddress().equals(getDevice().getAddress());
        }else{
            return false;
        }

    }

    @Override
    public String toString() {
        return String.format("%s\n%s",getDevice().getName(),getDevice().getAddress());
    }
}
