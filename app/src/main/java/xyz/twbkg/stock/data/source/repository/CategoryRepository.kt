package xyz.twbkg.stock.data.source.repository

import io.reactivex.Flowable
import twbkg.xyz.stock.data.source.scope.Local
import twbkg.xyz.stock.data.source.scope.Remote
import xyz.twbkg.stock.data.Resource
import xyz.twbkg.stock.data.model.db.Category
import xyz.twbkg.stock.data.model.response.CategoryResponse
import xyz.twbkg.stock.data.source.local.category.CategoryDao
import xyz.twbkg.stock.data.source.remote.category.CategoryService
import xyz.twbkg.stock.util.NetworkBoundResource
import xyz.twbkg.stock.util.NetworkUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
        @Remote var categoryService: CategoryService,
        @Local var categoryDao: CategoryDao,
        var networkUtils: NetworkUtils
) {
    fun loadContents(): Flowable<Resource<List<Category>>> {
        return object : NetworkBoundResource<List<Category>, CategoryResponse>(networkUtils) {

            override fun saveCallResult(request: CategoryResponse) {
                categoryDao.insertAll(request.data)
            }

            override fun loadFromDb(): Flowable<List<Category>> {
                return categoryDao.findAll()
            }

            override fun createCall(): Flowable<CategoryResponse> {
                return categoryService.getCategory()
            }

        }.asFlowable()
    }
}