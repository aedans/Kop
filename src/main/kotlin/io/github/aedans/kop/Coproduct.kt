package io.github.aedans.kop

import kategory.*

@higherkind
sealed class Coproduct<out A, out B> : CoproductKind<A, B> {
    data class Left<out A>(val value: A) : Coproduct<A, Nothing>()
    data class Right<out B>(val value: B) : Coproduct<Nothing, B>()

    val asLeft get() = this as Coproduct.Left
    val asRight get() = this as Coproduct.Right

    inline fun <C> fold(fnA: (A) -> C, fnB: (B) -> C): C =
            if (this is Coproduct.Left) {
                fnA(value)
            } else {
                fnB(asRight.value)
            }

    inline fun <C, D> map(fnA: (A) -> C, fnB: (B) -> D): Coproduct<C, D> = fold(
            { Coproduct.Left(fnA(it)) },
            { Coproduct.Right(fnB(it)) }
    )
}
