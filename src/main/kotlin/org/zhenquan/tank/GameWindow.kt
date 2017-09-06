package org.zhenquan.tank

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window
import org.zhenquan.tank.business.Blockable
import org.zhenquan.tank.business.Moveable
import org.zhenquan.tank.enums.Direction
import org.zhenquan.tank.moudle.*
import java.io.File
import javax.swing.text.View

class GameWindow : Window(title = "坦克大战", icon = "img/symbol.gif", width = Config.width, height = Config.height) {

    val viewsList = arrayListOf<org.zhenquan.tank.moudle.View>()
    lateinit var myTank: Tank
    override fun onCreate() {
        val file = File(javaClass.getResource("/map/1.map").path)
        val lines = file.readLines()
        var lineNum = 0
        lines.forEach { line ->
            var columnNum = 0
            line.toCharArray().forEach { column ->
                when (column) {
                    '砖' -> viewsList.add(Wall(columnNum * Config.block, lineNum * Config.block))
                    '铁' -> viewsList.add(Steel(columnNum * Config.block, lineNum * Config.block))
                    '水' -> viewsList.add(Water(columnNum * Config.block, lineNum * Config.block))
                    '草' -> viewsList.add(Grass(columnNum * Config.block, lineNum * Config.block))
                }
                columnNum++
            };
            lineNum++
        }

        myTank = Tank(Config.block * 10, Config.block * 12)
        viewsList.add(myTank)
    }

    override fun onDisplay() {
        viewsList.forEach {
            it.draw()
        }
    }

    override fun onKeyPressed(event: KeyEvent) {
        when (event.code) {
            KeyCode.W -> {
                myTank.move(Direction.UP)
            }
            KeyCode.S -> {
                myTank.move(Direction.DOWN)
            }
            KeyCode.A -> {
                myTank.move(Direction.LEFT)
            }
            KeyCode.D -> {
                myTank.move(Direction.RIGHT)
            }
            KeyCode.ENTER -> {
                //发射子弹
                val bullet = myTank.shot()
                //交给views管理
                viewsList.add(bullet)
            }
        }
    }

    override fun onRefresh() {
        //判断运动的物体和阻塞的物体是否发生碰撞
        //1.找到运动的物体
        viewsList.filter { it is Moveable }.forEach { move ->
            move as Moveable
            var badDorection: Direction? = null
            var badBlock: Blockable? = null

            viewsList.filter { (it is Blockable) and (move != it) }.forEach blockTag@ { block ->

                block as Blockable
                //获得碰撞的方向
                val willCollision = move.willCollision(block)
                willCollision?.let {
                    //移动的发现碰撞，跳出当前循环
                    badDorection = willCollision
                    badBlock = block
                    return@blockTag
                }

            }
            //如果发现碰撞，就传入碰撞的方向以及碰撞的块
            move.notifyCollision(badDorection, badBlock)
        }

    }
}

