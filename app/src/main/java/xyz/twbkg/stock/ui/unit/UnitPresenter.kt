package xyz.twbkg.stock.ui.unit

import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import xyz.twbkg.stock.R
import xyz.twbkg.stock.data.model.db.UnitMeasure
import xyz.twbkg.stock.data.source.repository.impl.UnitRepository
import javax.inject.Inject

class UnitPresenter @Inject constructor(
        val view: UnitContract.View,
        val unitRepository: UnitRepository
) : UnitContract.Presenter {

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
    }

    fun setId(id: Int) {
        this.unitId = id
    }

    private fun isNewItem(): Boolean {
        return unitId == 0
    }

    private fun createModel(model: UnitMeasure) {
        Timber.i("model $model")
        disposable.add(
                unitRepository.save(model)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ loadLastItem() }, this::showFailMessage)
        )
    }

    override fun save(name: String, description: String) {

        val unit = UnitMeasure(name, description)
        Timber.d("unit $unit")
        if (isNewItem()) {
            createModel(unit)
        } else {
            unit.apply {
                id = unitId
            }.also {
                unitRepository.update(it)
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
            view.updateReult(position, unit)
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
                            view.updateReult(position, result)
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
        Timber.e("save unit showFailMessage $throwable")
        view.showErrorMessage(R.string.action_fail)
    }
}