package jh.apple.documentstore.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@Profile("default")

@EnableJpaRepositories(basePackages = ["jh.apple.documentstore"])
class DocumentApplicationConfig{

    @Bean(name = "bootstrapDataTextExample")
    String getDummy() {
        final def dummy = "Now is the time for all good men to come to the aid of their country"
        dummy
    }

}