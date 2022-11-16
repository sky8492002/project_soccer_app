package com.example.project_soccer_app.data.model

import android.graphics.drawable.Drawable
import com.example.project_soccer_app.R

data class ProfileImageData(
    var character: String = "",
    var image: Drawable,
    var selected:Boolean = false
)