package com.killprojects.utils

import com.killprojects.learning.models.main.SVMParams

/**
 * Created by Vladimir on 17.05.2017.
 */
class ParamsUtil {
    static SVMParams initParamsRBF( double gamma, double nu) {
        SVMParams param = basic()
        param.kernel_type = SVMParams.RBF

        param.gamma = gamma
        param.nu = nu
        param
    }

    static SVMParams initParamsPoly(double degree, double gamma, double coef, double nu) {
        SVMParams param = basic()
        param.kernel_type = SVMParams.POLY

        param.gamma = gamma
        param.coef0 = coef
        param.degree = degree
        param.nu = nu
        param
    }

    private static SVMParams basic() {
        SVMParams param = new SVMParams()
        param.svm_type = SVMParams.ONE_CLASS

        param.cache_size = 100
        param.eps = 1e-3
        param.shrinking = 1
        param.probability = 0
        param
    }
}
