package org.zhenquan.tank.moudle

import org.itheima.kotlin.game.core.Painter
import org.zhenquan.tank.Config
import org.zhenquan.tank.business.Blockable

class Steel(override var x: Int, override var y: Int) :Blockable{
    override var width: Int  = Config.block
    override var height: Int = Config.block

    override fun draw() {
        Painter.drawImage("img/steel.gif", x, y)
    }
}