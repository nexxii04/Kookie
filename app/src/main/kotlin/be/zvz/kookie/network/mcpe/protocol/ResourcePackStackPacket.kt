/**
 *
 * _  __           _    _
 * | |/ /___   ___ | | _(_) ___
 * | ' // _ \ / _ \| |/ / |/ _ \
 * | . \ (_) | (_) |   <| |  __/
 * |_|\_\___/ \___/|_|\_\_|\___|
 *
 * A server software for Minecraft: Bedrock Edition
 *
 * Copyright (C) 2021 organization Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package be.zvz.kookie.network.mcpe.protocol

import be.zvz.kookie.network.mcpe.handler.PacketHandlerInterface
import be.zvz.kookie.network.mcpe.protocol.types.resourcepack.BehaviorPackEntry
import be.zvz.kookie.network.mcpe.protocol.types.resourcepack.ResourcePackEntry
import be.zvz.kookie.network.mcpe.serializer.PacketSerializer

@ProtocolIdentify(ProtocolInfo.IDS.RESOURCE_PACK_STACK_PACKET)
class ResourcePackStackPacket : DataPacket(), ClientboundPacket {

    var mustAccept: Boolean = false
    val behaviorStacks = mutableListOf<BehaviorPackEntry>()
    val resourcePackStacks = mutableListOf<ResourcePackEntry>()

    override fun decodePayload(input: PacketSerializer) {
        mustAccept = input.getBoolean()
        val behaviorPackCount = input.getUnsignedVarInt()
        for (i in 0..behaviorPackCount) {
            behaviorStacks.add(BehaviorPackEntry.read(input))
        }
        val resourcePackCount = input.getUnsignedVarInt()
        for (i in 0..resourcePackCount) {
            resourcePackStacks.add(ResourcePackEntry.read(input))
        }
    }

    override fun encodePayload(output: PacketSerializer) {
        TODO("Not yet implemented")
    }

    override fun handle(handler: PacketHandlerInterface): Boolean {
        TODO("Not yet implemented")
    }
}