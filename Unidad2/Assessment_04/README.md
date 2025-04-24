# Practica_Random Forest

### 1. Estas líneas se usan para importar las librerias necesarias para construir, entrenar, transformar y evaluar un modelo de clasificación  Random Forest en Spark.
```
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.{RandomForestClassificationModel, RandomForestClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}
```
Ejecución:
```
scala> import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.Pipeline

scala> import org.apache.spark.ml.classification.{RandomForestClassificationModel, RandomForestClassifier}
import org.apache.spark.ml.classification.{RandomForestClassificationModel, RandomForestClassifier}

scala> import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

scala> import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}
```

### 2. Esta línea se usa para crear y manejar la sesión principal de Spark.
```
 import org.apache.spark.sql.SparkSession
```
Ejecución:
```
scala> import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SparkSession
```
### 3. Esta línea se usa para cargar los datos de entrenamiento en formato LIBSVM como un DataFrame.
```
 val data = spark.read.format("libsvm").load("C:/Users/kathe/Documents/BigData/Practicas_unidad2/RandomForest/sample_libsvm_data.txt")
```
Ejecución:
```
scala> val data = spark.read.format("libsvm").load("C:/Users/kathe/Documents/BigData/Practicas_unidad2/RandomForest/sample_libsvm_data.txt")
25/04/02 19:13:53 WARN LibSVMFileFormat: 'numFeatures' option not specified, determining the number of features by going though the input. If you know the number in advance, please specify it via 'numFeatures' option to avoid the extra scan.
```
### 4. Esta línea se usa para convertir las etiquetas (clases) de texto o numéricas en índices numéricos consecutivos para que puedan ser entendidas por los algoritmos de machine learning (categorías).
```
 val labelIndexer = new StringIndexer()
      .setInputCol("label")
      .setOutputCol("indexedLabel")
      .fit(data)
```
Ejecución:
```
scala>     val labelIndexer = new StringIndexer()
val labelIndexer: org.apache.spark.ml.feature.StringIndexer = strIdx_a6b11a82e429

scala>       .setInputCol("label")
val res0: labelIndexer.type = strIdx_a6b11a82e429

scala>       .setOutputCol("indexedLabel")
val res1: res0.type = strIdx_a6b11a82e429

scala>       .fit(data)
val res2: org.apache.spark.ml.feature.StringIndexerModel = StringIndexerModel: uid=strIdx_a6b11a82e429, handleInvalid=error

```
### 5. Esta línea se usa para identificar automáticamente qué columnas dentro del vector de entrada son categóricas y convertirlas en números que el modelo pueda entender.
```
 val featureIndexer = new VectorIndexer()
      .setInputCol("features")
      .setOutputCol("indexedFeatures")
      .setMaxCategories(4)
      .setHandleInvalid("skip")
      .fit(data)
```
Ejecución:
```

scala>     val featureIndexer = new VectorIndexer()
val featureIndexer: org.apache.spark.ml.feature.VectorIndexer = vecIdx_6e52e7bc2ed0

scala>       .setInputCol("features")
val res3: featureIndexer.type = vecIdx_6e52e7bc2ed0

scala>       .setOutputCol("indexedFeatures")
val res4: res3.type = vecIdx_6e52e7bc2ed0

scala>       .setMaxCategories(4)
val res5: res4.type = vecIdx_6e52e7bc2ed0

scala>       .setHandleInvalid("skip")
val res6: res5.type = vecIdx_6e52e7bc2ed0

scala>       .fit(data)
val res7: org.apache.spark.ml.feature.VectorIndexerModel = VectorIndexerModel: uid=vecIdx_6e52e7bc2ed0, numFeatures=692, handleInvalid=skip

```
### 6. Esta línea se usa para dividir los datos en dos conjuntos: 70% entrenamiento y 30% de prueba.
```
 val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))
```
Ejecución:
```
scala> val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))
val trainingData: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row] = [label: double, features: vector]
val testData: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row] = [label: double, features: vector]
```
### 7. Estas lineas se usan para crear un clasificador basado en el algoritmo de Random Forest, definir la columna que contiene los valores a predicir, la columna que tiene las caracteristicas y definir el número de arboles a utilizar
```
val rf = new RandomForestClassifier()
.setLabelCol("indexedLabel")
.setFeaturesCol("indexedFeatures")
.setNumTrees(10)
```
Ejecución:
```
scala>     val rf = new RandomForestClassifier()
val rf: org.apache.spark.ml.classification.RandomForestClassifier = rfc_a3d068ca1578

scala>       .setLabelCol("indexedLabel")
val res8: org.apache.spark.ml.classification.RandomForestClassifier = rfc_a3d068ca1578

scala>       .setFeaturesCol("indexedFeatures")
val res9: org.apache.spark.ml.classification.RandomForestClassifier = rfc_a3d068ca1578

scala>       .setNumTrees(10)
val res10: res9.type = rfc_a3d068ca1578

```

