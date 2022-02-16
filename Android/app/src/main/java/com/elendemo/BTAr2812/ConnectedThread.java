package com.elendemo.BTAr2812;

import android.bluetooth.BluetoothSocket;
import java.io.IOException;
import java.io.OutputStream;

public class ConnectedThread extends Thread{
    private final OutputStream mmOutStream;
    private static ConnectedThread threadConexion;

    //creation of the connect thread
    public ConnectedThread(BluetoothSocket socket) {
        OutputStream tmpOut = null;
        try {
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }
        mmOutStream = tmpOut;
    }

    public static ConnectedThread getThreadConexion(BluetoothSocket socket){
            if(threadConexion==null){
                threadConexion  = new ConnectedThread(socket);
            }
            return threadConexion ;
    }

    //write method
    public void write(byte[] input) {
        try {
            mmOutStream.write(input);
        } catch (IOException e) {
            finish();
        }
    }

    public void finish() {
        threadConexion=null;
    }
}
