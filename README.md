# BTAR2812

BTAR2812 es una aplicación android para controlar leds WS2812 utilizando comunicación bluetooth con un controlador Arduino y módulo HC-05/HC-06.


### Propósito
BTAR2812 está pensando en la necesidad particular de la creaciòn de herramientas para utilizar en la técnica fotográfica de lightpainting (Si deseas saber sobre la técnica de lightpainting estas en el lugar indicado!, puedes pasar por mi web [www.elendemo.com.ar](http://www.elendemo.com.ar "www.elendemo.com.ar")).

![IMG_5572](https://user-images.githubusercontent.com/60631810/154155399-808fe86d-cb8c-4b8a-93a6-b790a6a763a9.jpg)
Toma de larga exposicion directa de camara utilizando la herramienta controlada por la aplicacion para la iluminacion y efectos de luz. (Credito: @orgasmovisual.photogroup / @el_endemoniau).

### Status

El estado actual del proyecto permite controlar hasta 255 leds WS2812B, testeado en matriz de 8x8, tira de 32 leds, anillo de 16 leds y encapsulado de 7 leds. También cuenta con recursos de mensajes localizados en Español e Inglés.

### Setup ![ic_setup](https://user-images.githubusercontent.com/60631810/154331668-be282bf3-1fdc-48d7-9bdc-3e1ce0d46ca4.jpg) 


![Screenshot_20220618-004033](https://user-images.githubusercontent.com/60631810/174422172-3976c65e-40ba-4bf3-bc86-70eb945ac870.png)

Permite seleccionar el dispositivo al que se va a controlar e informa el status de conexión.


### Modo Bitmap ![ic_matriz](https://user-images.githubusercontent.com/60631810/154332376-bc6fecbe-9455-4b56-8f35-34f9b2b38f81.jpg)

![Screenshot_20220618-004211](https://user-images.githubusercontent.com/60631810/174422186-b1aa6be0-0fe1-4c34-8506-cda6080756c5.png)

Permite cargar un archivo de bitmap y enviarlo a los leds cuando se presiona sobre la imagen cargada asi como hacer algunos ajustes:
- Desplazamiento de la informacion de color (experimental).
- Repeticiones.
- Velocidad que permanece encendida cada línea que se reproduce del bitmap.
- Ajustar el brillo de los leds.
- Modo Matriz ideal para matrices de leds y representar un bitmap de manera no lineal. El modificador estatico mantiene fijo el encendido de los leds.



### Modo Barra ![ic_barra](https://user-images.githubusercontent.com/60631810/154332448-d4f69af7-aeb5-4af5-8715-4886d68fd944.jpg)

![Screenshot_20220618-004222](https://user-images.githubusercontent.com/60631810/174422196-f8f0fdec-2c48-4802-909c-075ef87d86e5.png)

Utilizando [Skydoves Colorpicker](https://github.com/skydoves/ColorPickerView "Skydoves Colorpicker") permite seleccionar un color y hacer ajustes:
- Fundido para el nivel de degradado del brillo.
- Intervalo de leds encendidos y apagados.
- Amplitud de la cantidad de leds encendidos.
- Todos los controles cuentan con un checkbox para invertir el ajuste.
- Los distintos ajustes combinados brindan un amplio rango de trazos de luz.

### Esquema
(A modo ilustrativo el módulo wifi del esquema cumple la función del modulo bluetooth)
![BTAR2812](https://user-images.githubusercontent.com/60631810/154372486-57432e4a-b26b-4526-85d9-6ba39b7c1859.png)

[Enlace a esquema](https://www.tinkercad.com/things/8bRt67OoYUw "Enlace a esquema")

### Conexión

- Pin TX de Arduino -> RX del módulo bluetooth
- Pin RX de Arduino -> TX del módulo bluetooth
- Pin 9 de Arduino -> DIN de los leds

### Consideraciones
- Se debe incluir [FastLED](https://github.com/FastLED/FastLED "FastLED") en el código Arduino.


### Agradecimientos a [fcastro79](https://github.com/fcastro79 "fcastro79")!
