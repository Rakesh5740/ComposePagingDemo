package com.example.pagingsample

import androidx.paging.PagingSource
import androidx.paging.PagingState

class ProductPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, Product>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Product> {

        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize

            val response = apiService.getProducts(
                limit = pageSize,
                skip = page
            )

            LoadResult.Page(
                data = response.products,
                prevKey = if (page == 0) null else page - pageSize,
                nextKey = if (response.products.isEmpty())
                    null
                else
                    page + pageSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(
        state: PagingState<Int, Product>
    ): Int? {
        return state.anchorPosition
    }
}