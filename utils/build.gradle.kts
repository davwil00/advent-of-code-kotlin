plugins {
    id("aoc.kotlin-conventions")
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.2.8")
}

configurations {
    create("test")
}

tasks.register<Jar>("testArchive") {
    archiveBaseName.set("utils-test")
    from(project.the<SourceSetContainer>()["test"].output)
}

artifacts {
    add("test", tasks["testArchive"])
}