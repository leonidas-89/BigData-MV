# Práctica Evaluatoria

### 1. Comienza una simple sesión Spark.
Este código inicializa una sesión de Spark, que es el punto de entrada para realizar operaciones de procesamiento de datos con DataFrames, SQL y otras APIs de Spark.
```scala
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()
```
✅ Resultado
```scala
val spark: org.apache.spark.sql.SparkSession = org.apache.spark.sql.SparkSession@59a8891e
```
### 2. Cargue el archivo Netflix Stock CSV en dataframe llamado df, haga que Spark infiera los tipos de datos.
Aqui se carga un archivo CSV llamado "Netflix_2011_2016.csv" en un DataFrame de Spark. Usa spark.read para leer el archivo, con option("header", "true") indicando que la primera fila contiene nombres de columnas y option("inferSchema", "true") para que Spark detecte automáticamente los tipos de datos y el resultado se almacena en la variable netflixdf.
```scala
val netflixdf = spark.read.option("header","true").option("inferSchema","true").csv("Netflix_2011_2016.csv")
```
✅ Resultado

```scala
val netflixdf: org.apache.spark.sql.DataFrame = [Date: date, Open: double ... 5 more fields]
```
### 3. ¿Cuáles son los nombres de las columnas?
En este codigo muestra un array de strings que contiene los nombres de las columnas del DataFrame netflixdf permitiendo ver qué atributos están presentes en el conjunto de datos cargado desde el archivo CSV
```scala
netflixdf.columns
```
✅ Resultado
```scala
val res0: Array[String] = Array(Date, Open, High, Low, Close, Volume, Adj Close)
```
### 4. ¿Cómo es el esquema?
Muestra la estructura del DataFrame netflixdf, incluyendo los nombres de las columnas, sus tipos de datos (entero, cadena, decimal, etc.) y si permiten valores nulos.
```scala
netflixdf.printSchema()
```
✅ Resultado
```scala
root
 |-- Date: date (nullable = true)
 |-- Open: double (nullable = true)
 |-- High: double (nullable = true)
 |-- Low: double (nullable = true)
 |-- Close: double (nullable = true)
 |-- Volume: integer (nullable = true)
 |-- Adj Close: double (nullable = true)
```
### 5. Imprime las primeras 5 renglones.
Se devuelve un array de las primeras 5 filas del DataFrame netflixdf. A diferencia de show(5), que imprime los datos de forma tabular en la consola, head(5) devuelve las filas como objetos tipo Row, permitiendo acceder a los valores programáticamente
```scala
netflixdf.head(5)
```
✅ Resultado
```scala
val res2: Array[org.apache.spark.sql.Row] = Array([2011-10-24,119.100002,120.28000300000001,115.100004,118.839996,120460200,16.977142], [2011-10-25,74.899999,79.390001,74.249997,77.370002,315541800,11.052857000000001], [2011-10-26,78.73,81.420001,75.399997,79.400002,148733900,11.342857], [2011-10-27,82.179998,82.71999699999999,79.249998,80.86000200000001,71190000,11.551428999999999], [2011-10-28,80.280002,84.660002,79.599999,84.14000300000001,57769600,12.02])
```
### 6. Usa el método describe () para aprender sobre el DataFrame.
Con el metodo describe.show() muestra los detalles sobre el DataFrame, que en este caso seria la variable declarada "netflixdf"
```scala
netflixdf.describe().show()
```
✅ Resultado \
En la descripción de los detalles del DataFrame de Netflix, podemos visualizar un resumen estadístico descriptivo sobre las columnas seleccionadas, incluyendo el total de valores no nulos de la columna, promedio de los valores, desviación estándar de los valores de la columna, valor mínimo y máximo. Se aplica .show() para mostrarlo en la consola como se muestra a continuación:

```scala
+-------+------------------+------------------+------------------+------------------+--------------------+------------------+
|summary|              Open|              High|               Low|             Close|              Volume|         Adj Close|
+-------+------------------+------------------+------------------+------------------+--------------------+------------------+
|  count|              1259|              1259|              1259|              1259|                1259|              1259|
|   mean|230.39351086656092|233.97320872915006|226.80127876251044|  230.522453845909|2.5634836060365368E7|55.610540036536875|
| stddev|164.37456353264244| 165.9705082667129| 162.6506358235739|164.40918905512854| 2.306312683388607E7|35.186669331525486|
|    min|         53.990001|         55.480001|             52.81|              53.8|             3531300|          7.685714|
|    max|        708.900017|        716.159996|        697.569984|        707.610001|           315541800|        130.929993|
+-------+------------------+------------------+------------------+------------------+--------------------+------------------+
```

