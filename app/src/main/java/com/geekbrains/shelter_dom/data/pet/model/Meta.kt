package com.geekbrains.shelter_dom.data.pet.model

data class Meta(
    val current_page: Int? = null,
    val from: Int? = null,
    val last_page: Int? = null,
    val links: List<Link>? = listOf(),
    val path: String? = "",
    val per_page: Int? = null,
    val to: Int? = null,
    val total: Int? = null
)