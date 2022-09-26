package com.chandra.sekhar.playcode.codeScreen.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CodeViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CodeViewModel::class.java)) {
            return CodeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}