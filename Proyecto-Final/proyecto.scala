//Contenido del proyecto
//1.- Objectivo: Comparación del rendimiento de los siguientes algoritmos de machine learning
// - SVM
// - Decision Three
// - Logistic Regresion
// - Multilayer perceptron
//Con el siguiente data set: https://archive.ics.uci.edu/ml/datasets/Bank+Marketing

// Contenido del documento de proyecto final
// 1. Portada
// 2. Indice
// 3. Introduccion
// 4. Marco teorico de los algoritmos
// 5. Implementación (Que herramientas usaron y porque (en este caso spark con scala))
// 6. Resultados (Un tabular con los datos de 30 corridas por cada algoritmo y hacer un promedio 
//                para ver su preformance)
//    y su respectiva explicacion.
// 7. Conclusiones
// 8. Referencias (No wikipedia por ningun motivo, traten que sean de articulos cientificos)
//    El documento debe estar referenciado 

// Nota: si el documento no es presentado , no revisare su desarrollo del proyecto


// Import
println(" ")
println(s"******** Importando librerias ********")
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame
import scala.collection.mutable.ListBuffer
import scala.util.Random
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat 
//import scala.util.Random

// Import del multilayer perceptron
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
//import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, VectorAssembler, OneHotEncoder}
//import org.apache.spark.ml.{Pipeline, PipelineStage}
//import scala.collection.mutable.ListBuffer

// Import del regression
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer, VectorIndexer, OneHotEncoder}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.Pipeline
import org.apache.spark.mllib.evaluation.MulticlassMetrics

// Funciones
println(" ")
print("******** Definiendo funciones globales ")
def randombetween(min: Int, max: Int): Int =
  min + Random.nextInt(max - min)

// GLOBALES
println(" ")
print("******** Favor de ingresar la cantidad de iteraciones a realizar: ")
println("")

val iteraciones = scala.io.StdIn.readInt()
println(s"Se realizarán "+ iteraciones + " iteraciones")
println("")

val nodosMin = 2
val nodosMax = 5
val capasMin = 0
val capasMax = 5

// Se inicial la sesión
val spark = SparkSession.builder().getOrCreate()

//Se genera el dataframe
println(" ")
println(s"******** Carga del archivo a un dataframe ********")
val bankDF = spark.read.option("header","true").option("inferSchema","true").option("delimiter", ";").csv("bank-full.csv")

println(" ")
println(s"********Eliminar duplicados y vacíos ********")
val bank = bankDF.na.drop().dropDuplicates()

// Pasos para la regresion logistica
val banklogreg2 = (bank.select(bank("y").as("label"), $"age", $"job", $"marital", $"education", $"default", $"balance", $"housing", $"loan", $"contact", $"day", $"month", $"duration", $"campaign", $"pdays", $"previous",$"poutcome"))

// Se converite el label a binario
println(" ")
println(s"********Conversión a binario del label ********")
val banklogreg = banklogreg2.withColumn("label", when(col("label") === "yes", 1).otherwise(0))
banklogreg.show()

// Conversion de strings a valores numericos
println(" ")
println(s"******** Vectorización ********")
val jobIndexer = new StringIndexer().setInputCol("job").setOutputCol("jobIndex")
val maritalIndexer = new StringIndexer().setInputCol("marital").setOutputCol("maritalIndex")
val educationIndexer = new StringIndexer().setInputCol("education").setOutputCol("educationIndex")
val defaultIndexer = new StringIndexer().setInputCol("default").setOutputCol("defaultIndex")
val housingIndexer = new StringIndexer().setInputCol("housing").setOutputCol("housingIndex")
val loanIndexer = new StringIndexer().setInputCol("loan").setOutputCol("loanIndex")
val contactIndexer = new StringIndexer().setInputCol("contact").setOutputCol("contactIndex")
val monthIndexer = new StringIndexer().setInputCol("month").setOutputCol("monthIndex")
val poutcomeIndexer = new StringIndexer().setInputCol("poutcome").setOutputCol("poutcomeIndex")

