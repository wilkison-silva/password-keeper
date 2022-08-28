package br.com.passwordkeeper.presentation.ui.viewmodel.helper

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 * <p>
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 * <p>
 * Note that only one observer is going to be notified of changes.
 */
class SingleLiveData<T> : MutableLiveData<T>() {

    private val isPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) Log.w(
            "SingleLiveData",
            "Some of the observers will not be notified of the changes"
        )

        super.observe(owner, Observer<T> { event ->
            if (isPending.compareAndSet(true, false)) {
                observer.onChanged(event)
            }
        })
    }

    @MainThread
    override fun setValue(@Nullable value: T) {
        isPending.set(true)
        super.setValue(value)
    }
}