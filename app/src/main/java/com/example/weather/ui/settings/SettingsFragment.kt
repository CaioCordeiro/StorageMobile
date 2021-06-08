package com.example.weather.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
                ViewModelProvider(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val save: Button = root.findViewById(R.id.saveButton)
        val language: RadioGroup =  root.findViewById(R.id.languageButtons)
        val temperature: RadioGroup =  root.findViewById(R.id.temperatureButtons)

        save.setOnClickListener {
            // Getting Language
            val selectedLanguage: Int = language!!.checkedRadioButtonId
            val radioButtonLanguage: RadioButton = root.findViewById(selectedLanguage)
            // Getting Temperature
            val selectedTemperature: Int = temperature!!.checkedRadioButtonId
            val radioButtonTemperature: RadioButton = root.findViewById(selectedTemperature)
            // Logging
            // TODO: Add this to SharedPreference
            Log.i("RADIO GROUP LANGUAGE", radioButtonLanguage.text.toString());
            Log.i("RADIO GROUP LANGUAGE", radioButtonTemperature.text.toString());
        }

        return root
    }
}