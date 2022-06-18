package com.elendemo.btar2812;
import android.bluetooth.BluetoothSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectedThread extends Thread {
    private final OutputStream mmOutStream;
    private static ConnectedThread threadConexion;
    private  InputStream mmInStream;
    private Boolean isSending=false;

    public ConnectedThread (BluetoothSocket socket) {
        OutputStream tmpOut = null;
        InputStream tmpIn = null;
        try {
            tmpOut = socket.getOutputStream();
            tmpIn = socket.getInputStream();
        } catch (IOException e) {
        }
        mmOutStream = tmpOut;
        mmInStream = tmpIn;
    }

    public static ConnectedThread getThreadConexion(BluetoothSocket socket){
        if(threadConexion==null){
            threadConexion  = new ConnectedThread(socket);
            threadConexion.start();
        }
        return threadConexion ;
    }

    public void write(byte[] input) {
        try {
            for (byte i : input) mmOutStream.write(i);
        } catch (IOException e) {
            finish();
        }
    }


    public void run() {
        byte[] mmBuffer = new byte[1];
        int numBytes;
        while (true) {
            try {
                numBytes = mmInStream.read(mmBuffer);
                if (numBytes==1){
                    isSending=false;
                }
                else {
                    isSending=true;
                }
            } catch (IOException e) {
                break;
            }
        }
    }

    public void finish() {
        threadConexion=null;
    }
}
