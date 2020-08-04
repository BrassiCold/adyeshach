package ink.ptms.adyeshach

import ink.ptms.adyeshach.common.entity.MetadataExtend
import ink.ptms.adyeshach.common.entity.element.EntityRotation
import ink.ptms.adyeshach.common.entity.type.impl.AdyAreaEffectCloud
import ink.ptms.adyeshach.common.entity.type.impl.AdyArmorStand
import ink.ptms.adyeshach.common.entity.type.impl.AdyBat
import ink.ptms.adyeshach.common.util.Serializer
import ink.ptms.adyeshach.nms.NMS
import io.izzel.taboolib.module.command.lite.CommandBuilder
import io.izzel.taboolib.module.inject.TInject
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.EulerAngle

/**
 * @author Arasple
 * @date 2020/8/3 21:55
 * Villager test
 */
object TestV {

    @TInject
    val testV: CommandBuilder = CommandBuilder
            .create("test-v", Adyeshach.plugin)
            .execute { sender, _ ->
                if (sender is Player) {
                    val entity = AdyArmorStand(sender)
                    entity.spawn(sender.location)
                    entity.setArms(true)
                    entity.setSmall(true)
                    entity.setGlowing(true)
                    entity.setRotation(EntityRotation.LEFT_ARM, EulerAngle(10.0, 50.0, 100.0))
                    entity.setRotation(EntityRotation.RIGHT_ARM, EulerAngle(10.0, 50.0, 100.0))
                    NMS.INSTANCE.updateEntityMetadata(entity.owner, entity.index, *entity.metadata().toTypedArray())
                    Bukkit.getScheduler().runTaskLaterAsynchronously(Adyeshach.plugin, Runnable {
                        entity.destroy()
                        sender.sendMessage("§c[System] §7LRemoved.")
                    }, 60L)
                    sender.sendMessage("§c[System] §7Json:")
                    sender.sendMessage(io.izzel.taboolib.internal.gson.GsonBuilder().setPrettyPrinting().create().toJson(Serializer.serializer.toJsonTree(entity)))
                    sender.sendMessage("§c[System] §7Done.")
                }
            }
}