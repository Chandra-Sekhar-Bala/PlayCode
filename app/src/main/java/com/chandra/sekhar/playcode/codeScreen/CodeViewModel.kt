package com.chandra.sekhar.playcode.codeScreen

import android.net.Uri
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CodeViewModel : ViewModel() {

    private var uri : Uri? = null
    private var _code = MutableLiveData<String>()
    val code : LiveData<String> get() = _code


    fun setUri(uri: Uri) {
        this.uri = uri
        codeFromUri()
    }

    private fun codeFromUri() {
        _code.value = "Some function"
    }
}