# Práctica Evaluatoria
# Desarrollar las siguientes instrucciones en Spark con el lenguaje de programación Scala, utilizando solo la documentación de la librería de Machine Learning Mllib de Spark y Google.

### Importar paquetes
```scala
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, VectorAssembler}
```
✅ Resultado
```scala
scala> import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SparkSession

scala> import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.DataFrame

scala> import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier

scala> import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

scala> import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, VectorAssembler}
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, VectorAssembler}
```

### Iniciar la sesión de Spark
```scala
val spark = SparkSession.builder().getOrCreate()
```
✅ Resultado
```scala
spark: org.apache.spark.sql.SparkSession = org.apache.spark.sql.SparkSession@8c84fb8
```

### Carga del dataframe
```scala
val irisdf = spark.read.option("header","true").option("inferSchema","true").csv("iris.csv")
```
✅ Resultado
```scala
```
###
```scala

```
✅ Resultado
```scala
```
###
```scala

```
✅ Resultado
```scala
```
###
```scala

```
✅ Resultado
```scala
```
###
```scala

```
✅ Resultado
```scala
```
###
```scala

```
✅ Resultado
```scala
```

println(s"******** Carga del archivo a un dataframe ********")
//Cargue el archivo Iris  CSV en dataframe llamado df, haga que Spark, infiera los tipos de datos.
val irisdf = spark.read.option("header","true").option("inferSchema","true").csv("iris.csv")
// val irisdf: org.apache.spark.sql.DataFrame = [sepal_length: double, sepal_width: double ... 3 more fields]

//elaborar la limpieza de datos necesaria paraser procesado por el siguiente algoritmo
println(s"********Eliminar duplicados y vacíos ********")
val irisdfClean = irisdf.na.drop().dropDuplicates()

// 2. ¿Cuáles son los nombres de las columnas?
println(s"******** Columnas ********")
irisdfClean.columns
// val res4: Array[String] = Array(sepal_length, sepal_width, petal_length, petal_width, species)

// 3. ¿Cómo es el esquema?
println(s"******** Esquema ********")
irisdfClean.printSchema()
// root
// |-- sepal_length: double (nullable = true)
// |-- sepal_width: double (nullable = true)
// |-- petal_length: double (nullable = true)
// |-- petal_width: double (nullable = true)
// |-- species: string (nullable = true)

// 4. Imprime las primeras 5 renglones.
println(s"******** Primeras 5 columnas ********")
irisdfClean.head(5)
// val res6: Array[org.apache.spark.sql.Row] = Array([5.1,3.5,1.4,0.2,setosa], [4.9,3.0,1.4,0.2,setosa], [4.7,3.2,1.3,0.2,setosa], [4.6,3.1,1.5,0.2,setosa], [5.0,3.6,1.4,0.2,setosa])


// 5. Usa el método describe () para aprender sobre el DataFrame.
// Con el metodo describe.show() muestar los detalles sobre el DataFrame, que en este caso seria la variable declarada "irisdf"
println(s"******** Detalles estadísticos del dataframe ********")
irisdfClean.describe().show()

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

// 6. Haga la transformación pertinente para los datos categóricos los cuales serán nuestras etiquetas a clasificar.
println(s"******** Transformación del datafame ********")
val indexer = new StringIndexer().setInputCol("species").setOutputCol("label").fit(irisdfClean)
    
val irisdfIndexed = indexer.transform(irisdfClean)

// Ensamblar las columnas de características en un solo vector
val assembler = new VectorAssembler().setInputCols(Array("sepal_length", "sepal_width", "petal_length", "petal_width")).setOutputCol("features")
val irisFinal = assembler.transform(irisdfIndexed).select("features", "label")



// 7. Construya el modelo de clasificación y explique su arquitectura.
// separacion de datos para entrenamiendo
println(s"******** Construcción del modelo y entrenamiento ********")
val splits = irisFinal.randomSplit(Array(0.7, 0.3), seed = 1234L)
val train = splits(0)
val test = splits(1)

// definición de capas
val layers = Array[Int](4, 5, 4, 3)

// definción de entrenador
val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)

//entrenar
val model = trainer.fit(train)

val result = model.transform(test)
val predictionAndLabels = result.select("prediction", "label")
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
println(s"******** Test set accuracy = ${evaluator.evaluate(predictionAndLabels)} ********")