package by.ealipatov.kotlin.materialyoufromealipatov.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.ealipatov.kotlin.materialyoufromealipatov.MainActivity
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentPictureOfTheDayBinding
import by.ealipatov.kotlin.materialyoufromealipatov.utils.toast
import by.ealipatov.kotlin.materialyoufromealipatov.viewmodel.PODFragmentViewModel
import by.ealipatov.kotlin.materialyoufromealipatov.viewmodel.PODFragmentViewModelAppState
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.bottom_sheet_layout.view.*
import kotlinx.android.synthetic.main.fragment_picture_of_the_day.*
import java.time.LocalDate


class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding
        get() {
            return _binding!!
        }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var imageTitleDescription: String
    private lateinit var imageDescription: String
    private lateinit var imageHDUrl: String
    private lateinit var imageUrl: String


    private val viewModel by lazy {
        ViewModelProvider(this)[PODFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner) { appState ->
            renderData(appState)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            viewModel.getPicture(LocalDate.now())
        }

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))

        setBottomAppBar(view)

        binding.chipGroup.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let {
                when (it.id){
                    R.id.chip_today ->{
                        viewModel.getPicture(LocalDate.now())
                        chip_hd_image.isChecked = false
                    }
                    R.id.chip_yesterday ->{
                        viewModel.getPicture(LocalDate.now().minusDays(1))
                        chip_hd_image.isChecked = false
                    }
                    R.id.chip_day_before_yesterday ->{
                        viewModel.getPicture(LocalDate.now().minusDays(2))
                        chip_hd_image.isChecked = false
                    }
                }
            }
        }

        binding.chipHdImage.setOnClickListener {
            //Проверим: если чип не нажат - выведем HD, нажат - обычную картинку
            if(!chip_hd_image.isChecked){
              displayPicture(imageHDUrl)
            } else{
              displayPicture(imageUrl)
            }
        }

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> toast(getString(R.string.favorite))
            R.id.app_bar_settings -> activity?.supportFragmentManager?.beginTransaction()?.add(R.id.container, ChipsFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(appState: PODFragmentViewModelAppState) {

        when (appState) {
            is PODFragmentViewModelAppState.Error -> {
                toast(getString(R.string.error_loading_picture))
                imageTitleDescription = getString(R.string.error_loading)
                imageDescription = getString(R.string.error_loading)
            }
            PODFragmentViewModelAppState.Loading -> {}
            is PODFragmentViewModelAppState.Success -> {
                val pictureData = appState.pictureData
                imageTitleDescription = pictureData.title.toString()
                imageDescription = pictureData.explanation.toString()
                imageHDUrl = pictureData.hdurl.toString()
                imageUrl = pictureData.url.toString()

                binding.imageTitleDescription.text = imageTitleDescription
                binding.imageDescription.text = imageDescription
                binding.imageDate.text = pictureData.date.toString()

                displayPicture(imageUrl)
            }
        }
    }

    /***
     * Функция вывода с помощью Coil картинки.
     * На вход принимает url в виде строки.
     * Поддерживает gif и svg
     */
    private fun displayPicture(url: String){
        val imageLoader = ImageLoader.Builder(requireContext())
            .components {
                //GIF
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
                //SVG
                add(SvgDecoder.Factory())
            }
            .build()

        Coil.setImageLoader(imageLoader)
        binding.imageView.load(url)
        {
            error(R.drawable.ic_baseline_no_photography_24)
            placeholder(R.drawable.loadingfast)
            crossfade(true)
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheet.bottomSheetDescriptionHeader.text = getString(R.string.expand_to_view)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        bottomSheet.bottomSheetDescriptionHeader.text = getString(R.string.expand_to_view)
                        bottomSheet.bottomSheetDescription.text = ""
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {

                        bottomSheet.bottomSheetDescriptionHeader.text = imageTitleDescription
                        bottomSheet.bottomSheetDescription.text = imageDescription

                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        bottomSheet.bottomSheetDescriptionHeader.text = getString(R.string.pull_up)
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        //ничего не делаем
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        //ничего не делаем
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        //ничего не делаем
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //не используется
            }
        })
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        binding.fab.setOnClickListener {
            if (isMain) {
                isMain = false
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}