package org.zhenquan.tank.moudle

import org.itheima.kotlin.game.core.Painter
import sun.security.krb5.Config

class Bullet(override var x: Int, override var y: Int) :View {


    override var width: Int = org.zhenquan.tank.Config.block

    override var height: Int =org.zhenquan.tank.Config.block


    override fun draw() {
       Painter.drawImage("img/bullet_l.gif",x,y)
    }
}