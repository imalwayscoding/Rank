package com.github.imalwayscoding.rank

import com.github.imalwayscoding.rank.command.KommandOwner
import com.github.imalwayscoding.rank.command.KommandRank
import com.github.imalwayscoding.rank.listener.Listeners
import com.github.imalwayscoding.rank.system.Ranks.*
import com.github.imalwayscoding.rank.system.System
import com.github.noonmaru.kommand.kommand
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Scoreboard

class RankPlugin : JavaPlugin() {

    companion object {
        lateinit var scoreboard: Scoreboard
    }

    override fun onEnable() {
        scoreboard = Bukkit.getScoreboardManager().mainScoreboard
        registerName()
        registerHealth()
        registerCommands()
        registerEvents()
    }

    private fun registerCommands() = kommand {
        register("rank") {
            KommandRank.register(this)
        }
        register("owner") {
            KommandOwner.register(this)
        }
    }

    private fun registerEvents() {
        this.server.pluginManager.registerEvents(Listeners(), this)
    }

    private fun registerHealth() {
        if (scoreboard.getObjective("health") != null) {
            scoreboard.getObjective("health")?.unregister()
        }
        val objective = scoreboard.registerNewObjective("health", "health", "§c❤")
        objective.displaySlot = DisplaySlot.BELOW_NAME
    }

    @Suppress("deprecation")
    private fun registerName() {

        val rankList = ArrayList<String>()
        for (rank in values()) {
            rankList.add(rank.toString())
        }


        for (rank in rankList) {
            if (scoreboard.getObjective(rank) != null) {
                scoreboard.getTeam(rank)?.unregister()
            }
        }

        val owner = scoreboard.registerNewTeam(OWNER.toString())
        val admin = scoreboard.registerNewTeam(ADMIN.toString())
        val friend = scoreboard.registerNewTeam(FRIEND.toString())
        val default = scoreboard.registerNewTeam(DEFAULT.toString())

        owner.prefix = OWNER.prefix()
        for (players in Bukkit.getServer().onlinePlayers) {
            when (System.rank(players)) {
                OWNER -> owner.addPlayer(players)
                ADMIN -> admin.addPlayer(players)
                FRIEND -> friend.addPlayer(players)
                DEFAULT -> default.addPlayer(players)
            }
        }
    }

}