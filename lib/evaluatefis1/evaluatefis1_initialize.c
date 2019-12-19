/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: evaluatefis1_initialize.c
 *
 * MATLAB Coder version            : 4.3
 * C/C++ source code generated on  : 18-Dec-2019 23:54:18
 */

/* Include Files */
#include "evaluatefis1_initialize.h"
#include "evaluatefis1.h"
#include "evaluatefis1_data.h"
#include "rt_nonfinite.h"

/* Function Definitions */

/*
 * Arguments    : void
 * Return Type  : void
 */
void evaluatefis1_initialize(void)
{
  rt_InitInfAndNaN();
  isInitialized_evaluatefis1 = true;
}

/*
 * File trailer for evaluatefis1_initialize.c
 *
 * [EOF]
 */
