package org.zhenquan.tank.moudle

import org.itheima.kotlin.game.core.Painter
import org.zhenquan.tank.Config
import org.zhenquan.tank.business.Attachable
import org.zhenquan.tank.business.Blockable
import org.zhenquan.tank.business.Destroyable
import org.zhenquan.tank.business.Sufferable

class Wall(override var x: Int, override var y: Int) : Blockable ,Sufferable,Destroyable{


    override var blood: Int = 3

    override val width: Int  = Config.block
    override val height: Int = Config.block

    override fun draw() {
        Painter.drawImage("img/wall.gif", x, y)
    }

    override fun notifySuffer(attach: Attachable) {
        blood -= attach.attackPower
        println("Wall notifySuffer")
    }

    override fun isDestroyed(): Boolean  = blood<=0
}