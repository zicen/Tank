package org.zhenquan.tank.business

import org.zhenquan.tank.moudle.View

interface Destroyable : View {
    fun isDestroyed():Boolean

    fun showDestry():Array<View>?{
        return null
    }
}