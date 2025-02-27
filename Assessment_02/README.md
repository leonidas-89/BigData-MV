# Assesment 2 / Practica 2
## Crea una lista llamada "lista" con los elementos "rojo", "blanco", "negro"
```scala
val lista = List("rojo", "blanco", "negro")

```
## Añadir 5 elementos mas a "lista" "verde" ,"amarillo", "azul", "naranja", "perla"
```scala
val lista = List("rojo", "blanco", "negro")
val nuevaLista = lista ++ List("verde", "amarillo", "azul", "naranja", "perla")
println(nuevaLista)
```
## Traer los elementos de "lista" "verde", "amarillo", "azul"
```scala
val lista = List("rojo", "blanco", "negro", "verde", "amarillo", "azul", "naranja", "perla")
val coloresSolicitados = lista.filter(e => e == "verde" || e == "amarillo" || e == "azul")
println(coloresSolicitados)
```
## Crea un arreglo de numero en rango del 1-1000 en pasos de 5 en 5
```scala
val arr = Array.range(0, 100, 5)
println(arr)
Array(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95)
```
## Cuales son los elementos unicos de la lista Lista(1,3,3,4,6,7,3,7) utilice conversion a conjuntos
```scala
var lista = collection.mutable.Set(1,3,3,4,6,7,3,7)
println(lista)
HashSet(1, 3, 4, 6, 7)
```
## Crea una mapa mutable llamado nombres que contenga los siguiente: "Jose", 20, "Luis", 24, "Ana", 23, "Susana", "27"
```scala
val mapamutable = collection.mutable.Map(("Jose", 20), ("Luis", 24), ("Ana", 23), ("Susana", 27))
println(mapamutable)
scala.collection.mutable.Map[String,Int] = HashMap(Susana -> 27, Jose -> 20, Ana -> 23, Luis -> 24)
```
## Imprime todas la llaves del mapa
```scala
println(mapamutable.keys)
Set(Susana, Jose, Ana, Luis)
```
## Agrega el siguiente valor al mapa("Miguel", 23)
```scala
mapamutable += ("Miguel" -> 23)
println(mapamutable)
HashMap(Susana -> 27, Miguel -> 23, Jose -> 20, Ana -> 23, Luis -> 24)
```
