package com.killprojects.learning

import com.killprojects.learning.models.main.FeatureNode
import com.killprojects.learning.models.main.SVMModel
import com.killprojects.learning.models.main.SVMParams
import com.killprojects.learning.models.main.SVNProblem
import org.springframework.stereotype.Component

/**
 * Created by Vladimir on 16.05.2017.
 */
@Component
class SVM {
    private SVNProblem prob
    private SVMModel model
    private final SVMEngine svm

    SVM() {
        svm = new SVMEngine()
    }

    private void parseData(List<List<Integer>> data, SVMParams params) {
        Vector<Double> vy = new Vector<Double>()
        Vector<FeatureNode[]> vx = new Vector<FeatureNode[]>()
        int max_index = 0;

        data.each { it ->
            vy.addElement(Double.parseDouble(it.get(3).toString()))
            FeatureNode[] x = new FeatureNode[3]
            x.eachWithIndex { FeatureNode entry, int i ->
                def node = new FeatureNode(index: i + 1, value: Double.parseDouble(it.get(i).toString()))
                x[i] = node
            }
            max_index = Collections.max(x.collect { it.index })
            vx.addElement(x)
        }
        prob = new SVNProblem()
        prob.l = vy.size()
        prob.x = new FeatureNode[prob.l][]
        for (int i = 0; i < prob.l; i++)
            prob.x[i] = vx.elementAt(i)
        prob.y = new double[prob.l]
        for (int i = 0; i < prob.l; i++)
            prob.y[i] = vy.elementAt(i)
        if (params.gamma == 0 && max_index > 0)
            params.gamma = 1.0 / max_index;
    }

    public void train(List<List<Integer>> data, SVMParams params) {
        parseData(data, params)
        model = svm.svm_train(prob, params)
    }

    public void predict() {

    }

    SVNProblem getProb() {
        return prob
    }

    SVMModel getModel() {
        return model
    }
}
