package org.zhenquan.tank.moudle

import org.itheima.kotlin.game.core.Painter
import org.zhenquan.tank.enums.Direction
import sun.security.krb5.Config

/**
 * create 函数返回两个值，是x,y
 */
class Bullet(val direction: Direction, create: (width:Int,height:Int) -> Pair<Int, Int>) : View {

    override val width: Int
    override val height: Int
    override val x: Int
    override val y: Int
    private val imgPath : String = when (direction) {
       Direction.UP -> "img/bullet_d.gif"
       Direction.DOWN -> "img/bullet_u.gif"
       Direction.LEFT -> "img/bullet_r.gif"
       Direction.RIGHT -> "img/bullet_l.gif"
   }

    init {
        //1、计算子弹的宽度和高度
        val size = Painter.size(imgPath)
        width = size[0]
        height = size[1]
        //2、根据子弹的宽度和高度求出x,y坐标
        val pair = create.invoke(width,height)
        x = pair.first
        y = pair.second
    }
    override fun draw() {
        Painter.drawImage(imgPath, x, y)
    }
}