package com.senyk.volodymyr.foregroundserviceapp

import java.util.*

class SayHelloTimerTask : TimerTask() {
    override fun run() {
        println("Hello World!")
    }
}
