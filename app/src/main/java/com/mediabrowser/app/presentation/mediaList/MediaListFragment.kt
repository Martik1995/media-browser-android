package com.mediabrowser.app.presentation.mediaList

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.mediabrowser.app.databinding.FragmentMediaListBinding
import com.mediabrowser.app.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MediaListFragment :
    BaseFragment<FragmentMediaListBinding>(FragmentMediaListBinding::inflate) {

    private val viewModel by viewModel<MediaListViewModel>()
    private val mediaListAdapter by lazy { MediaListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupFlow()
    }

    private fun setupFlow() {
        subscribeUiState(viewModel.state) { state ->
            handleState(state)
        }
    }

    private fun setupView() = with(binding) {
        rvItems.adapter = mediaListAdapter

        btnSearch.setOnClickListener {
            viewModel.onSearchClicked()
        }

        btnRetry.setOnClickListener {
            viewModel.loadData()
        }

        swipeRefresh.setOnRefreshListener {
            viewModel.loadData(isPullRefresh = true)
        }

        etSearch.doAfterTextChanged { editable ->
            viewModel.onSearchQueryChanged(editable?.toString().orEmpty())
        }
    }

    private fun handleState(state: MediaListUIState) {
        handleLoading(state.isLoading)
        handleItems(state.items, state.isLoading, state.errorMessageRes)
        handleErrorView(state.errorMessageRes)
        handlePullRefreshView(state.isPullRefresh)
        handleSearchView(state.searchVisible)
    }

    private fun handleLoading(isLoading: Boolean) = with(binding) {
        progressBar.isVisible = isLoading
    }

    private fun handleSearchView(searchVisible: Boolean) = with(binding) {
        tilSearch.isVisible = searchVisible

        val searchActionIcon =
            if (searchVisible) android.R.drawable.ic_menu_close_clear_cancel
            else android.R.drawable.ic_menu_search

        btnSearch.setImageResource(searchActionIcon)
    }

    private fun handleItems(
        items: List<com.mediabrowser.app.presentation.models.MediaItem>,
        isLoading: Boolean,
        errorMessageRes: Int?
    ) = with(binding) {
        mediaListAdapter.submitList(items)

        val showList = items.isNotEmpty()
        val showEmpty = items.isEmpty() && !isLoading && errorMessageRes == null

        rvItems.isVisible = showList
        layoutEmpty.isVisible = showEmpty
    }

    private fun handleErrorView(errorMessageRes: Int?) = with(binding) {
        val hasError = errorMessageRes != null

        layoutError.isVisible = hasError

        if (hasError) {
            tvError.text = getString(errorMessageRes)
        }
    }

    private fun handlePullRefreshView(isRefreshing: Boolean) {
        binding.swipeRefresh.isRefreshing = isRefreshing
    }
}