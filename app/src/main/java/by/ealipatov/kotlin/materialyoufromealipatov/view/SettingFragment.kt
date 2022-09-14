package by.ealipatov.kotlin.materialyoufromealipatov.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
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

        //Проверяем есть ли сохраненная тема и применяем изменения.
        if (sharedPreferences.contains(THEME_KEY)) {
            when (sharedPreferences.getInt(THEME_KEY, R.style.Theme_MaterialYouFromEalipatov)) {
                R.style.MoonTheme -> {
                    moon_theme.isCheckable = false
                    binding.moonTheme.setChipBackgroundColorResource(R.color.color_connected)
                    binding.selectedThemeName.text = getString(R.string.check_moon_theme)
                }
                R.style.MarsTheme -> {
                    mars_theme.isCheckable = false
                    binding.marsTheme.setChipBackgroundColorResource(R.color.color_connected)
                    binding.selectedThemeName.text = getString(R.string.check_mars_theme)
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
                "Day" -> {
                    binding.switchNightMod.isChecked = false
                }
                "Night" -> {
                    binding.switchNightMod.isChecked = true
                }
                "Auto" -> {
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
    }

    /***
     * Слушатель переключателей
     */
    override fun onCheckedChanged(switch: CompoundButton?, isChecked: Boolean) {
        when (switch?.id) {
            R.id.switchNightMod -> {
                if (isChecked) {
                    editor.putString(THEME_MODE_KEY, "Night")
                    if (autoSwitchNightMod.isChecked)
                        binding.autoSwitchNightMod.isChecked = false
                } else {
                    if (!autoSwitchNightMod.isChecked)
                        editor.putString(THEME_MODE_KEY, "Day")
                }
            }
            R.id.autoSwitchNightMod -> {
                if (isChecked) {
                    editor.putString(THEME_MODE_KEY, "Auto")
                    if (switchNightMod.isChecked)
                        binding.switchNightMod.isChecked = false
                    binding.autoSwitchNightMod.isChecked = true
                } else {
                    if (!switchNightMod.isChecked)
                        editor.putString(THEME_MODE_KEY, "System")
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
    }

    /***
     * Сохраняем изменения и пересоздаем активити.
     */
    private fun applyChanges() {
        editor.apply()
        requireActivity().recreate()
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = SettingFragment()
    }
}

