package com.ubaya.pentolsilang_160419038

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_players.*

class PlayersActivity : AppCompatActivity() {
    companion object {
        val EXTRA_PLAYER1_NAME = "EXTRA_PLAYER1_NAME"
        val EXTRA_PLAYER1_COLOR = "EXTRA_PLAYER1_COLOR"
        val EXTRA_PLAYER2_NAME = "EXTRA_PLAYER2_NAME"
        val EXTRA_PLAYER2_COLOR = "EXTRA_PLAYER2_COLOR"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)

        var color1 = ""
        var color2 = ""
        var selColor1 = 0
        var selColor2 = 1

        //Create adapter for the Spinner
        val adapter = ArrayAdapter(this, R.layout.myspinner_layout, Global.color)
        adapter.setDropDownViewResource(R.layout.myspinner_item_layout)
        spinColor1.adapter = adapter
        spinColor2.adapter = adapter
        spinColor2.setSelection(selColor2)

        spinColor1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                color1 = hexColor(spinColor1.selectedItem.toString())
                color2 = hexColor(spinColor2.selectedItem.toString())
                if(color1 == color2) {
                    AlertDialog.Builder(this@PlayersActivity).apply {
                        setTitle("Information")
                        setMessage("${spinColor2.selectedItem} color is already selected by Player 2. Please choose other color!")
                        setPositiveButton("OK") { _, _ ->
                            spinColor1.setSelection(selColor1)
                            color1 = hexColor(spinColor1.selectedItem.toString())
                            cardP1.setCardBackgroundColor(Color.parseColor(color1))
                        }
                        setCancelable(false)
                        create().show()
                    }
                } else {
                    selColor1 = spinColor1.selectedItemPosition
                    cardP1.setCardBackgroundColor(Color.parseColor(color1))
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }
        }

        spinColor2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                color1 = hexColor(spinColor1.selectedItem.toString())
                color2 = hexColor(spinColor2.selectedItem.toString())
                if(color1 == color2) {
                    AlertDialog.Builder(this@PlayersActivity).apply {
                        setTitle("Information")
                        setMessage("${spinColor1.selectedItem} color is already selected by Player 1. Please choose other color!")
                        setPositiveButton("OK") { _, _ ->
                            spinColor2.setSelection(selColor2)
                            color2 = hexColor(spinColor2.selectedItem.toString())
                            cardP2.setCardBackgroundColor(Color.parseColor(color2))
                        }
                        setCancelable(false)
                        create().show()
                    }
                } else {
                    selColor2 = spinColor2.selectedItemPosition
                    cardP2.setCardBackgroundColor(Color.parseColor(color2))
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }
        }

        btnPlay.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(EXTRA_PLAYER1_NAME, txtPlayerName1.text.toString())
            intent.putExtra(EXTRA_PLAYER1_COLOR, color1)
            intent.putExtra(EXTRA_PLAYER2_NAME, txtPlayerName2.text.toString())
            intent.putExtra(EXTRA_PLAYER2_COLOR, color2)
            startActivity(intent)
        }
    }

    fun hexColor(selColor:String):String {
        var result = ""
        result = if(selColor == "Red") "#FF0000"
        else if(selColor == "Yellow") "#FFFF00"
        else if(selColor == "Orange") "#FFA500"
        else if(selColor == "Blue") "#0096FF"
        else if(selColor == "Green") "#00FF00"
        else if(selColor == "Purple") "#800080"
        else if(selColor == "Pink") "#FFC0CB"
        else if(selColor == "Gray")"#E6E6E6"
        else if(selColor == "Cyan")"#00FFFF"
        else "#964B00"

        return  result
    }
}