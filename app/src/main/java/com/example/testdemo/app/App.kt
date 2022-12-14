/*******************************************************************************
 *                                                                             *
 *  Copyright (C) 2017 by Max Lv <max.c.lv@gmail.com>                          *
 *  Copyright (C) 2017 by Mygod Studio <contact-shadowsocks-android@mygod.be>  *
 *                                                                             *
 *  This program is free software: you can redistribute it and/or modify       *
 *  it under the terms of the GNU General Public License as published by       *
 *  the Free Software Foundation, either version 3 of the License, or          *
 *  (at your option) any later version.                                        *
 *                                                                             *
 *  This program is distributed in the hope that it will be useful,            *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 *  GNU General Public License for more details.                               *
 *                                                                             *
 *  You should have received a copy of the GNU General Public License          *
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.       *
 *                                                                             *
 *******************************************************************************/

package com.example.testdemo.app

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.example.testdemo.MainActivity
import com.example.testdemo.utils.KLog
import com.example.testdemo.utils.ResUtils
import com.github.shadowsocks.BuildConfig
import com.github.shadowsocks.Core
import com.google.android.gms.ads.MobileAds
import com.jeremyliao.liveeventbus.LiveEventBus

class App : Application(), androidx.work.Configuration.Provider by Core {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this) {}
        Core.init(this, MainActivity::class)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        ResUtils.init(this)
        LiveEventBus  // ?????????????????????
            .config().supportBroadcast(this) // ???????????????????????????APP???????????????Context????????????application onCreate?????????
            .lifecycleObserverAlwaysActive(true) //    ????????????????????????onCreate???onDestroy??????????????????????????????
        //????????????????????????
        KLog.init(BuildConfig.DEBUG)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Core.updateNotificationChannels()
    }
}
