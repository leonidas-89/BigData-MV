/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Katherynne Plessmann
// Salomon Cruz Vidal

// scalastyle:off println
package org.apache.spark.examples.ml

// Import
import org.apache.spark.ml.Pipeline //Encadena pasos como transformaciones y modelos en un solo flujo de trabajo.
import org.apache.spark.ml.classification.{RandomForestClassificationModel, RandomForestClassifier} //Crea, entrena y analiza modelos de clasificación con Random Forest.
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator //Evalua el modelo usando métricas como precisión (accuracy) y F1-score.
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}
/*
StringIndexer: Convierte etiquetas de texto a números.
VectorIndexer: Detecta y codifica variables categóricas en vectores.
IndexToString: Convierte predicciones numéricas a etiquetas originales.
*/

// Iniciar la sesión de Spark
import org.apache.spark.sql.SparkSession

// En el spark-shell, no necesitamos usar el objeto ni el main(),  ejecutar línea por línea fuera del objeto.
object RandomForestClassifierExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("RandomForestClassifierExample")
      .getOrCreate()

    // Carga los datos de entrenamiento en formato LIBSVM como un DataFrame, listos para ser procesados por Spark MLlib.
    //MLlib ->  librería de aprendizaje automático de Apache Spark
    val data = spark.read.format("libsvm").load("C:/Users/kathe/Documents/BigData/Practicas_unidad2/RandomForest/sample_libsvm_data.txt")

    // Convierte las etiquetas (clases) de texto o numéricas en índices numéricos consecutivos para que 
    //puedan ser entendidas por los algoritmos de machine learning (categorías).
    val labelIndexer = new StringIndexer()
      .setInputCol("label")
      .setOutputCol("indexedLabel")
      .fit(data)

    // Permite identificar automáticamente qué columnas dentro del vector de entrada son categóricas y convertirlas en números que el modelo pueda entender.
    val featureIndexer = new VectorIndexer()
      .setInputCol("features")
      .setOutputCol("indexedFeatures")
      .setMaxCategories(4)
      .setHandleInvalid("skip")
      .fit(data)

    // Divide los datos en dos conjuntos: 70% entrenamiento y 30% de prueba.
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))

    // Entrena el modelo Random Forest
    val rf = new RandomForestClassifier()
      .setLabelCol("indexedLabel")
      .setFeaturesCol("indexedFeatures")
      .setNumTrees(10) // Número de árboles del bosque

    // Convierte los resultados de las predicciones de nuevo a etiquetas originales (no indexadas)
    val labelConverter = new IndexToString()
      .setInputCol("prediction")
      .setOutputCol("predictedLabel")
      .setLabels(res2.labelsArray(0))

    // Encadena los pasos anteriores en un Pipeline
    val pipeline = new Pipeline()
      .setStages(Array(res2, featureIndexer, rf, labelConverter))

    // Entrena el modelo.
    val model = pipeline.fit(trainingData)

    // Hace predicciones con el conjunto de prueba.
    val predictions = model.transform(testData)

    // Muestra ejemplos de predicciones
    predictions.select("predictedLabel", "label", "features").show(5)

    //Evaluacion
    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("indexedLabel")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")
    val accuracy = evaluator.evaluate(predictions)
    println(s"Test Error = ${(1.0 - accuracy)}")

    val rfModel = model.stages(2).asInstanceOf[RandomForestClassificationModel]
    println(s"Learned classification forest model:\n ${rfModel.toDebugString}")
    // $example off$

    spark.stop()
  }
}
// scalastyle:on println
