package com.elendemo.btar2812;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.io.IOException;


public class FragmentAdress extends Fragment {
    public static ConnectedThread mConnectedThread;
    Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.RGB_565);
    public Bitmap selectedImage;
    public ContentResolver resolver;
    public ArrayLeds ledData;

    public FragmentAdress() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main= inflater.inflate(R.layout.fragment_adress, container, false);
        return main;
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SeekBar brightSlider = (SeekBar) view.findViewById(R.id.slideBrillo);
        SeekBar repeatSlider = (SeekBar) view.findViewById(R.id.repeat);
        SeekBar intervalSlider = (SeekBar) view.findViewById(R.id.interval);
        SeekBar shiftSlider = (SeekBar) view.findViewById(R.id.shiftv);
        CheckBox matrixMode = (CheckBox) view.findViewById(R.id.matrixCheck);
        CheckBox matrixStatic=(CheckBox) view.findViewById(R.id.matrixCheck2);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
        ImageButton pickImage = (ImageButton) view.findViewById(R.id.galeria);
        bitmap.eraseColor(Color.BLACK);
        ledData =  ArrayLeds.getArrayLeds();
        ledData.setBrightness(1);
        mConnectedThread =  ConnectedThread.getThreadConexion(null);
        resolver = getActivity().getContentResolver();
        imageView.setLongClickable(true);

        imageView.setOnLongClickListener(new ImageView.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(selectedImage!=null) {
                    int i;
                    for (i=0;i<=ledData.getRepeat();i++) {
                        ledData.arrayBMP(selectedImage);
                        mConnectedThread.write(ledData.getArrayBytes());
                    }
                }
                return true;
            }
        });

        matrixMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 ledData.setMatrix(isChecked);
                 matrixStatic.setEnabled(isChecked);
             }
         }
        );

        matrixStatic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  ledData.setMatrixStatic(isChecked);
              }
          }
        );

        intervalSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ledData.setInterval(progress);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        brightSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ledData.setBrightness(progress+1);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        repeatSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ledData.setRepeat(progress+1);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        shiftSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ledData.setShiftrgb(progress);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if (uri != null) {
                            try {
                                imageView.setImageURI(uri);
                                selectedImage = MediaStore.Images.Media.getBitmap(resolver, uri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });
    }
}