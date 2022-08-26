package by.ealipatov.kotlin.materialyoufromealipatov.view


import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.materialyoufromealipatov.R
import by.ealipatov.kotlin.materialyoufromealipatov.databinding.FragmentSettingBinding
import by.ealipatov.kotlin.materialyoufromealipatov.utils.SELECTED_THEME
import by.ealipatov.kotlin.materialyoufromealipatov.utils.SHARED_PREF_FILE
import by.ealipatov.kotlin.materialyoufromealipatov.utils.THEME_KEY
import by.ealipatov.kotlin.materialyoufromealipatov.utils.toast
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment: Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var themeName: String
    private var themeSelect: Int = 0


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

        themeSelect = sharedPreferences.getInt(THEME_KEY,0)

        when(themeSelect){
            R.style.MoonTheme -> {
                themeName = getString(R.string.check_moon_theme)
                moon_theme.isCheckable = false
                binding.moonTheme.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.green))
                binding.selectedThemeName.text = themeName
            }
            R.style.MarsTheme -> {
                themeName = getString(R.string.check_mars_theme)
                mars_theme.isCheckable = false
                binding.marsTheme.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.green))
                binding.selectedThemeName.text = themeName
            }
        }

        binding.chipThemeGroup.setOnCheckedStateChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position[0])?.let {
                when(it.id){
                    R.id.moon_theme ->{
                        if(themeSelect != R.style.MoonTheme){
                            selectTheme(R.style.MoonTheme)
                            toast(getString(R.string.check_moon_theme))
                        }
                    }
                    R.id.mars_theme ->{
                        if(themeSelect != R.style.MarsTheme){
                            selectTheme(R.style.MarsTheme)
                            toast(getString(R.string.check_mars_theme))
                        }
                    }
                }
            }
        }
    }

    /***
     * Функция смены темы. Сохраняем темы с помощью sharedPreferences и при перезапуске
     * активити считывавем выбранную тему и применяем ее.
     */
    private fun selectTheme(theme: Int){
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(THEME_KEY, theme)
        editor.putBoolean(SHARED_PREF_FILE, true)
        editor.apply()
        requireActivity().recreate()
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(SELECTED_THEME, themeName)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        themeName = savedInstanceState?.getString(SELECTED_THEME).toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = SettingFragment()
    }
}

