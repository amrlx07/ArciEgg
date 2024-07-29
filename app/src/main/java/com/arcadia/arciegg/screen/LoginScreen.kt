package com.arcadia.arciegg.screen


import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arcadia.arciegg.R
import com.arcadia.arciegg.uiState.SignInState
import com.arcadia.arciegg.viewModel.SignInViewModel

@Composable
fun LoginScreen(
    onSignUp: () -> Unit,
    @DrawableRes imageResource: Int = R.drawable.flip_2,
    state : SignInState
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    val gradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surfaceVariant
        )
    )
    Box(
        modifier = Modifier.background(gradient)
    ) {
        Image(
            painter = painterResource(imageResource),
            contentDescription = "background image",
            modifier = Modifier
                .offset(x = 160.dp, y = 73.dp)
                .alpha(0.6f)
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TextLanding(modifier = Modifier.padding(top = 103.dp, start = 20.dp, bottom = 450.dp))
            TextIntruks(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )
            ButtonLogin(
                onSignUp = onSignUp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }
}

@Composable
fun TextLanding(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Selamat Datang",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 10.dp),
            style = TextStyle(
                fontSize = 28.sp
            )
        )
        Text(
            text = "ARCI EGG",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 10.dp),
            style = TextStyle(
                fontSize = 24.sp
            )
        )
        Text(
            text = "Aplikasi Inkubator Telur",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 10.dp),
            style = TextStyle(
                fontSize = 16.sp
                )
            )
    }
}
@Composable
fun TextIntruks(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Silahkan masuk/login dengan akun Google",
            color = MaterialTheme.colorScheme.onBackground,
            style = TextStyle(
                fontSize = 12.sp
            )
        )
        Text(
            text = "anda dengan menekan tombol dibawah",
            color = MaterialTheme.colorScheme.onBackground,
            style = TextStyle(
                fontSize = 12.sp
            )
        )
    }
}

@Composable
fun ButtonLogin(
    onSignUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ElevatedButton(
            onClick = onSignUp,
            modifier = Modifier
                .height(61.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(21.dp),
            colors = ButtonDefaults.elevatedButtonColors(MaterialTheme.colorScheme.inversePrimary)
            ) {
                Text(text = "Masuk Dengan akun Google",
                    color = MaterialTheme.colorScheme.inverseOnSurface)
            }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton (
            onClick = { /*TODO*/ },
            modifier = Modifier
                .height(61.dp)
                .fillMaxWidth(),
            enabled = false,
            shape = RoundedCornerShape(21.dp),
            colors = ButtonDefaults.outlinedButtonColors(colorResource(R.color.orange100))
        ) {
            Text(
                text = "Masuk dengan opsi lainnya",
                color = MaterialTheme.colorScheme.onBackground,
                )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true,
    device = "spec:width=1080px,height=2400px,dpi=440"
)
@Composable
fun LoginScreenPreview() {
    val viewModel = viewModel<SignInViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    LoginScreen(onSignUp = {}, imageResource = R.drawable.flip_2, state = state)
}