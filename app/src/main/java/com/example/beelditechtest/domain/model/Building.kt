package com.example.beelditechtest.domain.model

/**
 * Bâtiment appartenant au parc immobilier
 */
data class Building(
    val id: String,
    val name: String,
    val address: String,
    val city: String,
)
