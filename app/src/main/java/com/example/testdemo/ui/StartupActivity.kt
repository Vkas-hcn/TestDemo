package com.example.testdemo.ui

import android.os.Bundle
import android.os.Looper
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.testdemo.MainActivity
import com.github.shadowsocks.R
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import java.util.*
import android.content.Intent
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.testdemo.utils.StatusBarUtils
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * Startup Page
 */
class StartupActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtils.translucent(this)
        setContentView(R.layout.activity_startup)
        supportActionBar?.hide()
        initView()
    }
    private fun initView() {
        val timer = Timer()
        val timerTask: TimerTask = HomeTimerTask()
        timer.schedule(timerTask, 2000)
        LiveEventBus
            .get("JUMP_PAGE", Boolean::class.java)
            .observeForever {
                jumpPage()
            }
    }
    /**
     * 延时
     */
    class HomeTimerTask : TimerTask() {
        override fun run() {
            Looper.prepare()
            LiveEventBus.get("JUMP_PAGE").post(true)
            Looper.loop()
        }
    }
    /**
     * 跳转页面
     */
    private fun jumpPage() {
        val intent = Intent(this@StartupActivity, MainActivity::class.java)
        startActivity(intent)
    }
    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}