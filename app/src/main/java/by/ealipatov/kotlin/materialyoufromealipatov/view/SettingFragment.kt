package by.ealipatov.kotlin.materialyoufromealipatov.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentSettingBinding
import by.ealipatov.kotlin.materialyoufromealipatov.utils.*
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment: Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var sharedPreferences2: SharedPreferences
    private lateinit var editor2: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_FILE,
            Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        sharedPreferences2 = requireActivity().getSharedPreferences(SHARED_PREF_FILE2,
            Context.MODE_PRIVATE)
        editor2 = sharedPreferences2.edit()

        if(sharedPreferences.contains(THEME_KEY)) {
            when(sharedPreferences.getInt(THEME_KEY,R.style.Theme_MaterialYouFromEalipatov)){
                R.style.MoonTheme -> {
                    moon_theme.isCheckable = false
                    binding.moonTheme.setChipBackgroundColorResource(R.color.green)
                    binding.selectedThemeName.text = getString(R.string.check_moon_theme)
                }
                R.style.MarsTheme -> {
                    mars_theme.isCheckable = false
                    binding.marsTheme.setChipBackgroundColorResource(R.color.green);
                    binding.selectedThemeName.text = getString(R.string.check_mars_theme)
                }
            }
        }

        binding.chipThemeGroup.setOnCheckedStateChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position[0])?.let {
                when(it.id){
                    R.id.moon_theme ->{
                        if(it.id != R.style.MoonTheme){
                        changeTheme(R.style.MoonTheme)
                            toast(getString(R.string.check_moon_theme))
                        }
                    }
                    R.id.mars_theme ->{
                        if(it.id != R.style.MarsTheme){
                            changeTheme(R.style.MarsTheme)
                            toast(getString(R.string.check_mars_theme))
                        }
                    }
                }
            }
        }

        if(sharedPreferences2.contains(NIGHT_MODE_KEY)) {
            binding.switchNightMod.isChecked = sharedPreferences2.getBoolean(NIGHT_MODE_KEY, false)
        }
        binding.switchNightMod.setOnCheckedChangeListener {_, isChecked ->
            if(isChecked){
                changeNightModeTheme(true)
            } else {
                changeNightModeTheme(false)
            }
        }

        binding.applyBtn.setOnClickListener {
            applyChanges()
        }

    }

    /***
     * Функция смены темы. Сохраняем темы с помощью sharedPreferences и при перезапуске
     * активити считывавем выбранную тему и применяем ее.
     */
    private fun changeTheme(theme: Int){
        editor.putInt(THEME_KEY, theme)
    }

    /***
     * Функция смены дневного/ночного режима темы. Сохраняем темы с помощью sharedPreferences
     * и при перезапуске активити считывавем выбранный режим темы и применяем ее.
     */
    private fun changeNightModeTheme(mode: Boolean){
        when(mode){
            true -> {
                editor2.putBoolean(NIGHT_MODE_KEY, true)
            }
            false -> {
                editor2.putBoolean(NIGHT_MODE_KEY, false)
            }
        }
    }

    /***
     * Сохраняем изменения и пересоздаем активити.
     */
    private fun applyChanges(){
        editor.apply()
        editor2.apply()
        requireActivity().recreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = SettingFragment()
    }
}

