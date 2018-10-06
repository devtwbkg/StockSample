package xyz.twbkg.stock.ui.category.list

import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import xyz.twbkg.stock.R
import xyz.twbkg.stock.data.model.db.Category
import xyz.twbkg.stock.data.source.repository.impl.CategoryRepository
import javax.inject.Inject

class CategoryPresenter @Inject constructor(
        var view: CategoryContract.View,
        var categoryRepository: CategoryRepository
) : CategoryContract.Presenter {

    private var disposables = CompositeDisposable()
    private var isForceLoading = false

    private lateinit var result: List<Category>

    override fun refreshData() {
        isForceLoading = true
    }

    override fun dropView() {
        disposables.clear()
    }

    override fun restoreSavedInstanceState(savedInstanceState: Bundle) {

    }

    override fun restoreArgument(arguments: Bundle) {
    }

    override fun findItem(index: Int) {
        if (::result.isInitialized) {
            try {
                val item = result[index]
                view.navigationToAddEditCategory(item)
            } catch (e: IndexOutOfBoundsException) {
                view.showErrorMessage(R.string.result_not_found)
            }
        }
    }

    override fun findAll() {
        if (isForceLoading) {
            categoryRepository.refreshData()
        }

        val disposable = categoryRepository
                .findAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::showResult, this::showEmpty)
        Timber.e("after load content")
        disposables.add(disposable)

    }

    private fun showResult(items: List<Category>) {
        Timber.i("ApiResource.Success $items")
        result = items
        isForceLoading = false
        view.hideLoading()
        view.showResult(items)
    }

    private fun showEmpty(throwable: Throwable) {
        Timber.e("ApiResource.throwable $throwable")
        isForceLoading = false
        view.hideLoading()
        view.showEmpty()
    }
}