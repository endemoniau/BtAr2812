package com.elendemo.btar2812;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import androidx.activity.result.ActivityResultLauncher;
import com.elendemo.btar2812.databinding.FragmentInicioBinding;
import java.util.ArrayList;
import java.util.Set;

public class BTdevices {
    public String macbt;
    public BluetoothAdapter btAdapter = null;
    public ArrayList<String> deviceList = new ArrayList<String>();
    public ArrayList<String> MACadress = new ArrayList<String>();
    public String initBt;
    public FragmentInicioBinding binding;

    public BTdevices(ActivityResultLauncher<Intent> resultLauncher, FragmentInicioBinding fragmentBinding){
        binding=fragmentBinding;
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        btAdapter.cancelDiscovery();

        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        if (btAdapter == null) {
            binding.setTextoStatus(binding.getTextoStatus()+"\n> " + this.binding.getRoot().getResources().getString(R.string.int_nobt));
        } else {
            resultLauncher.launch(enableBtIntent);
        }
    }

    public ArrayList<String> BtDeviceList (){
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            deviceList.add (initBt);
            MACadress.add("");
            for (BluetoothDevice device : pairedDevices) {
                deviceList.add(device.getName());
                MACadress.add(device.getAddress());
            }
        }
        return deviceList;
    }

    public void BtconnectSocket (int position) {
        binding.setTextoStatus(binding.getTextoStatus()+"\n> " + this.binding.getRoot().getResources().getString(R.string.int_conIn));
        macbt = MACadress.get(position);
        BluetoothDevice device = btAdapter.getRemoteDevice(macbt);
        ConnectThread connectBT=new ConnectThread(device, binding);
        connectBT.start();
    }
}
