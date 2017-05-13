package com.killprojects.learning

import com.killprojects.utils.Loader
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.api.java.JavaRDD
import org.apache.spark.mllib.classification.SVMModel
import org.apache.spark.mllib.classification.SVMWithSGD
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.util.MLUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import scala.Tuple2

/**
 * Created by Vladimir on 13.05.2017.
 */
@Component
class SVMLearning {

    public static final String NAME_FILE = "titanic.txt"
    public static final String PATH_FILE = "target/$NAME_FILE"

    private final SparkContext sc

    SVMLearning() {
        SparkConf conf = new SparkConf().setAppName("mySVM").setMaster("local[2]").set("spark.executor.memory","1g")
        sc = new SparkContext(conf)
    }

    @Autowired
    Loader loader

    def learn() {
        loader.createFileWithData(NAME_FILE, true)
        def data = MLUtils.loadLibSVMFile(sc, PATH_FILE).toJavaRDD()

        def training = data.sample(false, 0.6, 11L)
        def test = data.subtract(training)
        training.cache()

        int numIterations = 100
        SVMModel model = SVMWithSGD.train(training.rdd(), numIterations)

        // Clear the default threshold.
        model.clearThreshold();

        // Compute raw scores on the test set.
        JavaRDD<Tuple2<Object, Object>> scoreAndLabels = test.map {
            new Tuple2<>(model.predict(it.features()), it.label())
        }

        // Get evaluation metrics.
        BinaryClassificationMetrics metrics =
                new BinaryClassificationMetrics(JavaRDD.toRDD(scoreAndLabels))
        double auROC = metrics.areaUnderROC()
        double auPR = metrics.areaUnderPR()
    }
}
