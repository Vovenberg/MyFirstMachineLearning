package com.killprojects.utils

import com.killprojects.learning.models.main.SVMParams

/**
 * Created by Vladimir on 17.05.2017.
 */
class ParamsUtil {
    static SVMParams initParams(double degree, double gamma) {
        SVMParams param = new SVMParams()
        param.svm_type = SVMParams.ONE_CLASS
        param.kernel_type = SVMParams.RBF

        param.degree = degree
        param.gamma = gamma

        param.coef0 = 0
        param.nu = 0.5
        param.cache_size = 100
        param.eps = 1e-3
        param.shrinking = 1
        param.probability = 0
        param
    }
}
