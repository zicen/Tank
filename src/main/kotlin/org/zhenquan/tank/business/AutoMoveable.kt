package org.zhenquan.tank.business

import org.zhenquan.tank.enums.Direction

interface AutoMoveable : org.zhenquan.tank.moudle.View {
    val currentDirection :Direction

    val speed:Int

    fun autoMove()

}