package com.elendemo.btar2812;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;
import com.elendemo.btar2812.databinding.FragmentInicioBinding;
import java.io.IOException;

public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    public static ConnectedThread mConnectedThread;
    private static FragmentInicioBinding binding;

    public ConnectThread(BluetoothDevice device, FragmentInicioBinding binding) {
        this.binding=binding;
        BluetoothSocket tmp = null;
        mmDevice = device;
        try {
            ParcelUuid[] parcelUID = device.getUuids();
            tmp = device.createRfcommSocketToServiceRecord(parcelUID[0].getUuid());
        } catch (IOException e) {
            binding.setTextoStatus(binding.getTextoStatus()+"\n> " + this.binding.getRoot().getResources().getString(R.string.int_excCreaSock)+e);
        }
        mmSocket = tmp;
    }

    public void run() {
        try {
            mmSocket.connect();
        } catch (IOException connectException) {
            try {
                mmSocket.close();
                binding.setTextoStatus(binding.getTextoStatus()+"\n> " + this.binding.getRoot().getResources().getString(R.string.int_CloSock));
            } catch (IOException closeException) {
                binding.setTextoStatus(binding.getTextoStatus()+"\n> " + this.binding.getRoot().getResources().getString(R.string.int_excCloSock)+closeException);
            }
            return;
        }
        mConnectedThread.getThreadConexion(mmSocket);
        binding.setTextoStatus(binding.getTextoStatus()+"\n> " + this.binding.getRoot().getResources().getString(R.string.int_btReady));
    }

    public void cancel() {
        try {
            mmSocket.close();
            binding.setTextoStatus(binding.getTextoStatus()+"\n> " + this.binding.getRoot().getResources().getString(R.string.int_CloSock));
        } catch (IOException e) {
            binding.setTextoStatus(binding.getTextoStatus()+"\n> " + this.binding.getRoot().getResources().getString(R.string.int_excSock)+e);
        }
    }
}