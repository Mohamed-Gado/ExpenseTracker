package com.mgado.expensetracker.views.auth.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mgado.expensetracker.R
import com.mgado.expensetracker.components.InputField

@Composable
fun SubmitButton(label: String, loading: Boolean, validInputs: Boolean, onClick: ()->Unit) {
    Button(onClick = onClick,
        modifier = Modifier
            .height(48.dp)
            .width(226.dp),
        enabled = !loading && validInputs,
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.green)),
        shape = RoundedCornerShape(5.dp) ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = label,
            style = TextStyle(fontWeight = FontWeight.Bold, color = Color.White, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
        )
    }
}

@Composable
fun PasswordInput(modifier: Modifier,
                  passwordState: MutableState<String>,
                  labelId: String,
                  enabled: Boolean = true,
                  passwordVisibility: MutableState<Boolean>,
                  imeAction: ImeAction = ImeAction.Done,
                  onAction: KeyboardActions = KeyboardActions.Default) {
    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()
    InputField(
        valueState = passwordState,
        labelId = labelId,
        isSingleLine = true,
        modifier = modifier,
        enabled = enabled,
        keyboardType = KeyboardType.Password,
        imeAction = imeAction,
        onAction = onAction,
        visualTransformation = visualTransformation,
        trailingIcon =  { ToggleVisibility(visibility = passwordVisibility) }
    )
}


@Composable
fun ToggleVisibility(visibility: MutableState<Boolean>) {
    val visible = visibility.value
    IconButton(onClick = {visibility.value = !visible}) {
        Icon(imageVector = if (visibility.value)
            Icons.Filled.Visibility
        else Icons.Filled.VisibilityOff, contentDescription = "visibility icon")
    }
}


@Composable
fun EmailInput(modifier: Modifier = Modifier,
               emailState: MutableState<String>,
               labelId: String = "Email",
               enabled: Boolean = true,
               imeAction: ImeAction = ImeAction.Next,
               onAction: KeyboardActions = KeyboardActions.Default){
    InputField(modifier = modifier,
        valueState = emailState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction)
}

@Composable
fun CustomDivider(){
    Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 20.dp)) {
        HorizontalDivider(modifier = Modifier.width(107.dp), thickness = 1.dp, color = Color.Black)
        Text("OR",
            style = TextStyle(color = Color.Black, fontSize = MaterialTheme.typography.bodyLarge.fontSize),
            modifier = Modifier.padding(horizontal = 8.dp))
        HorizontalDivider(modifier = Modifier.width(107.dp), thickness = 1.dp, color = Color.Black)
    }
}

@Composable
fun GoogleSignInButton(onClick: ()->Unit) {
    Button(onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.shadow_green)),
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(5.dp) ) {
        Text(text = "Continue with Google",
            style = TextStyle(color = Color.Black, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
        )
    }
}

@Composable
fun SwitchAuthWidget(label: String, action: String, onClick: () -> Unit){
    Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 10.dp)) {
        Text(label, style = TextStyle(color = Color.Black, fontSize = MaterialTheme.typography.bodyLarge.fontSize))
        TextButton(onClick = onClick, contentPadding = PaddingValues(4.dp)) {
            Text(action, style = TextStyle(color = colorResource(R.color.green), fontSize = MaterialTheme.typography.bodyLarge.fontSize))
        }
    }
}

@Composable
fun TermsWidget(termsAgreement: MutableState<Boolean>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Checkbox(
            checked = termsAgreement.value,
            onCheckedChange = { termsAgreement.value = it },
            modifier = Modifier
                .background(colorResource(R.color.shadow_green))
                .width(16.dp)
                .height(16.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = colorResource(R.color.green), uncheckedColor = colorResource(
                    R.color.shadow_green
                )
            )
        )
        Column(modifier = Modifier.padding(horizontal = 5.dp)) {
            Text(
                "Agree terms and conditions",
                style = TextStyle(
                    color = colorResource(R.color.black),
                    fontSize = MaterialTheme.typography.labelSmall.fontSize
                )
            )
            Row {
                Text(
                    "Check our terms and condition and privacy policy",
                    style = TextStyle(
                        color = colorResource(R.color.black),
                        fontSize = MaterialTheme.typography.labelSmall.fontSize
                    )
                )
                Text(
                    "here.",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clickable { },
                    style = TextStyle(
                        color = colorResource(R.color.blue),
                        fontSize = MaterialTheme.typography.labelSmall.fontSize
                    )
                )
            }
        }
    }
}