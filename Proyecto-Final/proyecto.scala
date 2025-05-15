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
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame
//import scala.util.Random

// Import del multilayer perceptron
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, VectorAssembler, OneHotEncoder}
import org.apache.spark.ml.{Pipeline, PipelineStage}
import scala.collection.mutable.ListBuffer

// Import del regression


// Funciones
def randombetween(min: Int, max: Int): Int =
  min + Random.nextInt(max - min)

// GLOBALES
val iteraciones = 30
val nodosMin = 2
val nodosMax = 5
val capasMin = 0
val capasMax = 5

// Se inicial la sesión
val spark = SparkSession.builder().getOrCreate()

//Se genera el dataframe
println(s"******** Carga del archivo a un dataframe ********")
val bankDF = spark.read.option("header","true").option("inferSchema","true").option("delimiter", ";").csv("bank-full.csv")

println(s"********Eliminar duplicados y vacíos ********")
val bank = bankDF.na.drop().dropDuplicates()
bankDF.show()

println(s"******** Transformación del datafame ********")

import org.apache.spark.ml.Pipeline
val indexer = new StringIndexer().setInputCol("y").setOutputCol("label").fit(bank)

val indexers = Array(
  indexer,
  jobIndexer,   maritalIndexer,   educationIndexer,
  defaultIndexer, housingIndexer, loanIndexer,
  contactIndexer, monthIndexer,   poutcomeIndexer
)

val encoders = Array(
  jobEncoder,   maritalEncoder,   educationEncoder,
  defaultEncoder, housingEncoder, loanEncoder,
  contactEncoder, monthEncoder,   poutcomeEncoder
)

// assemble features at the end
val stages: Array[PipelineStage] = indexers ++ encoders ++ Array(assembler)
val pipeline = new Pipeline().setStages(stages)

val pipelineModel = pipeline.fit(bank)
val bankFinal     = pipelineModel.transform(bank).select("features","label")

// // 1) Create unfitted StringIndexers
// val indexers: Array[PipelineStage] = Array(
//     new StringIndexer().setInputCol("y").setOutputCol("label"),
//     new StringIndexer().setInputCol("job").setOutputCol("jobIndex"),
//     new StringIndexer().setInputCol("marital").setOutputCol("maritalIndex"),
//     new StringIndexer().setInputCol("education").setOutputCol("educationIndex"),
//     new StringIndexer().setInputCol("default").setOutputCol("defaultIndex"),
//     new StringIndexer().setInputCol("housing").setOutputCol("housingIndex"),
//     new StringIndexer().setInputCol("loan").setOutputCol("loanIndex"),
//     new StringIndexer().setInputCol("contact").setOutputCol("contactIndex"),
//     new StringIndexer().setInputCol("month").setOutputCol("monthIndex"),
//     new StringIndexer().setInputCol("poutcome").setOutputCol("poutcomeIndex")
// )

// // 2) Create OneHotEncoders (still unfitted)
// val encoders: Array[PipelineStage] = Array(
//     new OneHotEncoder().setInputCol("jobIndex").setOutputCol("jobVec"),
//     new OneHotEncoder().setInputCol("maritalIndex").setOutputCol("maritalVec"),
//     new OneHotEncoder().setInputCol("educationIndex").setOutputCol("educationVec"),
//     new OneHotEncoder().setInputCol("defaultIndex").setOutputCol("defaultVec"),
//     new OneHotEncoder().setInputCol("housingIndex").setOutputCol("housingnVec"),
//     new OneHotEncoder().setInputCol("loanIndex").setOutputCol("loanVec"),
//     new OneHotEncoder().setInputCol("contactIndex").setOutputCol("contactVec"),
//     new OneHotEncoder().setInputCol("monthIndex").setOutputCol("monthVec"),
//     new OneHotEncoder().setInputCol("poutcomeIndex").setOutputCol("poutcomeVec")
// )

// // 3) VectorAssembler to collect all the "*Vec" columns into "features"
// val assemblerStage = new VectorAssembler().setInputCols(Array( "jobVec","maritalVec","educationVec","defaultVec","housingnVec","loanVec","contactVec","monthVec","poutcomeVec")).setOutputCol("features")

// // 4) Build and run the pipeline
// val pipeline = new Pipeline().setStages(indexers ++ encoders ++ Array(assemblerStage))

// val pipelineModel = pipeline.fit(bank)
// val bankFinal     = pipelineModel.transform(bank).select("features","label")
// val indexer = new StringIndexer().setInputCol("y").setOutputCol("label").fit(bank)

// // Convertir strings a valores numericos - Transforming string into numerical values
// println(s"******** Convertir strings a valores numericos ********")
// val jobIndexer = new StringIndexer().setInputCol("job").setOutputCol("jobIndex").fit(bank)
// val maritalIndexer = new StringIndexer().setInputCol("marital").setOutputCol("maritalIndex").fit(bank)
// val educationIndexer = new StringIndexer().setInputCol("education").setOutputCol("educationIndex").fit(bank)
// val defaultIndexer = new StringIndexer().setInputCol("default").setOutputCol("defaultIndex").fit(bank)
// val housingIndexer = new StringIndexer().setInputCol("housing").setOutputCol("housingIndex").fit(bank)
// val loanIndexer = new StringIndexer().setInputCol("loan").setOutputCol("loanIndex").fit(bank)
// val contactIndexer = new StringIndexer().setInputCol("contact").setOutputCol("contactIndex").fit(bank)
// val monthIndexer = new StringIndexer().setInputCol("month").setOutputCol("monthIndex").fit(bank)
// val poutcomeIndexer = new StringIndexer().setInputCol("poutcome").setOutputCol("poutcomeIndex").fit(bank)

