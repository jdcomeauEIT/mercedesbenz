package com.example.mercedesbenz.model

import com.example.mercedesbenz.model.rest.model.PresentationData

sealed class UIState {
    object Empty: UIState()
    data class Loading(val isLoading: Boolean = true): UIState()
    data class Success(val data: List<PresentationData>): UIState()
    data class Error(val errorMessage: String): UIState()
}