### 7. Crea un nuevo dataframe con una columna nueva llamada “HV Ratio” que es la relación que existe entre el precio de la columna “High” frente a la columna “Volumen” de acciones negociadas por un día. Hint - es una operación
Se creo una copia del dataframe como "netflixdfcopy", endonde se agrego una columna nueva con el comando "withColumn" en donde se hace una operacion aritmenta entre la columna "High" y "Volume".
```scala
val netflixdfcopy = netflixdf.withColumn("HV Ratio",netflixdf("High")/netflixdf("Volume"))
```
✅ Resultado
```scala
+----------+-----------------+------------------+----------+-----------------+---------+------------------+--------------------+
|      Date|             Open|              High|       Low|            Close|   Volume|         Adj Close|            HV Ratio|
+----------+-----------------+------------------+----------+-----------------+---------+------------------+--------------------+
|2011-10-24|       119.100002|120.28000300000001|115.100004|       118.839996|120460200|         16.977142|9.985040951285156E-7|
|2011-10-25|        74.899999|         79.390001| 74.249997|        77.370002|315541800|11.052857000000001|2.515989989281927E-7|
|2011-10-26|            78.73|         81.420001| 75.399997|        79.400002|148733900|         11.342857|5.474206014903126E-7|
|2011-10-27|        82.179998| 82.71999699999999| 79.249998|80.86000200000001| 71190000|11.551428999999999|1.161960907430818...|
|2011-10-28|        80.280002|         84.660002| 79.599999|84.14000300000001| 57769600|             12.02|1.465476686700271...|
|2011-10-31|83.63999799999999|         84.090002| 81.450002|        82.080003| 39653600|         11.725715|2.120614572195210...|
|2011-11-01|        80.109998|         80.999998|     78.74|        80.089997| 33016200|         11.441428|2.453341026526372E-6|
|2011-11-02|        80.709998|         84.400002| 80.109998|        83.389999| 41384000|         11.912857|2.039435578967717E-6|
|2011-11-03|        84.130003|         92.600003| 81.800003|        92.290003| 94685500|13.184285999999998| 9.77974483949496E-7|
|2011-11-04|91.46999699999999| 92.89000300000001| 87.749999|        90.019998| 84483700|             12.86|1.099502069629999...|
|2011-11-07|             91.0|         93.839998| 89.979997|        90.830003| 47485200|         12.975715|1.976194645910725...|
|2011-11-08|91.22999899999999|         92.600003| 89.650002|        90.470001| 31906000|         12.924286|2.902275528113834...|
|2011-11-09|        89.000001|         90.440001| 87.999998|        88.049999| 28756000|         12.578571|3.145082800111281E-6|
|2011-11-10|        89.290001| 90.29999699999999| 84.839999|85.11999899999999| 39614400|             12.16|2.279474054889131E-6|
|2011-11-11|        85.899997|         87.949997|      83.7|        87.749999| 38140200|         12.535714|2.305965805108520...|
|2011-11-14|        87.989998|              88.1|     85.45|        85.719999| 21811300|         12.245714|4.039190694731629...|
|2011-11-15|            85.15|         87.050003| 84.499998|        86.279999| 21372400|         12.325714|4.073010190713256...|
|2011-11-16|        86.460003|         86.460003| 80.890002|        81.180002| 34560400|11.597142999999999|2.501707242971725E-6|
|2011-11-17|            80.77|         80.999998| 75.789999|        76.460001| 52823400|         10.922857|1.533411291208063...|
|2011-11-18|             76.7|         78.999999| 76.039998|        78.059998| 34729100|         11.151428|2.274749388841058...|
+----------+-----------------+------------------+----------+-----------------+---------+------------------+--------------------+
```

### 8. ¿Qué día tuvo el pico más alto en la columna “Open”?
Se utiliza el comando orderBy en donde se especifica el order descendiente (del mayor al menor) en donde solo estamos mostrando una sola fila en show(1) por l oque el valor maximo es 708.90
```scala
netflixdf.orderBy($"Open".desc).show(1)
```
✅ Resultado
```scala
+----------+----------+----------+----------+----------+--------+----------+
|      Date|      Open|      High|       Low|     Close|  Volume| Adj Close|
+----------+----------+----------+----------+----------+--------+----------+
|2015-07-14|708.900017|711.449982|697.569984|702.600006|19736500|100.371429|
+----------+----------+----------+----------+----------+--------+----------+
```

