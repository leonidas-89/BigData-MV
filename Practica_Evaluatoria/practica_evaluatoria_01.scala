//Comienza una simple sesión Spark.
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()

//Cargue el archivo Netflix Stock CSV en dataframe llamado df, haga que Spark, infiera los tipos de datos.
val netflixdf = spark.read.option("header","true").option("inferSchema","true").csv("Netflix_2011_2016.csv")

//¿Cuáles son los nombres de las columnas?
netflixdf.columns

//¿Cómo es el esquema?
netflixdf.printSchema()

//Imprime las primeras 5 renglones.
netflixdf.head(5)

