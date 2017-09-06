package org.zhenquan.tank.business

import org.zhenquan.tank.moudle.View

interface Sufferable : View {
    //生命值
    val blood:Int

    fun notifySuffer(attach:Attachable)
}