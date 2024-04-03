package com.daelim.springtest.main.api.model.dto

data class TestDto(
    val id: String,
    val name: String,
    val age: Int,
)

data class TestDtoRequest(
    val id: String,
)
