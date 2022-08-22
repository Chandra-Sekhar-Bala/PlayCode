package com.chandra.sekhar.playcode.codeScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CodeViewModelFactory(val context : Context) :  ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CodeViewModel::class.java)) {
            return CodeViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}