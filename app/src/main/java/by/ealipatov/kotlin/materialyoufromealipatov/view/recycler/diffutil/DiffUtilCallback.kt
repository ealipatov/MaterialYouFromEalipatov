package by.ealipatov.kotlin.materialyoufromealipatov.view.recycler.diffutil

import androidx.recyclerview.widget.DiffUtil
import by.ealipatov.kotlin.materialyoufromealipatov.view.recycler.Data

class DiffUtilCallback(
    private var oldItem: List<Pair<Data, Boolean>>,
    private var newItem: List<Pair<Data, Boolean>>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItem.size
    }

    // можно так записать
    override fun getNewListSize() = newItem.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItem[oldItemPosition].first.id == newItem[newItemPosition].first.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItem[oldItemPosition].first.name == newItem[newItemPosition].first.name
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
        val old = oldItem[oldItemPosition]
        val new = newItem[newItemPosition]

        return Change(old, new)
    }
}

data class Change<T>(val oldData: T, val newData: T)

fun <T> createCombinedPayload(payloads: List<Change<T>>): Change<T> {
    assert(payloads.isNotEmpty())
    val firstChange = payloads.first()
    val secondChange = payloads.last()

    return Change(firstChange.oldData, secondChange.newData)
}
