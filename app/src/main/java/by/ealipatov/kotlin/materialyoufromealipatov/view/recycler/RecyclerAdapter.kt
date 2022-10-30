package by.ealipatov.kotlin.materialyoufromealipatov.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentRecyclerItemEarthBinding
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentRecyclerItemHeaderBinding
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentRecyclerItemMarsBinding
import by.ealipatov.kotlin.materialyoufromealipatov.view.recycler.Data.Companion.TYPE_EARTH
import by.ealipatov.kotlin.materialyoufromealipatov.view.recycler.Data.Companion.TYPE_MARS

class RecyclerAdapter(private val listData:List<Data>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return listData[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class MarsViewHolder(val binding:FragmentRecyclerItemMarsBinding) : RecyclerView.ViewHolder(binding.root){
    }
    class EarthViewHolder(val binding:FragmentRecyclerItemEarthBinding) : RecyclerView.ViewHolder(binding.root){
    }
    class HeaderViewHolder(val binding: FragmentRecyclerItemHeaderBinding) : RecyclerView.ViewHolder(binding.root){
    }
}