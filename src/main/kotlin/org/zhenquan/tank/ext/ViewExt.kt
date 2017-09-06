package org.zhenquan.tank.ext

import org.zhenquan.tank.moudle.View

fun View.checkCollision(view: View): Boolean{
    return checkCollision(x,y,width,height,view.x,view.y,view.width,view.height)
}