# BTAR2812

BTAR2812 es una aplicacion android para controlar leds WS2812 utilizando comunicacion bluetooth con un controlador Arduino y modulo HC-05/HC-06.


### Proposito
BTAR2812 está pensando en la necesidad particular de la creaciòn de herramientas para utilizar en la tecnica fotografica de lightpainting (Si deseas saber sobre la tecnica de lightpainting estas en el lugar indicado!, puedes pasar por mi web [www.elendemo.com.ar](http://www.elendemo.com.ar "www.elendemo.com.ar")).

![IMG_5572](https://user-images.githubusercontent.com/60631810/154155399-808fe86d-cb8c-4b8a-93a6-b790a6a763a9.jpg)
Toma de larga exposicion directa de camara utilizando la herramienta controlada por la aplicacion para la iluminacion y efectos de luz. (Credito: @
orgasmovisual.photogroup / @el_endemoniau).

### Status

El estado actual del proyecto esta configurado para controlar 32 leds WS2812B. Mientras tanto continuo con el desarrollo que permitira especificar la cantidad de leds que se deseen controlar ya que se encuentran en distintas presentaciones (barra, anillo, matriz) y cada cual con su cantidad de leds.
Tambien cuenta con recursos de mensajes localizados en Español e Ingles.


### Seccion Inicio

![BTAR2812-1](https://user-images.githubusercontent.com/60631810/153987472-956a668c-5c75-4512-81e0-0e916d2760c3.jpg)

Permite seleccionar el dispositivo al que se va a controlar e informa el status de conexion.


### Modo Bitmap

![BTAR2812-2](https://user-images.githubusercontent.com/60631810/153987514-56b35e62-2ecb-4b94-ab59-62753fa21b91.jpg)

Permite cargar un archivo de bitmap y enviarlo a los leds cuando se presiona sobre la imagen cargada asi como hacer algunos ajustes:
- Desplazamiento de la informacion de color.
- Intervalo indica la pausa entre repeticiones cuando el control de repetecion es mayor a uno.
- Velocidad que permanece encendida cada linea que se reproduce del bitmap.
- Ajustar el brillo de los leds.


### Modo Barra

![BTAR2812-3](https://user-images.githubusercontent.com/60631810/153987538-b97c81ba-d3b1-4205-9ed9-8b89313b6349.jpg) 

Utilizando [Skydoves Colorpicker](https://github.com/skydoves/ColorPickerView "Skydoves Colorpicker") permite seleccionar un color y hacer ajustes:
- Fundido para el nivel de degradado del brillo.
- Intervalo de leds encendidos y apagados.
- Amplitud de la cantidad de leds encendidos.
- Todos los controles cuentan con un checkbox para invertir el ajuste.
- Los distintos ajustes combinados brindan un amplio rango de trazos de luz.



