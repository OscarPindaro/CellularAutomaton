/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 * File: _coder_evaluatefis1_api.h
 *
 * MATLAB Coder version            : 4.3
 * C/C++ source code generated on  : 18-Dec-2019 23:54:18
 */

#ifndef _CODER_EVALUATEFIS1_API_H
#define _CODER_EVALUATEFIS1_API_H

/* Include Files */
#include <stddef.h>
#include <stdlib.h>
#include "tmwtypes.h"
#include "mex.h"
#include "emlrt.h"

/* Variable Declarations */
extern emlrtCTX emlrtRootTLSGlobal;
extern emlrtContext emlrtContextGlobal;

/* Function Declarations */
extern void evaluatefis1(real_T x[2], real_T y[2]);
extern void evaluatefis1_api(const mxArray * const prhs[2], int32_T nlhs, const
  mxArray *plhs[1]);
extern void evaluatefis1_atexit(void);
extern void evaluatefis1_initialize(void);
extern void evaluatefis1_terminate(void);
extern void evaluatefis1_xil_shutdown(void);
extern void evaluatefis1_xil_terminate(void);

#endif

/*
 * File trailer for _coder_evaluatefis1_api.h
 *
 * [EOF]
 */