### 8. Las siguientes línes se usan para tomar la columna de prediction del modelo para crear una columna con las etiquetas originales y convertirlas en un array con los valores legibles
```
 val labelConverter = new IndexToString()
.setInputCol("prediction")
.setOutputCol("predictedLabel")
.setLabels(res2.labelsArray(0))
```
Ejecución:
```
scala>     val labelConverter = new IndexToString()
val labelConverter: org.apache.spark.ml.feature.IndexToString = idxToStr_1f265b604ce8

scala>       .setInputCol("prediction")
val res11: labelConverter.type = idxToStr_1f265b604ce8

scala>       .setOutputCol("predictedLabel")
val res12: res11.type = idxToStr_1f265b604ce8

scala>       .setLabels(res2.labelsArray(0))
val res13: res12.type = idxToStr_1f265b604ce8

```
### 9. Esta línea se usa para crear una pipeline para poder llevar las etapas de procesamiento y modelo del clasificador
```
val pipeline = new Pipeline()
.setStages(Array(res2, featureIndexer, rf, labelConverter))
```
Ejecución:
```

scala> val pipeline = new Pipeline()
val pipeline: org.apache.spark.ml.Pipeline = pipeline_82cdf2d9c7d4

scala>       .setStages(Array(res2, featureIndexer, rf, labelConverter))
val res14: pipeline.type = pipeline_82cdf2d9c7d4
```
### 10. Esta línea se usa para entrenar el modelo 
```
val model = pipeline.fit(trainingData)
```
Ejecución:
```

scala>  val model = pipeline.fit(trainingData)
val model: org.apache.spark.ml.PipelineModel = pipeline_82cdf2d9c7d4
```
### 11. Esta línea se usa para generar predicciones con el modelo entrenado
```
val predictions = model.transform(testData)

```
Ejecución:
```
scala> val predictions = model.transform(testData)
val predictions: org.apache.spark.sql.DataFrame = [label: double, features: vector ... 6 more fields]
```
### 12. Esta línea se usa para mostrar las predicciones del modelo junto con los datos reales (Caracteristicas) 
```
val predictions = model.transform(testData)
predictions.select("predictedLabel", "label", "features").show(5)
```
Ejecución:
```
scala> val predictions = model.transform(testData)
val predictions: org.apache.spark.sql.DataFrame = [label: double, features: vector ... 6 more fields]

scala> predictions.select("predictedLabel", "label", "features").show(5)
+--------------+-----+--------------------+
|predictedLabel|label|            features|
+--------------+-----+--------------------+
|           0.0|  0.0|(692,[123,124,125...|
|           0.0|  0.0|(692,[126,127,128...|
|           0.0|  0.0|(692,[126,127,128...|
|           1.0|  1.0|(692,[123,124,125...|
|           1.0|  1.0|(692,[124,125,126...|
+--------------+-----+--------------------+
only showing top 5 rows
```
### 13. Estas líneas se usan para evaluar al modelo de clasificacion, basado en sus metricas de precisión
```
val evaluator = new MulticlassClassificationEvaluator()
.setLabelCol("indexedLabel")
.setPredictionCol("prediction")
.setMetricName("accuracy")
val accuracy = evaluator.evaluate(predictions)
println(s"Test Error = ${(1.0 - accuracy)}")
```
Ejecución:
```

scala> val evaluator = new MulticlassClassificationEvaluator()
val evaluator: org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator = MulticlassClassificationEvaluator: uid=mcEval_f68e59be8e4b, metricName=f1, metricLabel=0.0, beta=1.0, eps=1.0E-15

scala>       .setLabelCol("indexedLabel")
val res16: evaluator.type = MulticlassClassificationEvaluator: uid=mcEval_f68e59be8e4b, metricName=f1, metricLabel=0.0, beta=1.0, eps=1.0E-15

scala>       .setPredictionCol("prediction")
val res17: res16.type = MulticlassClassificationEvaluator: uid=mcEval_f68e59be8e4b, metricName=f1, metricLabel=0.0, beta=1.0, eps=1.0E-15

scala>       .setMetricName("accuracy")
val res18: res17.type = MulticlassClassificationEvaluator: uid=mcEval_f68e59be8e4b, metricName=accuracy, metricLabel=0.0, beta=1.0, eps=1.0E-15

scala>     val accuracy = evaluator.evaluate(predictions)
val accuracy: Double = 1.0

scala>     println(s"Test Error = ${(1.0 - accuracy)}")
Test Error = 0.0
```
### 14. Estas líneas se utilizan para mostrar los arboles del modelo entrenado 
```
val rfModel = model.stages(2).asInstanceOf[RandomForestClassificationModel]
println(s"Learned classification forest model:\n ${rfModel.toDebugString}")
```
Ejecución:
```

scala>  val rfModel = model.stages(2).asInstanceOf[RandomForestClassificationModel]
val rfModel: org.apache.spark.ml.classification.RandomForestClassificationModel = RandomForestClassificationModel: uid=rfc_a3d068ca1578, numTrees=10, numClasses=2, numFeatures=692

scala>     println(s"Learned classification forest model:\n ${rfModel.toDebugString}")
Learned classification forest model:
 RandomForestClassificationModel: uid=rfc_a3d068ca1578, numTrees=10, numClasses=2, numFeatures=692
  Tree 0 (weight 1.0):
    If (feature 455 <= 13.0)
     If (feature 490 <= 27.5)
      Predict: 1.0
     Else (feature 490 > 27.5)
      Predict: 0.0
    Else (feature 455 > 13.0)
     Predict: 1.0
  Tree 1 (weight 1.0):
    If (feature 604 <= 118.5)
     If (feature 581 <= 6.0)
      Predict: 0.0
     Else (feature 581 > 6.0)
      Predict: 1.0
    Else (feature 604 > 118.5)
     If (feature 271 <= 9.5)
      Predict: 0.0
     Else (feature 271 > 9.5)
      If (feature 343 <= 253.5)
       Predict: 1.0
      Else (feature 343 > 253.5)
       Predict: 0.0
  Tree 2 (weight 1.0):
    If (feature 414 <= 8.5)
     If (feature 485 <= 33.0)
      Predict: 0.0
     Else (feature 485 > 33.0)
      Predict: 1.0
    Else (feature 414 > 8.5)
     Predict: 1.0
  Tree 3 (weight 1.0):
    If (feature 469 <= 4.0)
     If (feature 607 <= 8.5)
      If (feature 467 <= 36.5)
       Predict: 0.0
      Else (feature 467 > 36.5)
       Predict: 1.0
     Else (feature 607 > 8.5)
      Predict: 1.0
    Else (feature 469 > 4.0)
     Predict: 1.0
  Tree 4 (weight 1.0):
    If (feature 433 <= 70.5)
     If (feature 489 <= 1.5)
      Predict: 1.0
     Else (feature 489 > 1.5)
      Predict: 0.0
    Else (feature 433 > 70.5)
     Predict: 0.0
  Tree 5 (weight 1.0):
    If (feature 378 <= 126.0)
     If (feature 650 in {1.0})
      Predict: 0.0
     Else (feature 650 not in {1.0})
      Predict: 1.0
    Else (feature 378 > 126.0)
     Predict: 0.0
  Tree 6 (weight 1.0):
    If (feature 379 <= 11.5)
     If (feature 406 <= 10.0)
      Predict: 1.0
     Else (feature 406 > 10.0)
      Predict: 0.0
    Else (feature 379 > 11.5)
     Predict: 0.0
  Tree 7 (weight 1.0):
    If (feature 518 <= 28.5)
     If (feature 432 <= 56.0)
      Predict: 1.0
     Else (feature 432 > 56.0)
      If (feature 600 <= 175.0)
       Predict: 0.0
      Else (feature 600 > 175.0)
       Predict: 1.0
    Else (feature 518 > 28.5)
     Predict: 0.0
  Tree 8 (weight 1.0):
    If (feature 272 <= 3.0)
     If (feature 578 <= 9.0)
      Predict: 0.0
     Else (feature 578 > 9.0)
      Predict: 1.0
    Else (feature 272 > 3.0)
     Predict: 1.0
  Tree 9 (weight 1.0):
    If (feature 549 <= 5.5)
     If (feature 545 <= 1.0)
      If (feature 381 <= 2.0)
       Predict: 1.0
      Else (feature 381 > 2.0)
       Predict: 0.0
     Else (feature 545 > 1.0)
      Predict: 0.0
    Else (feature 549 > 5.5)
     If (feature 518 <= 28.5)
      Predict: 1.0
     Else (feature 518 > 28.5)
      Predict: 0.0
```
