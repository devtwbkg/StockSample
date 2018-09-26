package xyz.twbkg.stock.data.source.remote.category

import io.reactivex.Flowable
import retrofit2.http.GET
import xyz.twbkg.stock.data.model.response.CategoryResponse

interface CategoryService {
    @GET("top-headlines")
    fun getCategory(): Flowable<CategoryResponse>
}
