[![Build Status](https://api.travis-ci.org/TinkoffCreditSystems/allure-android.svg?branch=master)](https://api.travis-ci.org/TinkoffCreditSystems/allure-android)

[license]: http://www.apache.org/licenses/LICENSE-2.0 "Apache License 2.0"
[allure2]: https://github.com/allure-framework/allure2
[wiki]: https://github.com/TinkoffCreditSystems/allure-android/wiki
[contributing]: CONTRIBUTING.md

## Allure Android
[Allure][allure2] adapter for Android, written in Kotlin


### Usage
Add to: _app/build.gradle_
```gradle
apply plugin: 'kotlin-android'

dependencies {
    androidTestCompile "ru.tinkoff.allure_android:allure-android:2.1.0@aar"
    androidTestCompile "ru.tinkoff.allure_android:allure-common:2.1.0"
    androidTestCompile "ru.tinkoff.allure_android:allure-model:2.1.0"

    androidTestCompile "org.jetbrains.kotlin:kotlin-stdlib:1.1.2-4"
    androidTestCompile "junit:junit:4.12"
    androidTestCompile "com.android.support.test.uiautomator:uiautomator-v18:2.1.2"
}

android {
    testInstrumentationRunner "ru.tinkoff.allure_android.AllureMultiDexRunner"
}

```

### Documentation

Read [wiki]

### Features
#### [2.1.0] SoftAssert DSL
In order to continue execution on minor assertion use `SoftAssert`<br />
**!!! SoftAssert marks test passed, result seen only in report**

```kotlin
class Test {
    // DSL style
    @Test
    fun test2() {
        softly {
            checkThat("FirstAssert", true, IsEqual(false))
        }
    }
}
```

#### [2.0.8] Step DSL
Steps are any actions that constitute a testing scenario. Steps can be used in different testing scenarios. <br />
They can: be parametrized, make checks, have nested steps, and create attachments. Each step has a name.

```kotlin
/** KOTLIN USAGE */
fun step1() {
    step("My step") {
		invoke1()
		invoke2()
    }
}

fun moar() {
    step("1") {
        invoke1()
        step("1.1") {
            invoke1_1()
            invoke1_2()
        }
        invoke2()
    }
}
```

```java
/** JAVA USAGE */
class Clazz {
    public void step1() {
        step("My step", new Function0<Void>() {
            @Override
            public Void invoke() {
                invoke1();
                invoke2();
            }
        });
    }
}
```

#### Failshot Rule
You can use `FailshotRule` to capture screenshot in case of Throwable.
```java
class MyTest {

    @org.junit.Rule
    FailshotRule failshot = new FailshotRule();

    // ...
}
```



### Contributing


### License

Allure Android is released under version 2.0 of the [Apache License][license]