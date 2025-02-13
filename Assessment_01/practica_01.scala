// Assesment 1 / Practica 1
// 1. Implemente un programa en scala que calcule el radio de un circulo
var pi = 3.1416 
var circuferencia = 20 
var resultado1 = circuferencia / (2 * pi)
println(resultado1)

// 2. Implementa un programa en scala que me diga si un numero es primo
var numero = 3
var resultado2 = !(2 to (numero-1)).exists(x => numero % x == 0)
println(resultado2)

// 3. Dada la variable bird = "Tweet", utiliza la interpolacion de strings para imprimir "Estoy escribiendo un tweet"
val bird = "Tweet"
val mensaje2 = "Estoy"
val mensaje3 = "escribiendo"
val mensaje4 = "un"
val resultado3 = s"$mensaje2 $mensaje3 $mensaje4 $bird"
println (resultado3)

// 4. Dada la variable mensaje = "Hola Luke yo soy tu padre!" utiliza slice para extraer secuencia "Luke"
val mensaje = "Hola Luke yo soy tu padre!"
var resultado4 = mensaje slice(5,9)
println(resultado4)

// 5. ¿Cuál es la diferencia entre value (val) una variable (var) en scala?
val resultado5 = "Val es inmutable y Var puede ser reasignado"
println(resultado5)

// 6. Dada la tupla (2,4,5,1,2,3.1416,3,7) imprime "3.1"
val myTupla = (2,4,5,1,2,3.1416,3,7)
var tuplaValor = myTupla._6
var resultado6 = tuplaValor.toString.slice (0, 3)
println(resultado6)