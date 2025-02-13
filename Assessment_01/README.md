# Assesment 1 / Practica 1
##1. Implementa un programa en scala que calcule el radio de un circulo
##2. Implementa un programa en scala que me diga si un numero es primo
##3. Dada la variable bird = "tweet", utiliza interpolación de strings para imprimir
##4. Dada la variable mensaje = "Hola Luke yo soy tu padre!" utiliza slice para extraer secuencia "Luke"
##5. ¿Cuál es la diferencia entre value (val) una variable (var) en scala?
##6. Dada la tupla (2,4,5,1,2,3.1416,3,7) imprime "3.1"
```
val myTupla = (2,4,5,1,2,3.1416,3,7)
var tuplaValor = myTupla._6
var resultado6 = tuplaValor.toString.slice (0, 3)
println(resultado6)
```
define una tupla myTupla con ocho elementos y extrae el sexto valor (3.1416) usando myTupla._6. Luego, lo convierte en cadena y toma sus tres primeros caracteres con .toString.slice(0, 3), obteniendo "3.1", que finalmente se imprime en la consola.