// // Convertir los valores numericos a One Hot Encoding 0 - 1
// println(s"******** One Hot Encoding ********")
// val jobEncoder = new OneHotEncoder().setInputCol("jobIndex").setOutputCol("jobVec")
// val maritalEncoder = new OneHotEncoder().setInputCol("maritalIndex").setOutputCol("maritalVec")
// val educationEncoder = new OneHotEncoder().setInputCol("educationIndex").setOutputCol("educationVec")
// val defaultEncoder = new OneHotEncoder().setInputCol("defaultIndex").setOutputCol("defaultVec")
// val housingEncoder = new OneHotEncoder().setInputCol("housingIndex").setOutputCol("housingVec")
// val loanEncoder = new OneHotEncoder().setInputCol("loanIndex").setOutputCol("loanVec")
// val contactEncoder = new OneHotEncoder().setInputCol("contactIndex").setOutputCol("contactVec")
// val monthEncoder = new OneHotEncoder().setInputCol("monthIndex").setOutputCol("monthVec")
// val poutcomeEncoder = new OneHotEncoder().setInputCol("poutcomeIndex").setOutputCol("poutcomeVec")
// // (label, features)

// val bankIndexed = indexer.transform(bank)

// println(s"******** Transformación de los campos de texto ********")
// val bank1 = jobEncoder.transform(jobIndexer.transform(bankIndexed))
// val bank2 = maritalEncoder.transform(maritalIndexer.transform(bank1))
// val bank3 = educationEncoder.transform(educationIndexer.transform(bank2))
// val bank4 = defaultEncoder.transform(educationIndexer.transform(bank3))
// val bank5 = housingEncoder.transform(educationIndexer.transform(bank4))
// val bank6 = loanEncoder.transform(educationIndexer.transform(bank5))
// val bank7 = contactEncoder.transform(educationIndexer.transform(bank6))
// val bank8 = monthEncoder.transform(educationIndexer.transform(bank7))
// val bank9 = poutcomeEncoder.transform(educationIndexer.transform(bank8))

// //val assembler = new VectorAssembler().setInputCol(Array("Pclass", "SexVec", "Age", "SibSp", "Parch", "Fare", "EmbarkedVec")).setOutputCol("features")
// println(s"******** Vectorizar las columnas de inputs ********")
// val assembler = (new VectorAssembler().setInputCols(Array("jobVec","maritalVec", "educationVec","defaultVec","housingVec","loanVec","contactVec","monthVec","poutcomeVec")).setOutputCol("features"))

// //val assembler = new VectorAssembler().setInputCols(Array("age", "job", "marital", "education","default","balance","housing","loan","contact","day","month","duration","campaign","pdays","previous","poutcome")).setOutputCol("features")

// println(s"******** Avenger assemble ********")
// val bankFinal = assembler.transform(bank3).select("features","label")

//Se incia con el proceso iterativo para obtener datos
println(s"******** Se incia con el proceso iterativo para obtener datos ********")
val resultados = ListBuffer.empty[(Int,Double,Double)]
var multiAccuracy : Double = 0.0
var regAccuracy : Double = 0.0
//val r = new scala.util.Random
var randArray = new Array[Int](capasMax + 1)
var splits = bankFinal.randomSplit(Array(0.7, 0.3), seed = 1234L)
var train = splits(0)
var test = splits(1)
for( i <- 1 to iteraciones ){
    println("⌛️ Ejecutandose iteración:" + i)

//**************************Multilayer
    // definición de capas
    for (iArray <- capasMin to capasMax){
        println("CapasMin: "+ capasMin + " | CapasMax: " + capasMax)
        randArray(iArray) = randombetween(nodosMin, nodosMax)
        println(randArray)
        // agregar a array para ser usado en lso layers
    }

//var layers : Array[Int] = randArray
val layers = Array[Int](4, 5, 4, 3)

// definción de entrenador
var trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)

//entrenar
var model = trainer.fit(train)

var result = model.transform(test)
var predictionAndLabels = result.select("prediction", "label")
var evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
multiAccuracy = evaluator.evaluate(predictionAndLabels)


//**************************Regresion

    resultados += ((i, multiAccuracy, regAccuracy))

}

// Esto genera una lista
//https://stackoverflow.com/questions/59644404/appending-rows-to-a-dataframe
//val finalSeq: Seq[(Int,Double,Double)] = resultados.toList
//finalSeq.foreach(println)

println(s"******** Resultados de las ejecuciones ********")
val resultadosDF = spark.sparkContext.parallelize(resultados).toDF("Ejecución","Multilayer Perceptron Accuracy","Logistics Regression")
resultadosDF.show()

// +---------+------------------------------+--------------------+
// |Ejecución|Multilayer Perceptron Accuracy|Logistics Regression|
// +---------+------------------------------+--------------------+
// |        1|                          10.1|                10.1|
// |       19|                          10.1|                10.1|
// |       20|                          10.1|                10.1|
// +---------+------------------------------+--------------------+

println(s"******** Resumen estadístico de los resultados ********")
resultadosDF.describe().show()
// +-------+-----------------+------------------------------+--------------------+
// |summary|        Ejecución|Multilayer Perceptron Accuracy|Logistics Regression|
// +-------+-----------------+------------------------------+--------------------+
// |  count|               30|                            30|                  30|
// |   mean|             15.5|            10.100000000000001|  10.100000000000001|
// | stddev|8.803408430829505|                           0.0|                 0.0|
// |    min|                1|                          10.1|                10.1|
// |    max|               30|                          10.1|                10.1|
// +-------+-----------------+------------------------------+--------------------+

