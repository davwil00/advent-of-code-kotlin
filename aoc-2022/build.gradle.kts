plugins {
    id("aoc.kotlin-conventions")
}

dependencies {
    implementation(project(":utils"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.21")
    implementation("ch.qos.logback:logback-classic:1.4.5")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.7.21")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.1")
    testImplementation(project(":utils", "test"))
}
