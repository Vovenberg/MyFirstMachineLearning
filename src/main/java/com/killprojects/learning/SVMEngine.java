package com.killprojects.learning;

import com.killprojects.learning.models.kernel.Kernel;
import com.killprojects.learning.models.kernel.ONE_CLASS_Q;
import com.killprojects.learning.models.kernel.solver.Solver;
import com.killprojects.learning.models.main.FeatureNode;
import com.killprojects.learning.models.main.SVMModel;
import com.killprojects.learning.models.main.SVMParams;
import com.killprojects.learning.models.main.SVNProblem;

public class SVMEngine {

    private void solve_one_class(SVNProblem prob, SVMParams param,
                                 double[] alpha, Solver.SolutionInfo si) {
        int l = prob.l;
        double[] zeros = new double[l];
        byte[] ones = new byte[l];
        int i;

        int n = (int) (param.nu * prob.l);

        for (i = 0; i < n; i++)
            alpha[i] = 1;
        if (n < prob.l)
            alpha[n] = param.nu * prob.l - n;
        for (i = n + 1; i < l; i++)
            alpha[i] = 0;

        for (i = 0; i < l; i++) {
            zeros[i] = 0;
            ones[i] = 1;
        }

        Solver s = new Solver();
        s.Solve(l, new ONE_CLASS_Q(prob, param), zeros, ones, alpha, 1.0, 1.0,
                param.eps, si, param.shrinking);
    }

    class DecisionFunction {
        double[] alpha;
        double rho;
    }

    private DecisionFunction svm_train_one(SVNProblem prob, SVMParams param,
                                           double Cp, double Cn) {
        double[] alpha = new double[prob.l];
        Solver.SolutionInfo si = new Solver.SolutionInfo();

        solve_one_class(prob, param, alpha, si);

        System.out.println("obj = " + si.obj + ", rho = " + si.rho + "\n");
        // output SVs
        int nSV = 0;
        int nBSV = 0;
        for (int i = 0; i < prob.l; i++) {
            if (Math.abs(alpha[i]) > 0) {
                ++nSV;
                if (prob.y[i] > 0) {
                    if (Math.abs(alpha[i]) >= si.upper_bound_p)
                        ++nBSV;
                } else {
                    if (Math.abs(alpha[i]) >= si.upper_bound_n)
                        ++nBSV;
                }
            }
        }

        System.out.println("nSV = " + nSV + ", nBSV = " + nBSV + "\n");

        DecisionFunction f = new DecisionFunction();
        f.alpha = alpha;
        f.rho = si.rho;
        return f;
    }


    public SVMModel svm_train(SVNProblem prob, SVMParams param) {
        SVMModel model = new SVMModel();
        model.param = param;
        model.sv_coef = new double[1][];

        DecisionFunction f = svm_train_one(prob, param, 0, 0);
        model.rho = new double[1];
        model.rho[0] = f.rho;

        int nSV = 0;
        int i;
        for (i = 0; i < prob.l; i++)
            if (Math.abs(f.alpha[i]) > 0)
                ++nSV;
        model.l = nSV;
        model.SV = new FeatureNode[nSV][];
        model.sv_coef[0] = new double[nSV];
        int j = 0;
        for (i = 0; i < prob.l; i++)
            if (Math.abs(f.alpha[i]) > 0) {
                model.SV[j] = prob.x[i];
                model.sv_coef[0][j] = f.alpha[i];
                ++j;
            }
        return model;
    }

    ///////////////////PREDICT//////////////////////////////////////////////////////////

    private double svm_predict_values(SVMModel model, FeatureNode[] x,
                                      double[] dec_values) {
        int i;
        double[] sv_coef = model.sv_coef[0];
        double sum = 0;
        for (i = 0; i < model.l; i++)
            sum += sv_coef[i]
                    * Kernel.k_function(x, model.SV[i], model.param);
        sum -= model.rho[0];
        dec_values[0] = sum;

        return (sum > 0) ? 1 : -1;
    }

    public double svm_predict(SVMModel model, FeatureNode[] x) {
        double[] dec_values = new double[1];
        return svm_predict_values(model, x, dec_values);
    }

}
