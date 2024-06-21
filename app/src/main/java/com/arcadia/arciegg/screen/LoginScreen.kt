package com.arcadia.arciegg.screen


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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arcadia.arciegg.R

@Composable
fun LoginScreen(
    onSignUp: () -> Unit,
    @DrawableRes imageResource: Int
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            colorResource(id = R.color.orange100),
            colorResource(id = R.color.orange500)
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
            modifier = Modifier.padding(bottom = 10.dp),
            style = TextStyle(
                fontSize = 28.sp
            )
        )
        Text(
            text = "ARCI EGG",
            modifier = Modifier.padding(bottom = 10.dp),
            style = TextStyle(
                fontSize = 24.sp
            )
        )
        Text(
            text = "Aplikasi Inkubator Telur",
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
            style = TextStyle(
                fontSize = 12.sp
            )
        )
        Text(
            text = "anda dengan menekan tombol dibawah",
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
            colors = ButtonDefaults.elevatedButtonColors(colorResource(R.color.orange100))
            ) {
                Text(text = "Masuk Dengan akun Google")
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
                color = colorResource(R.color.black)
                )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true,
    device = "spec:width=1080px,height=2400px,dpi=440"
)
@Composable
fun LoginScreenPreview() {
    LoginScreen(onSignUp = {}, imageResource = R.drawable.flip_2)
}