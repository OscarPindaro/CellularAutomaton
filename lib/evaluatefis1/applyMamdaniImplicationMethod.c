/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: applyMamdaniImplicationMethod.c
 *
 * MATLAB Coder version            : 4.3
 * C/C++ source code generated on  : 18-Dec-2019 23:54:18
 */

/* Include Files */
#include "applyMamdaniImplicationMethod.h"
#include "evaluatefis1.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : const double antecedentOutputs[15]
 *                double consequentOutputs[1530]
 * Return Type  : void
 */
void applyMamdaniImplicationMethod(const double antecedentOutputs[15], double
  consequentOutputs[1530])
{
  int outputID;
  int indexOffset;
  int ruleID;
  int mfIndex;
  static const signed char iv[30] = { 3, 2, 2, 1, 1, 2, 3, 2, 4, 4, 4, 2, 4, 4,
    4, 2, 1, 3, 1, 3, 1, 2, 3, 1, 3, 1, 2, 3, 1, 3 };

  int ruleOutputIndex;
  int sampleID;
  int consequentOutputs_tmp;
  static const signed char outputMFCache[357] = { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 1, 0, 0, 0, 1 };

  for (outputID = 0; outputID < 2; outputID++) {
    indexOffset = outputID * 15;
    for (ruleID = 0; ruleID < 15; ruleID++) {
      mfIndex = iv[ruleID + 15 * outputID];
      ruleOutputIndex = indexOffset + ruleID;
      for (sampleID = 0; sampleID < 51; sampleID++) {
        consequentOutputs_tmp = sampleID + 51 * ruleOutputIndex;
        consequentOutputs[consequentOutputs_tmp] = outputMFCache[(((outputID <<
          2) + mfIndex) + 7 * sampleID) - 1];
        consequentOutputs[consequentOutputs_tmp] *= antecedentOutputs[ruleID];
      }
    }
  }
}

/*
 * File trailer for applyMamdaniImplicationMethod.c
 *
 * [EOF]
 */
