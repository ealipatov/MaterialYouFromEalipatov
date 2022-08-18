package by.ealipatov.kotlin.materialyoufromealipatov.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentPictureOfTheDayBinding
import by.ealipatov.kotlin.materialyoufromealipatov.viewmodel.PODFragmentViewModel
import by.ealipatov.kotlin.materialyoufromealipatov.viewmodel.PODFragmentViewModelAppState
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.load


class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding
        get() {
            return _binding!!
        }

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

  //      val picture = arguments?.getParcelable<Weather>(BUNDLE_WEATHER_EXTRA)

        viewModel.getLiveData().observe(viewLifecycleOwner) { appState ->
            renderData(appState)
        }

//        weather?.let {
//            viewModel.getWeather(it.city)
//        }
        viewModel.getPicture()

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
            is PODFragmentViewModelAppState.Error -> {}
            PODFragmentViewModelAppState.Loading -> {}
            is PODFragmentViewModelAppState.Success -> {
                val pictureData = appState.pictureData
                val pictureUrl = pictureData.url

                Coil.setImageLoader(imageLoader)
                binding.image.load(pictureUrl)
                {
                   error(R.drawable.loadingfast)
                   placeholder(R.drawable.loadingfast)
                }
            }
        }
    }

//    companion object {
//        fun newInstance(weather: Weather) = WeatherDetailFragment().also {
//            it.arguments = Bundle().apply { putParcelable(BUNDLE_WEATHER_EXTRA, weather) }
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}