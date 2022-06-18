package com.elendemo.btar2812;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class FragmentLed extends Fragment {
    private int[] ARGBcolor;
    public ConnectedThread mConnectedThread;
    public ArrayLeds ledData;
    public Switch turnOnOff;

    public FragmentLed() {
    }

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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SeekBar fade = (SeekBar) view.findViewById(R.id.fade);
        SeekBar ampli = (SeekBar) view.findViewById(R.id.amp);
        SeekBar brightness = (SeekBar) view.findViewById(R.id.bright);
        CheckBox invFade = (CheckBox) view.findViewById(R.id.invfade);
        CheckBox invamplitud = (CheckBox) view.findViewById(R.id.invampli);
        SeekBar intervalo = (SeekBar) view.findViewById(R.id.scrspace);
        CheckBox invintervalo = (CheckBox) view.findViewById(R.id.invinter);
        turnOnOff = (Switch) view.findViewById(R.id.switch1);
        ledData = ArrayLeds.getArrayLeds();
        ampli.setMax(ledData.getLedNumbers());
        fade.setMax(ledData.getLedNumbers());
        mConnectedThread =  ConnectedThread.getThreadConexion(null);
        ColorPickerView colorPickerView= (ColorPickerView) view.findViewById(R.id.colorPickerView);
        colorPickerView.setInitialColorRes(R.color.design_default_color_background);

        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                    ARGBcolor = envelope.getArgb();
                    checkOnOff();
                }
        });

        turnOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    ledData.colorOutput(ARGBcolor);
                    mConnectedThread.write(ledData.getArrayBytes());
                }
                else{
                    mConnectedThread.write(ledData.getLedsOff());
                }
            }
        }
        );

        brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ledData.setBrightness(progress);
                checkOnOff();
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        invFade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    ledData.setInvFad(isChecked);
                    checkOnOff();
                }
            }
        );

        fade.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ledData.setFade(progress);
                checkOnOff();
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        invintervalo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                   ledData.setInvInt(isChecked);
                   checkOnOff();
               }
           }
        );

        intervalo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ledData.setInterval(progress);
                checkOnOff();
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        invamplitud.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    ledData.setInvAmp(isChecked);
                    checkOnOff();
                }
            }
        );

        ampli.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ledData.setAmplitude(progress);
                checkOnOff();
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public  void onDestroy() {
        mConnectedThread.write(ledData.getLedsOff());
        mConnectedThread.finish();
        super.onDestroy();
    }

    public void checkOnOff(){
        if(turnOnOff.isChecked()) {
            ledData.colorOutput(ARGBcolor);
            mConnectedThread.write(ledData.getArrayBytes());
        }
    }
}