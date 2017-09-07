package org.zhenquan.tank

object Config {
    var block : Int = 64
    var width : Int = block*13
    var height : Int = block*13

    /**
     * 敌方坦克速度
     */
    val enemyTankSpeed :Int = 6

    /**
     * 敌方坦克血量
     */
    val enemyTankBlood :Int = 2

    /**
     * 敌方坦克的射击频率
     */
    val enemyShotFrequency = 1000
    /**
     * 敌方坦克的移动频率
     */
    val enemyMoveFrequency = 50







    /**
     * 我方坦克速度
     */
    val tankSpeed :Int = 8
    /**
     * 我方坦克血量
     */
    val tankBlood :Int = 3


}