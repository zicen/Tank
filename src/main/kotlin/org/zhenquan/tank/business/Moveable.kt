package org.zhenquan.tank.business

import org.zhenquan.tank.enums.Direction
import org.zhenquan.tank.moudle.View

interface Moveable : View {
    val speed :Int
    var currentDirection :Direction
    fun willCollision(block :Blockable) : Direction?
    fun notifyCollision(direction: Direction?,block: Blockable?)
}