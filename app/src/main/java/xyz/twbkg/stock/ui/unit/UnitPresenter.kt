package xyz.twbkg.stock.ui.unit

import android.net.ConnectivityManager
import android.os.Bundle
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscription
import timber.log.Timber
import xyz.twbkg.stock.R
import xyz.twbkg.stock.data.model.db.UnitMeasure
import xyz.twbkg.stock.data.model.request.UnitRequest
import xyz.twbkg.stock.data.model.response.BaseResponse
import xyz.twbkg.stock.data.source.repository.impl.UnitRepository
import xyz.twbkg.stock.util.NetworkUtils
import java.net.ConnectException
import javax.inject.Inject


class UnitPresenter @Inject constructor(
        val view: UnitContract.View,
        private val unitRepository: UnitRepository
) : UnitContract.Presenter {

    @Inject
    lateinit var networkUtils: NetworkUtils

    private val disposable = CompositeDisposable()
    private lateinit var subscription: Disposable

    private var itemList = arrayListOf<UnitMeasure>()

    private var disposables = CompositeDisposable()
    private var isForceLoading = false
    private var unitId: Int = 0
    private var position: Int = -1

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

    override fun findAll() {
        if (isForceLoading) {
            unitRepository.refreshData()
        }

        val disposable = unitRepository
                .findAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::showResult, this::showEmpty)
        Timber.e("after load content")
        disposables.add(disposable)
    }

    override fun findItem(index: Int) {
        try {
            this.position = index
            val item = itemList[index]
            unitId = item.id
            Timber.d("position found $position")
            view.showEditDialog(item)
        } catch (e: IndexOutOfBoundsException) {
            view.showErrorMessage(R.string.result_not_found)
        }
    }

    private fun showResult(items: List<UnitMeasure>) {
        Timber.i("ApiResource.Success $items")
        itemList.clear()
        itemList.addAll(items)
        isForceLoading = false
        view.hideLoading()
        view.showResult(items)
    }

    private fun showEmpty(throwable: Throwable) {
        Timber.e("ApiResource.throwable $throwable")
        isForceLoading = false
        view.hideLoading()
        view.showEmpty()
        showFailMessage(throwable)
    }

    fun setId(id: Int) {
        this.unitId = id
    }

    private fun isNewItem(): Boolean {
        return unitId == 0
    }

    private fun createModel(model: UnitRequest) {
        Timber.i("model $model")
        disposable.add(
                unitRepository.save(model)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ item ->
                            Timber.i("createModel $item")
                            showSuccessMessage()
                            itemList.add(item)
                            view.showResult(item)
                        }, this::showFailMessage)

        )
    }

    override fun save(name: String, description: String) {
        if (isNewItem()) {
            createModel(UnitRequest(name, description))
        } else {
            UnitMeasure(name, description).apply {
                id = unitId
            }.also { unit ->
                unitRepository.update(unit)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            updateValueAtIndex(position, unit)
                        }, this::showFailMessage)
            }
        }
    }

    private fun updateValueAtIndex(position: Int, unit: UnitMeasure) {
        try {
            showSuccessMessage()
            itemList[position] = unit
            view.updateResult(position, unit)
            this.position = -1
        } catch (e: IndexOutOfBoundsException) {
            Timber.e("update position error $e")
        }
    }

    private fun findItemById(id: Int) {
        disposable.add(
                unitRepository.findById(id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->
                            showSuccessMessage()
                            itemList[position] = result
                            view.updateResult(position, result)
                            position = -1
                        }, this::showFailMessage)
        )
    }

    private fun loadLastItem() {
        disposable.add(
                unitRepository.findLastId()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->
                            showSuccessMessage()
                            itemList.add(result)
                            view.showResult(result)
                        }, this::showFailMessage)


        )
    }

    private fun showSuccessMessage() {
        view.showSuccessMessage(R.string.success_action)
    }

    private fun showFailMessage(throwable: Throwable) {
        Timber.e("save unit showFailMessage ${throwable.message}")

        when (throwable) {
            is HttpException -> view.getResolution().onHttpException(throwable)
            // let your location implementation throw a custom exception on timeout, for example
//            is NetworkLocationTimeoutException -> resolution.onNetworkLocationError()
            else -> throwable?.apply { view.getResolution().onGenericRxException(this) }
        }

//        if (throwable.isHttpException()) {
//
//            view.getResolution()?.onNetworkLocationError()
//            throwable
//                    .httpExceptionHandler(view.contextView(), view)
//            view.showSnackBarErrorMessage(throwable
//                    .httpExceptionHandler(view.contextView()))
//        } else {
//            view.showToastErrorMessage(throwable.handleException())
//        }
    }
}