package com.mediabrowser.app.presentation.mediaDetails

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import coil.load
import com.mediabrowser.app.R
import com.mediabrowser.app.databinding.FragmentMediaDetailsBinding
import com.mediabrowser.app.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.getValue

class MediaDetailsFragment :
    BaseFragment<FragmentMediaDetailsBinding>(FragmentMediaDetailsBinding::inflate) {

    private val viewModel by viewModel<MediaDetailsViewModel> {
        val id = requireArguments().getString(ARG_DETAIL_ID).orEmpty()
        parametersOf(id)
    }

    companion object {
        const val ARG_DETAIL_ID = "arg_detail_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupFlow()
    }

    private fun setupView() = with(binding) {
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        btnRetry.setOnClickListener {
            viewModel.loadData()
        }

        val osPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        if (osPortrait) {
            setupHeaderHeight()
            setupToolbarColors()
        }
    }

    private fun setupFlow() {
        subscribeUiState(viewModel.state) { state ->
            handleState(state)
        }
    }

    private fun handleState(state: MediaDetailsUiState) {
        handleItem(state)
        handleErrorView(state)
        handleSearchView(state)
        handleLoading(state.isLoading)
    }

    private fun handleItem(state: MediaDetailsUiState) = with(binding) {
        val item = state.item ?: return@with

        ivPoster.load(item.imageUrl)
        tvMediaTitle.text = item.title
        tvDescription.text = item.description
        tvReleaseDate.text = getString(R.string.release_date_value, item.releaseDate)
        tvScore.text = getString(R.string.score_value, item.score)
        tvProducer.text = getString(R.string.producer_value, item.producer)
        tvDirector.text = getString(R.string.director_value, item.director)
        tvRunningTime.text = getString(R.string.running_time_value, item.delay)
    }

    private fun handleErrorView(state: MediaDetailsUiState) = with(binding) {
        llError.isVisible = state.isError

        if (state.isError && state.errorMessageRes != null) {
            tvError.text = getString(state.errorMessageRes)
        }
    }

    private fun handleSearchView(state: MediaDetailsUiState) = with(binding) {
        val contentVisible = state.item != null

        abLayout?.isVisible = contentVisible
        nsView?.isVisible = contentVisible
    }

    private fun handleLoading(loading: Boolean) = with(binding) {
        progressBar.isVisible = loading
    }

    private fun setupHeaderHeight() = with(binding) {
        val screenHeight = resources.displayMetrics.heightPixels
        val headerHeight = (screenHeight * 0.4f).toInt()

        ctLayout?.layoutParams = ctLayout.layoutParams.apply {
            height = headerHeight
        }
    }

    private fun setupToolbarColors() = with(binding) {
        abLayout?.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val isCollapsed = kotlin.math.abs(verticalOffset) >= appBarLayout.totalScrollRange

            val titleColor = if (isCollapsed) {
                requireContext().getColor(R.color.black)
            } else {
                requireContext().getColor(R.color.white)
            }

            val iconColor = if (isCollapsed) {
                requireContext().getColor(R.color.black)
            } else {
                requireContext().getColor(R.color.white)
            }

            toolbar.setTitleTextColor(titleColor)
            toolbar.navigationIcon?.setTint(iconColor)
        }
    }
}