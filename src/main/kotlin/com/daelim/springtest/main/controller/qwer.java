// Controller.kt
package com.daelim.springtest.main.controller

import com.daelim.springtest.main.api.model.dto.TestDto
import com.daelim.springtest.main.api.model.dto.TestDtoRequest
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import net.datafaker.Faker
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class Controller {
    private val tests = mutableListOf<TestDto>()

    @PostMapping("/test")
    fun postTestDto(
            @RequestBody testDtoRequest: TestDtoRequest
    ): ResponseEntity<TestDto> {
        val faker = Faker(Locale.KOREA)
        val test = TestDto(
                id = faker.name().name(),
                age = testDtoRequest.age
        )
        tests.add(test)
        return ResponseEntity.ok().body(test)
    }
    @GetMapping("/test")
    fun getAllTestDto(
    ): ResponseEntity<List<TestDto>> {
        val response = tests
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/test/{age}")
    fun getTestDto(
            @PathVariable("age") userAge: Int
    ): ResponseEntity<TestDto> {
        val response = tests.firstOrNull{it.age == userAge}
        return ResponseEntity.ok().body(response)
    }
}

// 나이 입력하면 이름을 출력

//TestResolve.kt
package com.daelim.springtest.main.resolver

import com.daelim.springtest.main.api.model.dto.TestDto
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.kickstart.tools.GraphQLQueryResolver
import net.datafaker.Faker
import org.springframework.stereotype.Component
import java.util.*

@Component
class PostResolver : GraphQLQueryResolver, GraphQLMutationResolver {
    private val tests = mutableListOf<TestDto>()

    val faker = Faker(Locale.KOREA)

    fun findAllTests(): List<TestDto> {
        return tests
    }

    fun findTestById(id: String): TestDto? {
    return tests.find { it.id == id }
    }

    fun createTest(userId: String): TestDto {
        val test = TestDto(
                id = userId,
                age = Random().nextInt(100)
        )
        tests.add(test)
        return test
    }
}

// TestDto.kt
package com.daelim.springtest.main.api.model.dto

data class TestDto(
        val id: String,
        val age: Int,
        )

data class TestDtoRequest(
        val age: Int,
        )


// testDto.graphqls
type Test {
    id: ID!
    age: Int!
}

type Query {
findAllTests: [Test],
findTestById(id: ID!): Test
}

type Mutation {
createTest(userId: String): Test
}
