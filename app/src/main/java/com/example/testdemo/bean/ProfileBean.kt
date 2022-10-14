package com.example.testdemo.bean

class ProfileBean {
    val safeLocation: MutableList<SafeLocation>? = null
    class SafeLocation {
        var ufo_pwd: String? = null
        var ufo_method: String? = null
        var ufo_port: Int? = null
        var ufo_country: String? = null
        var ufo_city: String? = null
        var ufo_ip: String? = null
        var cheek_state: Boolean? = false
    }
}