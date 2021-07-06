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

import be.zvz.kookie.math.Vector3
import be.zvz.kookie.network.mcpe.handler.PacketHandlerInterface
import be.zvz.kookie.network.mcpe.protocol.serializer.PacketSerializer

@ProtocolIdentify(ProtocolInfo.IDS.LEVEL_SOUND_EVENT_PACKET)
class LevelSoundEventPacket : DataPacket(), ClientboundPacket, ServerboundPacket {

    var sound: Int = 0
    lateinit var position: Vector3
    var extraData: Int = -1
    var entityType: String = ":" // ???
    var isBabyMob: Boolean = false // ...
    var disableRelativeVolume: Boolean = false

    override fun decodePayload(input: PacketSerializer) {
        sound = input.getUnsignedVarInt()
        position = input.getVector3()
        extraData = input.getVarInt()
        entityType = input.getString()
        isBabyMob = input.getBoolean()
        disableRelativeVolume = input.getBoolean()
    }

    override fun encodePayload(output: PacketSerializer) {
        output.putUnsignedVarInt(sound)
        output.putVector3(position)
        output.putVarInt(extraData)
        output.putString(entityType)
        output.putBoolean(isBabyMob)
        output.putBoolean(disableRelativeVolume)
    }

    override fun handle(handler: PacketHandlerInterface): Boolean = handler.handleLevelSoundEvent(this)

    companion object {
        @JvmStatic
        @JvmOverloads
        fun create(
            sound: Int,
            pos: Vector3?,
            extraData: Int = -1,
            entityType: String = ":",
            isBabyMob: Boolean = false
        ) = LevelSoundEventPacket().apply {
            this.sound = sound
            this.extraData = extraData
            pos?.let { this.position = it }
            this.disableRelativeVolume = pos === null
            this.entityType = entityType
            this.isBabyMob = isBabyMob
        }
    }

