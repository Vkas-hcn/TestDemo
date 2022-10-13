package com.example.testdemo

import android.os.Bundle
import android.os.RemoteException
import android.os.SystemClock
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceDataStore
import com.github.shadowsocks.Core
import com.github.shadowsocks.R
import com.github.shadowsocks.aidl.IShadowsocksService
import com.github.shadowsocks.aidl.ShadowsocksConnection
import com.github.shadowsocks.bg.BaseService
import com.github.shadowsocks.database.Profile
import com.github.shadowsocks.database.ProfileManager
import com.github.shadowsocks.preference.DataStore
import com.github.shadowsocks.preference.OnPreferenceDataStoreChangeListener
import com.github.shadowsocks.utils.Key
import com.github.shadowsocks.utils.StartService
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


private lateinit var imgSwitch: ImageView
private lateinit var txtConnect: TextView
private lateinit var timer: Chronometer
lateinit var mAdView: AdView
private var mInterstitialAd: InterstitialAd? = null
private var  rotateAnim :Animation? = null
class MainActivity : AppCompatActivity(), ShadowsocksConnection.Callback,
    OnPreferenceDataStoreChangeListener {
    companion object {
        var stateListener: ((BaseService.State) -> Unit)? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAd()
        initView()
    }

    private fun initAd() {
        var adRequest = AdRequest.Builder().build()
        mAdView = findViewById(R.id.adView)
        mAdView.loadAd(adRequest)

        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.toString().let { Log.d("TAG", "===$it") }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("TAG", "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            })
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d("TAG", "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d("TAG", "Ad dismissed fullscreen content.")
                mInterstitialAd = null
            }


            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d("TAG", "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d("TAG", "Ad showed fullscreen content.")
            }
        }

    }
    private fun initView() {
        timer = findViewById(R.id.timer)
        imgSwitch = findViewById(R.id.img_switch)
        txtConnect = findViewById(R.id.txt_connect)
        imgSwitch.setOnClickListener {
            startInterstitial()
        }
         rotateAnim = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnim?.duration = 3000;
        rotateAnim?.repeatCount = -1;//动画的重复次数
        rotateAnim?.fillAfter = true;//设置为true，动画转化结束后被应用
        changeState(BaseService.State.Idle, animate = false)
        connection.connect(this, this)
        DataStore.publicStore.registerChangeListener(this)
        ProfileManager.getProfile(DataStore.profileId).let {
            if (it != null) {
                ProfileManager.updateProfile(it)
            } else {
                ProfileManager.createProfile(Profile())
            }
        }
        DataStore.profileId = 1L
    }
    var state = BaseService.State.Idle
    private val connection = ShadowsocksConnection(true)
    private val connect = registerForActivityResult(StartService()) {
        if (it) {
            Toast.makeText(this, "权限不足", LENGTH_SHORT).show()
        }
    }

    /**
     * 启动VPN
     */
    private fun startVpn() {
        if (state.canStop) {
            Core.stopService()
        } else {
            connect.launch(null)
            timer.base = SystemClock.elapsedRealtime()
        }
    }

    /**
     * 启动插页广告
     */
    private fun startInterstitial() {
        startVpn()
//        if (mInterstitialAd != null) {
//            mInterstitialAd?.show(this)
//        } else {
//            Log.d("TAG", "The interstitial ad wasn't ready yet.")
//        }
    }


    override fun onServiceDisconnected() = changeState(BaseService.State.Idle)
    override fun onBinderDied() {
        connection.disconnect(this)
        connection.connect(this, this)
    }

    private fun changeState(
        state: BaseService.State,
        msg: String? = null,
        animate: Boolean = true
    ) {
        Log.i("TAG", "changeState: --->$state---msg=$msg")
        setConnectionStatusText(state.name)
        this.state = state
        stateListener?.invoke(state)
    }

    /**
     * 设置连接状态文本
     */
    private fun setConnectionStatusText(state: String) {
         when (state) {
            "Connecting" -> {
                txtConnect.text = "Connecting..."
            }
            "Connected" -> {
                timer.start()
                imgSwitch.startAnimation(rotateAnim)
                txtConnect.text = "Connected"
            }
            "Stopping" -> {
                txtConnect.text =  "Stopping"
            }
            "Stopped" -> {
                timer.stop()
                imgSwitch.clearAnimation()
                txtConnect.text =    "Stopped"
            }
            else -> {
                txtConnect.text =    "Configuring"
            }
        }

    }

    override fun stateChanged(state: BaseService.State, profileName: String?, msg: String?) =
        changeState(state, msg)

    override fun onServiceConnected(service: IShadowsocksService) = changeState(
        try {
            BaseService.State.values()[service.state]
        } catch (_: RemoteException) {
            BaseService.State.Idle
        }
    )

    override fun onPreferenceDataStoreChanged(store: PreferenceDataStore, key: String) {
        when (key) {
            Key.serviceMode -> {
                connection.disconnect(this)
                connection.connect(this, this)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        connection.bandwidthTimeout = 500
    }

    override fun onStop() {
        connection.bandwidthTimeout = 0
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        DataStore.publicStore.unregisterChangeListener(this)
        connection.disconnect(this)
    }

    override fun onPause() {
        super.onPause()
    }
}