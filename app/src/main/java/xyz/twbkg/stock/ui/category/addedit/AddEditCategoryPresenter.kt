package xyz.twbkg.stock.ui.category.addedit

import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscription
import timber.log.Timber
import xyz.twbkg.stock.R
import xyz.twbkg.stock.data.model.db.Category
import xyz.twbkg.stock.data.source.repository.CategoryRepo
import javax.inject.Inject


class AddEditCategoryPresenter @Inject constructor(
        var view: AddEditCategoryContract.View,
        var categoryRepo: CategoryRepo
) : AddEditCategoryContract.Presenter {

    private var categoryId: Int = 0
    private var disposable = CompositeDisposable()
    private lateinit var mSubscription: Subscription

    override fun refreshData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun restoreSavedInstanceState(savedInstanceState: Bundle) {
    }

    override fun restoreArgument(arguments: Bundle) {
    }

    override fun save(title: String, description: String) {

        var category = Category(title, description)
        Timber.d("category $category")
        if (isNewCategory()) {
            createCategory(category)
        } else {
            category.apply {
                id = categoryId
            }.also {
                categoryRepo.update(it)
            }
        }
    }

    override fun dropView() {
        disposable.clear()
    }


    private fun createCategory(category: Category) {
        Timber.i("category $category")
        disposable.add(
                categoryRepo.save(category)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::showSuccessMessage, this::showFailMessage)
        )
    }

    private fun showSuccessMessage() {
        view.showSuccessMessage(R.string.success_insert_action)
        view.clearInputBox()
    }

    private fun showFailMessage(throwable: Throwable) {
        Timber.i("save category showFailMessage $throwable")
        view.showErrorMessage(R.string.action_fail)
    }

    private fun updateCategory() {

    }
//
//    @NonNull
//    fun saveTask(title: String, description: String): Completable {
//        return createTask(title, description)
//                .doOnCompleted(???({ mNavigator.onTaskSaved() }))
//    }
//
//    private fun isNewTask(): Boolean {
//        return mTaskId == null
//    }
//
//    private fun createTask(title: String, description: String): Completable {
//        val newTask: Task
//        if (isNewTask()) {
//            newTask = Task(title, description)
//            if (newTask.isEmpty()) {
//                showSnackbar(R.string.empty_task_message)
//                return Completable.complete()
//            }
//        } else {
//            newTask = Task(title, description, mTaskId)
//        }
//        return mTasksRepository.saveTask(newTask)
//    }

    fun setId(id: Int) {
        this.categoryId = id
    }

    private fun isNewCategory(): Boolean {
        return categoryId == 0
    }
}