#include "FastLED.h"
 
#define DATA_PIN 9
 
CRGB leds[255];

int control=0;
int indice;
int red[0];
int green[0];
int blue[0];
int dato;
int contador=0;
int num_bytes;
int num_leds;

void setup() {
  Serial.begin(19200);
  delay(1000); 
  indice=0;
   control=-1;
  limpiar();
}


void loop() {
  if(Serial.available()>0) {
      dato=Serial.read();
 
      switch (control) {
          case -1:
            num_bytes=dato;
            num_leds=dato/3;
            FastLED.addLeds<NEOPIXEL, DATA_PIN>(leds, num_leds);
            control++;
            break;
          case 0:
            leds[indice].r=dato;
            control++;
            contador++;
            break;
          case 1:
            leds[indice].g=dato;
            control++;
            contador++;
           break;
          case 2:
              leds[indice].b=dato;
              control=0;
              contador++;
              indice++;
            break;
        }
        if (contador >=num_bytes){
          FastLED.show();
          indice=0;
          contador=0;
          control=-1;
          Serial.print("1");
        }
    }
}

void limpiar(){
   for(int i=0;i<num_leds;i++){
      leds[i].r=0;
      leds[i].g=0;
      leds[i].b=0;
      FastLED.show();
   }
}
