package com.elendemo.BTAr2812;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar;

public class FragmentLed extends Fragment implements AdapterView.OnItemSelectedListener {
    public SeekBar ampli ;
    public SeekBar intervalo ;
    public SeekBar fade;
    public int colores[];
    public TextView valorAmpli;
    public TextView valorInt;
    public TextView valorFade;
    public CheckBox invintervalo;
    public CheckBox invamplitud;
    public CheckBox invFade;
    public Switch prender;
    public ConnectedThread mConnectedThread;
    public ArrayLeds datosLed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main= inflater.inflate(R.layout.fragment_led, container, false);
        return main;
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fade = (SeekBar) view.findViewById(R.id.fade);
        ampli = (SeekBar) view.findViewById(R.id.amp);
        invFade= (CheckBox) view.findViewById(R.id.invfade);
        invamplitud= (CheckBox) view.findViewById(R.id.invamplitud);
        intervalo = (SeekBar) view.findViewById(R.id.espacio);
        invintervalo= (CheckBox) view.findViewById(R.id.invintervalo);
        valorAmpli = (TextView) view.findViewById(R.id.textView);
        valorInt = (TextView) view.findViewById(R.id.textView2);
        valorFade = (TextView) view.findViewById(R.id.textView6);
        prender = (Switch) view.findViewById(R.id.switch1);
        mConnectedThread =  ConnectedThread.getThreadConexion(null);
        datosLed = new ArrayLeds();
        datosLed.iniciar();
        ColorPickerView colorPickerView= (ColorPickerView) view.findViewById(R.id.colorPickerView);
        BrightnessSlideBar brightnessSlideBar = (BrightnessSlideBar) view.findViewById(R.id.brightnessSlide);
        colorPickerView.attachBrightnessSlider(brightnessSlideBar);
        colorPickerView.setInitialColorRes(R.color.design_default_color_background);
        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                colores = envelope.getArgb();
                datosLed.colorOutput(envelope.getArgb());
                if (prender.isChecked()) {
                    mConnectedThread.write(datosLed.arrayBytes);
                }
            }
        });


        prender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mConnectedThread.write(datosLed.arrayBytes);
                }
                else{
                    mConnectedThread.write(datosLed.ledsOff);
                }
            }
        }
        );

        invFade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    datosLed.setInvFad(isChecked);
                    datosLed.colorOutput(colores);
                    if(prender.isChecked()) {
                        mConnectedThread.write(datosLed.arrayBytes);
                    }
                }
            }
        );


        fade.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                datosLed.setFade(progress);
                datosLed.colorOutput(colores);
                if(prender.isChecked()) {
                  mConnectedThread.write(datosLed.arrayBytes);
                }
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        invintervalo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                   datosLed.setInvInt(isChecked);
                   datosLed.colorOutput(colores);
                   if(prender.isChecked()) {
                       mConnectedThread.write(datosLed.arrayBytes);
                   }
               }
           }
        );


        intervalo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                datosLed.setIntervalo(progress);
                datosLed.colorOutput(colores);
                if(prender.isChecked()) {
                    mConnectedThread.write(datosLed.arrayBytes);
                }
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        invamplitud.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    datosLed.setInvAmp(isChecked);
                    datosLed.colorOutput(colores);
                    if(prender.isChecked()) {
                        mConnectedThread.write(datosLed.arrayBytes);
                    }
                }
            }
        );

        ampli.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                datosLed.setAmplitud(progress);
                datosLed.colorOutput(colores);
                if(prender.isChecked()) {
                    mConnectedThread.write(datosLed.arrayBytes);
                }
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public FragmentLed() {
    }
    @Override
    public  void onDestroy() {
        mConnectedThread.write(datosLed.ledsOff);
        mConnectedThread.finish();
        super.onDestroy();
    }
}