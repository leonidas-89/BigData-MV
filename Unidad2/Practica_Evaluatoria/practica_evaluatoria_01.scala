// 1. Comienza una simple sesión Spark.
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()
// val spark: org.apache.spark.sql.SparkSession = org.apache.spark.sql.SparkSession@72a48403

//Cargue el archivo Iris  CSV en dataframe llamado df, haga que Spark, infiera los tipos de datos.
val irisdf = spark.read.option("header","true").option("inferSchema","true").csv("iris.csv")
// val irisdf: org.apache.spark.sql.DataFrame = [sepal_length: double, sepal_width: double ... 3 more fields]

//elaborar la limpieza de datos necesaria paraser procesado por el siguiente algoritmo
//PENDIENTE--------------------------------

// 2. ¿Cuáles son los nombres de las columnas?
irisdf.columns
// val res4: Array[String] = Array(sepal_length, sepal_width, petal_length, petal_width, species)

// 3. ¿Cómo es el esquema?
irisdf.printSchema()
// root
// |-- sepal_length: double (nullable = true)
// |-- sepal_width: double (nullable = true)
// |-- petal_length: double (nullable = true)
// |-- petal_width: double (nullable = true)
// |-- species: string (nullable = true)

// 4. Imprime las primeras 5 renglones.
irisdf.head(5)
// val res6: Array[org.apache.spark.sql.Row] = Array([5.1,3.5,1.4,0.2,setosa], [4.9,3.0,1.4,0.2,setosa], [4.7,3.2,1.3,0.2,setosa], [4.6,3.1,1.5,0.2,setosa], [5.0,3.6,1.4,0.2,setosa])

// 5. Usa el método describe () para aprender sobre el DataFrame.
// Con el metodo describe.show() muestar los detalles sobre el DataFrame, que en este caso seria la variable declarada "irisdf"
irisdf.describe().show()

// En la descripción de los detalles del DataFrame de Iris, podemos visualizar un resumen estadístico descriptivo sobre las columnas
// seleccionadas, incluyendo el total de valores no nulos de la columna, promedio de los valores, desviación estándar de los valores de
// la columna, valor mínimo y máximo. Se aplica .show() para mostrarlo en la consola como se muestra a continuación:

//+-------+------------------+-------------------+------------------+------------------+---------+
//|summary|      sepal_length|        sepal_width|      petal_length|       petal_width|  species|
//+-------+------------------+-------------------+------------------+------------------+---------+
//|  count|               150|                150|               150|               150|      150|
//|   mean| 5.843333333333335| 3.0540000000000007|3.7586666666666693|1.1986666666666672|     null|
//| stddev|0.8280661279778637|0.43359431136217375| 1.764420419952262|0.7631607417008414|     null|
//|    min|               4.3|                2.0|               1.0|               0.1|   setosa|
//|    max|               7.9|                4.4|               6.9|               2.5|virginica|
//+-------+------------------+-------------------+------------------+------------------+---------+