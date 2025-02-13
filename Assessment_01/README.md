# Assesment 1 / Practica 1
## Implementa un programa en scala que calcule el radio de un circulo
## Implementa un programa en scala que me diga si un numero es primo
## Dada la variable bird = "tweet", utiliza interpolación de strings para imprimir
## Dada la variable mensaje = "Hola Luke yo soy tu padre!" utiliza slice para extraer secuencia "Luke"
```scala
val mensaje = "Hola Luke yo soy tu padre!"
var resultado4 = mensaje slice(5,9)
println(resultado4)
Luke
```
El código en Scala extrae los caracteres del índice 5 al 8 de la cadena "Hola Luke yo soy tu padre!" usando .slice(5,9), obteniendo "Luke", y lo imprime en la consola.

## ¿Cuál es la diferencia entre value (val) una variable (var) en scala?
```scala
val resultado5 = "Val es inmutable y Var puede ser reasignado"
println(resultado5)
Val es inmutable y Var puede ser reasignado
```
El código en Scala declara una variable inmutable (val) llamada resultado5, que almacena la cadena "Val es inmutable y Var puede ser reasignado". Luego, con println(resultado5), imprime ese mensaje en la consola. Como val no permite reasignación, su valor permanecerá constante durante la ejecución del programa.

## Dada la tupla (2,4,5,1,2,3.1416,3,7) imprime "3.1"
```scala
val myTupla = (2,4,5,1,2,3.1416,3,7)
var tuplaValor = myTupla._6
var resultado6 = tuplaValor.toString.slice (0, 3)
println(resultado6)
3.1
```
El código define una tupla myTupla con ocho elementos y extrae el sexto valor (3.1416) usando myTupla._6. Luego, lo convierte en cadena y toma sus tres primeros caracteres con .toString.slice(0, 3), obteniendo "3.1", que finalmente se imprime en la consola.