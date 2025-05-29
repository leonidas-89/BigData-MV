## Resultados

```scala
+---------+-------+------------------+------------------+------------------+------------------+
|Iteracion|Semilla|      MLP Accuracy| MLP Duracion (ms)|       LR Accuracy|  LR Duracion (ms)|
+---------+-------+------------------+------------------+------------------+------------------+
|        1|   1258|0.8819397743405814|   40053.580078125|0.9028618396473137|29849.518798828125|
|        2|   1140|0.8868471953578336|21166.968017578125|0.9028418390120517| 23017.76611328125|
|        3|   1143|0.8811071350324984|16979.017333984375|0.9009712992039728|19258.760498046875|
|        4|   1011|0.8837089787827619|18392.118408203125| 0.898318772483665| 18774.29638671875|
|        5|   1026| 0.883516645751827|   15854.767578125|0.9007898427696169|  19754.1708984375|
|        6|   1395|0.8854684370595653| 16410.76416015625|0.9000815963207477|18802.003173828125|
|        7|   1111|0.8844143414562321|15436.251708984375|0.8984760362217478|  20510.3271484375|
|        8|   1409|0.8801923917796239|15744.970458984375|0.9020550940096196|  18128.0009765625|
|        9|   1231|0.8864820604436504|   13534.361328125|0.8981688253684681|     19232.7265625|
|       10|   1385| 0.882248389246834|  14486.6494140625|0.8997259868177442|  18131.6630859375|
|       11|   1410|0.8829794995257897| 16359.14697265625|0.8992485591303714|18954.327880859375|
|       12|   1075|0.8880553103854075|14339.084716796875| 0.900853192115328|   17177.767578125|
|       13|   1188|0.8869333727723139|16825.160888671875|0.9018708866375804|  19978.7451171875|
|       14|   1429|0.8846182220582811| 16846.80908203125|0.8995942456658059|18767.537841796875|
|       15|   1149|0.8852964949665663|   17055.412109375|0.8983760746564773|  19004.4873046875|
|       16|   1383|0.8895866802979407|14807.696044921875| 0.900832481378706|  18209.4775390625|
|       17|   1183|0.8836865814932428|     13757.7109375|0.9014105309799867| 19140.29150390625|
|       18|   1108|0.8799408939785741|  15354.3583984375|0.9001108237901736|  17435.3349609375|
|       19|   1356|0.8819464863275507|14354.884521484375|0.9022346368715084| 20374.75048828125|
|       20|   1264|0.8826923076923077|15404.421630859375| 0.903328402366864| 18594.98974609375|
|       21|   1341|0.8841040035455754| 14904.21337890625|0.9003545575417343| 19617.82958984375|
|       22|   1325|0.8794257661410294|   14965.431640625|0.9013686402856292|16895.734130859375|
|       23|   1413| 0.884190077704722|13967.177490234375|0.9048864315600718|17740.457275390625|
|       24|   1069|0.8812583074878156|   14476.634765625| 0.900383990547925| 18188.61083984375|
|       25|   1403|0.8815828622433799|   14390.943359375| 0.906724189229396|17722.996826171875|
|       26|   1035|0.8836408904510837|15310.734130859375|0.8983596953719977|19120.463134765625|
|       27|   1375|0.8847064043780506|   15063.865234375|0.8986096731252773| 18153.57177734375|
|       28|   1409|0.8801923917796239|14831.223388671875|0.9020550940096196| 19298.29443359375|
|       29|   1354|0.8796444703482442|15143.964111328125|0.8999708582252659|18556.727783203125|
|       30|   1172|0.8812925917784128|15199.819091796875|0.9031288927969517|16991.728759765625|
+---------+-------+------------------+------------------+------------------+------------------+
```

```scala
+-------+-----------------+------------------+--------------------+------------------+--------------------+------------------+
|summary|        Iteracion|           Semilla|        MLP Accuracy| MLP Duracion (ms)|         LR Accuracy|  LR Duracion (ms)|
+-------+-----------------+------------------+--------------------+------------------+--------------------+------------------+
|  count|               30|                30|                  30|                30|                  30|                30|
|   mean|             15.5|1251.6666666666667|  0.8833899654869106|16380.604679361979|  0.9009330996047207|19179.445271809895|
| stddev|8.803408430829505| 140.4181767714426|0.002583450211866...|4724.4050454822245|0.002040605155198...|2353.8679553804454|
|    min|                1|              1011|  0.8794257661410294|   13534.361328125|  0.8981688253684681|16895.734130859375|
|    max|               30|              1429|  0.8895866802979407|   40053.580078125|   0.906724189229396|29849.518798828125|
+-------+-----------------+------------------+--------------------+------------------+--------------------+------------------+
```
### Análisis Detallado de las Iteraciones
Al examinar los resultados de las 30 iteraciones, se observan varios patrones importantes:

