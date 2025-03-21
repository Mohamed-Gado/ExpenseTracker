package com.mgado.expensetracker.views.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mgado.expensetracker.R
import com.mgado.expensetracker.components.AppCustomLogo
import com.mgado.expensetracker.navigation.AppScreens
import com.mgado.expensetracker.views.auth.widgets.CustomDivider
import com.mgado.expensetracker.views.auth.widgets.EmailInput
import com.mgado.expensetracker.views.auth.widgets.GoogleSignInButton
import com.mgado.expensetracker.views.auth.widgets.PasswordInput
import com.mgado.expensetracker.views.auth.widgets.SubmitButton
import com.mgado.expensetracker.views.auth.widgets.SwitchAuthWidget


@Composable
fun LoginView(navController: NavHostController, viewModel: AuthViewModel = viewModel()) {
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)) {
        AppCustomLogo()
        Spacer(Modifier.height(20.dp))
        LoginForm(
            navController = navController,
            loading = viewModel.loading.value == true,
            viewModel = viewModel,
            onDone = { email, password ->
                viewModel.signInWithEmailAndPassword(email, password){
                    navController.navigate(AppScreens.HomeScreen.name){
                        popUpTo(AppScreens.LoginScreen.name){
                            inclusive = true
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun LoginForm(navController: NavHostController, loading: Boolean, onDone: (String, String) -> Unit, viewModel: AuthViewModel) {
    val webClientId = stringResource(R.string.default_web_client_id)
    val context = LocalContext.current
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val password = rememberSaveable {
        mutableStateOf("")
    }
    val passwordVisibility = rememberSaveable {
        mutableStateOf(false)
    }
    val passwordFocusRequest = FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    EmailInput(emailState = email,
        enabled = !loading,
        onAction = KeyboardActions{
            passwordFocusRequest.requestFocus()
        })
    Spacer(Modifier.height(10.dp))
    PasswordInput(
        modifier = Modifier.focusRequester(passwordFocusRequest),
        enabled = !loading,
        passwordState = password,
        labelId = "Password",
        passwordVisibility = passwordVisibility,
        onAction = KeyboardActions {
            if (!valid) return@KeyboardActions
            keyboardController?.hide()
            onDone(email.value.trim(), password.value.trim())
        }
    )
    Spacer(Modifier.height(20.dp))
    SubmitButton(label = "Login", loading = loading, validInputs = valid) {
        keyboardController?.hide()
        onDone(email.value.trim(), password.value.trim())
    }
    CustomDivider()
    GoogleSignInButton {
        viewModel.signInWithGoogle(webClientId, context = context, home = {
            navController.navigate(AppScreens.HomeScreen.name){
                popUpTo(AppScreens.LoginScreen.name){
                    inclusive = true
                }
            }
        })
    }
    SwitchAuthWidget(label = "Not a existing user?", action = "Register") {
        navController.navigate(AppScreens.RegisterScreen.name){
            popUpTo(AppScreens.LoginScreen.name){
                inclusive = true
            }
        }
    }
}

