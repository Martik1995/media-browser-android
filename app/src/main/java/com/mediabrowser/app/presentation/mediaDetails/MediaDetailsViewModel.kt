package com.mediabrowser.app.presentation.mediaDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mediabrowser.app.domain.useCase.GetMediaDetailsUseCase
import com.mediabrowser.app.presentation.mapper.toItem
import com.mediabrowser.app.presentation.models.MediaDetailsItem
import com.mediabrowser.app.shared.toAppError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MediaDetailsUiState(
    val id: String,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessageRes: Int? = null,
    val item: MediaDetailsItem? = null
)

class MediaDetailsViewModel(
    id: String,
    private val getMediaDetailsUseCase: GetMediaDetailsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        MediaDetailsUiState(
            id = id,
            isLoading = true
        )
    )
    val state: StateFlow<MediaDetailsUiState> = _state.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            val id = _state.value.id
            _state.value = _state.value.copy(
                isLoading = true,
                isError = false,
                errorMessageRes = null
            )

            runCatching {
                getMediaDetailsUseCase(id)
            }.onSuccess { details ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    item = details.toItem(),
                    isError = false,
                    errorMessageRes = null
                )
            }.onFailure { throwable ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    isError = true,
                    item = null,
                    errorMessageRes = throwable.toAppError().messageRes
                )
            }
        }
    }
}