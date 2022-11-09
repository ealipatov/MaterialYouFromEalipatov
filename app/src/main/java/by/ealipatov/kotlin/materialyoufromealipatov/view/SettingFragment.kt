package by.ealipatov.kotlin.materialyoufromealipatov.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.MainActivity
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentSettingBinding
import by.ealipatov.kotlin.materialyoufromealipatov.utils.*
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment(), CompoundButton.OnCheckedChangeListener {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    lateinit var spannableRainbow: SpannableString

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences(
            SHARED_PREF_FILE,
            Context.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
        spannableRainbow = SpannableString("тема")

      //  setBottomAppBar(view)

        //Проверяем есть ли сохраненная тема и применяем изменения.
        if (sharedPreferences.contains(THEME_KEY)) {
            when (sharedPreferences.getInt(THEME_KEY, R.style.Theme_MaterialYouFromEalipatov)) {
                R.style.MoonTheme -> {
                    moon_theme.isCheckable = false
                    binding.moonTheme.setChipBackgroundColorResource(R.color.color_connected)
                   // binding.selectedThemeName.text = getString(R.string.check_moon_theme)
                    spannableRainbow = SpannableString(getString(R.string.check_moon_theme))
                }
                R.style.MarsTheme -> {
                    mars_theme.isCheckable = false
                    binding.marsTheme.setChipBackgroundColorResource(R.color.color_connected)
                    //binding.selectedThemeName.text = getString(R.string.check_mars_theme)
                    spannableRainbow = SpannableString(getString(R.string.check_mars_theme))
                }
            }
        }

        //Выбор темы приложения
        binding.chipThemeGroup.setOnCheckedStateChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position.last())?.let {
                when (it.id) {
                    R.id.moon_theme -> {
                        changeTheme(R.style.MoonTheme)
                        binding.moonTheme.setChipBackgroundColorResource(R.color.color_connected)
                        binding.marsTheme.setChipBackgroundColorResource(R.color.color_disconnected)
                        moon_theme.isCheckable = false
                        mars_theme.isCheckable = true
                    }
                    R.id.mars_theme -> {
                        changeTheme(R.style.MarsTheme)
                        binding.marsTheme.setChipBackgroundColorResource(R.color.color_connected)
                        binding.moonTheme.setChipBackgroundColorResource(R.color.color_disconnected)
                        mars_theme.isCheckable = false
                        moon_theme.isCheckable = true
                    }
                }
            }
        }

        //Выбипаем сохраненный режим день/ночь/авто
        if (sharedPreferences.contains(THEME_MODE_KEY)) {
            when (sharedPreferences.getString(THEME_MODE_KEY, "Day")) {
                DAY -> {
                    binding.switchNightMod.isChecked = false
                }
                NIGHT -> {
                    binding.switchNightMod.isChecked = true
                }
                AUTO -> {
                    binding.autoSwitchNightMod.isChecked = true
                    binding.switchNightMod.isChecked = false
                }
            }
        }
        //Надуваем слушателеей для переключателей
        binding.switchNightMod.setOnCheckedChangeListener(this)
        binding.autoSwitchNightMod.setOnCheckedChangeListener(this)

        //Применияем изменения по нажатию кнопки
        binding.applyBtn.setOnClickListener {
            applyChanges()
        }
        rainbow(1)
    }

    fun rainbow(i:Int) {
        var currentCount = i
        val x = object : CountDownTimer(20000, 200) {
            override fun onTick(millisUntilFinished: Long) {
                colorText(currentCount)
                currentCount = if (++currentCount>5) 1 else currentCount
            }
            override fun onFinish() {
                rainbow(currentCount)
            }
        }
        x.start()
    }

    private fun colorText(colorFirstNumber:Int){
        binding.selectedThemeName.setText(spannableRainbow, TextView.BufferType.SPANNABLE)
        spannableRainbow = binding.selectedThemeName.text as SpannableString
        val map = mapOf(
            0 to ContextCompat.getColor(requireContext(), R.color.red),
            1 to ContextCompat.getColor(requireContext(), R.color.orange),
            2 to ContextCompat.getColor(requireContext(), R.color.yellow),
            3 to ContextCompat.getColor(requireContext(), R.color.green),
            4 to ContextCompat.getColor(requireContext(), R.color.blue),
            5 to ContextCompat.getColor(requireContext(), R.color.purple_700),
            6 to ContextCompat.getColor(requireContext(),R.color.purple_500)
        )

        removeSpan()

        var colorNumber = colorFirstNumber
        for (i in 0 until binding.selectedThemeName.text.length) {
            if (colorNumber == 5) colorNumber = 0 else colorNumber += 1
            spannableRainbow.setSpan(
                ForegroundColorSpan(map.getValue(colorNumber)),
                i, i + 1,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
    }

    private fun removeSpan(){
        val spans = spannableRainbow.getSpans(
            0, spannableRainbow.length,
            ForegroundColorSpan::class.java
        )
        for (span in spans) {
            spannableRainbow.removeSpan(span)
        }
    }

    /***
     * Слушатель переключателей
     */
    override fun onCheckedChanged(switch: CompoundButton?, isChecked: Boolean) {
        when (switch?.id) {
            R.id.switch_night_mod -> {
                if (isChecked) {
                    editor.putString(THEME_MODE_KEY, NIGHT)
                    MainActivity().changeMode(NIGHT)
                    if (auto_switch_night_mod.isChecked)
                        binding.autoSwitchNightMod.isChecked = false
                } else {
                    if (!auto_switch_night_mod.isChecked){
                        editor.putString(THEME_MODE_KEY, DAY)
                        MainActivity().changeMode(DAY)
                    }
                }
            }
            R.id.auto_switch_night_mod -> {
                if (isChecked) {
                    editor.putString(THEME_MODE_KEY, AUTO)
                    if (switch_night_mod.isChecked)
                        binding.switchNightMod.isChecked = false
                    binding.autoSwitchNightMod.isChecked = true
                } else {
                    if (!switch_night_mod.isChecked)
                        editor.putString(THEME_MODE_KEY, SYSTEM)
                }
            }
        }
    }

    /***
     * Функция смены темы. Сохраняем темы с помощью sharedPreferences и при перезапуске
     * активити считывавем выбранную тему и применяем ее.
     */
    private fun changeTheme(theme: Int) {
        editor.putInt(THEME_KEY, theme)
        requireActivity().setTheme(theme)
    }

    /***
     * Сохраняем изменения и пересоздаем активити.
     */
    private fun applyChanges() {
        editor.apply()
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

