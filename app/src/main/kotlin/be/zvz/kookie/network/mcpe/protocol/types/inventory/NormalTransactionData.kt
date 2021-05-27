package be.zvz.kookie.network.mcpe.protocol.types.inventory

import be.zvz.kookie.network.mcpe.protocol.InventoryTransactionPacket
import be.zvz.kookie.network.mcpe.serializer.PacketSerializer

class NormalTransactionData : TransactionData() {

    override val typeId = InventoryTransactionPacket.TYPE_NORMAL
    override fun decodeData(input: PacketSerializer) {
    }

    override fun encodeData(output: PacketSerializer) {
    }

    companion object {
        fun new(actions: MutableList<NetworkInventoryAction>): NormalTransactionData {
            val result = NormalTransactionData()
            result.setActions(actions)
            return result
        }
    }
}