### 9. ¿Cuál es el significado de la columna Cerrar “Close” en el contexto de información financiera, explíquelo no hay que codificar nada?
El precio de cierre "Close" es el precio final al que se negoció un activo (acción, bono, etc.) antes de que el mercado cerrara en un día normal de operaciones. En resumen la ultima transaccion realizada del valor, ejemplo el valor maximo de la columna:
```scala
netflixdf.select(max("Close")).show()
```
✅ Resultado
```scala
+----------+
|max(Close)|
+----------+
|707.610001|
+----------+
```

### 10. ¿Cuál es el máximo y mínimo de la columna “Volumen”?
Con los parametros max y min del comando "select" podemos mostrar en la consola los maximos y minimos de la columna "Volumen"
```scala
netflixdf.select(max("Volume")).show()
netflixdf.select(min("Volume")).show()
```
✅ Resultado
```scala
+-----------+
|max(Volume)|
+-----------+
|  315541800|
+-----------+

+-----------+
|min(Volume)|
+-----------+
|    3531300|
+-----------+
```

### 11. Con Sintaxis Scala/Spark $ conteste lo siguiente:
##### a) ¿Cuántos días fue la columna “Close” inferior a $ 600?
Este código filtra el DataFrame para contar cuántas filas tienen un valor en la columna "Close" menor a 600
```scala
netflixdf.filter($"Close"<600).count()
```
✅ Resultado
```scala
Long = 1218
```
##### b) ¿Qué porcentaje del tiempo fue la columna “High” mayor que $ 500?
Este código calcula el porcentaje de registros en el DataFrame que tienen un valor en la columna "High" mayor a 500 del total de todos los registros
```scala
(netflixdf.filter($"High">500).count()*1.0/netflixdf.count())*100
```
✅ Resultado
```scala
Double = 4.924543288324067
```
##### c) ¿Cuál es la correlación de Pearson entre columna “High” y la columna “Volumen”?
Este código prueba la relación estadística entre las columnas "High" y "Volume"
```scala
netflixdf.select(corr("High","Volume")).withColumnRenamed("corr(High, Volume)","Correlación Pearson").show()
```
✅ Resultado
```scala
+--------------------+
| Correlación Pearson|
+--------------------+
|-0.20960233287942157|
+--------------------+
```
##### d) ¿Cuál es el máximo de la columna “High” por año?
Este código obtiene el valor máximo de la columna "High" para cada año en el DataFrame, primero crea una columna "Año" que extrae el año de la columna "Date", después genera otro DataFrame agrupado por año con su máximo y después muestra el resultado
```scala
val yeardf = netflixdf.withColumn("Year",year(netflixdf("Date"))).withColumnRenamed("Year","Año")
val yearmaxs = yeardf.select($"Año",$"High").groupBy("Año").max().withColumnRenamed("max(High)","Máximo")
yearmaxs.select($"Año",$"Máximo").orderBy("Año").show()
```
✅ Resultado
```scala
+----+------------------+
| Año|            Máximo|
+----+------------------+
|2011|120.28000300000001|
|2012|        133.429996|
|2013|        389.159988|
|2014|        489.290024|
|2015|        716.159996|
|2016|129.28999299999998|
+----+------------------+
```
##### e) ¿Cuál es el promedio de la columna “Close” para cada mes del calendario?
Este código calcula el promedio mensual de la columna "Close". Primero, agrega una nueva columna "Mes" extrayendo el mes de "Date". Luego, agrupa los datos por mes y calcula el promedio de la columna "Close" usando .mean(). Finalmente, selecciona y muestra los valores de "Mes" y "Promedio"
```scala
val monthdf = netflixdf.withColumn("Month",month(netflixdf("Date"))).withColumnRenamed("Month","Mes")
val monthavgs = monthdf.select($"Mes",$"Close").groupBy("Mes").mean().withColumnRenamed("avg(Close)","Promedio")
monthavgs.select($"Mes",$"Promedio").orderBy("Mes").show()
```
✅ Resultado
```scala
+---+------------------+
|Mes|          Promedio|
+---+------------------+
|  1|212.22613874257422|
|  2| 254.1954634020619|
|  3| 249.5825228971963|
|  4|246.97514271428562|
|  5|264.37037614150944|
|  6| 295.1597153490566|
|  7|243.64747528037387|
|  8|195.25599892727263|
|  9|206.09598121568627|
| 10|205.93297300900903|
| 11| 194.3172275445545|
| 12| 199.3700942358491|
+---+------------------+
```