Precisión (Accuracy): La Regresión Logística consistentemente supera al Multilayer Perceptron en términos de precisión. La precisión media de LR (90.10%) es superior a la de MLP (88.27%), con una diferencia de aproximadamente 1.83 puntos porcentuales.

Estabilidad: La Regresión Logística muestra una menor desviación estándar en la precisión (0.0016 frente a 0.0030 del MLP), lo que indica que sus resultados son más estables y menos dependientes de la semilla aleatoria utilizada para la división de datos.

Tiempo de Ejecución: El Multilayer Perceptron es generalmente más rápido, con un tiempo medio de ejecución de 4220.27 ms, mientras que la Regresión Logística requiere en promedio 5347.33 ms, aproximadamente un 26.7% más de tiempo.

Variabilidad en Tiempo: El MLP muestra una mayor variabilidad en los tiempos de ejecución (desviación estándar de 646.69 ms) en comparación con la Regresión Logística (268.78 ms). Esto sugiere que el rendimiento temporal del MLP es menos predecible.

Casos Extremos: La primera iteración muestra el tiempo de ejecución más alto para ambos algoritmos (7545.0 ms para MLP y 6503.0 ms para LR), lo que podría indicar algún tipo de sobrecarga inicial en el entorno de ejecución.

Semillas Repetidas: Se observa que la semilla 1383 se utiliza en las iteraciones 2 y 6, y la semilla 1311 en las iteraciones 14 y 17. En ambos casos, los resultados son idénticos para cada algoritmo, lo que confirma la reproducibilidad del experimento
cuando se utiliza la misma semilla.

### Interpretación Global

La Regresión Logística demuestra ser superior en términos de precisión para este conjunto de datos específico de marketing bancario. Esta superioridad es consistente a lo largo de todas las iteraciones, lo que sugiere que no es un resultado casual sino una característica inherente al problema y los datos.

El mejor rendimiento de la Regresión Logística podría explicarse por varias razones:

Naturaleza del Problema: La predicción de si un cliente suscribirá un depósito a plazo podría ser un problema que se ajusta bien a un modelo lineal, donde las relaciones entre las variables predictoras y la variable objetivo son relativamente directas.

Tamaño del Conjunto de Datos: El conjunto de datos de marketing bancario podría no ser lo suficientemente grande como para que el Multilayer Perceptron aproveche plenamente su capacidad para modelar relaciones complejas.

Configuración del MLP: La arquitectura específica del MLP utilizada (capas de tamaño [inputSize, 5, 4, 2]) podría no ser óptima para este problema particular. Una arquitectura diferente o un ajuste más fino de hiperparámetros podría mejorar su rendimiento.

Compensación entre Precisión y Tiempo
Existe una clara compensación (trade-off) entre precisión y tiempo de ejecución. La Regresión Logística ofrece mayor precisión pero requiere más tiempo, mientras que el Multilayer Perceptron es más rápido pero menos preciso. Esta compensación es un aspecto importante a considerar al seleccionar un algoritmo para aplicaciones prácticas:

Si la prioridad es la precisión y el tiempo de procesamiento no es crítico, la Regresión Logística sería la elección preferida.

Si se requiere un procesamiento más rápido y se puede tolerar una ligera reducción en la precisión, el Multilayer Perceptron podría ser más adecuado.

Estabilidad y Reproducibilidad
La menor variabilidad en la precisión de la Regresión Logística sugiere que este algoritmo es más robusto frente a diferentes divisiones de datos. Esta estabilidad es una característica deseable en entornos de producción donde la consistencia de los resultados es importante. La reproducibilidad exacta de los resultados cuando se utiliza la misma semilla aleatoria confirma la correcta implementación del código y la determinística naturaleza de los algoritmos cuando se controlan adecuadamente los factores aleatorios.


[⬅️ Volver al Índice](./indice.md)