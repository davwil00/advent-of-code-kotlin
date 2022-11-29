plugins {
    id("aoc.kotlin-conventions")
}

dependencies {
    implementation("org.slf4j:slf4j-api:1.7.9")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testImplementation("org.assertj:assertj-core:3.18.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.6.0")
}