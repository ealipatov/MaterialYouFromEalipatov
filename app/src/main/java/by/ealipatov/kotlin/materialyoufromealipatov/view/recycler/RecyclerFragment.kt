package by.ealipatov.kotlin.materialyoufromealipatov.view.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentRecyclerBinding
import by.ealipatov.kotlin.materialyoufromealipatov.view.recycler.Data.Companion.TYPE_EARTH
import by.ealipatov.kotlin.materialyoufromealipatov.view.recycler.Data.Companion.TYPE_HEADER
import by.ealipatov.kotlin.materialyoufromealipatov.view.recycler.Data.Companion.TYPE_MARS

class RecyclerFragment : Fragment() {

    private var _binding: FragmentRecyclerBinding? = null
    private val binding: FragmentRecyclerBinding
        get() {
            return _binding!!
        }

    private val data = arrayListOf(
        Data(TYPE_HEADER,"Заголовок"),
        Data(TYPE_EARTH,"Earth"),
        Data(TYPE_EARTH,"Earth"),
        Data(TYPE_MARS,"Mars"),
        Data(TYPE_EARTH,"Earth"),
        Data(TYPE_EARTH,"Earth"),
        Data(TYPE_EARTH,"Earth"),
        Data(TYPE_MARS,"Mars")
    )

    lateinit var adapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecyclerAdapter(data, callbackAdd, callbackRemove, callbackUp, callbackDown)
        binding.recyclerView.adapter = adapter
    }

    private val callbackAdd = AddItem {
        data.add(it, Data(TYPE_MARS,"Марс"))
        adapter.setListDataAdd(data, it)
    }

    private val callbackRemove = RemoveItem {
        data.removeAt(it)
        adapter.setListDataRemove(data, it)
    }

    private val callbackUp = MoveUpItem {
        data.removeAt(it).apply {
            data.add(it-1, this)
        }
        adapter.setListDataMoveUp(data, it)
    }
    private val callbackDown = MoveDownItem {
        data.removeAt(it).apply {
            data.add(it+1, this)
        }
        adapter.setListDataMoveDown(data, it)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}