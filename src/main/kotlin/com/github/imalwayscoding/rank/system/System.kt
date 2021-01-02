package com.github.imalwayscoding.rank.system

import com.github.imalwayscoding.rank.RankPlugin
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.io.IOException

class System {

    companion object {
        private val file = File(Bukkit.getPluginManager().getPlugin("Rank")?.dataFolder, "rank.yml")
        private val config = YamlConfiguration.loadConfiguration(file)

        @Suppress("deprecation")
        fun setRank(player: Player, rank: Ranks) {

            if (RankPlugin.scoreboard.getTeam(rank(player).toString())?.hasPlayer(player) == true) {
                RankPlugin.scoreboard.getTeam(rank(player).toString())?.removePlayer(player)
            }
            RankPlugin.scoreboard.getTeam(rank.toString())?.addPlayer(player)

            player.setDisplayName("${rank.prefix()}${player.displayName}")
            player.playerListHeader = "${rank.prefix()}${player.displayName}"

            config.set(player.uniqueId.toString(), rank.toString())

            try {
                config.save(file)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun rank(player: Player): Ranks {
            val value = config.getString(player.uniqueId.toString())
            return if (value == null) Ranks.DEFAULT else Ranks.valueOf(value)
        }

        fun hasRank(player: Player, rank: Ranks): Boolean {
            return rank(player) == rank
        }
    }

}