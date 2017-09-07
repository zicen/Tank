package org.zhenquan.tank.moudle

import org.zhenquan.tank.Config
import org.zhenquan.tank.business.*
import org.zhenquan.tank.enums.Direction
import javax.swing.Painter

class Tank(override var x: Int, override var y: Int) : Moveable ,Blockable,Sufferable,Destroyable{


    override var blood: Int = Config.tankBlood
    override val width: Int = Config.block
    override val height: Int = Config.block
    override var currentDirection = Direction.UP
    override val speed: Int = Config.tankSpeed
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

    override fun notifySuffer(attach: Attachable): Array<View>? {
        blood -= attach.attackPower
        return arrayOf(Blast(x,y))
    }


    fun shot(): Bullet {

        return Bullet(this,currentDirection, { bulletWidth, bulletHeight ->
            //計算子弹的真实坐标
            val tankX = x
            val tankY = y
            val tankWidth = width
            val tankHeight = height

            var bulletX = 0
            var bulletY = 0

            when (currentDirection) {
                Direction.UP -> {
                    bulletX = tankX + (tankWidth - bulletWidth) / 2
                    bulletY = tankY - bulletHeight / 2
                }
                Direction.DOWN -> {
                    bulletX = tankX + (tankWidth - bulletWidth) / 2
                    bulletY = tankY + tankHeight - bulletHeight / 2
                }
                Direction.LEFT -> {
                    bulletX = tankX - bulletWidth / 2
                    bulletY = tankY + (tankHeight - bulletHeight) / 2
                }
                Direction.RIGHT -> {
                    bulletX = tankX + tankWidth - bulletWidth / 2
                    bulletY = tankY + (tankHeight - bulletHeight) / 2
                }
            }
            Pair(bulletX, bulletY)
        })
    }

    override fun isDestroyed(): Boolean {
        return blood<=0
    }
}