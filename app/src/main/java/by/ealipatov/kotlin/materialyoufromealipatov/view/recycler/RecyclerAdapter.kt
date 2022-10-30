package by.ealipatov.kotlin.materialyoufromealipatov.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentRecyclerItemMarsBinding

class RecyclerAdapter(private val listData:List<Data>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = FragmentRecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context))
        return MarsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class MarsViewHolder(val binding:FragmentRecyclerItemMarsBinding) : RecyclerView.ViewHolder(binding.root){
    }
}