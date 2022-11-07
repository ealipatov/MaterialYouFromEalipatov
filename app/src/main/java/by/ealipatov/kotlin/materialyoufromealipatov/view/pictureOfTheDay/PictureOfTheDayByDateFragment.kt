package by.ealipatov.kotlin.materialyoufromealipatov.view.pictureOfTheDay

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.view.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentPictureOfTheDayByDateBinding
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
import kotlinx.android.synthetic.main.fragment_animation_slide.*
import java.time.LocalDate


class PictureOfTheDayByDateFragment(private val date: LocalDate) : Fragment() {
    private var _binding: FragmentPictureOfTheDayByDateBinding? = null
    private val binding: FragmentPictureOfTheDayByDateBinding
        get() {
            return _binding!!
        }

    private lateinit var imageTitleDescription: String
    private lateinit var imageDescription: String
    private lateinit var imageUrl: String


    private val viewModel by lazy {
        ViewModelProvider(this)[PODFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayByDateBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner) { appState ->
            renderData(appState)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            viewModel.getPicture(date)
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

    private fun renderData(appState: PODFragmentViewModelAppState) {

        when (appState) {
            is PODFragmentViewModelAppState.Error -> {
                toast(getString(R.string.error_loading_picture))
                imageTitleDescription = getString(R.string.error_loading)
                imageDescription = getString(R.string.error_loading)
            }
            PODFragmentViewModelAppState.Loading -> {}
            is PODFragmentViewModelAppState.Success -> {

                val spannableStringBuilder: SpannableStringBuilder

                val textForTest = "My text \n" +
                        "bullet one\n" +
                        "bullet two\n" +
                        "bullet tree\n" +
                        "bullet four\n" +
                        "bullet five\n"

                spannableStringBuilder = SpannableStringBuilder(textForTest)

                val indexesTestString = textForTest.indexesOf("\n")
                var currentIndex = indexesTestString.first()
                indexesTestString.forEach {
                    if (currentIndex != it) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            spannableStringBuilder.setSpan(
                                BulletSpan(
                                    20,
                                    ContextCompat.getColor(requireContext(), R.color.color_connected),
                                    20
                                ), currentIndex + 1, it, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        } else {
                            spannableStringBuilder.setSpan(
                                BulletSpan(
                                    20,
                                    ContextCompat.getColor(requireContext(), R.color.color_connected)
                                ), currentIndex + 1, it, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }
                    }
                    currentIndex = it
                }


                //Пройти по тексту и все 't' покрасить
                for (i in textForTest.indices) {
                    if (textForTest[i] == 't') {
                        spannableStringBuilder.setSpan(
                            ForegroundColorSpan(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.color_primary
                                )
                            ),
                            i, i + 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE // флаги для разрешения или запрета применения спена для ячейки
                        )
                    }
                }
                //Заменить 'o' на значок планеты
                val bitmap =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_earth)!!.toBitmap()
                for (i in textForTest.indices) {
                    if (textForTest[i] == 'o') {
                        spannableStringBuilder.setSpan(
                            ImageSpan(requireContext(), bitmap),
                            i, i + 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                }

                val pictureData = appState.pictureData
                imageTitleDescription = pictureData.title.toString()
                imageDescription = pictureData.explanation.toString()
                imageUrl = pictureData.url.toString()

                binding.imageTitleDescription.text = imageTitleDescription
                binding.imageDescription.text = imageDescription
                binding.imageDate.text = pictureData.date.toString()

                displayPicture(imageUrl)

                binding.testText.typeface =
                    Typeface.createFromAsset(requireContext().assets, "fonts/Aloevera.ttf")

              //  spannableStringBuilder.insert(3, "word")
                spannableStringBuilder.replace(3, 4,"word")
                binding.testText.text = spannableStringBuilder
            }
        }
    }

    fun String.indexesOf(subString: String, ignoreCase: Boolean = true): List<Int> =
        (if (ignoreCase) Regex(subString, RegexOption.IGNORE_CASE) else Regex(subString))
            .findAll(this).map { it.range.first }.toList()

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}