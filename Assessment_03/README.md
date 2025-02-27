
## Código 1
```scala
def listEvens(list:List[Int]): String ={
    for(n <- list){
        if(n%2==0){
            println(s"$n is even")
        }else{
            println(s"$n is odd")
        }
    }
    return "Done"
}

val l = List(1,2,3,4,5,6,7,8)
val l2 = List(4,3,22,55,7,8)
listEvens(l)
listEvens(l2)
```
Este código recorre una lista de números, verifica si cada número es par o impar e imprime un mensaje indicando lo que es

## Código 2
```scala
//3 7 afortunado

def afortunado(list:List[Int]): Int={
    var res=0
    for(n <- list){
        if(n==7){
            res = res + 14
        }else{
            res = res + n
        }
    }
    return res
}

val af= List(1,7,7)
println(afortunado(af))
```
Este código recorre una lista de números y lo suma. Si el número es un 7 entonces adiciona 14 en vez del 7

## Código 3
```scala
def balance(list:List[Int]): Boolean={
    var primera = 0
    var segunda = 0

    segunda = list.sum

    for(i <- Range(0,list.length)){
        primera = primera + list(i)
        segunda = segunda - list(i)

        if(primera == segunda){
            return true
        }
    }
    return false 
}

val bl = List(3,2,1)
val bl2 = List(2,3,3,2)
val bl3 = List(10,30,90)

balance(bl)
balance(bl2)
balance(bl3)
```
Este codigo crea dos balances para comparar si en la iteración de la lista ocurren un punto en el cual ambos balances son iguales. Primero agrega la suma total de la lista y en cada iteración le va restando el valor de la lista y a su vez a la primera le suma cada valor de la lista. Si en alguna iteracion ambos valores son iguales regrsa true en caso contrario es false

## Código 4
```scala
def palindromo(palabra:String):Boolean ={
    return (palabra == palabra.reverse)
}

val palabra = "OSO"
val palabra2 = "ANNA"
val palabra3 = "JUAN"

println(palindromo(palabra))
println(palindromo(palabra2))
println(palindromo(palabra3))
```
El codigo valida si el input es un palindromo comparando el inputo con el texto invertido mediante el reverse
