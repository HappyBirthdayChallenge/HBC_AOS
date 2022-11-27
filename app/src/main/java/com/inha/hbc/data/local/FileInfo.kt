package com.inha.hbc.data.local

import android.net.Uri

data class FileInfo(
    val path: String,
    val uri: Uri,
    val type: Int,// 0 사진 1 동영상 2 음성
    val id: Int,
    var success: Boolean,
    var realId: String?
)
