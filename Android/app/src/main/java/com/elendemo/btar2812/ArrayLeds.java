package com.elendemo.btar2812;

import static com.elendemo.btar2812.ByteOperations.*;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class ArrayLeds {
    private byte[] arrayBytes;
    private byte[] ledsOff;
    private int [] unicolor;
    private int [] fromBitmap ;
    private int brightness;
    private int interval;
    private int amplitude;
    private int fade;
    private int repeat;
    private int pause;
    private int shiftrgb;
    private int rows;
    private int ledNumbers;
    private int bytesArrayLeds;
    private int bytesLeds;
    private int ledType;
    private boolean isInvAmp;
    private boolean isInvInt;
    private boolean isInvFad;
    private boolean isInvBright;
    private boolean isMatrix;
    private boolean isMatrixStatic;
    private boolean isMatrixLong;
    private boolean isContinuous;
    private String ledText;
    private Bitmap selectedImage;
    private Bitmap tmp_bmp;
    private static ArrayLeds ledData;

    public ArrayLeds (){

    }

    public static ArrayLeds getArrayLeds(){
        if(ledData ==null){
            ledData = new ArrayLeds();
        }
        return ledData;
    }

    public void colorOutput(int[] color) {
        int redChannel=color[1];
        int greenChannel=color[2];
        int blueChannel=color[3];
        arrayBytes = new byte[bytesLeds + 1];
        arrayBytes[0] = (byte) bytesLeds;
        if (this.brightness > 0) {
            redChannel/= brightness;
            greenChannel/= brightness;
            blueChannel/= brightness;
        }
        for (int i = 1; i < this.bytesArrayLeds; i += 3) {
            arrayBytes[i] = (byte) redChannel ;
            arrayBytes[i + 1] = (byte) greenChannel;
            arrayBytes[i + 2] = (byte) blueChannel ;
                arrayBytes[i] = changeFade(i,redChannel,bytesLeds,fade,isInvFad);
                arrayBytes[i + 1] = changeFade(i ,greenChannel,bytesLeds,fade,isInvFad);
                arrayBytes[i + 2] = changeFade(i,blueChannel ,bytesLeds,fade,isInvFad);
            if (amplitude > 0){
                arrayBytes[i] = changeAmplitude(i,arrayBytes[i],bytesLeds, amplitude,isInvAmp);
                arrayBytes[i + 1] = changeAmplitude(i ,arrayBytes[i + 1],bytesLeds, amplitude,isInvAmp);
                arrayBytes[i + 2] = changeAmplitude(i,arrayBytes[i + 2] ,bytesLeds, amplitude,isInvAmp);
            }
            if (interval > 0){
                arrayBytes[i] = changeInterval(i,arrayBytes[i], interval,isInvInt);
                arrayBytes[i + 1] = changeInterval(i ,arrayBytes[i + 1], interval,isInvInt);
                arrayBytes[i + 2] = changeInterval(i,arrayBytes[i + 2] , interval,isInvInt);
            }
        }
    }

    public void arrayBMP(@NonNull Bitmap srcBitmap) {
        int dataIndex =0;
        float bmpWidth = srcBitmap.getWidth();
        float bmpHeight = srcBitmap.getHeight();
        float factor;
        float scaledFactorW;
        float scaledFactorH;
        float scrWidth = bmpWidth;
        tmp_bmp = srcBitmap;
        scaledFactorW=tmp_bmp.getWidth();
        scaledFactorH=tmp_bmp.getHeight();

        if (isMatrix) {
            scaledFactorW = scaledFactorH = (float) Math.sqrt(ledNumbers);
        }
        else {
            scaledFactorW=ledNumbers;
            if (bmpWidth != ledNumbers) scaledFactorH = (ledNumbers / bmpWidth)*bmpHeight;
        }

        tmp_bmp = Bitmap.createScaledBitmap(srcBitmap, Math.round(scaledFactorW), Math.round(scaledFactorH), false);

            if (shiftrgb > 0) {
                tmp_bmp = bitmapShift(tmp_bmp, shiftrgb);
            }
            fromBitmap = new int[tmp_bmp.getWidth() * tmp_bmp.getHeight()];
            tmp_bmp.getPixels(fromBitmap, 0, tmp_bmp.getWidth(), 0, 0, tmp_bmp.getWidth(), tmp_bmp.getHeight());
            rows = tmp_bmp.getHeight() + 1;

            int size = ((tmp_bmp.getWidth()) * (tmp_bmp.getHeight())) * 3;
            int sizeByteBmp = (size + tmp_bmp.getHeight());

            if (ledData.getMatrixStatic()) {
                arrayBytes = new byte[sizeByteBmp];
            } else {
                arrayBytes = new byte[sizeByteBmp + ledsOff.length];
            }


            for (int pixelsTmp : fromBitmap) {
                if (dataIndex % (bytesLeds + 1) == 0) {
                    arrayBytes[dataIndex] = (byte) bytesLeds;
                    dataIndex++;
                }
                arrayBytes[dataIndex] = (byte) (((pixelsTmp >> 16) & 0xff) / brightness);
                arrayBytes[dataIndex + 1] = (byte) (((pixelsTmp >> 8) & 0xff) / brightness);
                arrayBytes[dataIndex + 2] = (byte) ((pixelsTmp & 0xff) / brightness);
                dataIndex += 3;
            }

            if (!ledData.getMatrixStatic()) {
                arrayBytes[dataIndex] = (byte) bytesLeds;
            }
        return ;
    }


    public boolean isContinuous() {
        return isContinuous;
    }

    public void setContinuous(boolean continuous) {
        isContinuous = continuous;
    }

    public boolean getMatrixLong() {
        return isMatrixLong;
    }

    public void setMatrixLong(boolean matrixLong) {
        isMatrixLong= matrixLong;
    }

    public boolean getMatrixStatic() {
        return isMatrixStatic;
    }

    public void setMatrixStatic(boolean matrixStatic) {
        isMatrixStatic = matrixStatic;
    }

    public boolean getMatrix() {
        return isMatrix;
    }

    public void setMatrix(boolean matrix) {
        isMatrix = matrix;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(int amplitude) {
        this.amplitude = amplitude;
    }

    public int getFade() {
        return fade;
    }

    public void setFade(int fade) {
        this.fade = fade;
    }

    public boolean isInvBright () {
        return isInvBright;
    }

    public void setInvBright (boolean invBright) {
        isInvBright = invBright;
    }

    public boolean isInvAmp() {
        return isInvAmp;
    }

    public void setInvAmp(boolean invAmp) {
        isInvAmp = invAmp;
    }

    public boolean isInvInt() {
        return isInvInt;
    }

    public void setInvInt(boolean invInt) {
        isInvInt = invInt;
    }

    public boolean isInvFad() {
        return isInvFad;
    }

    public void setInvFad(boolean invFad) {
        isInvFad = invFad;
    }


    public byte[] getArrayBytes () {
        return arrayBytes;
    }

    public void setArrayBytes (byte[] arrayBytes) {
        this.arrayBytes = arrayBytes;
    }

    public byte[] getLedsOff () {
        return ledsOff;
    }

    public void setLedsOff (byte[] ledsOff) {
        this.ledsOff = ledsOff;
    }

    public int[] getUnicolor () {
        return unicolor;
    }

    public void setUnicolor (int[] unicolor) {
        this.unicolor = unicolor;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getPause() {
        return pause;
    }

    public void setPause(int pause) {
        this.pause = pause;
    }

    public int getShiftrgb () {
        return shiftrgb;
    }

    public void setShiftrgb (int shiftrgb) {
        this.shiftrgb = shiftrgb;
    }

    public int getLedNumbers() {
        return ledNumbers;
    }

    public void setLedNumbers(int ledNumbers) {
        this.ledNumbers = ledNumbers;
        this.bytesLeds= ledNumbers *3;
        this.bytesArrayLeds=(ledNumbers *3)+1;
        this.ledsOff = new byte[bytesArrayLeds];
        this.arrayBytes = new byte[bytesArrayLeds];
        ledsOff[0] = (byte) bytesLeds;
    }

    public int getBytesArrayLeds () {
        return bytesArrayLeds;
    }

    public void setBytesArrayLeds (int bytesArrayLeds) {
        this.bytesArrayLeds = bytesArrayLeds;
    }

    public int getBytesLeds () {
        return bytesLeds;
    }

    public void setBytesLeds (int bytesLeds) {

        this.bytesLeds = bytesLeds;
    }

    public int getLedType() {
        return ledType;
    }

    public void setLedType(int ledType) {
        this.ledType = ledType;
    }

    public String getLedText() {
        return ledText;
    }

    public void setLedText(String ledText) {
        this.ledText = ledText;
    }

    public Bitmap getSelectedImage () {
        return selectedImage;
    }

    public void setSelectedImage (Bitmap selectedImage) {
        this.selectedImage = selectedImage;
    }

    public Bitmap getTmp_bmp () {
        return tmp_bmp;
    }

    public void setTmp_bmp (Bitmap tmp_bmp) {
        this.tmp_bmp = tmp_bmp;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public static ArrayLeds getLedData() {
        return ledData;
    }

    public static void setLedData(ArrayLeds ledData) {
        ArrayLeds.ledData = ledData;
    }

}
