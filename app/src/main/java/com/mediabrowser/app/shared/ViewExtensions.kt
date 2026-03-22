package com.mediabrowser.app.shared

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment

fun EditText.clear() {
    setText("")
}

fun Fragment.showKeyboard(view: View) {
    view.requestFocus()
    view.post {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun Fragment.hideKeyboardAndClearFocus() {
    val currentView = activity?.currentFocus ?: view
    currentView?.clearFocus()

    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    currentView?.windowToken?.let { token ->
        imm.hideSoftInputFromWindow(token, 0)
    }
}