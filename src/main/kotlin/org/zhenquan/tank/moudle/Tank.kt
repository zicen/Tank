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
    override val speed:Int = 8
    private var badDirection: Direction? = null

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
        this.badDirection = direction
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
        //和边界进行检测
        //越界判断
        if (x < 0) return Direction.LEFT
        if (x > Config.width - width) return Direction.RIGHT
        if (y < 0) return Direction.UP
        if (y > Config.height - height) return Direction.DOWN

        val collision = checkCollision(block.x, block.y, block.width, block.height
               , x, y, width, height)
        return if(collision) currentDirection else null

    }
    fun checkCollision(x1: Int, y1: Int, w1: Int, h1: Int
                       , x2: Int, y2: Int, w2: Int, h2: Int): Boolean {
        //两个物体的x,y,w,h的比较
        return when {
            y2 + h2 <= y1 -> //如果 阻挡物 在运动物的上方时 ，不碰撞
                false
            y1 + h1 <= y2 -> //如果 阻挡物 在运动物的下方时 ，不碰撞
                false
            x2 + w2 <= x1 -> //如果 阻挡物 在运动物的左方时 ，不碰撞
                false
            else -> x1 + w1 > x2
        }
    }
}