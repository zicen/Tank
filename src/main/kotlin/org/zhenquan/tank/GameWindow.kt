package org.zhenquan.tank

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window
import org.zhenquan.tank.business.*
import org.zhenquan.tank.enums.Direction
import org.zhenquan.tank.moudle.*
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList
import javax.swing.text.View

class GameWindow : Window(title = "坦克大战", icon = "img/symbol.gif", width = Config.width, height = Config.height) {

    val viewsList = CopyOnWriteArrayList<org.zhenquan.tank.moudle.View>()  //使用线程安全的集合CopyOnWriteArrayList
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
                    '敌' -> viewsList.add(Enemy(columnNum * Config.block, lineNum * Config.block))
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


        //检测自动移动能力的物体，让他们自己动起来
        viewsList.filter { it is AutoMoveable }.forEach{
            (it as AutoMoveable).autoMove()
        }

        //检测销毁
        viewsList.filter {  it is Destroyable }.forEach{
            if ((it as Destroyable).isDestroyed()) {
                viewsList.remove(it)
            }

        }


        //检测攻击者对象与被攻击者对象
        viewsList.filter { (it is Attachable) }.forEach { attach->
            attach as Attachable
            viewsList.filter { it is Sufferable }.forEach sufferTag@{ suffer->
                suffer as Sufferable
                if (attach.isCollision(suffer)) {
                    //产生碰撞，通知攻击者
                    attach.notifyAttach(suffer)
                    val sufferViews = suffer.notifySuffer(attach)

                    sufferViews?.let {
                        viewsList.addAll(sufferViews)
                    }
                    return@sufferTag
                }
            }
        }
    }
}

