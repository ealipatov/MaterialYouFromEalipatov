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
import by.ealipatov.kotlin.materialyoufromealipatov.utils.SHARED_PREF_FILE
import by.ealipatov.kotlin.materialyoufromealipatov.utils.THEME_KEY
import by.ealipatov.kotlin.materialyoufromealipatov.utils.toast
import com.google.android.material.chip.Chip

class SettingFragment: Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chipThemeGroup.setOnCheckedStateChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position[0])?.let {
                when(it.id){
                    R.id.moon_theme ->{
                        selectTheme(R.style.MoonTheme)
                        toast("Выбрана лунная тема")
                    }
                    R.id.mars_theme ->{
                        selectTheme(R.style.MarsTheme)
                        toast("Выбрана марсианская тема")
                    }
                }
            }
        }
    }

    private fun selectTheme(theme: Int){
        val sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_FILE,
            Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(THEME_KEY, theme)
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

