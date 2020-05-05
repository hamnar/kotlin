/*
 * Created by M1028379 on 18/12/2019
 * Copyright (c) CARTUS B2B 2019 .
 * All rights reserved.
 * Last modified 12/17/19 3:05 PM
 *
 * CHANGE LOG
 * —------------------------------------------------------
 * ID     Bug ID   Author Name     Date         Description
 * —------------------------------------------------------
 */

package com.hem.kotlinrecyclerview

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.WindowManager


class CustomProgressDialog {
    companion object {
        fun progressDialog(context: Context): Dialog {
            val dialog = Dialog(context)
            val inflate =
                LayoutInflater.from(context).inflate(R.layout.custom_progress_dialog, null)
            dialog.setContentView(inflate)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT)
            )
            dialog.window!!.setFlags(
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
            )
            return dialog
        }
    }
}