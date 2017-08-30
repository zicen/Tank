package org.zhenquan.tank

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window
import org.zhenquan.tank.enums.Direction
import org.zhenquan.tank.moudle.*
import java.io.File
import javax.swing.text.View

class GameWindow : Window(title = "坦克大战",icon = "img/symbol.gif",width = Config.width,height = Config.height) {

    val viewsList  = arrayListOf<org.zhenquan.tank.moudle.View>()
    lateinit var myTank:Tank
    override fun onCreate() {
        val file = File(javaClass.getResource("/map/1.map").path)
        val lines = file.readLines()
        var lineNum = 0
        lines.forEach{ line->
            var columnNum = 0
            line.toCharArray().forEach { column->
                when(column){
                    '砖'-> viewsList.add(Wall(columnNum*Config.block,lineNum*Config.block))
                    '铁'->viewsList.add(Steel(columnNum*Config.block,lineNum*Config.block))
                    '水'->viewsList.add(Water(columnNum*Config.block,lineNum*Config.block))
                    '草'->viewsList.add(Grass(columnNum*Config.block,lineNum*Config.block))
                }
                columnNum++
            };
            lineNum++
        }

        myTank = Tank(Config.block*10,Config.block*12)
        viewsList.add(myTank)
    }

    override fun onDisplay() {
        viewsList.forEach{
            it.draw()
        }
    }

    override fun onKeyPressed(event: KeyEvent) {
        when(event.code){
            KeyCode.W -> myTank.move(Direction.UP)
            KeyCode.S -> myTank.move(Direction.DOWN)
            KeyCode.A -> myTank.move(Direction.LEFT)
            KeyCode.D -> myTank.move(Direction.RIGHT)
        }
    }

    override fun onRefresh() {
    }
}

