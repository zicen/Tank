package org.zhenquan.tank.moudle

import org.itheima.kotlin.game.core.Painter
import org.zhenquan.tank.Config
import org.zhenquan.tank.business.Attachable
import org.zhenquan.tank.business.Blockable
import org.zhenquan.tank.business.Destroyable
import org.zhenquan.tank.business.Sufferable

/**
 * 大本营
 *
 * 具备阻挡的功能
 * 具备接受攻击的功能
 * 具备接销毁的能力
 */
class Camp(override var x: Int, override var y: Int) : Blockable,Sufferable,Destroyable {


    override var width: Int = Config.block*2
    override var height: Int= Config.block +32
    override var blood: Int = 12

    override fun draw() {
        //血量低于 6个时画的时 砖墙
        //血量低于 3个时画的时 没有墙
        if (blood <= 3) {
            width = Config.block
            height = Config.block
            x = (Config.width - Config.block) / 2
            y = Config.height - Config.block
            Painter.drawImage("img/camp.gif", x, y)

        } else if (blood <= 6) {

            //绘制外围的砖块
            Painter.drawImage("img/wall_small.gif", x, y)
            Painter.drawImage("img/wall_small.gif", x + 32, y)
            Painter.drawImage("img/wall_small.gif", x + 64, y)
            Painter.drawImage("img/wall_small.gif", x + 96, y)

            Painter.drawImage("img/wall_small.gif", x, y + 32)
            Painter.drawImage("img/wall_small.gif", x, y + 64)

            Painter.drawImage("img/wall_small.gif", x + 96, y + 32)
            Painter.drawImage("img/wall_small.gif", x + 96, y + 64)

            Painter.drawImage("img/camp.gif", x + 32, y + 32)

        } else {

            //绘制外围的砖块
            Painter.drawImage("img/steel_small.gif", x, y)
            Painter.drawImage("img/steel_small.gif", x + 32, y)
            Painter.drawImage("img/steel_small.gif", x + 64, y)
            Painter.drawImage("img/steel_small.gif", x + 96, y)

            Painter.drawImage("img/steel_small.gif", x, y + 32)
            Painter.drawImage("img/steel_small.gif", x, y + 64)

            Painter.drawImage("img/steel_small.gif", x + 96, y + 32)
            Painter.drawImage("img/steel_small.gif", x + 96, y + 64)

            Painter.drawImage("img/camp.gif", x + 32, y + 32)

        }
    }
    override fun notifySuffer(attach: Attachable): Array<View>? {
        blood-= attach.attackPower
        if (blood == 3 || blood == 6) {
            val x = x - 32
            val y = y - 32
            return arrayOf(Blast(x, y)
                    , Blast(x + 32, y)
                    , Blast(x + Config.block, y)
                    , Blast(x + Config.block + 32, y)
                    , Blast(x + Config.block * 2, y)
                    , Blast(x, y + 32)
                    , Blast(x, y + Config.block)
                    , Blast(x, y + Config.block + 32)
                    , Blast(x + Config.block * 2, y + 32)
                    , Blast(x + Config.block * 2, y + Config.block)
                    , Blast(x + Config.block * 2, y + Config.block + 32))
        }
        return null
    }

    override fun isDestroyed(): Boolean {
        return blood <=0
    }

    override fun showDestry(): Array<View>? {
        return arrayOf(Blast(x - 32, y - 32)
                , Blast(x, y - 32)
                , Blast(x + 32, y - 32)

                , Blast(x - 32, y)
                , Blast(x, y)
                , Blast(x + 32, y)

                , Blast(x - 32, y + 32)
                , Blast(x, y + 32)
                , Blast(x + 32, y + 32))
    }
}