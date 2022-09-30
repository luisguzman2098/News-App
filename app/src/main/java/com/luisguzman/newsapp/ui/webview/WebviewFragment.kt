package com.luisguzman.newsapp.ui.webview

import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.luisguzman.newsapp.R
import com.luisguzman.newsapp.databinding.FragmentWebviewBinding
import com.luisguzman.newsapp.databinding.FragmentWebviewBinding.bind
import com.luisguzman.newsapp.domain.WebViewModel
import com.luisguzman.newsapp.utils.Resource
import com.luisguzman.newsapp.utils.addOnBackPressedCallback
import com.luisguzman.newsapp.utils.showShortToast

class WebviewFragment : Fragment(R.layout.fragment_webview) {

    private lateinit var binding: FragmentWebviewBinding
    private val viewModel: WebViewModel by viewModels()
    private val args: WebviewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = bind(view)
        addOnBackPressedCallback { handleBackPressed() }

        setObservers()
        setListeners()

    }

    private fun setObservers() {

        viewModel.loading.observe(viewLifecycleOwner) { progress ->
            binding.refresh.isRefreshing = progress
        }

        viewModel.showLoading()
    }

    private fun setListeners() {

        val toolbarBack: View = binding.toolbarWebView.getChildAt(0)
        toolbarBack.setOnClickListener {
            handleBackPressed()
        }

        binding.toolbarWebView.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.reload -> {
                   binding.webView.reload()
                }
            }
            true
        }

        binding.toolbarWebView.inflateMenu(R.menu.menu_webview)

        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(args.url)
    }

    private fun handleBackPressed() {
        findNavController().popBackStack()
    }

    inner class WebViewClient : android.webkit.WebViewClient() {

        // Load the URL
        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            viewModel.showLoading()
        }

        // ProgressBar will disappear once page is loaded
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            viewModel.hideLoading()
        }
    }
}