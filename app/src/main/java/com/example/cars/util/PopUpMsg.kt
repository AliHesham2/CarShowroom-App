package com.example.cars.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.cars.R
import com.google.android.material.snackbar.Snackbar

class PopUpMsg {

    companion object{
        fun alertMsg(view: View, msg: String){
            Snackbar.make(view, msg, Snackbar.LENGTH_LONG).also { snackBar ->
                snackBar.setBackgroundTint(ContextCompat.getColor(view.context, R.color.red))
            }.show()
        }

        fun toastMsg(context: Context, msg:String){
            Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
        }

    }

}