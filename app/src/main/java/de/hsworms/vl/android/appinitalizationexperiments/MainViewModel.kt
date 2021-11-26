package de.hsworms.vl.android.appinitalizationexperiments

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepo: DataRepo,
) : ViewModel() {

    private var _initialized = MutableLiveData<Boolean>(false)

    val isInitialized: LiveData<Boolean>
        get() { return _initialized }

    private val onRepoInitializationChangedObserver = Observer<Boolean> { _ -> _initialized.value =
                dataRepo.isInitialized.value
    }

    init {
        dataRepo.isInitialized.observeForever(onRepoInitializationChangedObserver)
    }

    fun initialize() = viewModelScope.launch {
        dataRepo.initialize()
    }

    override fun onCleared() {
        dataRepo.isInitialized.removeObserver(onRepoInitializationChangedObserver)
        super.onCleared()
    }

    fun getString(): String {
        return dataRepo.getString()
    }

}
