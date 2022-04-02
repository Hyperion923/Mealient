package gq.kirmanak.mealient.data.disclaimer

import android.content.SharedPreferences
import gq.kirmanak.mealient.extensions.getBooleanOrFalse
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

private const val IS_DISCLAIMER_ACCEPTED_KEY = "IS_DISCLAIMER_ACCEPTED"

@Singleton
class DisclaimerStorageImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : DisclaimerStorage {

    override suspend fun isDisclaimerAccepted(): Boolean {
        Timber.v("isDisclaimerAccepted() called")
        val isAccepted = sharedPreferences.getBooleanOrFalse(IS_DISCLAIMER_ACCEPTED_KEY)
        Timber.v("isDisclaimerAccepted() returned: $isAccepted")
        return isAccepted
    }

    override fun acceptDisclaimer() {
        Timber.v("acceptDisclaimer() called")
        sharedPreferences.edit()
            .putBoolean(IS_DISCLAIMER_ACCEPTED_KEY, true)
            .apply()
    }
}