package by.ealipatov.kotlin.materialyoufromealipatov.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.MainActivity
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentSettingBinding
import by.ealipatov.kotlin.materialyoufromealipatov.utils.*
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
                    moon_theme.isChecked = true
                }
                R.style.MarsTheme -> {
                    mars_theme.isChecked = true
                }
            }
        }

        //Выбор темы приложения
        binding.themeGroup.setOnCheckedChangeListener { group, position ->
            group.findViewById<RadioButton>(position)?.let {
                when (it.id) {
                    R.id.moon_theme -> {
                        changeTheme(R.style.MoonTheme)
                    }
                    R.id.mars_theme -> {
                        changeTheme(R.style.MarsTheme)
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
                    if (!auto_switch_night_mod.isChecked) {
                        editor.putString(THEME_MODE_KEY, DAY)
                        MainActivity().changeMode(DAY)
                    }
                }
            }
            R.id.auto_switch_night_mod -> {
                if (isChecked) {
                    editor.putString(THEME_MODE_KEY, AUTO)
                    MainActivity().changeMode(AUTO)
                    if (switch_night_mod.isChecked)
                        binding.switchNightMod.isChecked = false
                    binding.autoSwitchNightMod.isChecked = true
                } else {
                    if (!switch_night_mod.isChecked) {
                        editor.putString(THEME_MODE_KEY, SYSTEM)
                        MainActivity().changeMode(SYSTEM)
                    }
                }
            }
        }
    }

    /***
     * Функция смены темы. Сохраняем темы с помощью sharedPreferences и при перезапуске
     * активити считывавем выбранную тему и применяем ее.
     */
    private fun changeTheme(theme: Int) {
        if (sharedPreferences.getInt(THEME_KEY,0) != theme) {
            editor.putInt(THEME_KEY, theme)
            requireActivity().setTheme(theme)
        }
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

