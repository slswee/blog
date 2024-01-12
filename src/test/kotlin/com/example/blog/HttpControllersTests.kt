package com.example.blog

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class HttpControllersTests(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var userRepository: UserRepository

    @MockkBean
    lateinit var articleRepository: ArticleRepository

    @Test
    fun `List articles`() {
        val testUser = User("coolBunny", "Usagi", "Cool")
        val testArticle1 = Article("testArticle1", "testHeadline", "testContent it's going to be a great day!!", testUser)
        val testArticle2 = Article("testArticle1", "testHeadline--fun stuff", "testContent -- yo yo yo", testUser)

        every { articleRepository.findAllByOrderByAddedAtDesc() } returns listOf(testArticle1, testArticle2)

        mockMvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].author.login").value(testUser.login))
            .andExpect(jsonPath("\$.[0].slug").value(testArticle1.slug))
            .andExpect(jsonPath("\$.[1].author.login").value(testUser.login))
            .andExpect(jsonPath("\$.[1].slug").value(testArticle2.slug))
    }

    @Test
    fun `List users`() {
        val testUser1 = User("miku", "Hatsune", "Miku")
        val testUser2 = User("sonic", "Sonic", "Hedgehog")
        every { userRepository.findAll() } returns listOf(testUser1, testUser2)
        mockMvc.perform(get("/api/user/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].login").value(testUser1.login))
            .andExpect(jsonPath("\$.[1].login").value(testUser2.login))
    }
}
