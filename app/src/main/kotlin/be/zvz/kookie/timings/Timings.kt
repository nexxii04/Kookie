package be.zvz.kookie.timings

import be.zvz.kookie.block.tile.Tile
import be.zvz.kookie.network.mcpe.protocol.DataPacket
import be.zvz.kookie.network.mcpe.protocol.ProtocolIdentify
import be.zvz.kookie.network.mcpe.protocol.ProtocolInfo
import com.koloboke.collect.map.hash.HashObjObjMaps

object Timings {
    const val INCLUDED_BY_OTHER_TIMINGS_PREFIX = "** "

    private val timings: MutableMap<String, TimingsHandler> = HashObjObjMaps.newMutableMap()

    fun getTileEntityTimings(tile: Tile): TimingsHandler {
        var exist = timings[tile::class.java.simpleName]
        if (exist == null) {
            exist = TimingsHandler("Tile " + tile::class.java.simpleName)
            timings[tile::class.java.simpleName] = exist
        }
        return exist
    }

    fun getPacketDecodeTimings(packet: DataPacket): TimingsHandler {
        val protocolIdentify = this::class.java.getAnnotation(ProtocolIdentify::class.java)!!
        val networkId = if (protocolIdentify.networkId == ProtocolInfo.IDS.UNKNOWN && protocolIdentify.customId != -1) {
            protocolIdentify.customId
        } else {
            protocolIdentify.networkId.id
        }
        val key = "PacketDecode: $networkId"
        var exist = timings[key]
        if (exist === null) {
            exist = TimingsHandler("Packet " + packet::class.java.simpleName + "($networkId) decode", null)
            timings[key] = exist
        }
        return exist
    }

    fun getPacketSendTimings(packet: DataPacket): TimingsHandler {
        val protocolIdentify = this::class.java.getAnnotation(ProtocolIdentify::class.java)!!
        val networkId = if (protocolIdentify.networkId == ProtocolInfo.IDS.UNKNOWN && protocolIdentify.customId != -1) {
            protocolIdentify.customId
        } else {
            protocolIdentify.networkId.id
        }
        val key = "PacketSend: $networkId"
        var exist = timings[key]
        if (exist === null) {
            exist = TimingsHandler("Packet " + packet::class.java.simpleName + "($networkId) send", null)
            timings[key] = exist
        }
        return exist
    }
}
