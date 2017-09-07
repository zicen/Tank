import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlin_version: String by extra
buildscript {
    var kotlin_version: String by extra
    kotlin_version = "1.1.4-3"
    /**
     * 2、添加maven仓库
     */
    repositories {
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
    dependencies {
        classpath(kotlin("gradle-plugin", kotlin_version))
    }
}
/**
 * 1、解决compile爆红问题
 */
/*apply {
    plugin("kotlin")
}*/
plugins {
    kotlin("jvm","1.1.4-3")
    application
}
/**
 * 4、设置程序入口
 */
application {
    mainClassName = "org.zhenquan.tank.AppKt"
}

dependencies {
    compile(kotlin("stdlib-jre8", kotlin_version))
    /**
     * 3、添加依赖
     */
    compile("com.github.shaunxiao:kotlinGameEngine:v0.0.2")
}
/**
 * 2、添加maven仓库
 */
repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}