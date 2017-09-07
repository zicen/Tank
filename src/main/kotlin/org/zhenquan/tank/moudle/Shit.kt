package org.zhenquan.tank.moudle

import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import org.zhenquan.tank.Config
import org.zhenquan.tank.business.Attachable
import org.zhenquan.tank.business.Blockable
import org.zhenquan.tank.business.Destroyable
import org.zhenquan.tank.business.Sufferable
import org.zhenquan.tank.ext.checkCollision

class Shit(override val x: Int, override val y: Int, override val owner: Tank) :  Destroyable, Attachable, Sufferable {
    override var blood: Int = 3
    override val attackPower: Int = 3
    override val width: Int = Config.block
    override val height: Int = Config.block
    private var isDestroyed = false
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



    override fun notifySuffer(attach: Attachable): Array<View>? {

       return  arrayOf(Blast(x, y))
    }



    override fun draw() {
       Painter.drawImage("/img/shit.png",x,y)
    }

}