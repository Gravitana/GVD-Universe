package ru.gravitana.gvd_universe.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_recycler.*
import kotlinx.android.synthetic.main.activity_recycler_item_earth.view.*
import kotlinx.android.synthetic.main.activity_recycler_item_mars.view.*
import ru.gravitana.gvd_universe.R

class NotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        val data = arrayListOf(
            Pair(Data("Note", ""), false)
        )

        data.add(0, Pair(Data("Заметки"), false))

        val adapter = NotesActivityAdapter(
            object : NotesActivityAdapter.OnListItemClickListener {
                override fun onItemClick(data: Pair<Data, Boolean>) {
                    Toast.makeText(this@NotesActivity, data.first.someText, Toast.LENGTH_SHORT).show()
                }
            },
            data
        )

        recyclerView.adapter = adapter
        recyclerActivityFAB.setOnClickListener { adapter.appendItem() }
    }
}
