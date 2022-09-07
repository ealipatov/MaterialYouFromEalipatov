package by.ealipatov.kotlin.materialyoufromealipatov.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentSettingBinding
import by.ealipatov.kotlin.materialyoufromealipatov.utils.*
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {

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

        if (sharedPreferences.contains(THEME_KEY)) {
            when (sharedPreferences.getInt(THEME_KEY, R.style.Theme_MaterialYouFromEalipatov)) {
                R.style.MoonTheme -> {
                    moon_theme.isCheckable = false
                    binding.moonTheme.setChipBackgroundColorResource(R.color.color_connected)
                    binding.selectedThemeName.text = getString(R.string.check_moon_theme)
                }
                R.style.MarsTheme -> {
                    mars_theme.isCheckable = false
                    binding.marsTheme.setChipBackgroundColorResource(R.color.color_connected);
                    binding.selectedThemeName.text = getString(R.string.check_mars_theme)
                }
            }
        }

        //Выбор темы приложения
        binding.chipThemeGroup.setOnCheckedStateChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position[0])?.let {
                when (it.id) {
                    R.id.moon_theme -> {
                        if (it.id != R.style.MoonTheme) {
                            changeTheme(R.style.MoonTheme)
                            toast(getString(R.string.check_moon_theme))
                        }
                    }
                    R.id.mars_theme -> {
                        if (it.id != R.style.MarsTheme) {
                            changeTheme(R.style.MarsTheme)
                            toast(getString(R.string.check_mars_theme))
                        }
                    }
                }
            }
        }

        //Смена режима день/ночь
        if (sharedPreferences.contains(NIGHT_MODE_KEY)) {
            binding.switchNightMod.isChecked = sharedPreferences.getBoolean(NIGHT_MODE_KEY, false)
        }
        binding.switchNightMod.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editor.putBoolean(NIGHT_MODE_KEY, true)
                binding.autoSwitchNightMod.isChecked = false
                editor.remove(AUTO_NIGHT_MODE_KEY)
            } else {
                editor.putBoolean(NIGHT_MODE_KEY, false)
            }
        }

        //Вкл/выкл автоматического ночного режима
        if (sharedPreferences.contains(AUTO_NIGHT_MODE_KEY)) {
            binding.autoSwitchNightMod.isChecked =
                sharedPreferences.getBoolean(AUTO_NIGHT_MODE_KEY, false)
        }
        binding.autoSwitchNightMod.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editor.putBoolean(AUTO_NIGHT_MODE_KEY, true)
                binding.switchNightMod.isChecked = false
                editor.remove(NIGHT_MODE_KEY)

            } else {
                editor.putBoolean(AUTO_NIGHT_MODE_KEY, false)
            }
        }

        //Применияем изменения
        binding.applyBtn.setOnClickListener {
            applyChanges()
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

