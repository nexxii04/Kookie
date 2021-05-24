package be.zvz.kookie.network.mcpe.convert

import be.zvz.kookie.utils.config.JsonBrowser
import com.koloboke.collect.map.hash.HashIntIntMaps
import com.koloboke.collect.map.hash.HashIntObjMaps
import com.koloboke.collect.map.hash.HashObjIntMaps
import com.koloboke.collect.map.hash.HashObjObjMaps
import java.util.concurrent.atomic.AtomicBoolean

class ItemTranslator(dictionary: ItemTypeDictionary, simpleMappings: Map<String, Int>, complexMappings: Map<String, Pair<Int, Int>>) {

    private val simpleCoreToNetMapping: MutableMap<Int, Int> = HashIntIntMaps.newMutableMap()
    private val simpleNetToCoreMapping: MutableMap<Int, Int> = HashIntIntMaps.newMutableMap()
    private val complexCoreToNetMapping: MutableMap<Int, MutableMap<Int, Int>> = HashIntObjMaps.newMutableMap()
    private val complexNetToCoreMapping: MutableMap<Int, Pair<Int, Int>> = HashIntObjMaps.newMutableMap()

    init {
        dictionary.getEntires().forEach { entry ->
            val stringId = entry.stringId
            val netId = entry.numericId

            when {
                complexMappings.containsKey(stringId) -> {
                    val (id, meta) = complexMappings.getValue(stringId)
                    complexCoreToNetMapping.getValue(id)[meta] = netId
                    complexNetToCoreMapping[netId] = Pair(id, meta)
                }
                simpleMappings.containsKey(stringId) -> {
                    simpleCoreToNetMapping[simpleMappings.getValue(stringId)] = netId
                    simpleNetToCoreMapping[netId] = simpleMappings.getValue(stringId)
                }
                stringId != "minecraft:unknown" -> {
                    throw IllegalArgumentException("Unmapped entry $stringId")
                }
            }
        }
    }

    companion object {
        private val instance: ItemTranslator = make()
        fun getInstance(): ItemTranslator {
            return instance
        }

        private fun make(): ItemTranslator {
            val data = JsonBrowser().parse(this::class.java.getResourceAsStream("vanilla/r16_to_current_item_map.json"))
            val legacyStringToIntMap = JsonBrowser().parse(this::class.java.getResourceAsStream("vanilla/item_id_map.json")).toMap<String, Int>()

            val simpleMappings: MutableMap<String, Int> = HashObjIntMaps.newMutableMap()
            data["simple"].toMap<String, String>().forEach { (oldId, newId) ->
                simpleMappings[newId] = legacyStringToIntMap.getValue(oldId).toInt()
            }
            legacyStringToIntMap.forEach { (stringId, intId) ->
                simpleMappings[stringId] = intId
            }
            val complexMappings: MutableMap<String, Pair<Int, Int>> = HashObjObjMaps.newMutableMap()
            data["complex"].toMap<String, Map<Int, String>>().forEach { (oldId, map) ->
                map.forEach { (meta, newId) ->
                    complexMappings[newId] = Pair(legacyStringToIntMap.getValue(oldId).toInt(), meta)
                }
            }
            return ItemTranslator(ItemTypeDictionary.getInstance(), simpleMappings, complexMappings)
        }
    }

    fun toNetworkId(internalId: Int, internalMeta: Int): Pair<Int, Int> {
        if (complexCoreToNetMapping[internalId]?.containsKey(internalMeta) == true) {
            return Pair(complexCoreToNetMapping.getValue(internalId).getValue(internalMeta), 0)
        }
        if (simpleCoreToNetMapping.containsKey(internalId)) {
            return Pair(simpleCoreToNetMapping.getValue(internalId), internalMeta)
        }
        throw IllegalArgumentException("Unmapped ID/metadata combination $internalId:$internalMeta")
    }

    fun fromNetworkId(networkId: Int, networkMeta: Int, isComplexMapping: AtomicBoolean = AtomicBoolean(false)): Pair<Int, Int> {
        if (complexNetToCoreMapping.containsKey(networkId)) {
            if (networkMeta != 0) {
                throw IllegalArgumentException("Unexpected non-zero network meta on complex item mapping")
            }
            isComplexMapping.set(true)
            return complexNetToCoreMapping.getValue(networkId)
        }
        isComplexMapping.set(false)
        if (simpleNetToCoreMapping.containsKey(networkId)) {
            return Pair(simpleNetToCoreMapping.getValue(networkId), networkMeta)
        }
        throw IllegalArgumentException("Unmapped network ID/metadata combination $networkId:$networkMeta")
    }
}