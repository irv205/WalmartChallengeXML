package com.irv205.walmartchallengexml.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irv205.walmartchallengexml.core.util.ResponseHandler
import com.irv205.walmartchallengexml.domain.model.Product
import com.irv205.walmartchallengexml.domain.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _errorMessage.value = null

            when(val response = repository.getProducts()) {
                is ResponseHandler.Error -> {
                    _errorMessage.value = response.message
                }
                is ResponseHandler.Success -> {
                    response.data.let {
                        _products.value = it
                    }
                }
            }
            _loading.value = false
        }
    }
}