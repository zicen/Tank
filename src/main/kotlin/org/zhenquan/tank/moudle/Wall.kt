package org.zhenquan.tank.moudle

import org.itheima.kotlin.game.core.Painter
import org.zhenquan.tank.Config
import org.zhenquan.tank.business.Blockable

class Wall(override var x: Int, override var y: Int) : View , Blockable {
    override var width: Int  = Config.width
    override var height: Int = Config.height

    override fun draw() {
        Painter.drawImage("img/wall.gif", x, y)
    }
}