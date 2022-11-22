package com.example.cyclingapp

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun showDefaultDialog(context: Context) {
    val alertDialog = AlertDialog.Builder(context)

    alertDialog.apply {
        //setIcon(R.drawable.ic_hello)
        setTitle("Item Management")
        setMessage("Have you already prepared the following items? \n\n* Water \n" +
                "* Helmet \n* Money \n* Headlight")
        setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
            //Toast.makeText(context, "EquipmentYes", Toast.LENGTH_SHORT).show()
        }
        setNegativeButton("No, not yet") { _, _ ->
            //Toast.makeText(context, "Equipment No", Toast.LENGTH_SHORT).show()
        }

        setOnDismissListener {
            Toast.makeText(context, "Equipment Check", Toast.LENGTH_SHORT).show()
        }

    }.create().show()
}
