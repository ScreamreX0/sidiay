package com.example.sidiay.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.sidiay.R
import com.example.sidiay.databinding.ItemSettingBinding
import java.util.*

class SettingsAdapter(private val settings: List<String>, private val parent: Fragment)
    : RecyclerView.Adapter<SettingsAdapter.SettingViewHolder>() {
    inner class SettingViewHolder(val binding: ItemSettingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val binding = ItemSettingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SettingViewHolder(binding)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        with(holder) {
            settings[position].let {
                binding.iSettingTitle.text = it
            }
        }

        holder.itemView.setOnClickListener {
            when (settings[position]) {
                parent.getString(R.string.change_language) -> Locale.setDefault(Locale("en_en"))
                parent.getString(R.string.change_theme) -> {
                    val preferences = parent.requireActivity().getSharedPreferences("Settings", MODE_PRIVATE)
                    val editor = preferences.edit()

                    val theme = preferences.getString("Theme", "")
                    if (theme == "Dark" || theme == "") {
                        editor.putString("Theme", "Light")
                        editor.apply()
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    } else {
                        editor.putString("Theme", "Dark")
                        editor.apply()
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                }
                //parent.getString(R.string.change_password) ->
            }
        }
    }

    override fun getItemCount() = settings.size
}