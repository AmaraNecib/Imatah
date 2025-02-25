package com.example.imatah.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = RescueBlue, // تأكد من تعريف RescueBlue في ملف Color.kt
    secondary = AlertOrange, // تأكد من تعريف AlertOrange في ملف Color.kt
    tertiary = AsphaltGray, // تأكد من تعريف AsphaltGray في ملف Color.kt
    background = LightSurface, // تأكد من تعريف LightSurface في ملف Color.kt
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    error = HazardRed, // تأكد من تعريف HazardRed في ملف Color.kt
    //warning = ConeOrange, // تأكد من تعريف ConeOrange في ملف Color.kt
    //success = SafetyGreen // تأكد من تعريف SafetyGreen في ملف Color.kt
)

private val DarkColorScheme = darkColorScheme(
    primary = NightBlue, // تأكد من تعريف NightBlue في ملف Color.kt
    secondary = EmberOrange, // تأكد من تعريف EmberOrange في ملف Color.kt
    tertiary = ConcreteGray, // تأكد من تعريف ConcreteGray في ملف Color.kt
    background = DarkSurface, // تأكد من تعريف DarkSurface في ملف Color.kt
    surface = Color(0xFF37474F),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    error = HazardRed, // تأكد من تعريف HazardRed في ملف Color.kt
    //warning = ConeOrange, // تأكد من تعريف ConeOrange في ملف Color.kt
    //success = SafetyGreen // تأكد من تعريف SafetyGreen في ملف Color.kt
)

@Composable
fun ImatahTheme( // تم تغيير الاسم هنا من ImatahAppTheme إلى ImatahTheme
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography, // تأكد من تعريف Typography في ملف Type.kt
        content = content
    )
}