    enum class Sound(val sound: Int) {
        ITEM_USE_ON(0),
        HIT(1),
        STEP(2),
        FLY(3),
        JUMP(4),
        BREAK(5),
        PLACE(6),
        HEAVY_STEP(7),
        GALLOP(8),
        FALL(9),
        AMBIENT(10),
        AMBIENT_BABY(11),
        AMBIENT_IN_WATER(12),
        BREATHE(13),
        DEATH(14),
        DEATH_IN_WATER(15),
        DEATH_TO_ZOMBIE(16),
        HURT(17),
        HURT_IN_WATER(18),
        MAD(19),
        BOOST(20),
        BOW(21),
        SQUISH_BIG(22),
        SQUISH_SMALL(23),
        FALL_BIG(24),
        FALL_SMALL(25),
        SPLASH(26),
        FIZZ(27),
        FLAP(28),
        SWIM(29),
        DRINK(30),
        EAT(31),
        TAKEOFF(32),
        SHAKE(33),
        PLOP(34),
        LAND(35),
        SADDLE(36),
        ARMOR(37),
        MOB_ARMOR_STAND_PLACE(38),
        ADD_CHEST(39),
        THROW(40),
        ATTACK(41),
        ATTACK_NODAMAGE(42),
        ATTACK_STRONG(43),
        WARN(44),
        SHEAR(45),
        MILK(46),
        THUNDER(47),
        EXPLODE(48),
        FIRE(49),
        IGNITE(50),
        FUSE(51),
        STARE(52),
        SPAWN(53),
        SHOOT(54),
        BREAK_BLOCK(55),
        LAUNCH(56),
        BLAST(57),
        LARGE_BLAST(58),
        TWINKLE(59),
        REMEDY(60),
        UNFECT(61),
        LEVELUP(62),
        BOW_HIT(63),
        BULLET_HIT(64),
        EXTINGUISH_FIRE(65),
        ITEM_FIZZ(66),
        CHEST_OPEN(67),
        CHEST_CLOSED(68),
        SHULKERBOX_OPEN(69),
        SHULKERBOX_CLOSED(70),
        ENDERCHEST_OPEN(71),
        ENDERCHEST_CLOSED(72),
        POWER_ON(73),
        POWER_OFF(74),
        ATTACH(75),
        DETACH(76),
        DENY(77),
        TRIPOD(78),
        POP(79),
        DROP_SLOT(80),
        NOTE(81),
        THORNS(82),
        PISTON_IN(83),
        PISTON_OUT(84),
        PORTAL(85),
        WATER(86),
        LAVA_POP(87),
        LAVA(88),
        BURP(89),
        BUCKET_FILL_WATER(90),
        BUCKET_FILL_LAVA(91),
        BUCKET_EMPTY_WATER(92),
        BUCKET_EMPTY_LAVA(93),
        ARMOR_EQUIP_CHAIN(94),
        ARMOR_EQUIP_DIAMOND(95),
        ARMOR_EQUIP_GENERIC(96),
        ARMOR_EQUIP_GOLD(97),
        ARMOR_EQUIP_IRON(98),
        ARMOR_EQUIP_LEATHER(99),
        ARMOR_EQUIP_ELYTRA(100),
        RECORD_13(101),
        RECORD_CAT(102),
        RECORD_BLOCKS(103),
        RECORD_CHIRP(104),
        RECORD_FAR(105),
        RECORD_MALL(106),
        RECORD_MELLOHI(107),
        RECORD_STAL(108),
        RECORD_STRAD(109),
        RECORD_WARD(110),
        RECORD_11(111),
        RECORD_WAIT(112),
        STOP_RECORD(113), // Not really a sound
        FLOP(114),
        ELDERGUARDIAN_CURSE(115),
        MOB_WARNING(116),
        MOB_WARNING_BABY(117),
        TELEPORT(118),
        SHULKER_OPEN(119),
        SHULKER_CLOSE(120),
        HAGGLE(121),
        HAGGLE_YES(122),
        HAGGLE_NO(123),
        HAGGLE_IDLE(124),
        CHORUSGROW(125),
        CHORUSDEATH(126),
        GLASS(127),
        POTION_BREWED(128),
        CAST_SPELL(129),
        PREPARE_ATTACK(130),
        PREPARE_SUMMON(131),
        PREPARE_WOLOLO(132),
        FANG(133),
        CHARGE(134),
        CAMERA_TAKE_PICTURE(135),
        LEASHKNOT_PLACE(136),
        LEASHKNOT_BREAK(137),
        GROWL(138),
        WHINE(139),
        PANT(140),
        PURR(141),
        PURREOW(142),
        DEATH_MIN_VOLUME(143),
        DEATH_MID_VOLUME(144),
        IMITATE_BLAZE(145),
        IMITATE_CAVE_SPIDER(146),
        IMITATE_CREEPER(147),
        IMITATE_ELDER_GUARDIAN(148),
        IMITATE_ENDER_DRAGON(149),
        IMITATE_ENDERMAN(150),
        IMITATE_EVOCATION_ILLAGER(152),
        IMITATE_GHAST(153),
        IMITATE_HUSK(154),
        IMITATE_ILLUSION_ILLAGER(155),
        IMITATE_MAGMA_CUBE(156),
        IMITATE_POLAR_BEAR(157),
        IMITATE_SHULKER(158),
        IMITATE_SILVERFISH(159),
        IMITATE_SKELETON(160),
        IMITATE_SLIME(161),
        IMITATE_SPIDER(162),
        IMITATE_STRAY(163),
        IMITATE_VEX(164),
        IMITATE_VINDICATION_ILLAGER(165),
        IMITATE_WITCH(166),
        IMITATE_WITHER(167),
        IMITATE_WITHER_SKELETON(168),
        IMITATE_WOLF(169),
        IMITATE_ZOMBIE(170),
        IMITATE_ZOMBIE_PIGMAN(171),
        IMITATE_ZOMBIE_VILLAGER(172),
        BLOCK_END_PORTAL_FRAME_FILL(173),
        BLOCK_END_PORTAL_SPAWN(174),
        RANDOM_ANVIL_USE(175),
        BOTTLE_DRAGONBREATH(176),
        PORTAL_TRAVEL(177),
        ITEM_TRIDENT_HIT(178),
        ITEM_TRIDENT_RETURN(179),
        ITEM_TRIDENT_RIPTIDE_1(180),
        ITEM_TRIDENT_RIPTIDE_2(181),
        ITEM_TRIDENT_RIPTIDE_3(182),
        ITEM_TRIDENT_THROW(183),
        ITEM_TRIDENT_THUNDER(184),
        ITEM_TRIDENT_HIT_GROUND(185),
        DEFAULT(186),
        BLOCK_FLETCHING_TABLE_USE(187),
        ELEMCONSTRUCT_OPEN(188),
        ICEBOMB_HIT(189),
        BALLOONPOP(190),
        LT_REACTION_ICEBOMB(191),
        LT_REACTION_BLEACH(192),
        LT_REACTION_EPASTE(193),
        LT_REACTION_EPASTE2(194),
        LT_REACTION_FERTILIZER(199),
        LT_REACTION_FIREBALL(200),
        LT_REACTION_MGSALT(201),
        LT_REACTION_MISCFIRE(202),
        LT_REACTION_FIRE(203),
        LT_REACTION_MISCEXPLOSION(204),
        LT_REACTION_MISCMYSTICAL(205),
        LT_REACTION_MISCMYSTICAL2(206),
        LT_REACTION_PRODUCT(207),
        SPARKLER_USE(208),
        GLOWSTICK_USE(209),
        SPARKLER_ACTIVE(210),
        CONVERT_TO_DROWNED(211),
        BUCKET_FILL_FISH(212),
        BUCKET_EMPTY_FISH(213),
        BUBBLE_UP(214),
        BUBBLE_DOWN(215),
        BUBBLE_POP(216),
        BUBBLE_UPINSIDE(217),
        BUBBLE_DOWNINSIDE(218),
        HURT_BABY(219),
        DEATH_BABY(220),
        STEP_BABY(221),
        BORN(223),
        BLOCK_TURTLE_EGG_BREAK(224),
        BLOCK_TURTLE_EGG_CRACK(225),
        BLOCK_TURTLE_EGG_HATCH(226),
        LAY_EGG(227),
        BLOCK_TURTLE_EGG_ATTACK(228),
        BEACON_ACTIVATE(229),
        BEACON_AMBIENT(230),
        BEACON_DEACTIVATE(231),
        BEACON_POWER(232),
        CONDUIT_ACTIVATE(233),
        CONDUIT_AMBIENT(234),
        CONDUIT_ATTACK(235),
        CONDUIT_DEACTIVATE(236),
        CONDUIT_SHORT(237),
        SWOOP(238),
        BLOCK_BAMBOO_SAPLING_PLACE(239),
        PRESNEEZE(240),
        SNEEZE(241),
        AMBIENT_TAME(242),
        SCARED(243),
        BLOCK_SCAFFOLDING_CLIMB(244),
        CROSSBOW_LOADING_START(245),
        CROSSBOW_LOADING_MIDDLE(246),
        CROSSBOW_LOADING_END(247),
        CROSSBOW_SHOOT(248),
        CROSSBOW_QUICK_CHARGE_START(249),
        CROSSBOW_QUICK_CHARGE_MIDDLE(250),
        CROSSBOW_QUICK_CHARGE_END(251),
        AMBIENT_AGGRESSIVE(252),
        AMBIENT_WORRIED(253),
        CANT_BREED(254),
        ITEM_SHIELD_BLOCK(255),
        ITEM_BOOK_PUT(256),
        BLOCK_GRINDSTONE_USE(257),
        BLOCK_BELL_HIT(258),
        BLOCK_CAMPFIRE_CRACKLE(259),
        ROAR(260),
        STUN(261),
        BLOCK_SWEET_BERRY_BUSH_HURT(262),
        BLOCK_SWEET_BERRY_BUSH_PICK(263),
        BLOCK_CARTOGRAPHY_TABLE_USE(264),
        BLOCK_STONECUTTER_USE(265),
        BLOCK_COMPOSTER_EMPTY(266),
        BLOCK_COMPOSTER_FILL(267),
        BLOCK_COMPOSTER_FILL_SUCCESS(268),
        BLOCK_COMPOSTER_READY(269),
        BLOCK_BARREL_OPEN(270),
        BLOCK_BARREL_CLOSE(271),
        RAID_HORN(272),
        BLOCK_LOOM_USE(273),
        AMBIENT_IN_RAID(274),
        UI_CARTOGRAPHY_TABLE_TAKE_RESULT(275),
        UI_STONECUTTER_TAKE_RESULT(276),
        UI_LOOM_TAKE_RESULT(277),
        BLOCK_SMOKER_SMOKE(278),
        BLOCK_BLASTFURNACE_FIRE_CRACKLE(279),
        BLOCK_SMITHING_TABLE_USE(280),
        SCREECH(281),
        SLEEP(282),
        BLOCK_FURNACE_LIT(283),
        CONVERT_MOOSHROOM(284),
        MILK_SUSPICIOUSLY(285),
        CELEBRATE(286),
        JUMP_PREVENT(287),
        AMBIENT_POLLINATE(288),
        BLOCK_BEEHIVE_DRIP(289),
        BLOCK_BEEHIVE_ENTER(290),
        BLOCK_BEEHIVE_EXIT(291),
        BLOCK_BEEHIVE_WORK(292),
        BLOCK_BEEHIVE_SHEAR(293),
        DRINK_HONEY(294),
        AMBIENT_CAVE(295),
        RETREAT(296),
        CONVERTED_TO_ZOMBIFIED(297),
        ADMIRE(298),
        STEP_LAVA(299),
        TEMPT(300),
        PANIC(301),
        ANGRY(302),
        AMBIENT_WARPED_FOREST_MOOD(303),
        AMBIENT_SOULSAND_VALLEY_MOOD(304),
        AMBIENT_NETHER_WASTES_MOOD(305),
        RESPAWN_ANCHOR_BASALT_DELTAS_MOOD(306),
        AMBIENT_CRIMSON_FOREST_MOOD(307),
        RESPAWN_ANCHOR_CHARGE(308),
        RESPAWN_ANCHOR_DEPLETE(309),
        RESPAWN_ANCHOR_SET_SPAWN(310),
        RESPAWN_ANCHOR_AMBIENT(311),
        PARTICLE_SOUL_ESCAPE_QUIET(312),
        PARTICLE_SOUL_ESCAPE_LOUD(313),
        RECORD_PIGSTEP(314),
        LODESTONE_COMPASS_LINK_COMPASS_TO_LODESTONE(315),
        SMITHING_TABLE_USE(316),
        ARMOR_EQUIP_NETHERITE(317),
        AMBIENT_WARPED_FOREST_LOOP(318),
        AMBIENT_SOULSAND_VALLEY_LOOP(319),
        AMBIENT_NETHER_WASTES_LOOP(320),
        AMBIENT_BASALT_DELTAS_LOOP(321),
        AMBIENT_CRIMSON_FOREST_LOOP(322),
        AMBIENT_WARPED_FOREST_ADDITIONS(323),
        AMBIENT_SOULSAND_VALLEY_ADDITIONS(324),
        AMBIENT_NETHER_WASTES_ADDITIONS(325),
        AMBIENT_BASALT_DELTAS_ADDITIONS(326),
        AMBIENT_CRIMSON_FOREST_ADDITIONS(327),
        BUCKET_FILL_POWDER_SNOW(328),
        BUCKET_EMPTY_POWDER_SNOW(329),
        UNDEFINED(330),
    }
}
