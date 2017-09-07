package org.zhenquan.tank.moudle

import org.itheima.kotlin.game.core.Painter
import org.zhenquan.tank.Config
import org.zhenquan.tank.business.Attachable
import org.zhenquan.tank.business.Blockable
import org.zhenquan.tank.business.Sufferable

class Steel(override var x: Int, override var y: Int) : Blockable, Sufferable {
    override var blood: Int = 1

    override fun notifySuffer(attach: Attachable): Array<View>? {
        return null
    }

    override val width: Int = Config.block
    override val height: Int = Config.block

    override fun draw() {
        Painter.drawImage("img/steel.gif", x, y)
    }
}