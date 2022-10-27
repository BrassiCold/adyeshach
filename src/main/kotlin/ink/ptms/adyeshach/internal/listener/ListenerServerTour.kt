package ink.ptms.adyeshach.internal.listener

import io.netty.util.internal.ConcurrentSet
import taboolib.common.platform.Ghost
import taboolib.common.platform.event.SubscribeEvent
import tech.mistermel.servertours.api.event.RoutePlayBeginEvent
import tech.mistermel.servertours.api.event.RoutePlayEndEvent

object ListenerServerTour {

    var touringPlayer = ConcurrentSet<String>()

    @Ghost
    @SubscribeEvent
    private fun onBegin(e: RoutePlayBeginEvent) {
        touringPlayer.add(e.player.name)
    }

    @Ghost
    @SubscribeEvent
    private fun onEnd(e: RoutePlayEndEvent) {
        touringPlayer.remove(e.player.name)
    }
}