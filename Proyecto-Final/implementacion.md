## Implementación
En esta implementacion se decidio utilizar las herramientas de Scala y Spark, debido a las siguientes razones:
### Spark
* Spark permite procesar grandes volúmenes de datos distribuyéndolos automáticamente en un clúster de máquinas y es mucho más rápido que MapReduce de Hadoop gracias a su uso de procesamiento en memoria (in-memory computing).
* Spark puede ser hasta 100 veces más rápido que Hadoop en ciertos casos, ya que minimiza las operaciones de lectura/escritura en disco al mantener los datos en RAM mientras sea posible.
* Con Spark puedes hacer desde procesamiento por lotes (batch) hasta procesamiento en tiempo real (streaming), machine learning (MLlib) y consultas SQL (Spark SQL), todo dentro de una sola plataforma unificada.
* Spark tiene APIs para Scala, Java, Python, R y SQL, lo que facilita su adopción en distintos entornos y equipos de desarrollo.
* Se integra fácilmente con herramientas como HDFS, Hive, Cassandra, HBase, Kafka, y más, lo que lo convierte en una solución poderosa para pipelines de datos complejos.
* Spark ha sido probado en producción en empresas como Netflix, Uber, y Alibaba, procesando petabytes de datos diariamente.

### Scala
* Scala permite escribir código con ambos paradigmas, esto significa que puedes usar clases y objetos como en Java, pero también aprovechar funciones puras, inmutabilidad y expresiones lambda, lo que facilita escribir código más conciso, reutilizable y menos propenso a errores.
* Scala se ejecuta en la JVM (Java Virtual Machine), lo que permite reutilizar bibliotecas existentes de Java sin problemas.
* Es el lenguaje nativo de frameworks como Apache Spark, lo que lo hace muy eficiente para trabajar con Big Data, pipelines de datos en tiempo real y procesamiento distribuido.
* Scala cuenta con un sistema de tipos muy potente que permite detectar errores en tiempo de compilación sin perder flexibilidad, gracias al uso de inferencia de tipos.
* La comunidad Scala es muy activa en el ámbito académico e industrial, especialmente en startups de tecnología y empresas que manejan grandes volúmenes de datos.

### Explicación de Código
Contenido del proyecto

Objectivo: Comparación del rendimiento de los siguientes algoritmos de machine learning:
> Logistic Regresion ✅

> Multilayer perceptron ✅

Data set: https://archive.ics.uci.edu/ml/datasets/Bank+Marketing

#
Se importan las librerias correspondientes:

```scala
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame
import scala.collection.mutable.ListBuffer
import scala.util.Random
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat 
```
Se importan las librerias del multilayer perceptron
```scala
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.linalg.Vector
```

Se importan las librerias de lineal regression
```scala
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer, VectorIndexer, OneHotEncoder}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.Pipeline
import org.apache.spark.mllib.evaluation.MulticlassMetrics
```

Definicion de funciones y variables globales:
```scala
def randombetween(min: Int, max: Int): Int = min + Random.nextInt(max - min)

val iteraciones = scala.io.StdIn.readInt()

val nodosMin = 2
val nodosMax = 5
val capasMin = 0
val capasMax = 5
```

Se inicial la sesión y el data frame:
```scala
val spark = SparkSession.builder().getOrCreate()
val bankDF = spark.read.option("header","true").option("inferSchema","true").option("delimiter", ";").csv("bank-full.csv")
val bank = bankDF.na.drop().dropDuplicates()
```

Pasos para la regresion logistica:
```scala
val banklogreg2 = (bank.select(bank("y").as("label"), $"age", $"job", $"marital", $"education", $"default", $"balance", $"housing", $"loan", $"contact", $"day", $"month", $"duration", $"campaign", $"pdays", $"previous",$"poutcome"))
```
Se converite el label a binario:
```scala
val banklogreg = banklogreg2.withColumn("label", when(col("label") === "yes", 1).otherwise(0))
banklogreg.show()
```

Conversion de strings a valores numericos:
```scala
val jobIndexer = new StringIndexer().setInputCol("job").setOutputCol("jobIndex")
val maritalIndexer = new StringIndexer().setInputCol("marital").setOutputCol("maritalIndex")
val educationIndexer = new StringIndexer().setInputCol("education").setOutputCol("educationIndex")
val defaultIndexer = new StringIndexer().setInputCol("default").setOutputCol("defaultIndex")
val housingIndexer = new StringIndexer().setInputCol("housing").setOutputCol("housingIndex")
val loanIndexer = new StringIndexer().setInputCol("loan").setOutputCol("loanIndex")
val contactIndexer = new StringIndexer().setInputCol("contact").setOutputCol("contactIndex")
val monthIndexer = new StringIndexer().setInputCol("month").setOutputCol("monthIndex")
val poutcomeIndexer = new StringIndexer().setInputCol("poutcome").setOutputCol("poutcomeIndex")
```

