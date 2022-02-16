package com.elendemo.BTAr2812;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.android.material.tabs.TabLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class FragmentInicio extends Fragment implements AdapterView.OnItemSelectedListener {
    public TextView estado;
    public Spinner spinner;
    public Boolean prespinner=true;
    public String macbt;
    public TabLayout menu_tabs;
    public ViewPager2 viewpager_main;
    public final int BT_OK = 2;
    public BluetoothAdapter btAdapter = null;
    public BluetoothSocket btSocket = null;
    public ConnectedThread mConnectedThread;
    public ArrayList<String> dispositivos = new ArrayList<String>();
    public ArrayList<String> direccionMAC = new ArrayList<String>();
    public static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public FragmentInicio() {
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main= inflater.inflate(R.layout.fragment_inicio, container, false);
        menu_tabs= (TabLayout) getActivity().findViewById(R.id.tabs);
        viewpager_main= (ViewPager2) getActivity().findViewById(R.id.view_pager2);
        return main;
    }


    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        estado= (TextView) view.findViewById(R.id.estado);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        estado.setText("");
        estado.setText(R.string.int_init);

        // Chequear capacidad Bluetooth del dispositivo //
        if (btAdapter == null) {
            estado.append("\n> " + getString(R.string.int_nobt));
        } else {
            if (btAdapter.isEnabled()) {
                resultado_bt();
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, BT_OK);
            }
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        // Seleccionar dispositivo Bluetooth al cual conectar //
        if (prespinner) {
            prespinner = false;
        } else {
            estado.setText(" ");
            macbt =direccionMAC.get(position);
            BluetoothDevice device = btAdapter.getRemoteDevice(macbt);
            try {
                estado.append("\n> " + getString(R.string.int_conIn));
                btSocket = device.createRfcommSocketToServiceRecord(BTMODULEUUID);
            } catch (IOException e) {
                estado.append("\n> " + getString(R.string.int_excSock) + e.toString());
            }
            try
            {
                // Establecer conexion con el socket
                btSocket.connect();
                mConnectedThread =  ConnectedThread.getThreadConexion(btSocket);
                mConnectedThread.start();
                estado.append("\n> " + getString(R.string.int_creatSock));
                menu_tabs.setVisibility(View.VISIBLE);
                estado.append("\n> " + getString(R.string.int_btReady));
            } catch (IOException e)
            {
                estado.append("\n> " + getString(R.string.int_excCreaSock)+ e.toString());
                try
                {
                    btSocket.close();
                } catch (IOException e2)
                {
                    estado.append("\n> " + getString(R.string.int_excCloSock) + e2.toString());
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    protected void resultado_bt (){
        /// BLUETOOTH HABILITADO - Lista de dispositivos
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                dispositivos.add(device.getName());
                direccionMAC.add(device.getAddress());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_item, dispositivos);
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_item);
            spinner.setAdapter(adapter);
            estado.append("\n> " + getString(R.string.int_selDev));
        }
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                }
            });




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode==BT_OK) {
            estado.append("\n> " + getString(R.string.int_btActive));
            resultado_bt();
        }
    }

}

