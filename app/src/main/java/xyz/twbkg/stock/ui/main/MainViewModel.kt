package xyz.twbkg.stock.ui.main

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.view.View
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import xyz.twbkg.stock.R
import xyz.twbkg.stock.data.Resource
import xyz.twbkg.stock.data.model.db.Category
import xyz.twbkg.stock.data.source.repository.CategoryRepo
import xyz.twbkg.stock.data.source.repository.CategoryRepository
import xyz.twbkg.stock.data.source.repository.category.CategoryDataSource
import xyz.twbkg.stock.networking.ApiResource
import xyz.twbkg.stock.networking.Scheduler
import xyz.twbkg.stock.networking.Status
import javax.inject.Inject
import io.reactivex.Completable


class MainViewModel @Inject constructor(
        private var categoryRepository: CategoryRepository,
        var categoryRepo: CategoryRepo
) : ViewModel() {

    private var disposables = CompositeDisposable()

    val contents: MutableLiveData<List<Category>> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }


    fun loadContent() {
        Timber.e("load content")
//        if (showLoadingUI) {
//            mTasksView.setLoadingIndicator(true)
//        }
        categoryRepo.refreshData()

//        // The network request might be handled in a different thread so make sure Espresso knows
//        // that the app is busy until the response is handled.
//        EspressoIdlingResource.increment() // App is busy until further notice

        disposables.clear()
        val disposable = categoryRepo
                .findAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        // onNext
                        { tasks ->
                            errorMessage.value = null
                            loadingVisibility.value = View.GONE
                            contents.value = tasks
                            Timber.i("ApiResource.Success $tasks")
                        },
                        // onError
                        { throwable ->
                            Timber.e("ApiResource.throwable $throwable")
                        })
        Timber.e("after load conent")
        disposables.add(disposable)
    }

//    private fun isNewTask(): Boolean {
//        return mTaskId == null
//    }
//
//    private fun createTask(title: String, description: String): Completable {
//        val newCategory: Category
//        if (isNewTask()) {
//            newCategory = Category(title, description)
//            if (newCategory) {
//                showSnackbar(R.string.empty_task_message)
//                return Completable.complete()
//            }
//        } else {
//            newCategory = Category(title, description, mTaskId)
//        }
//        return categoryRepo.save(newCategory)
//    }


    @SuppressLint("CheckResult")
    fun loadContentV2() {
        categoryRepository.loadContentsV2()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ resource: ApiResource<List<Category>> ->
                    when (resource.status) {
                        Status.LOADING -> {
                            errorMessage.value = null
                            loadingVisibility.value = View.VISIBLE
                            Timber.i("ApiResource.Loading")
                        }
                        Status.SUCCESS -> {
                            errorMessage.value = null
                            loadingVisibility.value = View.GONE
                            contents.value = resource.data
                            Timber.i("ApiResource.Success $resource")
                        }
                        Status.ERROR -> {
                            loadingVisibility.value = View.GONE
                            errorMessage.value = R.string.post_error
                            Timber.e("ApiResource.Failure $resource")
                            // show error state
                        }
                    }
                }, { error -> Timber.e("loadconent v2 error $error") })

    }

    fun loadContents() {
        disposables.add(categoryRepository.loadContents()
                .subscribe({ resource: Resource<List<Category>> ->
                    when (resource) {
                        is Resource.Loading -> {
                            // show loading state
                            errorMessage.value = null
                            loadingVisibility.value = View.VISIBLE
                            Timber.d("ApiResource.Loading")
                        }
                        is Resource.Success -> {
                            // show data
                            errorMessage.value = null
                            loadingVisibility.value = View.GONE
                            contents.value = resource.data
                            Timber.d("ApiResource.Success $resource")
                        }
                        is Resource.LocalSuccess -> {
                            // show data
                            errorMessage.value = null
                            loadingVisibility.value = View.GONE
                            contents.value = resource.data
                            Timber.d("ApiResource.LocalSuccess $resource")
                        }
                        is Resource.Failure -> {
                            loadingVisibility.value = View.GONE
                            errorMessage.value = R.string.post_error
                            Timber.d("ApiResource.Failure $resource")
                            // show error state
                        }

                        is Resource.Error -> {
                            errorMessage.value = R.string.post_error
                            loadingVisibility.value = View.GONE
                            contents.value = resource.data
                            Timber.d("ApiResource.Error ${resource.toString()}")
                        }
                        else -> throw IllegalStateException("State not known or implemented.")
                    }
                }, { error: Throwable ->
                    errorMessage.value = R.string.post_error
                    throw OnErrorNotImplementedException(error) // Explicitly throw this exception to debug.
                }, { Timber.e("Completed.") }))
    }
}
