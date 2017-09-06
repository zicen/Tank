package org.zhenquan.tank.business

import org.zhenquan.tank.Config
import org.zhenquan.tank.enums.Direction
import org.zhenquan.tank.moudle.View

interface Moveable : View {
    val speed: Int
    var currentDirection: Direction
    fun willCollision(block: Blockable): Direction? {
        var x: Int = this.x
        var y: Int = this.y

        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
        //和边界进行检测
        //越界判断
        if (x < 0) return Direction.LEFT
        if (x > Config.width - width) return Direction.RIGHT
        if (y < 0) return Direction.UP
        if (y > Config.height - height) return Direction.DOWN

        val collision = checkCollision(block.x, block.y, block.width, block.height
                , x, y, width, height)
        return if (collision) currentDirection else null

    }

    fun notifyCollision(direction: Direction?, block: Blockable?)
}