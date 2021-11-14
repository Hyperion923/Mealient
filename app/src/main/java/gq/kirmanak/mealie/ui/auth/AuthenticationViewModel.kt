package gq.kirmanak.mealie.ui.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gq.kirmanak.mealie.data.auth.AuthRepo
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {
    init {
        Timber.v("constructor called")
    }

    suspend fun authenticate(username: String, password: String, baseUrl: String) {
        Timber.v("authenticate() called with: username = $username, password = $password, baseUrl = $baseUrl")
        authRepo.authenticate(username, password, baseUrl)
    }

    fun authenticationStatuses(): Flow<Boolean> {
        Timber.v("authenticationStatuses() called")
        return authRepo.authenticationStatuses()
    }
}