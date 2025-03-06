
package com.example.imatah.model

import androidx.annotation.DrawableRes

data class Category(
    val id: Long,
    val name: String,
    val description: String,
    @DrawableRes   val imageUrl: Int,
)