/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: applyMamdaniDefuzzificationMethod.c
 *
 * MATLAB Coder version            : 4.3
 * C/C++ source code generated on  : 18-Dec-2019 23:54:18
 */

/* Include Files */
#include "applyMamdaniDefuzzificationMethod.h"
#include "evaluatefis1.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : double sumAntecedentOutputs
 *                const double aggregatedOutputs[102]
 *                double defuzzifiedOutputs[2]
 * Return Type  : void
 */
void c_applyMamdaniDefuzzificationMe(double sumAntecedentOutputs, const double
  aggregatedOutputs[102], double defuzzifiedOutputs[2])
{
  int outputID;
  double y;
  static const signed char iv[4] = { 0, -1, 1, 1 };

  double area;
  int i;
  double x[51];
  static const double outputSamplePoints[102] = { 0.0, -1.0, 0.02, -0.96, 0.04,
    -0.92, 0.06, -0.88, 0.08, -0.84, 0.1, -0.8, 0.12, -0.76, 0.14, -0.72, 0.16,
    -0.68, 0.18, -0.64, 0.2, -0.6, 0.22, -0.56, 0.24, -0.52, 0.26, -0.48, 0.28,
    -0.44, 0.3, -0.4, 0.32, -0.36, 0.34, -0.32, 0.36, -0.28, 0.38, -0.24, 0.4,
    -0.2, 0.42, -0.16, 0.44, -0.12, 0.46, -0.08, 0.48, -0.04, 0.5, 0.0, 0.52,
    0.04, 0.54, 0.08, 0.56, 0.12, 0.58, 0.16, 0.6, 0.2, 0.62, 0.24, 0.64, 0.28,
    0.66, 0.32, 0.68, 0.36, 0.7, 0.4, 0.72, 0.44, 0.74, 0.48, 0.76, 0.52, 0.78,
    0.56, 0.8, 0.6, 0.82, 0.64, 0.84, 0.68, 0.86, 0.72, 0.88, 0.76, 0.9, 0.8,
    0.92, 0.84, 0.94, 0.88, 0.96, 0.92, 0.98, 0.96, 1.0, 1.0 };

  for (outputID = 0; outputID < 2; outputID++) {
    if (sumAntecedentOutputs == 0.0) {
      defuzzifiedOutputs[outputID] = ((double)iv[outputID] + 1.0) / 2.0;
    } else {
      y = 0.0;
      area = 0.0;
      for (i = 0; i < 51; i++) {
        x[i] = outputSamplePoints[outputID + (i << 1)];
        area += aggregatedOutputs[i + 51 * outputID];
      }

      if (area == 0.0) {
        defuzzifiedOutputs[outputID] = (x[0] + x[50]) / 2.0;
      } else {
        for (i = 0; i < 51; i++) {
          y += x[i] * aggregatedOutputs[i + 51 * outputID];
        }

        defuzzifiedOutputs[outputID] = y * (1.0 / area);
      }
    }
  }
}

/*
 * File trailer for applyMamdaniDefuzzificationMethod.c
 *
 * [EOF]
 */
