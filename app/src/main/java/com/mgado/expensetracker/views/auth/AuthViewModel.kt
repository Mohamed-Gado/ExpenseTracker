package com.mgado.expensetracker.views.auth

import android.content.Context
import android.util.Log
import androidx.credentials.*
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.*
import com.google.android.libraries.identity.googleid.*
import com.google.firebase.Firebase
import com.google.firebase.auth.*
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class AuthViewModel(private val auth: FirebaseAuth = Firebase.auth) : ViewModel() {
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private fun setLoadingState(isLoading: Boolean) {
        _loading.value = isLoading
    }

    private fun setError(message: String?) {
        _errorMessage.value = message
    }

    fun createUserWithEmailAndPassword(email: String, password: String, home: () -> Unit) {
        if (_loading.value == true) return
        setLoadingState(true)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                setLoadingState(false)
                if (task.isSuccessful) {
                    Log.d("Firebase", "createUserWithEmailAndPassword: ${task.result}")
                    home()
                } else {
                    setError(task.exception?.localizedMessage ?: "Unknown error")
                    Log.d("FirebaseException", "createUserWithEmailAndPassword: ${task.exception}")
                }
            }
    }

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) {
        if (_loading.value == true) return
        setLoadingState(true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                setLoadingState(false)
                if (task.isSuccessful) {
                    Log.d("Firebase", "signInWithEmailAndPassword: ${task.result}")
                    home()
                } else {
                    setError(task.exception?.localizedMessage ?: "Unknown error")
                    Log.d("FirebaseException", "signInWithEmailAndPassword: ${task.exception}")
                }
            }
    }

    fun signInWithGoogle(webClientId: String, context: Context, home: () -> Unit) {
        if (_loading.value == true) return
        setLoadingState(true)

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(webClientId)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        viewModelScope.launch {
            try {
                val credentialManager = CredentialManager.create(context)
                val result = credentialManager.getCredential(request = request, context = context)
                handleSignIn(result, home)
            } catch (e: GetCredentialException) {
                setLoadingState(false)
                setError("Google Sign-In failed: ${e.localizedMessage}")
                Log.e("FirebaseAuth", "Unexpected error $e")
            }
        }
    }

    fun handleSignIn(result: GetCredentialResponse, home: () -> Unit) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        auth.signInWithCredential(GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null))
                            .addOnCompleteListener { task ->
                                setLoadingState(false)
                                if (task.isSuccessful) {
                                    val user = auth.currentUser
                                    Log.d("FirebaseAuth", "signInWithCredential:success $user")
                                    home()
                                } else {
                                    setError(task.exception?.localizedMessage ?: "Unknown error")
                                    Log.w("FirebaseAuth", "signInWithCredential:failure", task.exception)
                                }
                            }
                    } catch (e: GoogleIdTokenParsingException) {
                        setLoadingState(false)
                        setError("Invalid Google ID Token")
                        Log.e("FirebaseAuth", "Received an invalid google id token response", e)
                    }
                } else {
                    setLoadingState(false)
                    setError("Unexpected credential type")
                    Log.e("FirebaseAuth", "Unexpected type of credential")
                }
            }
            else -> {
                setLoadingState(false)
                setError("Unexpected credential type")
                Log.e("FirebaseAuth", "Unexpected type of credential")
            }
        }
    }
}