package br.com.passwordkeeper.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.passwordkeeper.domain.result.viewmodelstate.BottomNavigationState

class MainViewModel : ViewModel() {

    private val _bottomNavigationState = MutableLiveData<BottomNavigationState>()
    val bottomNavigationState: LiveData<BottomNavigationState>
        get() = _bottomNavigationState

    fun updateBottomNavigationVisibility(visibility: Boolean) {
        if (visibility) {
            _bottomNavigationState.postValue(BottomNavigationState.Show)
        }
        else {
            _bottomNavigationState.postValue(BottomNavigationState.Hide)
        }
    }

}