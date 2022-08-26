package br.com.passwordkeeper.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.usecase.CardUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateNewCardViewModel(
    private val cardUseCase: CardUseCase
) : ViewModel() {

    private val _favorite = MutableLiveData<Boolean>(false)
    val favorite: LiveData<Boolean>
        get() = _favorite

    private fun getCurrentDateTime(): String {
        val date = Calendar.getInstance().time
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        return sdf.format(date)
    }

    fun createCardState(
        description: String,
        email: String,
        password: String,
        category: String,
        isFavorite: Boolean
    ) {
        viewModelScope.launch {
            val date = getCurrentDateTime()
        }
    }

    fun updateFavorite() {
        _favorite.value?.let {
            _favorite.postValue(!it)
        }
    }
}