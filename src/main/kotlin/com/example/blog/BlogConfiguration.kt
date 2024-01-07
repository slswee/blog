package com.example.blog

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BlogConfiguration {
    @Bean
    fun databaseInitializer(
        userRepository: UserRepository,
        articleRepository: ArticleRepository,
        ) = ApplicationRunner {
            val sallyLi = userRepository.save(User("sallyLi", "Sally", "Li"))

        articleRepository.save(Article(
            title = "Sally's favorite anime figures",
            headline = "It's an Otaku life!",
            content = "check it out!",
            author = sallyLi,
        ))
        articleRepository.save(Article(
            title = "Sally's 2024 Intentions",
            headline = "New Adventure!",
            content = "Adventure, Travel, Gym, Read, Code, Promotion!!",
            author = sallyLi,
        ))
    }
}