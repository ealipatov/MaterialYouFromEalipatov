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

class RecyclerAdapter(private val listData:List<Data>) : RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>() {

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

    class MarsViewHolder(val binding:FragmentRecyclerItemMarsBinding) :
        BaseViewHolder(binding.root){
        override fun bind(data: Data) {
            binding.marsTextView.text = data.name
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