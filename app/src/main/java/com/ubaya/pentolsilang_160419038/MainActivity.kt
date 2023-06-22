package com.ubaya.pentolsilang_160419038

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        val EXTRA_WINNER = "EXTRA_WINNER"
    }

    var player1Turn = true
    var roundCount = 0
    val currResult = arrayOf(
        arrayOf("", "", ""),
        arrayOf("", "", ""),
        arrayOf("", "", "")
    )
    var name1 = ""
    var name2 = ""
    var color1 = ""
    var color2 = ""
    lateinit var countDownTimer:CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name1 = intent.getStringExtra(PlayersActivity.EXTRA_PLAYER1_NAME).toString()
        color1 = intent.getStringExtra(PlayersActivity.EXTRA_PLAYER1_COLOR).toString()
        name2 = intent.getStringExtra(PlayersActivity.EXTRA_PLAYER2_NAME).toString()
        color2 = intent.getStringExtra(PlayersActivity.EXTRA_PLAYER2_COLOR).toString()

        txtName1.text = name1 + " (O)"
        txtName2.text = name2 + " (X)"
        txtTurn1.text = "YOUR TURN"
        txtTurn2.text = "$name1's turn"
        txtTimer2.text = "Waiting..."

        cardPlayer1.setCardBackgroundColor(Color.parseColor(color1))
        cardPlayer2.setCardBackgroundColor(Color.parseColor(color2))

        startTimer("player 1")
    }

    fun buttonClicked(v: View) {
        if(v is Button) {
            when(v.id) {
                R.id.btn00 -> currResult[0][0] = if(player1Turn) "O" else "X"
                R.id.btn01 -> currResult[0][1] = if(player1Turn) "O" else "X"
                R.id.btn02 -> currResult[0][2] = if(player1Turn) "O" else "X"
                R.id.btn10 -> currResult[1][0] = if(player1Turn) "O" else "X"
                R.id.btn11 -> currResult[1][1] = if(player1Turn) "O" else "X"
                R.id.btn12 -> currResult[1][2] = if(player1Turn) "O" else "X"
                R.id.btn20 -> currResult[2][0] = if(player1Turn) "O" else "X"
                R.id.btn21 -> currResult[2][1] = if(player1Turn) "O" else "X"
                R.id.btn22 -> currResult[2][2] = if(player1Turn) "O" else "X"
            }

            if(player1Turn)
                v.setBackgroundResource(R.drawable.pentol)
            else
                v.setBackgroundResource(R.drawable.silang)

            v.isEnabled = false
            roundCount++
            countDownTimer.cancel()

            if(checkForWin(currResult)) {
                val calendar = Calendar.getInstance()
                var dateFormat = SimpleDateFormat("dd MMMM yyyy HH:mm")
                val currDateTime = dateFormat.format(calendar.time)
                if(player1Turn) {
                    AlertDialog.Builder(this).apply {
                        setTitle("Game Over")
                        setMessage("$name1 wins!")
                        setPositiveButton("HOORAY!") {_, _ ->
                            goToResult("player1", currDateTime)
                        }
                        setCancelable(false)
                        create().show()
                    }
                }
                else {
                    AlertDialog.Builder(this).apply {
                        setTitle("Game Over")
                        setMessage("$name2 wins!")
                        setPositiveButton("HOORAY!") {_, _ ->
                            goToResult("player2", currDateTime)
                        }
                        setCancelable(false)
                        create().show()
                    }
                }
            }
            else if(roundCount == 9) {
                val calendar = Calendar.getInstance()
                var dateFormat = SimpleDateFormat("dd MMMM yyyy HH:mm")
                val currDateTime = dateFormat.format(calendar.time)
                AlertDialog.Builder(this).apply {
                    setTitle("Game Over")
                    setMessage("Game draw!")
                    setPositiveButton("OH NO...") {_, _ ->
                        goToResult("draw", currDateTime)
                    }
                    setCancelable(false)
                    create().show()
                }
            }
            else
                player1Turn = !player1Turn

            if(player1Turn) {
                startTimer("player 1")
                txtTurn1.text = "YOUR TURN"
                txtTurn2.text = "$name1's turn"
                txtTimer2.setTextColor(Color.BLACK)
                txtTimer2.text = "Waiting..."
            } else {
                startTimer("player 2")
                txtTurn1.text = "$name2's turn"
                txtTurn2.text = "YOUR TURN"
                txtTimer1.setTextColor(Color.BLACK)
                txtTimer1.text = "Waiting..."
            }
        }
    }

    fun checkForWin(arr: Array<Array<String>>):Boolean {
        for(i in 0 until 3) {
            if((arr[i][0] != "") && (arr[i][0] == arr[i][1]) && (arr[i][0] == arr[i][2])) //horizontal
                return true
            else if((arr[0][i] != "") && (arr[0][i] == arr[1][i]) && (arr[0][i] == arr[2][i])) //vertical
                return true
        }

        if((arr[0][0] != "") && (arr[0][0] == arr[1][1]) && (arr[0][0] == arr[2][2])) //right diagonal
            return true
        if((arr[0][2] != "") && (arr[0][2] == arr[1][1]) && (arr[0][2] == arr[2][0])) //left diagonal
            return true

        return false
    }

    fun goToResult(winner:String, currDateTime:String) {
        var history = History(currDateTime, name1, name2, color1, color2, winner)
        Global.histories.add(history)

        var intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(PlayersActivity.EXTRA_PLAYER1_NAME, name1)
        intent.putExtra(PlayersActivity.EXTRA_PLAYER1_COLOR, color1)
        intent.putExtra(PlayersActivity.EXTRA_PLAYER2_NAME, name2)
        intent.putExtra(PlayersActivity.EXTRA_PLAYER2_COLOR, color2)
        intent.putExtra(MainActivity.EXTRA_WINNER, winner)
        startActivity(intent)
        finish()
    }

    fun startTimer(currPlayer: String) {
        countDownTimer = object: CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerText(millisUntilFinished, currPlayer)
            }

            override fun onFinish() {
                updateTimerText(0, currPlayer)
                player1Turn = !player1Turn
                if(player1Turn) {
                    txtTurn1.text = "YOUR TURN"
                    txtTurn2.text = "$name1's turn"
                    txtTimer2.setTextColor(Color.BLACK)
                    txtTimer2.text = "Waiting..."
                } else {
                    txtTurn1.text = "$name2's turn"
                    txtTurn2.text = "YOUR TURN"
                    txtTimer1.setTextColor(Color.BLACK)
                    txtTimer1.text = "Waiting..."
                }
                countDownTimer.cancel()
                startTimer(if(player1Turn) "player 1" else "player 2")
            }
        }.start()
    }

    fun updateTimerText(currTime: Long, currPlayer:String) {
        var mins = ((currTime / 1000) / 60).toInt()
        var secs = ((currTime / 1000) % 60).toInt()
        val timeFormat = "${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}"

        if(currPlayer == "player 1") {
            txtTimer1.text = timeFormat
            if(currTime < 6000)
                txtTimer1.setTextColor(Color.RED)
            else
                txtTimer1.setTextColor(Color.BLACK)
        }
        else {
            txtTimer2.text = timeFormat
            if(currTime < 6000)
                txtTimer2.setTextColor(Color.RED)
            else
                txtTimer2.setTextColor(Color.BLACK)
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle("Quit Game")
            setMessage("Are you sure you want to quit this game?")
            setPositiveButton("QUIT") {_, _ ->
                if (countDownTimer != null)
                    countDownTimer.cancel()
                finish()
            }
            setNegativeButton("KEEP PLAYING", null)
            setCancelable(false)
            create().show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null)
            countDownTimer.cancel()
    }
}