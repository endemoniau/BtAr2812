#include "FastLED.h"
 
#define NUM_LEDS 32
#define DATA_PIN 9
 
CRGB leds[NUM_LEDS];

int control=0;
int indice;
int red[0];
int green[0];
int blue[0];
int dato;
int contador=0;

void setup() {
  FastLED.addLeds<NEOPIXEL, DATA_PIN>(leds, NUM_LEDS);
  indice=0;
  control=0;
  limpiar();
  Serial.begin(19200);
  delay(1000);
}

void limpiar(){
   for(int i=0;i<NUM_LEDS;i++){
      leds[i].r=0;
      leds[i].g=0;
      leds[i].b=0;
      FastLED.show();
   }
}
void loop() {
  while(Serial.available() > 0) {
    switch (control) {
        case 0:
        dato=Serial.read();
          leds[indice].r=dato;
          control++;
          contador++;
          break;
        case 1:
        dato=Serial.read();
          leds[indice].g=dato;
          control++;
          contador++;
         break;
        case 2:
        dato=Serial.read();
          leds[indice].b=dato;
           control=0;
           contador++;
          indice++;
          break;
      }
      if (contador >=96){
            FastLED.show();
            indice=0;
            contador=0;
     }
  }
}
