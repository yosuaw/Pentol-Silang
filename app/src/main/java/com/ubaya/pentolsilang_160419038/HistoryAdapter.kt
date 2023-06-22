package com.ubaya.pentolsilang_160419038

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_history_card.view.*

class HistoryAdapter(): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.activity_history_card, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val gameHistory = Global.histories[position]
        with(holder.view) {
            txtGameSession.text = "Game Session #${position+1}"
            txtDateTime.text = gameHistory.showDateTime
            txtPlayerHistory1.text = "Player 1 (O): ${gameHistory.playerName1} " + if(gameHistory.winner == "player1") "WIN" else ""
            txtPlayerHistory1.setBackgroundColor(Color.parseColor(gameHistory.color1))
            txtPlayerHistory2.text = "Player 2 (X): ${gameHistory.playerName2} " + if(gameHistory.winner == "player2") "WIN" else ""
            txtPlayerHistory2.setBackgroundColor(Color.parseColor(gameHistory.color2))
        }
    }

    override fun getItemCount() = Global.histories.size
}