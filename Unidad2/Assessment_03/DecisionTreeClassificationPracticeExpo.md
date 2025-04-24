```scala
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

// scalastyle:off println
package org.apache.spark.examples.ml

// $example on$
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}


// $example off$
import org.apache.spark.sql.SparkSession

object DecisionTreeClassificationExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("DecisionTreeClassificationExample").getOrCreate()

    Terminal
    warning: 1 deprecation (since 2.13.3); for details, enable `:setting -deprecation` or `:replay -deprecation`
    val spark: org.apache.spark.sql.SparkSession = org.apache.spark.sql.SparkSession@6b062cfd
    
    // $example on$
    // Load the data stored in LIBSVM format as a DataFrame.
    val data = spark.read.format("libsvm").load("sample_libsvm_data.txt")

    Terminal
    25/04/09 12:37:49 WARN LibSVMFileFormat: 'numFeatures' option not specified, determining the number of features by going though the input. If you know the number in advance, please specify it via 'numFeatures' option to avoid the extra scan.
    val data: org.apache.spark.sql.DataFrame = [label: double, features: vector]

    // Index labels, adding metadata to the label column.
    // Fit on whole dataset to include all labels in index.
    val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(data)

    Terminal
    val labelIndexer: org.apache.spark.ml.feature.StringIndexerModel = StringIndexerModel: uid=strIdx_2839a3597602, handleInvalid=error

    // Automatically identify categorical features, and index them. // features with > 4 distinct values are treated as continuous.
    val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(data)

    Terminal
    val featureIndexer: org.apache.spark.ml.feature.VectorIndexerModel = VectorIndexerModel: uid=vecIdx_6251a70c0a76, numFeatures=692, handleInvalid=error


    // Split the data into training and test sets (30% held out for testing).
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))

    Terminal
    val trainingData: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row] = [label: double, features: vector]
    val testData: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row] = [label: double, features: vector]

    // Train a DecisionTree model.
    val dt = new DecisionTreeClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures")

    Terminal
    val dt: org.apache.spark.ml.classification.DecisionTreeClassifier = dtc_f199500fa6b1

    // Convert indexed labels back to original labels.
    val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labelsArray(0))

    Terminal
    val labelConverter: org.apache.spark.ml.feature.IndexToString = idxToStr_e9284d59b1f7

    // Chain indexers and tree in a Pipeline.
    val pipeline = new Pipeline().setStages(Array(labelIndexer, featureIndexer, dt, labelConverter))

    Terminal
    val pipeline: org.apache.spark.ml.Pipeline = pipeline_fad3f1594e57

    // Train model. This also runs the indexers.
    val model = pipeline.fit(trainingData)

    Terminal
    val model: org.apache.spark.ml.PipelineModel = pipeline_fad3f1594e57

    // Make predictions.
    val predictions = model.transform(testData)

    Terminal
    val predictions: org.apache.spark.sql.DataFrame = [label: double, features: vector ... 6 more fields]

    // Select example rows to display.
    predictions.select("predictedLabel", "label", "features").show()

    Terminal
    +--------------+-----+--------------------+
    |predictedLabel|label|            features|
    +--------------+-----+--------------------+
    |           0.0|  0.0|(692,[122,123,148...|
    |           0.0|  0.0|(692,[123,124,125...|
    |           0.0|  0.0|(692,[123,124,125...|
    |           0.0|  0.0|(692,[123,124,125...|
    |           0.0|  0.0|(692,[124,125,126...|
    |           0.0|  0.0|(692,[124,125,126...|
    |           0.0|  0.0|(692,[124,125,126...|
    |           0.0|  0.0|(692,[151,152,153...|
    |           0.0|  0.0|(692,[153,154,155...|
    |           0.0|  0.0|(692,[181,182,183...|
    |           1.0|  1.0|(692,[97,98,99,12...|
    |           1.0|  1.0|(692,[100,101,102...|
    |           1.0|  1.0|(692,[123,124,125...|
    |           1.0|  1.0|(692,[123,124,125...|
    |           1.0|  1.0|(692,[123,124,125...|
    |           1.0|  1.0|(692,[125,126,153...|
    |           1.0|  1.0|(692,[126,127,128...|
    |           1.0|  1.0|(692,[126,127,128...|
    |           1.0|  1.0|(692,[127,128,129...|
    |           1.0|  1.0|(692,[127,128,129...|
    +--------------+-----+--------------------+
    only showing top 20 rows

    // Select (prediction, true label) and compute test error.
    val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")

    Terminal
    val evaluator: org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator = MulticlassClassificationEvaluator: uid=mcEval_5e5172602c45, metricName=accuracy, metricLabel=0.0, beta=1.0, eps=1.0E-15

    //
    val accuracy = evaluator.evaluate(predictions)

    Terminal
    val accuracy: Double = 1.0

    //
    println(s"Test Error = ${(1.0 - accuracy)}")

    Terminal
    Test Error = 0.0

    //
    val treeModel = model.stages(2).asInstanceOf[DecisionTreeClassificationModel]

    Terminal
    val treeModel: org.apache.spark.ml.classification.DecisionTreeClassificationModel = DecisionTreeClassificationModel: uid=dtc_f199500fa6b1, depth=2, numNodes=5, numClasses=2, numFeatures=692

    //
    println(s"Learned classification tree model:\n ${treeModel.toDebugString}")

    Terminal
    Learned classification tree model:
    DecisionTreeClassificationModel: uid=dtc_f199500fa6b1, depth=2, numNodes=5, numClasses=2, numFeatures=692
        If (feature 434 <= 88.5)
            If (feature 99 in {2.0})
                Predict: 0.0
            Else (feature 99 not in {2.0})
                Predict: 1.0
        Else (feature 434 > 88.5)
            Predict: 0.0
    
    //Diagrama del arbol
                         [feature 434 <= 88.5]
                        /                    \
                    Sí                        No
                 /                               \
    [feature 99 in {2.0}]                      Predict: 0.0
       /          \
    Sí           No
   /               \
Predict: 0.0    Predict: 1.0



    // $example off$

    spark.stop()
  }
}
// scalastyle:on println
```