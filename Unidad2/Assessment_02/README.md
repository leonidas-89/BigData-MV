### Librerías y configuración de logging
```scala
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession

import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)
```

### Creación de sesión Spark
```scala
val spark = SparkSession.builder().getOrCreate()
```

### Carga de datos
```scala
val data = spark.read
  .option("header","true")
  .option("inferSchema","true")
  .format("csv")
  .load("titanic.csv")
```

### Exploración inicial de los datos
```scala
data.printSchema()
data.head(1)

val colnames = data.columns
val firstrow = data.head(1)(0)
println("\n")
println("Example data row")
for (ind <- Range(1, colnames.length)) {
  println(colnames(ind))
  println(firstrow(ind))
  println("\n")
}
```

### Selección de variables y limpieza
```scala
val logregdataall = data.select(
  data("Survived").as("label"),
  $"Pclass", $"Name", $"Sex", $"Age", $"SibSp", $"Parch", $"Fare", $"Embarked"
)
val logregdata = logregdataall.na.drop()
```

### Ingeniería de características
```scala
import org.apache.spark.ml.feature.{VectorAssembler, StringIndexer, OneHotEncoder}
import org.apache.spark.ml.linalg.Vectors

// Indexación de variables categóricas
val genderIndexer = new StringIndexer()
  .setInputCol("Sex")
  .setOutputCol("SexIndex")
val embarkIndexer = new StringIndexer()
  .setInputCol("Embarked")
  .setOutputCol("EmbarkIndex")

// Codificación One-Hot
val genderEncoder = new OneHotEncoder()
  .setInputCol("SexIndex")
  .setOutputCol("SexVec")
val embarkEncoder = new OneHotEncoder()
  .setInputCol("EmbarkIndex")
  .setOutputCol("EmbarkVec")

// Ensamblado de todas las características en un vector
val assembler = new VectorAssembler()
  .setInputCols(Array("Pclass","SexVec","Age","SibSp","Parch","Fare","EmbarkVec"))
  .setOutputCol("features")
```

### División de datos
```scala
val Array(training, test) = logregdata.randomSplit(Array(0.7, 0.3), seed = 12345)
```

### Construcción y entrenamiento del pipeline
```scala
import org.apache.spark.ml.Pipeline

val lr = new LogisticRegression()
val pipeline = new Pipeline().setStages(Array(
  genderIndexer, embarkIndexer,
  genderEncoder, embarkEncoder,
  assembler, lr
))

val model = pipeline.fit(training)
```

### Predicción y resultados
```scala
val results = model.transform(test)
results.select("prediction", "label", "features").show(5)
```

### Evaluación del modelo
```scala
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

val evaluator = new MulticlassClassificationEvaluator()
  .setLabelCol("label")
  .setPredictionCol("prediction")
  .setMetricName("accuracy")
val accuracy = evaluator.evaluate(results)
println(s"Accuracy = ${accuracy}")

val predictionAndLabels = results
  .select($"prediction", $"label")
  .as[(Double, Double)]
  .rdd
val metrics = new MulticlassMetrics(predictionAndLabels)

println("Confusion matrix:")
println(metrics.confusionMatrix)
```