Convertir los valores numericos a One Hot Encoding 0 - 1:
```scala
val jobEncoder = new OneHotEncoder().setInputCol("jobIndex").setOutputCol("jobVec")
val maritalEncoder = new OneHotEncoder().setInputCol("maritalIndex").setOutputCol("maritalVec")
val educationEncoder = new OneHotEncoder().setInputCol("educationIndex").setOutputCol("educationVec")
val defaultEncoder = new OneHotEncoder().setInputCol("defaultIndex").setOutputCol("defaultVec")
val housingEncoder = new OneHotEncoder().setInputCol("housingIndex").setOutputCol("housingVec")
val loanEncoder = new OneHotEncoder().setInputCol("loanIndex").setOutputCol("loanVec")
val contactEncoder = new OneHotEncoder().setInputCol("contactIndex").setOutputCol("contactVec")
val monthEncoder = new OneHotEncoder().setInputCol("monthIndex").setOutputCol("monthVec")
val poutcomeEncoder = new OneHotEncoder().setInputCol("poutcomeIndex").setOutputCol("poutcomeVec")
```
Se ensamblan los vectores:
```scala
val assembler = (new VectorAssembler()
                  .setInputCols(Array("age","jobVec", "maritalVec","educationVec","defaultVec","balance","housingVec","loanVec","contactVec","day","monthVec","duration","campaign","pdays","previous","poutcomeVec")).setOutputCol("features"))
```
Se realiza la transformacion:
```scala
val pipelineTransform = new Pipeline().setStages(Array(
  jobIndexer, maritalIndexer, educationIndexer, defaultIndexer, housingIndexer,
  loanIndexer, contactIndexer, monthIndexer, poutcomeIndexer,
  jobEncoder, maritalEncoder, educationEncoder, defaultEncoder, housingEncoder,
  loanEncoder, contactEncoder, monthEncoder, poutcomeEncoder,
  assembler
))

val transformer = pipelineTransform.fit(banklogreg)
val bankMLP = transformer.transform(banklogreg).select("features", "label")
```

Se inicia con el proceso iterativo para obtener datos:
```scala
val resultados = ListBuffer.empty[(Int,Int,Double,Double,Double,Double)]
for( i <- 1 to iteraciones ){
    println("⌛️ Ejecutandose iteración: " + i + "...")
    val randseed = randombetween(1000,1500)
```
## -- MULTILAYER PERCEPTRON -- 
Declaracion de variables:
```scala
val procesoInicioMLP = System.currentTimeMillis()
  var splits = bankMLP.randomSplit(Array(0.7, 0.3), seed = randseed)
  var train = splits(0)
  var test = splits(1)
```
Definición de capas
```scala
  val firstRow = bankMLP.select("features").head()
  val firstFeatureVector = firstRow.getAs[Vector]("features")
  val inputSize = firstFeatureVector.size
  //println("inputsize: " + inputSize)
  val numClasses = bankMLP.select("label").distinct().count().toInt
  //println("numClasses: " + numClasses)
  var layers = Array[Int](inputSize, 5, 4, numClasses)
  var trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(randseed).setMaxIter(100)
```
Entrenamiento:
```scala
  var model = trainer.fit(train)
  var result = model.transform(test)
  var predictionAndLabels = result.select("prediction", "label")
  var evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
  var accuracyMLP = evaluator.evaluate(predictionAndLabels)
  val procesoFinMLP = System.currentTimeMillis()
  val duracionMLP = procesoFinMLP - procesoInicioMLP
```

## -- REGRESSION -- 
Declaracion de variables:
```scala
  val procesoInicioREG = System.currentTimeMillis()
  val Array(trainingREG, testREG) = banklogreg.randomSplit(Array(0.7, 0.3), seed = randseed)
  val lr = new LogisticRegression()
  val pipeline = new Pipeline().setStages(Array(jobIndexer,maritalIndexer,educationIndexer,defaultIndexer,housingIndexer,loanIndexer,contactIndexer,monthIndexer,poutcomeIndexer,jobEncoder,maritalEncoder,educationEncoder,defaultEncoder,housingEncoder,loanEncoder,contactEncoder,monthEncoder,poutcomeEncoder,assembler,lr))

  val modelREG = pipeline.fit(trainingREG)

  val resultsREG = modelREG.transform(testREG)
  val evaluatorREG = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("accuracy")
  val accuracyREG = evaluatorREG.evaluate(resultsREG)
  val procesoFinREG = System.currentTimeMillis()
  val duracionREG = procesoFinREG - procesoInicioREG

resultados += ((i, randseed, accuracyMLP, duracionMLP, accuracyREG, duracionREG))
```
## -- RESULTADOS -- 
```scala
Esto genera una lista: https://stackoverflow.com/questions/59644404/appending-rows-to-a-dataframe

val finalSeq: Seq[(Int,Double,Double)] = resultados.toList
finalSeq.foreach(println)
```
Resultados de las ejecuciones:
```scala
val resultadosDF = spark.sparkContext.parallelize(resultados).toDF("Iteracion","Semilla","MLP Accuracy","MLP Duracion (ms)","LR Accuracy", "LR Duracion (ms)")
resultadosDF.show(iteraciones)
```

Se muestra el resumen estadístico de los resultados:
```scala
resultadosDF.describe().show()
```

Se exportan los resultados correspondientes:
```scala
resultadosDF.coalesce(1).write.format("com.databricks.spark.csv").option("header", "true").option("encoding","UTF-8").mode("overwrite").save("resultados-iteraciones")
resultadosDF.describe().coalesce(1).write.format("com.databricks.spark.csv").option("header", "true").option("encoding","UTF-8").mode("overwrite").save("resultados-describe")
```