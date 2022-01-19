package com.zinc.datastoreexample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.zinc.datastoreexample.MyApplication
import com.zinc.datastoreexample.R
import com.zinc.datastoreexample.data.PreferenceDataStoreModule
import com.zinc.datastoreexample.databinding.ActivityPreferenceBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PreferenceActivity : AppCompatActivity() {

    private lateinit var preferenceDataStoreModule: PreferenceDataStoreModule
    private lateinit var binding: ActivityPreferenceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferenceDataStoreModule = MyApplication.getInstance().getDataStore()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preference)

        setUpViews()
        loadNickName()
    }

    private fun setUpViews() {
        binding.setNickNameButtonClicked { setNickName() }
    }

    private fun loadNickName() {
        lifecycleScope.launch {
            preferenceDataStoreModule.loadNickName.collect {
                binding.nickNameCollect = it
            }
        }
    }

    private fun setNickName() {
        val newNickName = binding.nickNameEditTextView.text.toString()
        lifecycleScope.launch {
            preferenceDataStoreModule.setNickName(newNickName)
        }
    }
}