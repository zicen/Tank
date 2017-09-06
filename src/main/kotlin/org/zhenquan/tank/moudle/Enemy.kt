package org.zhenquan.tank.moudle

import org.itheima.kotlin.game.core.Painter
import org.zhenquan.tank.Config
import org.zhenquan.tank.business.AutoMoveable
import org.zhenquan.tank.business.Blockable
import org.zhenquan.tank.business.Moveable
import org.zhenquan.tank.enums.Direction
import java.util.*

/**
 * 敌方坦克
 */
class Enemy(override var x: Int, override var y: Int) : Moveable ,AutoMoveable,Blockable{


    override val speed: Int = 8

    override var currentDirection: Direction = Direction.DOWN
    var badDirection: Direction ?= null


    override val width: Int = Config.block

    override val height: Int = Config.block


    override fun draw() {
        val imagePath = when (currentDirection) {
            Direction.UP -> "img/enemy_1_u.gif"
            Direction.DOWN -> "img/enemy_1_d.gif"
            Direction.LEFT -> "img/enemy_1_l.gif"
            Direction.RIGHT -> "img/enemy_1_r.gif"
        }
        Painter.drawImage(imagePath, x, y)
    }

    override fun autoMove() {
        //当前方向是 阻挡方向时return
        if (currentDirection == badDirection) {
            currentDirection = rdmDirection(badDirection)
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
    private fun rdmDirection(bad:Direction?):Direction{
        val i = Random().nextInt(4)
        val direction = when(i){
            0 -> Direction.UP
            1 -> Direction.DOWN
            2 -> Direction.LEFT
            3 -> Direction.RIGHT
            else -> Direction.UP
        }

        //判断  不能要错误的方向
        if (direction == bad) {
            return rdmDirection(bad)
        }
        return direction
    }
}