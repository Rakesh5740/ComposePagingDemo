package com.example.pagingsample

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository(
        RetrofitInstance.api
    )

    val products =
        repository.getProducts()
            .cachedIn(viewModelScope)
}