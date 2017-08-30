package org.zhenquan.tank.moudle

import org.zhenquan.tank.Config
import org.zhenquan.tank.business.Blockable
import org.zhenquan.tank.business.Moveable
import org.zhenquan.tank.enums.Direction
import javax.swing.Painter

class Tank(override var x: Int, override var y: Int) : Moveable {


    override var width: Int = Config.block
    override var height: Int = Config.block
    override var currentDirection = Direction.UP
    override val speed = 8
    var badDirection: Direction? = null

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
        //当前方向是 阻挡方向时return
        if (direction == badDirection) {
            return
        }

        //点击一下方向的时候不移动只变方向
        if (direction != this.currentDirection) {
            this.currentDirection = direction
            return
        }

        //移动
        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        //越界判断
        if (x < 0) x = 0
        if (x > Config.width - width) x = Config.width - width
        if (y < 0) y = 0
        if (y > Config.height - height) y = Config.width - height
    }


    override fun notifyCollision(direction: Direction?, block: Blockable?) {
        badDirection = direction
    }

    override fun willCollision(block: Blockable): Direction? {
        var x: Int = this.x
        var y: Int = this.y

        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
        val collision: Boolean = when {
            block.y + block.height <= y ->//如果阻挡物在运动物的上方的时候，不碰撞
                false
        //如果阻挡物在运动物的下方的时候，不碰撞
            y + height <= block.y ->
                false
        //如果阻挡物在运动物的做方的时候，不碰撞
            block.x + block.width <= x ->
                false
            else -> x + width > block.x
        }


        return if(collision) currentDirection else null

    }
}