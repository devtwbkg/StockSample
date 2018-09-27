package xyz.twbkg.stock.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.exceptions.OnErrorNotImplementedException
import timber.log.Timber
import xyz.twbkg.stock.R
import xyz.twbkg.stock.data.Resource
import xyz.twbkg.stock.data.model.db.Category
import xyz.twbkg.stock.data.source.repository.CategoryRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private var categoryRepository: CategoryRepository) : ViewModel() {

    private var disposables = CompositeDisposable()

    val contents: MutableLiveData<List<Category>> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun loadContents() {
        disposables.add(categoryRepository.loadContents()
                .subscribe({ resource: Resource<List<Category>> ->
                    when (resource) {
                        is Resource.Loading -> {
                            // show loading state
                            errorMessage.value = null
                            loadingVisibility.value = View.VISIBLE
                            Timber.d("Resource.Loading")
                        }
                        is Resource.Success -> {
                            // show data
                            errorMessage.value = null
                            loadingVisibility.value = View.GONE
                            contents.value = resource.data
                            Timber.d("Resource.Success $resource")
                        }
                        is Resource.Failure -> {
                            loadingVisibility.value = View.GONE
                            errorMessage.value = R.string.post_error
                            Timber.d("Resource.Failure")
                            // show error state
                        }
                        else -> throw IllegalStateException("State not known or implemented.")
                    }
                }, { error: Throwable ->
                    errorMessage.value = R.string.post_error
                    throw OnErrorNotImplementedException(error) // Explicitly throw this exception to debug.
                }, { Timber.e("Completed.") }))
    }
}
