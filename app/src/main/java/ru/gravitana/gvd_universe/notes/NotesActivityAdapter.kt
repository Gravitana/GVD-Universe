package ru.gravitana.gvd_universe.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycler_item_notes.view.*
import ru.gravitana.gvd_universe.R

class NotesActivityAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: MutableList<Pair<Data, Boolean>>
) :
    RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_NOTE -> NotesViewHolder(
                inflater.inflate(R.layout.activity_recycler_item_notes, parent, false) as View
            )
            else -> HeaderViewHolder(
                inflater.inflate(R.layout.activity_recycler_item_header, parent, false) as View
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            data[position].first.someDescription.isNullOrBlank() -> TYPE_NOTE
            else -> TYPE_NOTE
        }
    }

    fun appendItem() {
        data.add(generateItem())
        notifyItemInserted(itemCount - 1)
    }
    
    private fun generateItem() = Pair(Data("Заметка", ""), false)

    inner class NotesViewHolder(view: View) : BaseViewHolder(view) {

        override fun bind(data: Pair<Data, Boolean>) {
            itemView.notesImageView.setOnClickListener { onListItemClickListener.onItemClick(data) }
            itemView.addItemImageView.setOnClickListener { addItem() }
            itemView.removeItemImageView.setOnClickListener { removeItem() }
            itemView.moveItemDown.setOnClickListener { moveDown() }
            itemView.moveItemUp.setOnClickListener { moveUp() }
            itemView.notesDescriptionTextView.visibility =
                if (data.second) View.VISIBLE else View.GONE
            itemView.notesTextView.setOnClickListener { toggleText() }
        }

        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        private fun addItem() {
            data.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < data.size - 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {

        override fun bind(data: Pair<Data, Boolean>) {
            itemView.setOnClickListener { onListItemClickListener.onItemClick(data) }
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(data: Pair<Data, Boolean>)
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_NOTE = 1
    }
}