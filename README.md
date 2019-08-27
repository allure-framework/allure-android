[![Build Status](https://api.travis-ci.org/TinkoffCreditSystems/allure-android.svg?branch=master)](https://api.travis-ci.org/TinkoffCreditSystems/allure-android)

[license]: http://www.apache.org/licenses/LICENSE-2.0 "Apache License 2.0"
[allure2]: https://github.com/allure-framework/allure2
[blog]: https://qameta.io/blog
[gitter]: https://gitter.im/allure-framework/allure-core
[gitter-ru]: https://gitter.im/allure-framework/allure-ru
[twitter]: https://twitter.com/QametaSoftware "Qameta Software"
[twitter-team]: https://twitter.com/QametaSoftware/lists/team/members "Team"
[CONTRIBUTING.md]: .github/CONTRIBUTING.md
[docs]: https://docs.qameta.io/allure/2.0/

# Allure Android
* [Source](https://github.com/allure-framework/allure-android)
* [Documentation][docs]
* [Gitter][gitter]

[Allure][allure2] adapter for Android, written in Kotlin.

## Installation and Usage

### Configure
#### Gradle

Add to: _app/build.gradle_

```gradle
apply plugin: 'kotlin-android'

dependencies {
    androidTestCompile "ru.tinkoff.allure:allure-android:2.5.1@aar"
    androidTestCompile "ru.tinkoff.allure:allure-common:2.5.1"
    androidTestCompile "ru.tinkoff.allure:allure-model:2.5.1"

    androidTestCompile "org.jetbrains.kotlin:kotlin-stdlib:1.1.51"
    androidTestCompile "junit:junit:4.12"
    androidTestCompile "com.android.support.test.uiautomator:uiautomator-v18:2.1.2"
}

android {
    defaultConfig {
        testInstrumentationRunner "ru.tinkoff.allure.android.AllureAndroidRunner"
    }
}

```

### Pull allure results
Just use comandline tools from Android sdk:
```shell
adb pull /sdcard/allure-results
```

### Generate Report
See [Allure Docs](https://docs.qameta.io/allure/2.0/#_reporting)

## Features

### Steps
Original [Steps](https://github.com/allure-framework/allure1/wiki/Steps) reworked:
* No more aspects required, `step` is an expression
* Name required, but not limited with const value as in annotations

You can use DSL-style steps anywhere in test method:
```kotlin
@Test
fun test() {
    step("First Step") {
        ...
    }
    step("Second Step") {
        ...
        myStep("Param") // Steps can be nested
    }
}

// Steps are expressions
fun myStep(param: String) = step("Do something with $param") {
    ...
}
```

### Soft Assertions
Soft Assertion can be used to continue test execution in case of minor assertion.

`SoftAssert` reports assertion inside `Step` it occured in.

**SoftAssert marks test passed, result seen only in report**

DSL-style:
```kotlin
fun myStep() = step("MyStep") {
    softly {
        checkThat("FirstAssert", true, IsEqual(false))
    }
}
```

### Basic screenshot
Screenshot using UIautomator, appending it to step, it was executed in.

```kotlin
@Test
fun test() {
    deviceScreenshot("screenshot_name")
}
```

### Failshot Rule
You can use `FailshotRule` to capture a screenshot in case of `Throwable` during test
```kotlin
class MyTest {

    @get:Rule
    val failshot = FailshotRule()

    // ...
}
```

### Logcat Rule
You can use `LogcatRule` to capture a device logs in case of `Throwable` during test
```kotlin
class MyTest {

    @get:Rule
    val logcatRule = LogcatRule()

    // ...
}
```

### Allure annotations
You can use some allure annotation

## Contributing to allure-android
This project exists thanks to all the people who contribute. Especially by [Tinkoff](https://www.tinkoff.ru/) and [@Badya](https://github.com/badya) who started and maintaining allure-android.

## License
Allure Android is released under version 2.0 of the [Apache License][license]