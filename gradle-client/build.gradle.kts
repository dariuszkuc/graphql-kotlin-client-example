import com.expediagroup.graphql.plugin.generator.ScalarConverterMapping
import com.expediagroup.graphql.plugin.gradle.graphql

plugins {
    application
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    id("com.expediagroup.graphql") version "3.0.0-RC6"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("com.expediagroup:graphql-kotlin-client:3.0.0-RC6")
}

application {
    mainClassName = "com.expediagroup.graphql.examples.client.ApplicationKt"
}

graphql {
    packageName = "com.expediagroup.graphql.generated"
    // you can also use direct sdlEndpoint instead
    endpoint = "http://localhost:8080/graphql"

    // optional
    allowDeprecatedFields = true
    scalarConverters.put("UUID", ScalarConverterMapping("java.util.UUID", "com.expediagroup.graphql.examples.client.UUIDScalarConverter"))
    queryFiles.setFrom()
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")

        }
    }
}

