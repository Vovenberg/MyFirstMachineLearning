//
// svm_model
//
package com.killprojects.learning.models.main;

import autovalue.shaded.org.apache.commons.lang.text.StrBuilder;

import java.util.Arrays;

public class SVMModel implements java.io.Serializable
{
	private static final long serialVersionUID = -2636161283150376737L;
	public SVMParams param;	// parameter
	public int nr_class;		// number of classes, = 2 in regression/one class models
	public int l;			// total #SV
	public FeatureNode[][] SV;	// SVs (SV[l])
	public double[][] sv_coef;	// coefficients for SVs in decision functions (sv_coef[k-1][l])
	public double[] rho;		// constants in decision functions (rho[k*(k-1)/2])
	public double[] probA;         // pariwise probability information
	public double[] probB;

	// for classification only

	public int[] label;		// label of each class (label[k])
	public int[] nSV;		// number of SVs for each class (nSV[k])
				// nSV[0] + nSV[1] + ... + nSV[k-1] = l
	
	// map of label names
	public String[] labelNames;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < sv_coef.length; i++) {
			builder.append(Arrays.toString(sv_coef[i]));
		}
		builder.append("\nrho: ").append(Arrays.toString(rho)).append("; probA: ").append(Arrays.toString(probA)).append("; probB: ").append(Arrays.toString(probB));
		return builder.toString();
	}
};