package org.zhenquan.tank.business

import org.zhenquan.tank.moudle.View

interface Destroyable : View {
    fun isDestroyed():Boolean
}