package by.ealipatov.kotlin.materialyoufromealipatov.view.recycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
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
        Pair(Data(TYPE_HEADER,"Заголовок"),false),
        Pair(Data(TYPE_EARTH,"Earth"),false),
        Pair(Data(TYPE_EARTH,"Earth"),false),
        Pair(Data(TYPE_MARS,"Mars"),false),
        Pair(Data(TYPE_EARTH,"Earth"),false),
        Pair(Data(TYPE_EARTH,"Earth"),false),
        Pair(Data(TYPE_EARTH,"Earth"),false),
        Pair(Data(TYPE_MARS,"Mars"),false),
    )

    private lateinit var adapter: RecyclerAdapter

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

        adapter = RecyclerAdapter(data, callbackAdd, callbackRemove, callbackUp, callbackDown, callbackChange, callbackMove)
        binding.recyclerView.adapter = adapter
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView)
    }

    private val callbackAdd = AddItem {
        data.add(it, Pair(Data(TYPE_MARS,"Марс"),false))
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
    private val callbackChange by lazy {
        ChangeItem { pos ->
        data[pos] = data[pos].let {
            it.first to !it.second
        }
        adapter.setListDataChange(data, pos)
    }
    }
    private val callbackMove = MoveToItem { fromPos: Int, toPos: Int ->
        data.removeAt(fromPos).apply {
            data.add(toPos, this)
        }
        adapter.setListDataMoveTo(data, fromPos, toPos)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}