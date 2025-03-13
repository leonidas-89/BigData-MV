# Práctica Evaluatoria

### 1. Comienza una simple sesión Spark.
Este código inicializa una sesión de Spark, que es el punto de entrada para realizar operaciones de procesamiento de datos con DataFrames, SQL y otras APIs de Spark
```scala
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()
```
✔️Resultado
```scala
val spark: org.apache.spark.sql.SparkSession = org.apache.spark.sql.SparkSession@59a8891e
```
### 2. Cargue el archivo Netflix Stock CSV en dataframe llamado df, haga que Spark infiera los tipos de datos.
Aqui se carga un archivo CSV llamado "Netflix_2011_2016.csv" en un DataFrame de Spark. Usa spark.read para leer el archivo, con option("header", "true") indicando que la primera fila contiene nombres de columnas y option("inferSchema", "true") para que Spark detecte automáticamente los tipos de datos y el resultado se almacena en la variable netflixdf
```scala
val netflixdf = spark.read.option("header","true").option("inferSchema","true").csv("Netflix_2011_2016.csv")
```
✔️Resultado

```scala
val netflixdf: org.apache.spark.sql.DataFrame = [Date: date, Open: double ... 5 more fields]
```
### 3. ¿Cuáles son los nombres de las columnas?
En este codigo muestra un array de strings que contiene los nombres de las columnas del DataFrame netflixdf permitiendo ver qué atributos están presentes en el conjunto de datos cargado desde el archivo CSV
```scala
netflixdf.columns
```
✔️Resultado
```scala
val res0: Array[String] = Array(Date, Open, High, Low, Close, Volume, Adj Close)
```
### 4. ¿Cómo es el esquema?
Muestra la estructura del DataFrame netflixdf, incluyendo los nombres de las columnas, sus tipos de datos (entero, cadena, decimal, etc.) y si permiten valores nulos
```scala
netflixdf.printSchema()
```
✔️Resultado
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
✔️Resultado
```scala
val res2: Array[org.apache.spark.sql.Row] = Array([2011-10-24,119.100002,120.28000300000001,115.100004,118.839996,120460200,16.977142], [2011-10-25,74.899999,79.390001,74.249997,77.370002,315541800,11.052857000000001], [2011-10-26,78.73,81.420001,75.399997,79.400002,148733900,11.342857], [2011-10-27,82.179998,82.71999699999999,79.249998,80.86000200000001,71190000,11.551428999999999], [2011-10-28,80.280002,84.660002,79.599999,84.14000300000001,57769600,12.02])
```
### 6. Usa el método describe () para aprender sobre el DataFrame.
```scala

```

### 7. Crea un nuevo dataframe con una columna nueva llamada “HV Ratio” que es la relación que existe entre el precio de la columna “High” frente a la columna “Volumen” de acciones negociadas por un día. Hint - es una operación
```scala

```

### 8. ¿Qué día tuvo el pico más alto en la columna “Open”?
```scala

```

### 9. ¿Cuál es el significado de la columna Cerrar “Close” en el contexto de información financiera, explíquelo no hay que codificar nada?
```scala

```

### 10. ¿Cuál es el máximo y mínimo de la columna “Volumen”?
```scala

```

### 11. Con Sintaxis Scala/Spark $ conteste lo siguiente:
##### a) ¿Cuántos días fue la columna “Close” inferior a $ 600?
```scala

```

##### ¿Qué porcentaje del tiempo fue la columna “High” mayor que $ 500?
```scala

```
##### c) ¿Cuál es la correlación de Pearson entre columna “High” y la columna “Volumen”?
```scala

```
##### d) ¿Cuál es el máximo de la columna “High” por año?
```scala

```
##### e) ¿Cuál es el promedio de la columna “Close” para cada mes del calendario?
```scala

```