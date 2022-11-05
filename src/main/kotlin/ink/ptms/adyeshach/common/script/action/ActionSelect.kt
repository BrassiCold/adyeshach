package ink.ptms.adyeshach.common.script.action

import ink.ptms.adyeshach.common.script.ScriptHandler.getEntities
import ink.ptms.adyeshach.common.script.ScriptHandler.getManager
import ink.ptms.adyeshach.common.script.ScriptHandler.loadError
import ink.ptms.adyeshach.common.script.ScriptHandler.setEntities
import taboolib.library.kether.ArgTypes
import taboolib.library.kether.ParsedAction
import taboolib.module.kether.*
import java.util.concurrent.CompletableFuture

/**
 * @author IzzelAliz
 */
class ActionSelect(val value: ParsedAction<*>, val byId: Boolean) : ScriptAction<Void>() {

    override fun run(frame: ScriptFrame): CompletableFuture<Void> {
        val script = frame.script()
        if (script.getManager() == null) {
            error("Manager is not selected")
        }
        return frame.newFrame(value).run<Any>().thenAccept {
            val manager = script.getManager()!!
            script.setEntities(if (byId) manager.getEntityById(it.toString()) else listOf(manager.getEntityByUniqueId(it.toString())))
        }
    }

    companion object {

        @KetherParser(["select"], namespace = "adyeshach", shared = true)
        fun parser1() = scriptParser {
            val value = it.next(ArgTypes.ACTION)
            var byId = true
            if (it.hasNext()) {
                it.mark()
                if (it.nextToken() == "by" && it.hasNext()) {
                    byId = when (val type = it.nextToken().lowercase()) {
                        "id" -> true
                        "uniqueid", "uuid" -> false
                        else -> throw loadError("Unknown select type $type")
                    }
                } else {
                    it.reset()
                }
            }
            ActionSelect(value, byId)
        }

        @KetherParser(["selected"], namespace = "adyeshach", shared = true)
        fun parser2() = scriptParser {
            actionNow {
                val npc = script().getEntities()
                when {
                    npc.isNullOrEmpty() -> null
                    npc.size == 1 -> npc.first()?.id
                    else -> npc.mapNotNull { it?.id }
                }
            }
        }
    }
}