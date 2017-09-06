package org.zhenquan.tank.moudle

interface View {
    val x : Int
    val y : Int
    val width :Int
    val height: Int

    fun draw()
}