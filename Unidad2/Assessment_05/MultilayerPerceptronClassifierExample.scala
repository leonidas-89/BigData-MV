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
//package org.apache.spark.examples.ml

// $example on$
    println()
    println(s"******** Importing classifier and evaluator ********")
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
// $example off$
import org.apache.spark.sql.SparkSession

/**
 * An example for Multilayer Perceptron Classification.
 */

//object MultilayerPerceptronClassifierExample {
  
//def main(args: Array[String]): Unit = {
println()
println(s"******** Creating session and loading data ********")
val spark = SparkSession.builder.appName("MultilayerPerceptronClassifierExample").getOrCreate()

// $example on$
// Load the data stored in LIBSVM format as a DataFrame.
val data = spark.read.format("libsvm").load("sample_multiclass_classification_data.txt")

// Split the data into train and test
println()
println(s"******** Splitting the data into training and testing ********")
val splits = data.randomSplit(Array(0.6, 0.4), seed = 1234L)
val train = splits(0)
val test = splits(1)

// specify layers for the neural network:
// input layer of size 4 (features), two intermediate of size 5 and 4
// and output of size 3 (classes)
println()
println(s"******** Specifying the layers for neural network ********")
val layers = Array[Int](4, 5, 4, 3)

// create the trainer and set its parameters
println()
println(s"******** Create the trainer ********")
val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)

// train the model
println()
println(s"******** Training the model ********")
val model = trainer.fit(train)

// compute accuracy on the test set
println()
println(s"******** Computing accuracy ********")
val result = model.transform(test)
val predictionAndLabels = result.select("prediction", "label")
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
println()
println(s"******** Test set accuracy = ${evaluator.evaluate(predictionAndLabels)} ********")
// $example off$

spark.stop()
//}
//}
// scalastyle:on println
