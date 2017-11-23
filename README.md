Kop
===

[![Download](https://api.bintray.com/packages/aedans/maven/kop/images/download.svg)](https://bintray.com/aedans/maven/kop/_latestVersion)

A small coproduct library for Kotlin.

Gradle
------

```gradle
repositories {
    maven { url 'https://dl.bintray.com/aedans/maven/' }
}

dependencies {
    compile 'io.github.aedans:kop:1.0.0'
}
```

Features
--------

- Standard coproduct type `Coproduct a b = Left a | Right b`
- Coproduct typealiases up to 26
- Coproduct.fold up to 26
- Coproduct.map up to 26
- Coproduct.value up to 26
- [Kategory](https://github.com/kategory/kategory) interop

Automatic Code Generation
-------------------------

The code generator is located [here](https://github.com/aedans/kop/blob/master/buildSrc/src/main/kotlin/Codegen.kt). 
Note that adding, when adding a new generator method, you must also call 
it from build.gradle:codegen.

To increase the number of auto-generated utilities, there is a variable 
called num in build.gradle. However, you will have to build and
publish to another Maven repository to use the change.
