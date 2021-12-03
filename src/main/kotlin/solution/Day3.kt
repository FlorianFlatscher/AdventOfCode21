package solution

import util.getInputAsText

// 2 stars in 00:44:35

private fun level1() {
    val input = getInputAsText(3).lines().map { row ->
        row.map { it.digitToInt() };
    }
    val transposed = transpose(input)!!;

    val gamma = transposed.map {
        if (it.count { it == 1 } > it.count { it == 0 }) 1 else 0
    }
    val g = gamma.joinToString(separator = "").toInt(2);

    val epsilon = transposed.map {
        if (it.count { it == 1 } < it.count { it == 0 }) 1 else 0
    }
    val e = epsilon.joinToString(separator = "").toInt(2);
    println(e * g)
}

private fun level2() {
    val input = getInputAsText(3).lines().map { row ->
        row.map { it.digitToInt() };
    }
    val transposed = transpose(input)!!;

    var os = input
    for (i in 0 until input[0].size) {
        if (os.size == 1) break;

        val max = transpose(os)!!.map {
            if (it.count { it == 1 } >= it.count { it == 0 }) 1 else 0
        };

        os = os.filter { it ->
            it[i] == max[i]
        }
    }

    var cs = input
    for (i in 0 until input[0].size) {
        if (cs.size == 1) break;
        val min = transpose(cs)!!.map {
            if (it.count { it == 1 } < it.count { it == 0 }) 1 else 0
        };

        cs = cs.filter { it ->
            it[i] == min[i]
        }
    }


    println(os.first().joinToString("").toInt(2) * cs.first().joinToString("").toInt(2))

}

fun <T> transpose(table: List<List<T>>): List<List<T>>? {
    val ret: MutableList<List<T>> = ArrayList()
    val N = table[0].size
    for (i in 0 until N) {
        val col: MutableList<T> = ArrayList()
        for (row in table) {
            col.add(row[i])
        }
        ret.add(col)
    }
    return ret
}

fun main() {
    level1();
    level2();
}