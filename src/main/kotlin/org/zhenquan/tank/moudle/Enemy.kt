package org.zhenquan.tank.moudle

import org.itheima.kotlin.game.core.Painter
import org.zhenquan.tank.Config
import org.zhenquan.tank.business.*
import org.zhenquan.tank.enums.Direction
import java.util.*

/**
 * 敌方坦克
 */
class Enemy(override var x: Int, override var y: Int) : Moveable, AutoMoveable, Blockable, AutoShot, Sufferable,Destroyable {

    override var blood: Int = Config.enemyTankBlood
    override val speed: Int = Config.enemyTankSpeed
    override var currentDirection: Direction = Direction.DOWN
    var badDirection: Direction? = null
    override val width: Int = Config.block
    override val height: Int = Config.block
    private var lastShotTime = 0L
    private val shotFrequency = Config.enemyShotFrequency
    private var lastMoveTime = 0L
    private val MoveFrequency = Config.enemyMoveFrequency
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
        //移动频率
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastMoveTime < MoveFrequency) return
        lastMoveTime = currentTimeMillis

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

    private fun rdmDirection(bad: Direction?): Direction {
        val i = Random().nextInt(4)
        val direction = when (i) {
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

    override fun autoShot(): View? {
        //射击频率
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastShotTime < shotFrequency) return null
        lastShotTime = currentTimeMillis
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

    override fun notifySuffer(attach: Attachable): Array<View>? {
        if (attach.owner is Enemy) {
            return null
        }
        blood -= attach.attackPower
        return arrayOf(Blast(x, y))
    }

    override fun isDestroyed(): Boolean {
        return blood <=0
    }

}