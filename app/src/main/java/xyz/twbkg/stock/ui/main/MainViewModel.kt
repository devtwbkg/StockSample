package xyz.twbkg.stock.ui.main

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.exceptions.OnErrorNotImplementedException
import timber.log.Timber
import xyz.twbkg.stock.data.Resource
import xyz.twbkg.stock.data.model.db.Category
import xyz.twbkg.stock.data.source.repository.CategoryRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(var categoryRepository: CategoryRepository) : ViewModel() {

    private var disposables = CompositeDisposable()
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun loadContents() {
        disposables.add(categoryRepository.loadContents()
                .subscribe({ t: Resource<List<Category>> ->
                    when (t) {
                        is Resource.Loading -> {
                            // show loading state

                            Timber.d("Resource.Loading")
                        }
                        is Resource.Success -> {
                            // show data
                            Timber.d("Resource.Success")
                        }
                        is Resource.Failure -> {
                            Timber.d("Resource.Failure")
                            // show error state
                        }
                        else -> throw IllegalStateException("State not known or implemented.")
                    }
                }, { t: Throwable ->
                    throw OnErrorNotImplementedException(t) // Explicitly throw this exception to debug.
                }, { Timber.e("Completed.") }))
    }
}
