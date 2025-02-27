# Assesment 2 / Practica 2
## Crea una lista llamada "lista" con los elementos "rojo", "blanco", "negro"
## Añadir 5 elementos mas a "lista" "verde" ,"amarillo", "azul", "naranja", "perla"
## Traer los elementos de "lista" "verde", "amarillo", "azul"
## Crea un arreglo de numero en rango del 1-1000 en pasos de 5 en 5
```scala
val arr = Array.range(0, 100, 5)
println(arr)
```
## Cuales son los elementos unicos de la lista Lista(1,3,3,4,6,7,3,7) utilice conversion a conjuntos
```scala
var lista = collection.mutable.Set(1,3,3,4,6,7,3,7)
println(lista)
```
## Crea una mapa mutable llamado nombres que contenga los siguiente: "Jose", 20, "Luis", 24, "Ana", 23, "Susana", "27"
```scala
val mapamutable = collection.mutable.Map(("Jose", 20), ("Luis", 24), ("Ana", 23), ("Susana", 27))
println(mapamutable)
```
## Imprime todas la llaves del mapa
```scala
println(mapamutable.keys)
```
## Agrega el siguiente valor al mapa("Miguel", 23)
```scala
mapamutable += ("Miguel" -> 23)
println(mapamutable)
```
