package org.zhenquan.tank.moudle

import org.itheima.kotlin.game.core.Painter
import org.zhenquan.tank.Config
import org.zhenquan.tank.business.Blockable

class Water(override var x: Int, override var y: Int) :  Blockable {
    override var width: Int  = Config.block
    override var height: Int = Config.block

    override fun draw() {
        Painter.drawImage("img/water.gif", x, y)
    }
}