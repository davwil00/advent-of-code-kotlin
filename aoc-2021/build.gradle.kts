plugins {
    id("aoc.kotlin-conventions")
}

dependencies {
    implementation(project(":utils"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
    implementation("ch.qos.logback:logback-classic:1.2.8")
    testImplementation("org.assertj:assertj-core:3.21.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.6.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    testImplementation(project(":utils", "test"))
}
