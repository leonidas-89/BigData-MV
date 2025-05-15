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
import scala.util.Random

// Import del multilayer perceptron
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, VectorAssembler}

// Import del regression

// GLOBALES
val iteraciones = 30
val nodosMin = 3
val nodosMax = 5
val capasMin = 4
val capasMax = 5

// Se inicial la sesión
val spark = SparkSession.builder().getOrCreate()

//Se genera el dataframe
println(s"******** Carga del archivo a un dataframe ********")
val bankDF = spark.read.option("header","true").option("inferSchema","true").csv("bank-full.csv")

println(s"********Eliminar duplicados y vacíos ********")
val bank = bankDF.na.drop().dropDuplicates()

println(s"******** Transformación del datafame ********")
val indexer = new StringIndexer().setInputCol("y").setOutputCol("label").fit(bank)

// Etiquetas a clasificar
val bankIndexed = indexer.transform(bank)
// Ensamblar las columnas de características en un solo vector
val assembler = new VectorAssembler().setInputCols(Array("age", "job", "marital", "education","default","balance","housing","loan","contact","day","month","duration","campaign","pdays","previos","poutcom")).setOutputCol("features")
val bankFinal = assembler.transform(bankIndexed).select("features", "label")


//Se incia con el proceso iterativo para obtener datos
val resultados = ListBuffer.empty[(Int,Double,Double)]
val multiAccuracy : Double
val regAccuracy : Double
val rand: Int
val randArray = new Array
for( i <- 1 to iteraciones ){
    println("Ejecutandose iteración:" + i)

//**************************Multilayer
val splits = bankFinal.randomSplit(Array(0.7, 0.3), seed = 1234L)
val train = splits(0)
val test = splits(1)

// definición de capas
for (iArray <- capasMin to capasMax){
    randArray(iArray) = Random.between(nodoMin, nodoMax)
    // agregar a array para ser usado en lso layers
}

val layers : Array[Int] = randArray

// definción de entrenador
val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)

//entrenar
val model = trainer.fit(train)

val result = model.transform(test)
val predictionAndLabels = result.select("prediction", "label")
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
multiAccuracy = {evaluator.evaluate(predictionAndLabels)


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

