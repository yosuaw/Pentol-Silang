package com.ubaya.pentolsilang_160419038

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val linearLayoutManager = LinearLayoutManager(this)
        with(recyclerView) {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = HistoryAdapter()
        }

        switchReverse.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                linearLayoutManager.reverseLayout = true
                linearLayoutManager.stackFromEnd = true
                with(recyclerView) {
                    layoutManager = linearLayoutManager
                }
            } else {
                linearLayoutManager.reverseLayout = false
                linearLayoutManager.stackFromEnd = false
                with(recyclerView) {
                    layoutManager = linearLayoutManager
                }
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}