// Convertir los valores numericos a One Hot Encoding 0 - 1
val jobEncoder = new OneHotEncoder().setInputCol("jobIndex").setOutputCol("jobVec")
val maritalEncoder = new OneHotEncoder().setInputCol("maritalIndex").setOutputCol("maritalVec")
val educationEncoder = new OneHotEncoder().setInputCol("educationIndex").setOutputCol("educationVec")
val defaultEncoder = new OneHotEncoder().setInputCol("defaultIndex").setOutputCol("defaultVec")
val housingEncoder = new OneHotEncoder().setInputCol("housingIndex").setOutputCol("housingVec")
val loanEncoder = new OneHotEncoder().setInputCol("loanIndex").setOutputCol("loanVec")
val contactEncoder = new OneHotEncoder().setInputCol("contactIndex").setOutputCol("contactVec")
val monthEncoder = new OneHotEncoder().setInputCol("monthIndex").setOutputCol("monthVec")
val poutcomeEncoder = new OneHotEncoder().setInputCol("poutcomeIndex").setOutputCol("poutcomeVec")


val assembler = (new VectorAssembler()
                  .setInputCols(Array("age","jobVec", "maritalVec","educationVec","defaultVec","balance","housingVec","loanVec","contactVec","day","monthVec","duration","campaign","pdays","previous","poutcomeVec")).setOutputCol("features"))

//val bankMLP = assembler.transform(irisdfIndexed).select("features", "label")
val pipelineTransform = new Pipeline().setStages(Array(
  jobIndexer, maritalIndexer, educationIndexer, defaultIndexer, housingIndexer,
  loanIndexer, contactIndexer, monthIndexer, poutcomeIndexer,
  jobEncoder, maritalEncoder, educationEncoder, defaultEncoder, housingEncoder,
  loanEncoder, contactEncoder, monthEncoder, poutcomeEncoder,
  assembler
))

val transformer = pipelineTransform.fit(banklogreg)
val bankMLP = transformer.transform(banklogreg).select("features", "label")
//Se incia con el proceso iterativo para obtener datos
println(" ")
println(s"******** Se incia con el proceso iterativo para comparar los métodos ********")

// se genera lista de resultados
val resultados = ListBuffer.empty[(Int,Int,Double,Double,Double,Long)]
//var randArray = new Array[Int](capasMax + 1)

for( i <- 1 to iteraciones ){
    println("⌛️ Ejecutandose iteración: " + i + "...")
    val randseed = randombetween(1000,1500)

//************************************
//**** Multilayer Perceptron *********
//************************************
  val procesoInicioMLP = System.currentTimeMillis()
  var splits = bankMLP.randomSplit(Array(0.7, 0.3), seed = randseed)
  var train = splits(0)
  var test = splits(1)

// definición de capas
  var layers = Array[Int](4, 5, 4, 3)
  var trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(randseed).setMaxIter(100)

//entrenar
  var model = trainer.fit(train)
  var result = model.transform(test)
  var predictionAndLabels = result.select("prediction", "label")
  var evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
  var accuracyMLP = evaluator.evaluate(predictionAndLabels)
  val procesoFinMLP = System.currentTimeMillis()
  val duracionMLP = procesoFinMLP - procesoInicioMLP

  println("✅ MultiLayer Perceptron")

//************************************
//*********** Regresion **************
//************************************
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

  println("✅ Regresion Lineal")
  println(" ")

    resultados += ((i, randseed, accuracyMLP, duracionMLP, accuracyREG, duracionREG))

}

// Esto genera una lista
//https://stackoverflow.com/questions/59644404/appending-rows-to-a-dataframe
//val finalSeq: Seq[(Int,Double,Double)] = resultados.toList
//finalSeq.foreach(println)

println(s"******** Resultados de las ejecuciones ********")
val resultadosDF = spark.sparkContext.parallelize(resultados).toDF("Iteración","Semilla","MLP Accuracy","MLP Duración (ms)","LR Accuracy", "LR Duración (ms)")
resultadosDF.show()

println(s"******** Resumen estadístico de los resultados ********")
resultadosDF.describe().show()
