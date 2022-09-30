package com.luisguzman.newsapp.utils

import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment

fun View.hideVisibility() {
    this.visibility = View.GONE
}

fun View.showVisibility() {
    this.visibility = View.VISIBLE
}

fun Fragment.addOnBackPressedCallback(onBackPressed: OnBackPressedCallback.() -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, true, onBackPressed)
}

fun Fragment.showShortToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}