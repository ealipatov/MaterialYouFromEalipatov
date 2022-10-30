package by.ealipatov.kotlin.materialyoufromealipatov.view.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentRecyclerItemEarthBinding
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentRecyclerItemHeaderBinding
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentRecyclerItemMarsBinding
import by.ealipatov.kotlin.materialyoufromealipatov.view.recycler.Data.Companion.TYPE_EARTH
import by.ealipatov.kotlin.materialyoufromealipatov.view.recycler.Data.Companion.TYPE_MARS

class RecyclerAdapter(private var listData:List<Data>, val callbackAdd: AddItem, val callbackRemove: RemoveItem) :
    RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>() {

    fun setListDataAdd(listDataNew:List<Data>, position:Int){
        listData = listDataNew
     //   notifyDataSetChanged() // изменения без анимации
        notifyItemInserted(position)
    }
    fun setListDataRemove(listDataNew:List<Data>, position:Int){
        listData = listDataNew
        notifyItemRemoved(position)
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].type
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
        override fun bind(data: Data) {
            binding.marsTextView.text = data.name
            binding.addItemImageView.setOnClickListener{
                callbackAdd.add(layoutPosition)
              //  callbackAdd.add(adapterPosition)
            }
            binding.removeItemImageView.setOnClickListener{
                callbackRemove.remove(layoutPosition)
            }
        }
    }
    class EarthViewHolder(val binding:FragmentRecyclerItemEarthBinding) :
        BaseViewHolder(binding.root){
        override fun bind(data: Data) {
            binding.earthTextView.text = data.name
        }
    }
    class HeaderViewHolder(val binding: FragmentRecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root){
        override fun bind(data: Data) {
            binding.header.text = data.name
        }
    }
    abstract class BaseViewHolder(view: View) :
        RecyclerView.ViewHolder(view){
            abstract fun bind(data: Data)
    }
}