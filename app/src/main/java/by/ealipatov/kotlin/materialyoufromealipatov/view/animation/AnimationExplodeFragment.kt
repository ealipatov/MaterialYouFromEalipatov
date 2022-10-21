package by.ealipatov.kotlin.materialyoufromealipatov.view.animation


import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentAnimationExplodeBinding

class AnimationExplodeFragment: Fragment() {
    private var _binding: FragmentAnimationExplodeBinding? = null
    private val binding: FragmentAnimationExplodeBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationExplodeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = Adapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    inner class Adapter:RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
           return MyViewHolder(
               LayoutInflater.from(parent.context).inflate(
                   R.layout.fragment_animation_explode_item,
                   parent,false) as View
           )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            holder.itemView.setOnClickListener{
                //как бы бланк
                val rect = Rect()
                //в бланк запишем координаты нажатой кнопки
                it.getGlobalVisibleRect(rect)

                val explode = Explode()
                explode.duration = 1000L
                //координаты прямоугольника откуда будет разлет взрыва (эпицентр)
                explode.epicenterCallback = object : Transition.EpicenterCallback(){
                    override fun onGetEpicenter(transition: Transition): Rect {
                        return rect
                    }
                }
                TransitionManager.beginDelayedTransition(binding.recyclerView, explode)
                binding.recyclerView.adapter = null
            }
        }

        override fun getItemCount(): Int {
           return 28
        }

        inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view)

    }
}