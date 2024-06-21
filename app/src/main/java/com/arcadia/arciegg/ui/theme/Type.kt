package com.arcadia.arciegg.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.arcadia.arciegg.R

import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font


// Set of Material typography styles to start with

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val bodyFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = provider,
    )
)

val displayFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Montserrat"),
        fontProvider = provider,
    )
)
@Composable
fun AppTypography() : Typography {
    val typography = MaterialTheme.typography
    return Typography(
        displayLarge = typography.displayLarge.copy(fontFamily = displayFontFamily),
        displayMedium = typography.displayMedium.copy(fontFamily = displayFontFamily),
        displaySmall = typography.displaySmall.copy(fontFamily = displayFontFamily),
        headlineLarge = typography.headlineLarge.copy(fontFamily = displayFontFamily),
        headlineMedium = typography.headlineMedium.copy(fontFamily = displayFontFamily),
        headlineSmall = typography.headlineSmall.copy(fontFamily = displayFontFamily),
        titleLarge = typography.titleLarge.copy(fontFamily = displayFontFamily),
        titleMedium = typography.titleMedium.copy(fontFamily = displayFontFamily),
        titleSmall = typography.titleSmall.copy(fontFamily = displayFontFamily),
        bodyLarge = typography.bodyLarge.copy(fontFamily = bodyFontFamily),
        bodyMedium = typography.bodyMedium.copy(fontFamily = bodyFontFamily),
        bodySmall = typography.bodySmall.copy(fontFamily = bodyFontFamily),
        labelLarge = typography.labelLarge.copy(fontFamily = bodyFontFamily),
        labelMedium = typography.labelMedium.copy(fontFamily = bodyFontFamily),
        labelSmall = typography.labelSmall.copy(fontFamily = bodyFontFamily),
    )
}


val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)