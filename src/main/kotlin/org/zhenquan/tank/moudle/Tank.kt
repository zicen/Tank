package org.zhenquan.tank.moudle

import org.zhenquan.tank.Config
import org.zhenquan.tank.business.Moveable
import org.zhenquan.tank.enums.Direction
import javax.swing.Painter

class Tank(override var x: Int, override var y: Int) : View ,Moveable{
    override var width: Int = Config.block

    override var height: Int = Config.block

    var currentDirection = Direction.UP
    val speed = 8
    override fun draw() {
        val s = when (currentDirection) {
            Direction.UP -> "img/tank_u.gif"
            Direction.DOWN -> "img/tank_d.gif"
            Direction.LEFT -> "img/tank_l.gif"
            Direction.RIGHT -> "img/tank_r.gif"
        }
        org.itheima.kotlin.game.core.Painter.drawImage(s, x, y)
    }

    /**
     * 坦克移动
     */
    fun move(direction: Direction) {
        if (direction != this.currentDirection) {
            this.currentDirection = direction
            return
        }


        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        //越界判断
        if (x<0) x=0
        if (x>Config.width-width) x= Config.width-width
        if (y<0) y = 0
        if (y>Config.height-height) y=Config.width-height
    }
}