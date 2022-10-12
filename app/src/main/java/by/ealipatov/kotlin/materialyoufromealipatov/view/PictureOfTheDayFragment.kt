package by.ealipatov.kotlin.materialyoufromealipatov.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentPictureOfTheDayBinding
import by.ealipatov.kotlin.materialyoufromealipatov.utils.isConnection
import by.ealipatov.kotlin.materialyoufromealipatov.utils.toast
import by.ealipatov.kotlin.materialyoufromealipatov.viewmodel.PODFragmentViewModel
import by.ealipatov.kotlin.materialyoufromealipatov.viewmodel.PODFragmentViewModelAppState
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.load
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

     //   setBottomAppBar(view)

        binding.chipGroup.setOnCheckedStateChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position.last())?.let {
                when (it.id) {
                    R.id.chip_today -> {
                        getPictureByDate(LocalDate.now())
                    }
                    R.id.chip_yesterday -> {
                        getPictureByDate(LocalDate.now().minusDays(1))
                    }
                    R.id.chip_day_before_yesterday -> {
                        getPictureByDate(LocalDate.now().minusDays(2))
                    }
                }
            }
        }

        binding.chipHdImage.setOnClickListener {
            //Проверим: если чип не нажат - выведем HD, нажат - обычную картинку
            if (chip_hd_image.isChecked) {
                displayPicture(imageHDUrl)
            } else {
                displayPicture(imageUrl)
            }
        }

        binding.inputLayout.setEndIconOnClickListener {
            if (isConnection(requireContext())) {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data =
                        Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
                })
            } else {
                toast(getString(R.string.no_internet_connection))
            }
        }
    }

    /***
     * Запрашиваем изображение по дате (через вью модель)
     */
    private fun getPictureByDate(date: LocalDate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            viewModel.getPicture(date)
        }
        if (chip_hd_image.isChecked)
            chip_hd_image.isChecked = false
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
    private fun displayPicture(url: String) {
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
                        bottomSheet.bottomSheetDescriptionHeader.text =
                            getString(R.string.expand_to_view)
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

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}