package com.github.imalwayscoding.rank.command

import com.github.imalwayscoding.rank.system.Ranks
import com.github.imalwayscoding.rank.system.System
import com.github.noonmaru.kommand.KommandBuilder
import com.github.noonmaru.kommand.argument.player
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object KommandRank {

    fun register(builder: KommandBuilder) {
        builder.apply {
            then("set") {
                then("target" to player()) {
                    require { this is Player }
                    then("owner") {
                        executes {
                            val player = it.sender as Player
                            if (System.hasRank(player, Ranks.OWNER) || KommandOwner.isOwner(player)) {
                                val target = it.parseArgument<Player>("target")
                                if (player == target) {
                                    player.sendMessage("§c자기 스스로 랭크를 내릴 수 없습니다.")
                                    return@executes
                                }
                                staffMessage(player, target, Ranks.OWNER)
                                System.setRank(target, Ranks.OWNER)
                            } else {
                                player.sendMessage("권한이 없습니다.")
                            }
                        }
                    }
                    then("admin") {
                        executes {
                            val player = it.sender as Player
                            if (System.hasRank(player, Ranks.OWNER)) {
                                val target = it.parseArgument<Player>("target")
                                if (player == target) {
                                    player.sendMessage("§c자기 스스로 랭크를 내릴 수 없습니다.")
                                    return@executes
                                }
                                if (System.rank(target) == Ranks.ADMIN) {
                                    player.sendMessage("§c이미 ${target.displayName}님은 ${Ranks.ADMIN.prefix()} 입니다.")
                                    return@executes
                                }
                                staffMessage(player, target, Ranks.ADMIN)
                                System.setRank(target, Ranks.ADMIN)
                            }
                        }
                    }
                    then("friend") {
                        executes {
                            val player = it.sender as Player
                            if (System.hasRank(player, Ranks.OWNER) || System.hasRank(player, Ranks.ADMIN)) {
                                val target = it.parseArgument<Player>("target")
                                if (player == target) {
                                    player.sendMessage("§c자기 스스로 랭크를 내릴 수 없습니다.")
                                    return@executes
                                }
                                if (System.rank(target) == Ranks.FRIEND) {
                                    player.sendMessage("§c이미 ${target.displayName}님은 ${Ranks.FRIEND.prefix()} 입니다.")
                                    return@executes
                                }
                                staffMessage(player, target, Ranks.FRIEND)
                                System.setRank(target, Ranks.FRIEND)
                            }
                        }
                    }
                    then("default") {
                        executes {
                            val player = it.sender as Player
                            if (System.hasRank(player, Ranks.OWNER) || System.hasRank(player, Ranks.ADMIN)) {
                                val target = it.parseArgument<Player>("target")
                                if (player == target) {
                                    player.sendMessage("§c자기 스스로 랭크를 내릴 수 없습니다.")
                                    return@executes
                                }
                                if (System.rank(target) == Ranks.DEFAULT) {
                                    player.sendMessage("§c이미 ${target.displayName}님은 ${Ranks.DEFAULT.prefix()} 입니다.")
                                    return@executes
                                }
                                staffMessage(player, target, Ranks.DEFAULT)
                                System.setRank(target, Ranks.DEFAULT)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun staffMessage(player: Player, target: Player, rank: Ranks) {
        player.sendMessage("&a플레이어 ${target.displayName}&a님의 랭크는 이제 $rank 입니다.")
        Bukkit.getServer().broadcastMessage("§b[STAFF] ${player.displayName}&f님이 ${target.name} 님의 랭크를 ${rank.messagePrefix()}로 변경했습니다.")
    }

}