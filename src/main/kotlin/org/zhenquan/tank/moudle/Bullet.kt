package org.zhenquan.tank.moudle

import org.itheima.kotlin.game.core.Painter
import org.zhenquan.tank.business.AutoMoveable
import org.zhenquan.tank.enums.Direction
import sun.security.krb5.Config

/**
 * create 函数返回两个值，是x,y
 */
class Bullet(direction: Direction, create: (width: Int, height: Int) -> Pair<Int, Int>) : AutoMoveable {
    override var currentDirection: Direction = direction
    override val speed: Int = 8
    override val width: Int
    override val height: Int
    override var x: Int = 0
    override var y: Int = 0
    private val imgPath: String = when (direction) {
        Direction.UP -> "img/bullet_d.gif"
        Direction.DOWN -> "img/bullet_u.gif"
        Direction.LEFT -> "img/bullet_r.gif"
        Direction.RIGHT -> "img/bullet_l.gif"
    }

    init {
        val size = Painter.size(imgPath)
        width = size[0]
        height = size[1]
        val invoke = create.invoke(width, height)
        x = invoke.first
        y = invoke.second
    }

    override fun draw() {
        Painter.drawImage(imgPath, x, y)
    }

    override fun autoMove() {
       when(currentDirection){
           Direction.UP -> y-=speed
           Direction.DOWN -> y+=speed
           Direction.LEFT -> x-=speed
           Direction.RIGHT -> x+=speed
       }
    }
}