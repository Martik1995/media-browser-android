package com.mediabrowser.app.presentation.mediaDetails

import com.mediabrowser.app.databinding.FragmentMediaDetailsBinding
import com.mediabrowser.app.presentation.base.BaseFragment

class MediaDetailsFragment :
    BaseFragment<FragmentMediaDetailsBinding>(FragmentMediaDetailsBinding::inflate) {

    companion object {
        const val DETAIL_ID = "DetailId"
    }
}