# Práctica Evaluatoria
# Desarrollar las siguientes instrucciones en Spark con el lenguaje de programación Scala, utilizando solo la documentación de la librería de Machine Learning Mllib de Spark y Google.


### 1. Cargar en un dataframe de la fuente de datos Iris.csv que se encuentra en https://github.com/jcromerohdz/iris, elaborar la limpieza de datos necesaria para ser procesado por el siguiente algoritmo (Importante, esta limpieza debe ser por medio de un script de Scala en Spark).
#### Utilice el algoritmo de Machine Learning Multilayer Perceptron Classifier de la librería Mllib de Spark
```scala
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, VectorAssembler}

val spark = SparkSession.builder().getOrCreate()

val irisdf = spark.read.option("header","true").option("inferSchema","true").csv("iris.csv")

println(s"********Eliminar duplicados y vacíos ********")
val irisdfClean = irisdf.na.drop().dropDuplicates()
```
✅ Resultado
```scala
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, VectorAssembler}
spark: org.apache.spark.sql.SparkSession = org.apache.spark.sql.SparkSession@8c84fb8
irisdf: org.apache.spark.sql.DataFrame = [sepal_length: double, sepal_width: double ... 3 more fields]

********Eliminar duplicados y vacíos ********
irisdfClean: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row] = [sepal_length: double, sepal_width: double ... 3 more fields]
```

### 2. ¿Cuáles son los nombres de las columnas?
```scala
println(s"******** Columnas ********")
irisdfClean.columns
```
✅ Resultado
```scala
******** Columnas ********
res124: Array[String] = Array(sepal_length, sepal_width, petal_length, petal_width, species)
```

### 3. ¿Cómo es el esquema?
```scala
println(s"******** Esquema ********")
irisdfClean.printSchema()
```
✅ Resultado
```scala
******** Esquema ********
root
 |-- sepal_length: double (nullable = true)
 |-- sepal_width: double (nullable = true)
 |-- petal_length: double (nullable = true)
 |-- petal_width: double (nullable = true)
 |-- species: string (nullable = true)
```

### 4. Imprime las primeras 5 renglones.
```scala
println(s"******** Primeras 5 columnas ********")
irisdfClean.head(5)
```
✅ Resultado
```scala
******** Primeras 5 columnas ********
res128: Array[org.apache.spark.sql.Row] = Array([5.1,3.5,1.4,0.3,setosa], [5.0,3.4,1.6,0.4,setosa], [4.4,3.2,1.3,0.2,setosa], [4.8,3.4,1.6,0.2,setosa], [5.0,3.3,1.4,0.2,setosa])
```

### 5. Usa el método describe () para aprender sobre el DataFrame.
```scala
println(s"******** Detalles estadísticos del dataframe ********")
irisdfClean.describe().show()
```
✅ Resultado
```scala
******** Detalles estadísticos del dataframe ********
+-------+------------------+-------------------+------------------+------------------+---------+
|summary|      sepal_length|        sepal_width|      petal_length|       petal_width|  species|
+-------+------------------+-------------------+------------------+------------------+---------+
|  count|               147|                147|               147|               147|      147|
|   mean|5.8564625850340155|   3.05578231292517| 3.780272108843537|1.2088435374149662|     NULL|
| stddev|0.8290998607345103|0.43700870680343545|1.7591108999509795|0.7578742052400403|     NULL|
|    min|               4.3|                2.0|               1.0|               0.1|   setosa|
|    max|               7.9|                4.4|               6.9|               2.5|virginica|
+-------+------------------+-------------------+------------------+------------------+---------+
```

### 6. Haga la transformación pertinente para los datos categóricos los cuales serán nuestras etiquetas a clasificar.
```scala
println(s"******** Transformación del datafame ********")
val indexer = new StringIndexer().setInputCol("species").setOutputCol("label").fit(irisdfClean)
val irisdfIndexed = indexer.transform(irisdfClean)
val assembler = new VectorAssembler().setInputCols(Array("sepal_length", "sepal_width", "petal_length", "petal_width")).setOutputCol("features")
val irisFinal = assembler.transform(irisdfIndexed).select("features", "label")
```
✅ Resultado
```scala
******** Transformación del datafame ********
indexer: org.apache.spark.ml.feature.StringIndexerModel = StringIndexerModel: uid=strIdx_201de1346870, handleInvalid=error
irisdfIndexed: org.apache.spark.sql.DataFrame = [sepal_length: double, sepal_width: double ... 4 more fields]
assembler: org.apache.spark.ml.feature.VectorAssembler = VectorAssembler: uid=vecAssembler_ec77bdf87b02, handleInvalid=error, numInputCols=4
irisFinal: org.apache.spark.sql.DataFrame = [features: vector, label: double]
```

### 7. Construya el modelo de clasificación y explique su arquitectura.
```scala
println(s"******** Construcción del modelo y entrenamiento ********")
val splits = irisFinal.randomSplit(Array(0.7, 0.3), seed = 1234L)
val train = splits(0)
val test = splits(1)
val layers = Array[Int](4, 5, 4, 3)
val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)
val model = trainer.fit(train)
val result = model.transform(test)
val predictionAndLabels = result.select("prediction", "label")
```
✅ Resultado
```scala
******** Construcción del modelo y entrenamiento ********
splits: Array[org.apache.spark.sql.Dataset[org.apache.spark.sql.Row]] = Array([features: vector, label: double], [features: vector, label: double])
train: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row] = [features: vector, label: double]
test: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row] = [features: vector, label: double]
layers: Array[Int] = Array(4, 5, 4, 3)
trainer: org.apache.spark.ml.classification.MultilayerPerceptronClassifier = mlpc_cc216b44b362
model: org.apache.spark.ml.classification.MultilayerPerceptronClassificationModel = MultilayerPerceptronClassificationModel: uid=mlpc_cc216b44b362, numLayers=4, numClasses=3, numFeatures=4
result: org.apache.spark.sql.DataFrame = [features: vector, label: double ... 3 more fields]
predictionAndLabels: org.apache.spark.sql.DataFrame = [prediction: double, label: double]
```

### 8. Imprima los resultados del modelo y de sus observaciones.
```scala
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
println(s"******** Test set accuracy = ${evaluator.evaluate(predictionAndLabels)} ********")
```
✅ Resultado
```scala
evaluator: org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator = MulticlassClassificationEvaluator: uid=mcEval_1ff81fa15bae, metricName=accuracy, metricLabel=0.0, beta=1.0, eps=1.0E-15
******** Test set accuracy = 0.96 ********
```