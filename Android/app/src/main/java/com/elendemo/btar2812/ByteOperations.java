package com.elendemo.btar2812;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public abstract class ByteOperations {
    /**
     * Change length of turned on/off leds.
     * @param position Led channel index.
     * @param outValue Color value. Pass through if no change is applied
     * @param length Led array length.
     * @param width Leds on length.
     * @param isInverted Invert led status on/off.
     * @return Byte
     */
    public static byte changeAmplitude (int position, byte outValue, int length, int width, boolean isInverted){
        if (isInverted) {
            if ((position >= width * 3) & (position < (length - (width * 3)))) outValue = 0;
        } else {
            if ((position <= (width * 3))  || (position >(length-width*3))) outValue = 0;
        }
        return outValue;
    }

    /**
     * Step turning on/off leds.
     * @param position Led channel index.
     * @param step Level of fading.
     * @param isInverted Invert led status on/off.
     * @return Byte
     */
    public static byte changeInterval (int position, byte outValue, int step, boolean isInverted){
        int factor = (position / 3) % step;
        if (isInverted) {
            if (factor == 0) outValue = 0;
        } else {
            if (factor != 0) outValue = 0;
        }
        return outValue;
    }

    /**
     * Apply a fade at the given position of a led array.
     * @param position Led channel index.
     * @param inValue Color value.
     * @param length Number of leds.
     * @param level Level of fading.
     * @return Byte
     */
    public static byte changeFade (int position, int inValue, int length, int level, boolean isInverted){
        byte outValue=0;
        if (level > 0) {
            double factor;
            if (isInverted) {
                if (position <= length / 2) factor = Math.pow(2, ((double) (length - position) / level)) + 1;
                else factor = Math.pow(2, (double) (position / level)) + 1;
            } else {
                if (position <= length / 2) factor = Math.pow(2, (double) (position/level))+1;
                else factor = Math.pow(2, ((double) (length - position)/level))+1 ;
            }
            outValue = (byte) (inValue / factor);
            if (outValue < 0) outValue = 0;
        }
        else {
            outValue= (byte) inValue;
        }
        return outValue;
    }


    /**
     * Apply ColorMatrix transform to the input bitmap.
     * @param bitmapSrc Input bitmap.
     * @param shiftIn Shift value.
     * @return Bitmap
     */
    public static Bitmap bitmapShift (Bitmap bitmapSrc, int shiftIn){
        float reshift= (float) shiftIn/10;
        float shiftR= (float) Math.random()*reshift;
        float shiftG= (float) Math.random()*reshift;
        float shiftB= (float) Math.random()*reshift;

        float[] colorTransform = {
                1,shiftG , shiftB, 0, 0,
                shiftR, 1, shiftB, 0, 0,
                shiftR, shiftG, 1,0 , 0,
                0, 0, 0, 1f, 0};
        int width, height;
        height = bitmapSrc.getHeight();
        width = bitmapSrc.getWidth();
        Bitmap bitmapTransform = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmapTransform);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        cm.set(colorTransform);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bitmapSrc, 0, 0, paint);
        return bitmapTransform;
    }
}
