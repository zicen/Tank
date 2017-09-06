package org.zhenquan.tank.moudle

import org.itheima.kotlin.game.core.Painter
import org.zhenquan.tank.Config

class Grass(override var x: Int, override var y: Int) :View {
    override val width: Int  = Config.block
    override val height: Int = Config.block

    override fun draw() {
        Painter.drawImage("img/grass.gif",x,y)
    }
}