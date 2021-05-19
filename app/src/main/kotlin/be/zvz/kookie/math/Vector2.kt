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
package be.zvz.kookie.math

import kotlin.math.*

class Vector2 @JvmOverloads constructor(var x: Float = 0F, var y: Float = 0F) : Vector {
    override fun equals(other: Any?): Boolean = other is Vector2 && other.x == x && other.y == y

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    @JvmOverloads
    fun add(x: Float, y: Float = 0F): Vector2 = Vector2(this.x + x, this.y + y)

    fun add(x: Vector2): Vector2 = this + x

    operator fun plus(x: Vector2): Vector2 = Vector2(this.x + x.x, this.y + x.y)

    @JvmOverloads
    fun subtract(x: Float, y: Float = 0F): Vector2 = Vector2(this.x - x, this.y - y)

    fun subtract(x: Vector2): Vector2 = this - x

    operator fun minus(x: Vector2): Vector2 = Vector2(this.x - x.x, this.y - x.y)

    fun multiply(len: Float): Vector2 = this * len

    operator fun times(len: Float): Vector2 = Vector2(x * len, y * len)

    fun divide(len: Float): Vector2 = this / len

    operator fun div(len: Float): Vector2 {
        if (len == 0F) {
            throw RuntimeException("Division by zero")
        }
        return Vector2(x / len, y / len)
    }

    fun ceil(): Vector2 = Vector2(ceil(x), ceil(y))

    fun floor(): Vector2 = Vector2(floor(x), floor(y))

    fun round(): Vector2 = Vector2(round(x), round(y))

    fun abs(): Vector2 = Vector2(abs(x), abs(y))

    fun distance(pos: Vector2): Float = distance(x, y)

    fun distance(x: Float, y: Float): Float = sqrt(distanceSquared(x, y))

    fun distanceSquared(pos: Vector2): Float = distanceSquared(pos.x, pos.y)

    fun distanceSquared(x: Float, y: Float): Float = (this.x - x).pow(2) + (this.y - y).pow(2)

    fun length(): Float = sqrt(lengthSquared())

    fun lengthSquared(): Float = x * x + y * y

    fun normalize(): Vector2 {
        val len = lengthSquared()
        return if (len > 0) {
            this / sqrt(len)
        } else {
            Vector2(0F, 0F)
        }
    }

    fun dot(v: Vector2): Float = x * v.x + y * v.y
}