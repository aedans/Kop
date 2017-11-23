package io.github.aedans.kop

val <T> T.left get() = Coproduct.Right(this)
val <T> T.right get() = Coproduct.Right(this)
