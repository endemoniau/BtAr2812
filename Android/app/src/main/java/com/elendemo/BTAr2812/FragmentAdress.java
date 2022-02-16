package com.elendemo.BTAr2812;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.io.IOException;
import java.nio.ByteBuffer;

public class FragmentAdress extends Fragment implements AdapterView.OnItemSelectedListener{
    public ConnectedThread mConnectedThread;
    Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.RGB_565);
    public final int SELECT_PHOTO = 1;
    public final int RESULT_OK = -1;
    public ImageView imageView;
    public Bitmap selectedImage;
    Bitmap tmp_bmp;
    byte [] b ;
    int enviado=0;
    int pausa=1;
    int brillo=1;
    int repetir=1;
    int inter=0;
    int shiftrgb=0;
    private SeekBar barraBrillo;
    private SeekBar velocidad;
    private SeekBar repeticiones ;
    private SeekBar intervalo ;
    private TextView t_repe;
    private SeekBar shiftV;
    public ContentResolver resolver;

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
        barraBrillo = (SeekBar) view.findViewById(R.id.slideBrillo);
        velocidad = (SeekBar) view.findViewById(R.id.velocidad);
        repeticiones = (SeekBar) view.findViewById(R.id.repeticiones);
        intervalo = (SeekBar) view.findViewById(R.id.intervalo);
        t_repe= (TextView) view.findViewById(R.id.textView3);
        shiftV = (SeekBar) view.findViewById(R.id.shiftv);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
        ImageButton pickImage = (ImageButton) view.findViewById(R.id.galeria);
        bitmap.eraseColor(Color.BLACK);
        mConnectedThread =  ConnectedThread.getThreadConexion(null);
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        resolver = getActivity().getContentResolver();
        imageView.setLongClickable(true);
        imageView.setOnLongClickListener(new ImageView.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                if(selectedImage!=null) {
                    EnviarBMP();
                }
                return true;
            }
        });

        intervalo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                inter = progress;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        repeticiones.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                repetir = progress+1;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        barraBrillo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brillo = progress+1;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        velocidad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pausa = progress+1;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        shiftV.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shiftrgb = progress;
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        pickImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri imageUri = imageReturnedIntent.getData();
                    imageView.setImageURI(imageUri);
                    selectedImage = MediaStore.Images.Media.getBitmap(resolver, imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    protected void EnviarBMP() {
        float ancho = selectedImage.getWidth();
        float alto = selectedImage.getHeight();
        float factor;
        float alto_sc = alto;
        tmp_bmp = selectedImage;
        if (ancho > alto) {
            factor = (32 / ancho);
        } else {
            factor = (32 / alto);
        }
        if (ancho > 32) {
            alto_sc = (alto * factor);
            tmp_bmp = Bitmap.createScaledBitmap(selectedImage, 32, Math.round(alto_sc), false);
            imageView.setImageBitmap(tmp_bmp);
        }
        int size = (tmp_bmp.getWidth() * (tmp_bmp.getHeight())*3); // Bytes de pixeles
        int c[]= new int [(size*repetir*pausa)+(repetir*(inter*96))+96];
        ByteBuffer byteBuffer = ByteBuffer.allocate(c.length);
        enviado = 0;
        for (int l = 0; l < repetir; l++) {
            for (int y = 0; y < tmp_bmp.getHeight(); y++) {
                for (int r = 0; r < pausa; r++) {
                    for (int x = 0; x < 32; x++) {
                        int pixel_tmp;
                        int pixel = tmp_bmp.getPixel(x, y);
                        c[enviado] = ((pixel >> 16) & 0xff) / brillo;
                        pixel_tmp= (((pixel >> 16) & 0xff)+shiftrgb) ;
                        if (pixel_tmp > 255){
                            pixel_tmp=(pixel_tmp-255)/ brillo;
                        }
                        else {
                            pixel_tmp= ((pixel >> 16) & 0xff) / brillo;
                        }
                        c[enviado] =pixel_tmp;
                        byteBuffer.put((byte) c[enviado]);
                        enviado++;
                        c[enviado] = ((pixel >> 8) & 0xff) / brillo;
                        pixel_tmp= (((pixel >> 8) & 0xff)+shiftrgb) ;
                        if (pixel_tmp > 255){
                            pixel_tmp=(pixel_tmp-255)/ brillo;
                        }
                        else {
                            pixel_tmp= ((pixel >>8) & 0xff) / brillo;
                        }
                        c[enviado] =pixel_tmp;
                        byteBuffer.put((byte) c[enviado]);
                        enviado++;
                        c[enviado] = (pixel & 0xff) / brillo;
                        pixel_tmp= ((pixel  & 0xff)+shiftrgb) ;
                        if (pixel_tmp > 255){
                            pixel_tmp=(pixel_tmp-255)/ brillo;
                        }
                        else {
                            pixel_tmp= (pixel  & 0xff) / brillo;
                        }
                        c[enviado] =pixel_tmp;
                        byteBuffer.put((byte) c[enviado]);
                        enviado++;
                    }
                }

            }
            for (int i = 0; i < inter * 96; i++) {
                c[enviado] = 0;
                byteBuffer.put((byte) c[enviado]);
                enviado++;
            }
        }
        b = byteBuffer.array();
        mConnectedThread.write(b);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}