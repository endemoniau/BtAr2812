package com.elendemo.btar2812;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.elendemo.btar2812.databinding.FragmentInicioBinding;
import com.google.android.material.tabs.TabLayout;

public class FragmentInicio extends Fragment implements AdapterView.OnItemSelectedListener {
    public TabLayout menu_tabs;
    public ArrayLeds ledData;
    public BTdevices adaptorBT;
    public FragmentInicioBinding binding;
    public SharedPreferences sharedPref;
    public FragmentInicio() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inicio, container, false);
            View main = binding.getRoot();
            binding.setLifecycleOwner(this);
            menu_tabs= (TabLayout) getActivity().findViewById(R.id.tabs);
            menu_tabs.setVisibility(View.VISIBLE);
            sharedPref= getActivity().getPreferences(Context.MODE_PRIVATE);
        return main;
    }

     @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        int leds = sharedPref.getInt(getString(R.string.pref_leds), 0);
        binding.setTextoStatus("");
        binding.setLedNumbers(String.valueOf(leds));
        ledData = ArrayLeds.getArrayLeds();
        ledData.setLedNumbers(leds);
        binding.spinner.setOnItemSelectedListener(this);

        ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_item,
                                    adaptorBT.BtDeviceList());
                            adapter.setDropDownViewResource( android.R.layout.simple_spinner_item);
                            binding.spinner.setAdapter(adapter);
                            binding.estado.setText(binding.getTextoStatus()+"\n> " + getString(R.string.int_selDev));
                        }
                    }
                });
        adaptorBT =new BTdevices(mGetContent,binding);
        adaptorBT.initBt=getString(R.string.int_initBT);
        binding.cantidadleds.setOnClickListener(v -> {
            if (binding.cantidadleds.getText().length() > 0) {
                ledData.setLedNumbers(Integer.valueOf(binding.cantidadleds.getText().toString()));
                SharedPreferences.Editor editor = sharedPref.edit();
                String prefData=binding.getRoot().getResources().getString(R.string.pref_leds);
                int newData=ledData.getLedNumbers();
                editor.putInt(prefData, newData);
                editor.commit();
            }
        });
    }

    @Override
    public void onItemSelected (AdapterView < ? > arg0, View arg1,int position, long id){
        if (position !=0){
            adaptorBT.BtconnectSocket(position);
        }
    }

    @Override
    public void onNothingSelected (AdapterView < ? > arg0){
    // TODO Auto-generated method stub
    }

}

