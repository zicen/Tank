package org.zhenquan.tank.business

import org.zhenquan.tank.moudle.View

interface Attachable:View {

    val attackPower:Int

    //判断是否与被攻击者碰撞
    fun isCollision(suffer:Sufferable):Boolean

    fun notifyAttach(suffer:Sufferable)
}