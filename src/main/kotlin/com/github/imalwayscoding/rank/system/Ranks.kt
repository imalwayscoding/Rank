package com.github.imalwayscoding.rank.system

enum class Ranks (prefix: String) {

    OWNER("§c[OWNER] "),
    ADMIN("§c[ADMIN] "),
    FRIEND("§e[FRIEND] "),
    DEFAULT("§9");

    private var prefix = ""

    init {
        this.prefix = prefix
    }

    fun prefix(): String {
        return prefix
    }

    fun messagePrefix(): String {

        return when (prefix()) {
            "§c[OWNER] " -> "§cOWNER"
            "§c[ADMIN] " -> "§cADMIN"
            "§e[FRIEND] " -> "§eFRIEND"
            else -> prefix()
        }
    }

}