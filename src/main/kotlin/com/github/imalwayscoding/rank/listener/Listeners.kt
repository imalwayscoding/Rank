package com.github.imalwayscoding.rank.listener

import com.github.imalwayscoding.rank.system.System
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class Listeners : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        System.setRank(player, System.rank(player))
        event.joinMessage = "§7[§a+§7]${player.displayName}&f님이 서버에 §a접속§f 하셨습니다."
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player
        event.quitMessage = "§7[§c-§7]${player.displayName}&f님이 서버에서 &c퇴장&f 하셨습니다."
    }

    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {
        val player = event.player
        val message = event.message
        event.format = "${player.displayName}&f: $message"
    }

}