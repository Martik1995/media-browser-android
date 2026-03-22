package com.mediabrowser.app.presentation.mediaList

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mediabrowser.app.R
import com.mediabrowser.app.databinding.FragmentMediaListBinding
import com.mediabrowser.app.presentation.base.BaseFragment
import com.mediabrowser.app.presentation.mediaDetails.MediaDetailsFragment
import com.mediabrowser.app.presentation.models.MediaItem
import com.mediabrowser.app.shared.clear
import com.mediabrowser.app.shared.hideKeyboardAndClearFocus
import com.mediabrowser.app.shared.showKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class MediaListFragment :
    BaseFragment<FragmentMediaListBinding>(FragmentMediaListBinding::inflate) {

    private val viewModel by viewModel<MediaListViewModel>()
    private val mediaListAdapter by lazy { MediaListAdapter { openDetailFragment(it) } }

    private fun openDetailFragment(it: MediaItem) {
        val bundle = Bundle().apply {
            putString(MediaDetailsFragment.DETAIL_ID, it.id)
        }
        findNavController().navigate(
            R.id.mediaDetailsFragment,
            bundle
        )
    }

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
        rvItems.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        btnRetry.setOnClickListener {
            viewModel.loadData()
        }

        swipeRefresh.setOnRefreshListener {
            viewModel.loadData(isPullRefresh = true)
        }

        tilSearch.setEndIconOnClickListener {
            handleEndIconClick()
        }

        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboardAndClearFocus()
                true
            } else {
                false
            }
        }

        etSearch.doAfterTextChanged { editable ->
            viewModel.onSearchQueryChanged(editable?.toString().orEmpty())
        }
    }

    private fun handleEndIconClick() = with(binding) {
        val isRemoveQuery = viewModel.state.value.searchQuery.isNotEmpty()
        if (isRemoveQuery) {
            etSearch.clear()
            hideKeyboardAndClearFocus()
        } else {
            showKeyboard(etSearch)
        }
    }

    private fun handleState(state: MediaListUIState) {
        handleItems(state)
        handleErrorView(state)
        handleSearchView(state)
        handleLoading(state.isLoading)
        handlePullRefreshView(state.isPullRefresh)
    }

    private fun handleLoading(isLoading: Boolean) = with(binding) {
        progressBar.isVisible = isLoading
    }

    private fun handleSearchView(state: MediaListUIState) = with(binding) {
        val contentVisible = state.allItems.isNotEmpty()

        tilSearch.isVisible = contentVisible

        val endIconRes =
            if (state.searchQuery.isNotEmpty()) android.R.drawable.ic_menu_close_clear_cancel
            else android.R.drawable.ic_menu_search

        tilSearch.setEndIconDrawable(endIconRes)
    }

    private fun handleItems(
        state: MediaListUIState,
    ) = with(binding) {
        mediaListAdapter.submitList(state.items)

        rvItems.isVisible = !state.isEmptyState
        tvEmpty.isVisible = state.isEmptyState
    }

    private fun handleErrorView(state: MediaListUIState) = with(binding) {
        layoutError.isVisible = state.isError

        if (state.isError && state.errorMessageRes != null) {
            tvError.text = getString(state.errorMessageRes)
        }
    }

    private fun handlePullRefreshView(isRefreshing: Boolean) {
        binding.swipeRefresh.isRefreshing = isRefreshing
    }
}