package com.github.imalwayscoding.rank.command

import com.github.imalwayscoding.rank.system.Ranks
import com.github.imalwayscoding.rank.system.System
import com.github.noonmaru.kommand.KommandBuilder
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.io.IOException
import java.util.*

object KommandOwner {

    private val file = File(Bukkit.getPluginManager().getPlugin("rank")?.dataFolder, "owner.yml")
    private val config = YamlConfiguration.loadConfiguration(file)

    fun register(builder: KommandBuilder) {
        builder.apply {
            require { this is Player }
            then("apply") {
                executes {
                    val player = it.sender as Player
                    if (config.get("status").toString() == false.toString()) {
                        setOwner(player)
                        System.setRank(player, Ranks.OWNER)
                        player.sendMessage("§a당신은 이제 서버장이 되었습니다!")
                    } else {
                        player.sendMessage("§c이미 서버장이 있습니다.")
                    }
                }
            }
            then("promote") {
                executes {
                    val player = it.sender as Player
                    if (isOwner(player)) {
                        setOwner(player)
                        System.setRank(player, Ranks.ADMIN)
                        player.sendMessage("§a서버장이 양도 되었습니다.")
                    } else {
                        player.sendMessage("§c서버장이 아닙니다.")
                    }
                }
            }
        }
    }

    fun setOwner(player: Player) {
        config.set("status", true.toString())
        config.set("owner", player.uniqueId)

        try {
            config.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun owner(): Player? {
        val value = config.get("owner")
        return Bukkit.getPlayer(UUID.fromString(value.toString()))
    }

    fun isOwner(player: Player): Boolean {
        val owner = owner()
        return player == owner
    }

}