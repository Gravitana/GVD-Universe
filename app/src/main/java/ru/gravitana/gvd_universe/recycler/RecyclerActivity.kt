package ru.gravitana.gvd_universe.recycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_recycler.*
import kotlinx.android.synthetic.main.activity_recycler_item_earth.view.*
import kotlinx.android.synthetic.main.activity_recycler_item_mars.view.*
import ru.gravitana.gvd_universe.R

class RecyclerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        val data = arrayListOf(
            Pair(Data("Mars", ""), false)
        )

        data.add(0, Pair(Data("Header"), false))

        val adapter = RecyclerActivityAdapter(
            object : RecyclerActivityAdapter.OnListItemClickListener {
                override fun onItemClick(data: Pair<Data, Boolean>) {
                    Toast.makeText(this@RecyclerActivity, data.first.someText, Toast.LENGTH_SHORT).show()
                }
            },
            data
        )

        recyclerView.adapter = adapter
        recyclerActivityFAB.setOnClickListener { adapter.appendItem() }
    }
}
