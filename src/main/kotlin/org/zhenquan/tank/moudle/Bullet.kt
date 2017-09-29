package org.zhenquan.tank.moudle

import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import org.zhenquan.tank.business.Attachable
import org.zhenquan.tank.business.AutoMoveable
import org.zhenquan.tank.business.Destroyable
import org.zhenquan.tank.business.Sufferable
import org.zhenquan.tank.enums.Direction
import org.zhenquan.tank.ext.checkCollision
import sun.security.krb5.Config

/**
 * create 函数返回两个值，是x,y
 */
class Bullet(override val owner: View, direction: Direction, create: (width: Int, height: Int) -> Pair<Int, Int>) :
        AutoMoveable, Destroyable, Attachable, Sufferable {
    override val blood: Int = 1

    override fun notifySuffer(attach: Attachable): Array<View>? {
        return arrayOf(Blast(x, y))
    }

    override val attackPower: Int = 1
    override var currentDirection: Direction = direction
    override val speed: Int = 8
    override val width: Int
    override val height: Int
    override var x: Int = 0
    override var y: Int = 0
    private var isDestroyed = false


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

        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
    }

    override fun isDestroyed(): Boolean {
        if (isDestroyed) return true
        if (x < -width) return true
        if (x > org.zhenquan.tank.Config.width) return true
        if (y < -height) return true
        if (y > org.zhenquan.tank.Config.height) return true
        return false
    }


    override fun isCollision(suffer: Sufferable): Boolean {
        return checkCollision(suffer)
    }

    override fun notifyAttach(suffer: Sufferable) {
        isDestroyed = true
    }
}