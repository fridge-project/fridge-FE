package com.example.alne.presentation.view.Recipe

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.alne.GlobalApplication
import com.example.alne.domain.model.recipe
import com.example.alne.utils.Recipe_TYPE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipePagingSource(val service: String?, val type: Recipe_TYPE) : PagingSource<Int, recipe>() {

    override fun getRefreshKey(state: PagingState<Int, recipe>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, recipe> {
        return try{
            var data: List<recipe> = ArrayList()
            val nextPageNumber = params.key ?: 1

            CoroutineScope(Dispatchers.IO).launch {
                if(type == Recipe_TYPE.DEFAULT){
                    data = GlobalApplication.appDatabase.recipeDao().getAll(nextPageNumber)
                }else if(type == Recipe_TYPE.CATEGORY1){
                    data = GlobalApplication.appDatabase.recipeDao().getList(nextPageNumber, service)
                }else if(type == Recipe_TYPE.CATEGORY2){
                    var list = service!!.split(" ")
                    Log.d("list1", list[0] + " " + list[1])
                    data = GlobalApplication.appDatabase.recipeDao().getList1(nextPageNumber, list[0], list[1])
                }else if(type == Recipe_TYPE.SEARCH){
                    data = GlobalApplication.appDatabase.recipeDao().getAllRecipe()
                        .filter {
                            it.recipe.contains(service!!)
                        }
                }else{

                }
                Log.d("PagingData", data.toString())
            }.join()

            LoadResult.Page(
                data = data,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber-1,
                nextKey = if(data.isNullOrEmpty()) null else nextPageNumber+1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}