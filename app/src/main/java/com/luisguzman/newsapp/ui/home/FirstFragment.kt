package com.luisguzman.newsapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.luisguzman.newsapp.data.model.Article
import com.luisguzman.newsapp.data.remote.api.dataSource.NewDataSource
import com.luisguzman.newsapp.databinding.FragmentFirstBinding
import com.luisguzman.newsapp.di.NetworkModule
import com.luisguzman.newsapp.domain.NewsViewModel
import com.luisguzman.newsapp.domain.NewsViewModelFactory
import com.luisguzman.newsapp.repository.home.NewRepositoryImpl
import com.luisguzman.newsapp.ui.adapters.homeAdapter.HomeAdapter
import com.luisguzman.newsapp.utils.Resource

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels { NewsViewModelFactory(
        NewRepositoryImpl(
        NewDataSource(NetworkModule.newService)
    ))}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
    }

    private fun setObservers() {

        viewModel.loading.observe(viewLifecycleOwner) { progress ->
            binding.loadingContainer.isVisible = progress
        }

        viewModel.fetchNews().observe(viewLifecycleOwner) { result ->
            when(result) {
                is Resource.Loading -> {
                    viewModel.showLoading()
                }
                is Resource.Success -> {
                    viewModel.hideLoading()
                    setUpRecyclerview(result.data.articles)
                }
                is Resource.Error -> {
                    viewModel.hideLoading()
                    Log.d("NewsServices", "${result.exception}")
                }
            }
        }
    }

    private fun setUpRecyclerview(articleList: List<Article>) {
        binding.rvNews.adapter = HomeAdapter(articleList = articleList, onItemClickListener = {
            onItemClickListener(it)
        })
    }

    private fun onItemClickListener(article: Article) {
        findNavController().navigate(FirstFragmentDirections.actionFirstFragmentToWebviewFragment(
            url = article.url
        ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}