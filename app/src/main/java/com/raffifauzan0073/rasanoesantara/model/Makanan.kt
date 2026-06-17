package com.raffifauzan0073.rasanoesantara.model

data class Makanan(
    val id: Long,
    val createdAt: String,
    val userId: String,
    val nama: String,
    val daerah: String,
    val imageUrl: String
)
