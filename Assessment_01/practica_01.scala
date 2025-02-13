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