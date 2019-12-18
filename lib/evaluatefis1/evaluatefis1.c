/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: evaluatefis1.c
 *
 * MATLAB Coder version            : 4.3
 * C/C++ source code generated on  : 18-Dec-2019 23:54:18
 */

/* Include Files */
#include "evaluatefis1.h"
#include "applyMamdaniDefuzzificationMethod.h"
#include "applyMamdaniImplicationMethod.h"
#include "evaluatefis1_data.h"
#include "evaluatefis1_initialize.h"
#include "rt_nonfinite.h"
#include "trapmf.h"

/* Function Definitions */

/*
 * Arguments    : const double x[2]
 *                double y[2]
 * Return Type  : void
 */
void evaluatefis1(const double x[2], double y[2])
{
  double dv[8];
  static const double dv1[4] = { 0.0, 0.0, 0.2, 0.3 };

  static const double dv2[4] = { 0.2, 0.3, 0.5, 0.6 };

  static const double dv3[4] = { 0.5, 0.6, 1.042, 1.375 };

  static const double dv4[4] = { -150.0, -80.0, -10.0, 0.0 };

  static const double dv5[4] = { 0.0, 10.0, 80.0, 180.0 };

  static const double dv6[4] = { -180.0, -180.0, -150.0, -90.0 };

  static const double dv7[4] = { 90.0, 150.0, 180.0, 180.0 };

  double sw;
  int ruleID;
  double w[15];
  double orr[1530];
  double d;
  static const signed char iv[30] = { 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3,
    3, 2, 1, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5 };

  double aggVal;
  int id;
  double arr[102];
  double b_x[2];
  if (isInitialized_evaluatefis1 == false) {
    evaluatefis1_initialize();
  }

  dv[0] = trapmf(x[0], dv1);
  dv[1] = trapmf(x[0], dv2);
  dv[2] = trapmf(x[0], dv3);
  dv[3] = trapmf(x[1], dv4);
  dv[4] = 0.0;
  if ((-10.0 < x[1]) && (x[1] < 0.0)) {
    dv[4] = (x[1] - -10.0) * 0.1;
  }

  if ((0.0 < x[1]) && (x[1] < 10.0)) {
    dv[4] = (10.0 - x[1]) * 0.1;
  }

  if (x[1] == 0.0) {
    dv[4] = 1.0;
  }

  dv[5] = trapmf(x[1], dv5);
  dv[6] = trapmf(x[1], dv6);
  dv[7] = trapmf(x[1], dv7);
  sw = 0.0;
  for (ruleID = 0; ruleID < 15; ruleID++) {
    d = dv[iv[ruleID] - 1] * dv[iv[ruleID + 15] + 2];
    w[ruleID] = d;
    sw += d;
  }

  applyMamdaniImplicationMethod(w, orr);
  for (ruleID = 0; ruleID < 51; ruleID++) {
    aggVal = orr[ruleID];
    for (id = 2; id < 16; id++) {
      d = orr[ruleID + 51 * (id - 1)];
      aggVal = (aggVal + d) - aggVal * d;
    }

    arr[ruleID] = aggVal;
  }

  for (ruleID = 0; ruleID < 51; ruleID++) {
    aggVal = orr[ruleID + 765];
    for (id = 17; id < 31; id++) {
      d = orr[ruleID + 51 * (id - 1)];
      aggVal = (aggVal + d) - aggVal * d;
    }

    arr[ruleID + 51] = aggVal;
  }

  c_applyMamdaniDefuzzificationMe(sw, arr, b_x);
  y[0] = b_x[0];
  y[1] = b_x[1];
}

/*
 * File trailer for evaluatefis1.c
 *
 * [EOF]
 */
