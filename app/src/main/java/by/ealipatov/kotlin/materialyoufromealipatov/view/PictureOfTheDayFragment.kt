package by.ealipatov.kotlin.materialyoufromealipatov.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
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


class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding
        get() {
            return _binding!!
        }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner) { appState ->
            renderData(appState)
        }

        viewModel.getPicture()

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))

        setBottomAppBar(view)

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
            R.id.app_bar_fav -> toast("Favourite")
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

        when (appState) {
            is PODFragmentViewModelAppState.Error -> {
                toast(getString(R.string.error_loading_picture))
            }
            PODFragmentViewModelAppState.Loading -> {}
            is PODFragmentViewModelAppState.Success -> {
                val pictureData = appState.pictureData
                val pictureUrl = pictureData.url

                Coil.setImageLoader(imageLoader)
                binding.imageView.load(pictureUrl)
                {
                   error(R.drawable.ic_baseline_no_photography_24)
                   placeholder(R.drawable.loadingfast)
                   crossfade(true)
                }
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
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
//        fun newInstance(pictureData: ServerNasaPODResponseData) = PictureOfTheDayFragment().also {
//            it.arguments = Bundle().apply { putParcelable(BUNDLE_PICTURE_EXTRA, pictureData) }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}