//Practice 2
// 1. Crea una lista llamada "lista" con los elementos "rojo", "blanco", "negro"
val lista = List("rojo", "blanco", "negro")
// 2. Añadir 5 elementos mas a "lista" "verde" ,"amarillo", "azul", "naranja", "perla"
val lista = List("rojo", "blanco", "negro")
val nuevaLista = lista ++ List("verde", "amarillo", "azul", "naranja", "perla")
println(nuevaLista)
// 3. Traer los elementos de "lista" "verde", "amarillo", "azul"
val lista = List("rojo", "blanco", "negro", "verde", "amarillo", "azul", "naranja", "perla")
val coloresSolicitados = lista.filter(e => e == "verde" || e == "amarillo" || e == "azul")
println(coloresSolicitados)
// 4. Crea un arreglo de numero en rango del 1-1000 en pasos de 5 en 5
val arr = Array.range(0, 100, 5)
println(arr)
// 5. Cuales son los elementos unicos de la lista Lista(1,3,3,4,6,7,3,7) utilice conversion a conjuntos
var lista = collection.mutable.Set(1,3,3,4,6,7,3,7)
println(lista)
// 6. Crea una mapa mutable llamado nombres que contenga los siguiente
//     "Jose", 20, "Luis", 24, "Ana", 23, "Susana", "27"
val mapamutable = collection.mutable.Map(("Jose", 20), ("Luis", 24), ("Ana", 23), ("Susana", 27))
println(mapamutable)
// 6 a . Imprime todas la llaves del mapa
println(mapamutable.keys)
// 7 b . Agrega el siguiente valor al mapa("Miguel", 23)
mapamutable += ("Miguel" -> 23)
println(mapamutable)