package xyz.twbkg.stock.ui.category.list

import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import xyz.twbkg.stock.data.model.db.Category
import xyz.twbkg.stock.data.source.repository.CategoryRepo
import javax.inject.Inject

class CategoryPresenter @Inject constructor(
        var view: CategoryContract.View,
        var categoryRepo: CategoryRepo
) : CategoryContract.Presenter {

    private var disposables = CompositeDisposable()
    private var isForceLoading = false

    override fun refreshData() {
        isForceLoading = true
    }

    override fun dropView() {
        disposables.clear()
    }

    override fun findAll() {
        if (isForceLoading) {
            categoryRepo.refreshData()
        }

        val disposable = categoryRepo
                .findAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::showResult, this::showEmpty)
        Timber.e("after load conent")
        disposables.add(disposable)

    }

    private fun showResult(items: List<Category>) {
        Timber.i("ApiResource.Success $items")
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