plugins {
    kotlin("jvm") version "2.3.10"
    application
    kotlin("plugin.serialization") version "2.3.10"
    id("org.graalvm.buildtools.native") version "0.10.2"
}

group = "com.emmettchilds.coralseoswarmk26"
version = "0.1.0"

repositories {
    mavenCentral()
    mavenLocal()
}

val nativeImageConfigDir = layout.projectDirectory.dir("src/native-image").asFile

dependencies {
    testImplementation(kotlin("test"))
    val koogVersion = "0.6.4"
    api("ai.koog:koog-agents:$koogVersion")
    api("ai.koog:agents-mcp:$koogVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
}

application {
    mainClass.set("com.emmettchilds.coralseoswarmk26.keyword.MainKt")
}

graalvmNative {
    toolchainDetection.set(false)
    binaries {
        named("main") {
            imageName.set("keyword-researcher")
            mainClass.set("com.emmettchilds.coralseoswarmk26.keyword.MainKt")
            buildArgs.addAll(
                listOf(
                    "--no-fallback",
                    "-H:+ReportExceptionStackTraces",
                    "--initialize-at-build-time=kotlin.DeprecationLevel",
                    "--initialize-at-run-time=java.security.SecureRandom,com.sun.jndi.dns.DnsClient",
                    "--trace-class-initialization=java.security.SecureRandom",
                    "--trace-object-instantiation=com.sun.jndi.dns.DnsClient",
                    "--enable-http",
                    "--add-modules=java.net.http",
                    "--enable-url-protocols=https",
                    "--report-unsupported-elements-at-runtime",
                    "--no-server",
                    "-H:+UnlockExperimentalVMOptions"
                )
            )
            buildArgs.add("-H:ConfigurationFileDirectories=${nativeImageConfigDir.absolutePath}")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(24)
}

tasks.named<JavaExec>("run") {
    outputs.upToDateWhen { false }
}
