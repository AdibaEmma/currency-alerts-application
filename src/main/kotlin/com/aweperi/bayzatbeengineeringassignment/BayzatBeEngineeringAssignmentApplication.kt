package com.aweperi.bayzatbeengineeringassignment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class BayzatBeEngineeringAssignmentApplication

fun main(args: Array<String>) {
    runApplication<BayzatBeEngineeringAssignmentApplication>(*args)
}

fun configureApplication(builder: SpringApplicationBuilder): SpringApplicationBuilder {
    return builder.sources(BayzatBeEngineeringAssignmentApplication::class.java)
}
