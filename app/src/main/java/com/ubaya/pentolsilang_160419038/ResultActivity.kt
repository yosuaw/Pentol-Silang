package com.ubaya.pentolsilang_160419038

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        var name1 = intent.getStringExtra(PlayersActivity.EXTRA_PLAYER1_NAME).toString()
        var color1 = intent.getStringExtra(PlayersActivity.EXTRA_PLAYER1_COLOR).toString()
        var name2 = intent.getStringExtra(PlayersActivity.EXTRA_PLAYER2_NAME).toString()
        var color2 = intent.getStringExtra(PlayersActivity.EXTRA_PLAYER2_COLOR).toString()
        var winner = intent.getStringExtra(MainActivity.EXTRA_WINNER).toString()

        txtNameResult1.text = name1 + " (O)"
        txtNameResult2.text = name2 + " (X)"
        cardPlayerResult1.setCardBackgroundColor(Color.parseColor(color1))
        cardPlayerResult2.setCardBackgroundColor(Color.parseColor(color2))

        if(winner == "player1") {
            txtResult1.text = "YOU WIN!"
            txtResult2.text = "YOU LOSE!"
        } else if(winner == "player2") {
            txtResult2.text = "YOU WIN!"
            txtResult1.text = "YOU LOSE!"
        } else {
            txtResult2.text = "IT'S A DRAW..."
            txtResult1.text = "IT'S A DRAW..."
        }

        btnPlayAgain.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra(PlayersActivity.EXTRA_PLAYER1_NAME, name1)
            intent.putExtra(PlayersActivity.EXTRA_PLAYER1_COLOR, color1)
            intent.putExtra(PlayersActivity.EXTRA_PLAYER2_NAME, name2)
            intent.putExtra(PlayersActivity.EXTRA_PLAYER2_COLOR, color2)
            startActivity(intent)
            finish()
        }

        btnHistory.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}