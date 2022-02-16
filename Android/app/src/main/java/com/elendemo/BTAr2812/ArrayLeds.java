package com.elendemo.BTAr2812;

public class ArrayLeds {
    public byte[] arrayBytes;
    public byte[] ledsOff;
    public int unicolor[];
    public int brillo;
    public int intervalo;
    public int amplitud;
    public int fade;
    public boolean isInvAmp;
    public boolean isInvInt;
    public boolean isInvFad;

    public void iniciar() {
        intervalo = 0;
        amplitud = 16;
        ledsOff = new byte[96];
        arrayBytes = new byte[96];
        for (int i = 0; i < 96; i++) {
            ledsOff[i] = (byte) 0;
        }
    }

    public void colorOutput(int[] color) {
        unicolor = color;
        for (int i = 0; i < 96; i += 3) {
            arrayBytes[i] = (byte) unicolor[1];
            arrayBytes[i + 1] = (byte) unicolor[2];
            arrayBytes[i + 2] = (byte) unicolor[3];
            if (isInvAmp) {
                if ((i >= amplitud * 3) & (i < (96 - (amplitud * 3)))) {
                    arrayBytes[i] = (byte) 0;
                    arrayBytes[i + 1] = (byte) 0;
                    arrayBytes[i + 2] = (byte) 0;
                }
            } else {
                if ((i < (16 - amplitud) * 3) || (i > (48 + (amplitud * 3)))) {
                    arrayBytes[i] = (byte) 0;
                    arrayBytes[i + 1] = (byte) 0;
                    arrayBytes[i + 2] = (byte) 0;
                }
            }
            if (intervalo > 0) {
                int resto = (i / 3) % intervalo;
                if (isInvInt) {
                    if (resto == 0) {
                        arrayBytes[i] = (byte) 0;
                        arrayBytes[i + 1] = (byte) 0;
                        arrayBytes[i + 2] = (byte) 0;
                    }
                } else {
                    if (resto != 0) {
                        arrayBytes[i] = (byte) 0;
                        arrayBytes[i + 1] = (byte) 0;
                        arrayBytes[i + 2] = (byte) 0;
                    }
                }
            }

            if (fade > 0) {
                if (isInvFad) {
                    if ((i > (16 - fade) * 3) & (i < (48 + (fade * 3)))) {
                        arrayBytes[i] = (byte) (arrayBytes[i] / fade);
                        arrayBytes[i + 1] = (byte) (arrayBytes[i + 1] / fade);
                        arrayBytes[i + 2] = (byte) (arrayBytes[i + 2] / fade);
                    }
                } else {
                    if ((i < (16 - fade) * 3) || (i > (48 + (fade * 3)))) {
                        arrayBytes[i] = (byte) (arrayBytes[i] / fade);
                        arrayBytes[i + 1] = (byte) (arrayBytes[i + 1] / fade);
                        arrayBytes[i + 2] = (byte) (arrayBytes[i + 2] / fade);
                    }
                }
            }
        }
    }

    public int getBrillo() {
        return brillo;
    }

    public void setBrillo(int brillo) {
        this.brillo = brillo;
    }

    public int getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(int intervalo) {
        this.intervalo = intervalo;
    }

    public int getAmplitud() {
        return amplitud;
    }

    public void setAmplitud(int amplitud) {
        this.amplitud = amplitud;
    }

    public int getFade() {
        return fade;
    }

    public void setFade(int fade) {
        this.fade = fade;
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

}
