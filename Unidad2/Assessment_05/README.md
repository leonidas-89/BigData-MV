
# Multi Layer Perceptron

### 1. Importar paquetes
```scala
println()
println(s"******** Importing classifier and evaluator ********")
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.sql.SparkSession
```

### 2. Creación de la sesión e importación de los datos
```scala
println()
println(s"******** Creating session and loading data ********")
val spark = SparkSession.builder.appName("MultilayerPerceptronClassifierExample").getOrCreate()

// Load the data stored in LIBSVM format as a DataFrame.
val data = spark.read.format("libsvm").load("sample_multiclass_classification_data.txt")

```

### 3. Separación de los datos para entranamiento
```scala
// Split the data into train and test
println()
println(s"******** Splitting the data into training and testing ********")
val splits = data.randomSplit(Array(0.6, 0.4), seed = 1234L)
val train = splits(0)
val test = splits(1)
```

### 4. Especificar las capas de la red neuronal
```scala

// specify layers for the neural network:
// input layer of size 4 (features), two intermediate of size 5 and 4
// and output of size 3 (classes)
println()
println(s"******** Specifying the layers for neural network ********")
val layers = Array[Int](4, 5, 4, 3)
```

### 5. Creación del entrenador y configuración de parámetros
```scala
// create the trainer and set its parameters
println()
println(s"******** Create the trainer ********")
val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100)
```

### 6. Entrenamiento del modelo
```scala
// train the model
println()
println(s"******** Training the model ********")
val model = trainer.fit(train)
```

### 7. Calcular la precisión en el conjunto de prueba.
```scala
// compute accuracy on the test set
println()
println(s"******** Computing accuracy ********")
val result = model.transform(test)
val predictionAndLabels = result.select("prediction", "label")
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
println()
println(s"******** Test set accuracy = ${evaluator.evaluate(predictionAndLabels)} ********")

spark.stop()
```