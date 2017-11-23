import java.io.File

@Suppress("unused")
object Codegen {
    private val generics = ('A'..'Z').asSequence().map { it.toString() } + generateSequence(0) { it + 1 }.map { "_$it" }
    private fun generics(range: IntRange) = generics.drop(range.start).take(range.last - range.start + 1).toList()

    @JvmStatic
    fun generateTypealiases(num: Int, out: String) {
        fun generateTypealias(i: Int) = "typealias " +
                "Coproduct$i${generics(0 until i).joinToString(prefix = "<", postfix = ">")} = Coproduct<" +
                "${generics.first()}, " +
                "Coproduct${i - 1}${generics(1 until i).joinToString(prefix = "<", postfix = ">")}" +
                ">\n"

        val str = (3..num).joinToString(
                prefix = """
                    package io.github.aedans.kop

                    internal typealias Coproduct2<A, B> = Coproduct<A, B>

                    """.trimIndent(),
                postfix = "",
                separator = ""
        ) { generateTypealias(it) }

        File(out, "Typealiases.kt").writeText(str)
    }

    @JvmStatic
    fun generateFolds(num: Int, out: String) {
        fun generateFold(i: Int) = "inline fun " +
                "<${generics(0 until i).joinToString(prefix = "", postfix = "")}, Result> " +
                "Coproduct$i${generics(0 until i).joinToString(prefix = "<", postfix = ">")}" +
                ".fold" +
                generics(0 until i).joinToString(prefix = "(\n", postfix = "\n)", separator = ",\n") { "       fn$it: ($it) -> Result" } +
                ": Result = fold(fn${generics.first()}) {\n" +
                "    it.fold" +
                generics(1 until i).joinToString(prefix = "(", postfix = ")") { "fn$it" } + "\n" +
                "}\n"

        val str = (3..num).joinToString(
                prefix = """
                    package io.github.aedans.kop


                    """.trimIndent(),
                postfix = "",
                separator = "\n"
        ) { generateFold(it) }

        File(out, "Fold.kt").writeText(str)
    }

    @JvmStatic
    fun generateMaps(num: Int, out: String) {
        fun generateMap(i: Int) = "inline fun " +
                generics(0 until i).flatMap { listOf("${it}1", "${it}2") }.joinToString(prefix = "<", postfix = ">") +
                " Coproduct$i${generics(0 until i).joinToString(prefix = "<", postfix = ">") { "${it}1" }}" +
                ".map" +
                generics(0 until i).joinToString(prefix = "(\n", postfix = "\n)", separator = ",\n") { "       fn$it: (${it}1) -> ${it}2" } +
                ": Coproduct$i${generics(0 until i).joinToString(prefix = "<", postfix = ">") { "${it}2" }} =\n" +
                "        map(fn${generics.first()}) { it.map" +
                generics(1 until i).joinToString(prefix = "(", postfix = ")") { "fn$it" } + "\n" +
                "}\n"

        val str = (3..num).joinToString(
                prefix = """
                    package io.github.aedans.kop


                    """.trimIndent(),
                postfix = "",
                separator = "\n"
        ) { generateMap(it) }

        File(out, "Map.kt").writeText(str)
    }

    @JvmStatic
    fun generateValues(num: Int, out: String) {
        fun generateValue(i: Int) = "@JvmName(\"value$i\")\nfun " +
                "<Result, ${generics(0 until i).joinToString(prefix = "", postfix = "") { "$it : Result" }}> " +
                "Coproduct$i${generics(0 until i).joinToString(prefix = "<", postfix = ">")}" +
                ".value(): Result = \n" +
                "        fold${(0 until i).joinToString(prefix = "(", postfix = ")") { "{ it }" }}\n"

        val str = (2..num).joinToString(
                prefix = """
                    package io.github.aedans.kop


                    """.trimIndent(),
                postfix = "",
                separator = "\n"
        ) { generateValue(it) }

        File(out, "Value.kt").writeText(str)
    }
}
