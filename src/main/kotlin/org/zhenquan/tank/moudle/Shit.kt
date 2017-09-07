package org.zhenquan.tank.moudle

import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import org.zhenquan.tank.Config
import org.zhenquan.tank.business.Attachable
import org.zhenquan.tank.business.Destroyable
import org.zhenquan.tank.business.Sufferable
import org.zhenquan.tank.ext.checkCollision

class Shit(override val x: Int, override val y: Int, override val owner: Tank) :  Destroyable, Attachable, Sufferable {
    override var blood: Int = 1
    override val attackPower: Int = 3
    override val width: Int = Config.block
    override val height: Int = Config.block
    private var isDestroyed = false
    override fun isDestroyed(): Boolean {
        return blood <=0
    }



    override fun isCollision(suffer: Sufferable): Boolean {
        blood-=1
        Composer.play("snd/blast.wav")
        return checkCollision(suffer)
    }

    override fun notifyAttach(suffer: Sufferable) {

    }



    override fun notifySuffer(attach: Attachable): Array<View>? {
       return  arrayOf(Blast(x, y))
    }



    override fun draw() {
       Painter.drawImage("/img/shit.jpg",x,y)
    }

}