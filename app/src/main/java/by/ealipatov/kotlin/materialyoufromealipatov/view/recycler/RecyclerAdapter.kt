package by.ealipatov.kotlin.materialyoufromealipatov.view.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentRecyclerItemEarthBinding
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentRecyclerItemHeaderBinding
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentRecyclerItemMarsBinding
import by.ealipatov.kotlin.materialyoufromealipatov.view.recycler.Data.Companion.TYPE_EARTH
import by.ealipatov.kotlin.materialyoufromealipatov.view.recycler.Data.Companion.TYPE_MARS

class RecyclerAdapter(private var listData:List<Pair<Data, Boolean>>,
                      val callbackAdd: AddItem,
                      val callbackRemove: RemoveItem,
                      val callbackUp: MoveUpItem,
                      val callbackDown: MoveDownItem,
                      val callbackChange: ChangeItem,
                      val callbackMove: MoveToItem
) :
    RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>(), ItemTouchHelperAdapter {

    fun setListDataAdd(listDataNew:List<Pair<Data, Boolean>>, position:Int){
        listData = listDataNew
     //   notifyDataSetChanged() // изменения без анимации
        notifyItemInserted(position)
    }
    fun setListDataRemove(listDataNew:List<Pair<Data, Boolean>>, position:Int){
        listData = listDataNew
        notifyItemRemoved(position)
    }
    fun setListDataMoveUp(listDataNew:List<Pair<Data, Boolean>>, position:Int){
        listData = listDataNew
        notifyItemMoved(position, position - 1)
    }
    fun setListDataMoveDown(listDataNew:List<Pair<Data, Boolean>>, position:Int){
        listData = listDataNew
        notifyItemMoved(position, position + 1)
    }
    fun setListDataChange(listDataNew:List<Pair<Data, Boolean>>, position:Int){
        listData = listDataNew
        notifyItemChanged(position)
    }
    fun setListDataMoveTo(listDataNew:List<Pair<Data, Boolean>>, fromPosition: Int, toPosition: Int){
        listData = listDataNew
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].first.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

       return when (viewType) {
            TYPE_EARTH -> {
                val binding = FragmentRecyclerItemEarthBinding.inflate(LayoutInflater.from(parent.context))
                EarthViewHolder(binding)
            }
            TYPE_MARS -> {
                val binding = FragmentRecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context))
                MarsViewHolder(binding)
            }
            else -> {
                val binding = FragmentRecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context))
               HeaderViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MarsViewHolder(val binding:FragmentRecyclerItemMarsBinding) :
        BaseViewHolder(binding.root){
        override fun bind(data: Pair<Data, Boolean>) {
            binding.marsTextView.text = data.first.name

            binding.addItemImageView.setOnClickListener{
                callbackAdd.add(layoutPosition)
              //  callbackAdd.add(adapterPosition)
            }

            binding.removeItemImageView.setOnClickListener{
                callbackRemove.remove(layoutPosition)
            }

            binding.moveItemUp.setOnClickListener{
                callbackUp.moveUp(layoutPosition)
            }

            binding.moveItemDown.setOnClickListener{
                callbackDown.moveDown(layoutPosition)
            }

            binding.marsDescriptionTextView.visibility = if (listData[layoutPosition].second) View.VISIBLE else View.GONE

            binding.marsImageView.setOnClickListener{
                callbackChange.change(layoutPosition)
            }
        }

    }
    class EarthViewHolder(val binding:FragmentRecyclerItemEarthBinding) :
        BaseViewHolder(binding.root){
        override fun bind(data: Pair<Data, Boolean>) {
            binding.earthTextView.text = data.first.name
        }
    }

    class HeaderViewHolder(val binding: FragmentRecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root){
        override fun bind(data: Pair<Data, Boolean>) {
            binding.header.text = data.first.name
        }
    }
    abstract class BaseViewHolder(view: View) :
        RecyclerView.ViewHolder(view), ItemTouchHelperViewHolder {
            abstract fun bind(data: Pair<Data, Boolean>)

        override fun onItemSelected() {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.color_accent))
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        callbackMove.moveTo(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        callbackRemove.remove(position)
    }
}