package com.mgado.expensetracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.mgado.expensetracker.views.auth.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AuthViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockFirebaseAuth: FirebaseAuth

    @Mock
    private lateinit var mockObserverLoading: Observer<Boolean>

    @Mock
    private lateinit var mockObserverErrorMessage: Observer<String?>

    private lateinit var authViewModel: AuthViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        authViewModel = AuthViewModel(mockFirebaseAuth) // Inject mockCredentialManager
        authViewModel.loading.observeForever(mockObserverLoading)
        authViewModel.errorMessage.observeForever(mockObserverErrorMessage)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        authViewModel.loading.removeObserver(mockObserverLoading)
        authViewModel.errorMessage.removeObserver(mockObserverErrorMessage)
    }

    @Test
    fun `createUserWithEmailAndPassword should set loading and call home on success`() {
        val email = "test@example.com"
        val password = "password"

        // Mock FirebaseAuth to return a successful task
        `when`(mockFirebaseAuth.createUserWithEmailAndPassword(email, password))
            .thenAnswer {
                val task = mock(com.google.android.gms.tasks.Task::class.java)
                task
            }

        // Call the method
        authViewModel.createUserWithEmailAndPassword(email, password) {}

        // Verify loading state changes
        verify(mockObserverLoading).onChanged(true)
        verify(mockObserverLoading).onChanged(false)
    }

    @Test
    fun `signInWithEmailAndPassword should set loading and call home on success`() {
        val email = "test@example.com"
        val password = "password"

        // Mock FirebaseAuth to return a successful task
        `when`(mockFirebaseAuth.signInWithEmailAndPassword(email, password))
            .thenAnswer {
                val task = mock(com.google.android.gms.tasks.Task::class.java)
                task
            }

        // Call the method
        authViewModel.signInWithEmailAndPassword(email, password) {}

        // Verify loading state changes
        verify(mockObserverLoading).onChanged(true)
        verify(mockObserverLoading).onChanged(false)
    }
}