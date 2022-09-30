package com.luisguzman.newsapp.ui.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.luisguzman.newsapp.data.model.Article
import com.luisguzman.newsapp.data.remote.firebase.dataSource.BookmarkDataSource
import com.luisguzman.newsapp.databinding.FragmentSecondBinding
import com.luisguzman.newsapp.domain.BookmarkViewModel
import com.luisguzman.newsapp.domain.BookmarkViewModelFactory
import com.luisguzman.newsapp.repository.bookmark.BookmarkRepositoryImpl
import com.luisguzman.newsapp.ui.adapters.bookmark.BookmarkAdapter
import com.luisguzman.newsapp.ui.adapters.homeAdapter.HomeAdapter
import com.luisguzman.newsapp.utils.Resource

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BookmarkViewModel by viewModels { BookmarkViewModelFactory(BookmarkRepositoryImpl(
        BookmarkDataSource()
    )) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
    }

    private fun setObservers() {

        viewModel.progress.observe(viewLifecycleOwner) { loading ->
            binding.loadingContainer.isVisible = loading
        }

        viewModel.placesHolder.observe(viewLifecycleOwner) { placesHolder ->
            binding.placesHolder.isVisible = placesHolder
        }
        viewModel.fetchBookmark().observe(viewLifecycleOwner) { result ->
            when(result) {
                is Resource.Loading -> {
                    viewModel.showLoading()
                }
                is Resource.Success -> {
                    viewModel.hideLoading()
                    if (result.data.isEmpty()) {
                        viewModel.showPlacesHolder()
                    } else {
                        viewModel.hidePlacesHolder()
                        setUpRecyclerview(result.data)
                    }
                }
                is Resource.Error -> {
                    viewModel.hideLoading()
                }
            }
        }

    }

    private fun setUpRecyclerview(articleList: List<Article>) {
        binding.rvBookmark.adapter = BookmarkAdapter(
            articleList,
            onItemClickListener = { onItemClickListener(it) },
        )
    }

    private fun onItemClickListener(article: Article) {
        val db = Firebase.firestore.collection("bookmarks")
        db.whereEqualTo("url", article.url).get().addOnSuccessListener { result ->
                findNavController().navigate(SecondFragmentDirections.actionSecondFragmentToWebviewFragment(
                    article.url
                ))
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}