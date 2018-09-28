package xyz.twbkg.stock.data.source.repository

import io.reactivex.Flowable
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
        var categoryService: CategoryService,
        var categoryDao: CategoryDao,
        var networkUtils: NetworkUtils
) {
    fun loadContents(): Flowable<Resource<List<Category>>> {
        return object : NetworkBoundResource<List<Category>, CategoryResponse>(networkUtils) {
            override fun createFail(): Flowable<CategoryResponse> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun shouldFetch(data: List<Category>): Boolean = data.isEmpty()

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