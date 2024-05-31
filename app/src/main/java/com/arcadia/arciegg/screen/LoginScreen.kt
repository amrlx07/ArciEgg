package com.arcadia.arciegg.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arcadia.arciegg.R

@Composable
fun LoginScreen(@DrawableRes imageResource: Int ) {
    Box {
        Row {
            //Spacer(modifier = Modifier.width(140.dp))
            Image(
                painter = painterResource(imageResource),
                contentDescription = "background image",
                modifier = Modifier
                    .offset(x = 140.dp, y = 73.dp)
                    .alpha(0.6f)
            )
        }
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            TextLanding(modifier = Modifier.padding(top = 103.dp, start = 16.dp, bottom = 450.dp))
            TextIntruks(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )
            ButtonLogin()
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
    modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .height(61.dp)
                .width(328.dp)
            ) {
                Text(text = "Masuk Dengan akun Google")
            }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton (
            onClick = { /*TODO*/ },
            modifier = Modifier
                .height(61.dp)
                .width(328.dp),
            enabled = false
        ) {
            Text(text = "Masuk dengan opsi lainnya")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(R.drawable.flip_2)
}