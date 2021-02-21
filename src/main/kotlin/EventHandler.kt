import java.awt.MouseInfo
import java.awt.Point
import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.event.KeyEvent

class EventHandler {
    val parser = Parser()
    val robot: Robot = Robot()

    fun handleEvent(event : String) {
        val cmd = parser.parseResponse(event)
        if (cmd == "move") {
            val x = parser.getXValue(event)
            val y = parser.getYValue(event)
            val first = parser.getFirstValue(event)
            moveCursor(x, y, first)
        } else if (cmd == "click") {
            makeClick(parser.getTypeValue(event))
        } else if (cmd == "release") {
            makeRelease(parser.getTypeValue(event))
        } else if (cmd == "scroll") {
            makeScroll(parser.getYValue(event))
        } else if (cmd == "copy") {
            makeShortCut("copy")
        } else if (cmd == "paste") {
            makeShortCut("paste")
        }
    }

    private fun moveCursor(posX : Int, posY : Int, first : Boolean) {
        val p: Point = MouseInfo.getPointerInfo().location
        if (!first) {
            robot.mouseMove(p.x + (posX - lastPosX), p.y + (posY - lastPosY));
        }
        lastPosX = posX
        lastPosY = posY
    }

    private fun makeClick(type : String) {
        if (type == "left") {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        } else if (type == "right") {
            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        }
    }

    private fun makeRelease(type : String) {
        if (type == "left") {
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } else if (type == "right") {
            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        }
    }

    private fun makeShortCut(type : String) {
        if (type == "copy") {
            robot.keyPress(KeyEvent.VK_CONTROL)
            robot.keyPress(KeyEvent.VK_C)
            robot.keyRelease(KeyEvent.VK_C)
            robot.keyRelease(KeyEvent.VK_CONTROL)
        } else if (type == "paste") {
            robot.keyPress(KeyEvent.VK_CONTROL)
            robot.keyPress(KeyEvent.VK_V)
            robot.keyRelease(KeyEvent.VK_V)
            robot.keyRelease(KeyEvent.VK_CONTROL)
        }
    }

    private fun makeScroll(y : Int) {
        val mY = (y - lastPosY) / 10
        robot.mouseWheel(mY)
        lastPosY = y
    }

    companion object {
        var lastPosX = 0
        var lastPosY = 0
    }
}