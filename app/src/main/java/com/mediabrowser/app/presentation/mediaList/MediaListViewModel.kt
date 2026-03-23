package com.mediabrowser.app.presentation.mediaList

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mediabrowser.app.domain.useCase.GetMediaListUseCase
import com.mediabrowser.app.presentation.mapper.toItem
import com.mediabrowser.app.presentation.models.MediaItem
import com.mediabrowser.app.shared.toAppError
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

data class MediaListUIState(
    val allItems: List<MediaItem> = emptyList(),
    val items: List<MediaItem> = emptyList(),
    val isLoading: Boolean = false,
    val isPullRefresh: Boolean = false,
    val searchQuery: String = "",
    @param:StringRes val errorMessageRes: Int? = null,
) {
    val isLoadDataProcess get() = isLoading || isPullRefresh
    val isError get() = !isLoadDataProcess && errorMessageRes != null
    val isEmptyState get() = !isError && !isLoadDataProcess && items.isEmpty()
}

class MediaListViewModel(
    private val getMediaListUseCase: GetMediaListUseCase
) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 300L
    }

    private val _state = MutableStateFlow(MediaListUIState(isLoading = true))
    val state: StateFlow<MediaListUIState> = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadData()
    }

    fun loadData(isPullRefresh: Boolean = false) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = !isPullRefresh,
                isPullRefresh = isPullRefresh,
                errorMessageRes = null
            )

            runCatching {
                getMediaListUseCase()
            }.onSuccess { result ->
                val allItems = result.map { it.toItem() }
                _state.value = _state.value.copy(
                    allItems = allItems,
                    items = filterItems(
                        query = _state.value.searchQuery,
                        source = allItems
                    ),
                    isLoading = false,
                    isPullRefresh = false,
                    errorMessageRes = null
                )
            }.onFailure { throwable ->
                _state.value = _state.value.copy(
                    items = emptyList(),
                    allItems = emptyList(),
                    isLoading = false,
                    isPullRefresh = false,
                    errorMessageRes = throwable.toAppError().messageRes
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchJob?.cancel(cause = null)
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_MS)
            _state.value = _state.value.copy(
                searchQuery = query,
                items = filterItems(query, _state.value.allItems)
            )
        }
    }

    private fun filterItems(query: String, source: List<MediaItem>): List<MediaItem> {
        val normalizedQuery = query.trim().lowercase(Locale.getDefault())

        if (normalizedQuery.isBlank()) return source

        return source.filter { item ->
            item.title.lowercase(Locale.getDefault()).contains(normalizedQuery) ||
                    item.description.lowercase(Locale.getDefault()).contains(normalizedQuery) ||
                    item.releaseDate.lowercase(Locale.getDefault()).contains(normalizedQuery)
        }
    }
}