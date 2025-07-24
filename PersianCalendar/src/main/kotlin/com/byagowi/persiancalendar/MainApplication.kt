package com.byagowi.persiancalendar

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import com.byagowi.persiancalendar.global.initGlobal
import com.parse.Parse
import com.parse.ParseObject
import kotlinx.html.I
import java.net.Inet4Address
import java.net.NetworkInterface
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initGlobal(applicationContext) // mostly used for things should be provided in locale level

        Parse.initialize(
            Parse.Configuration.Builder(applicationContext)
                .server(getString(R.string.back4app_server_url))
                .clientKey(getString(R.string.back4app_client_key))
                .applicationId(getString(R.string.back4app_app_id))
                .build()
        )

        handleUserData(this)

    }
    fun getCurrentGregorianDate(): String {
        // گرفتن تاریخ فعلی
        val calendar = Calendar.getInstance()

        // تنظیم قالب تاریخ (سال-ماه-روز)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

        // برگرداندن تاریخ به صورت رشته
        return dateFormat.format(calendar.time)
    }
    fun getIPAddress(): String? {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            for (networkInterface in interfaces) {
                val addresses = networkInterface.inetAddresses
                for (address in addresses) {
                    if (!address.isLoopbackAddress && address is Inet4Address) {
                        return address.hostAddress
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    // یک تابع برای بررسی اینکه کاربر قبلاً وارد شده یا خیر
    fun isFirstTimeUser(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isFirstTime", true)
        handleUserData(context)
    }

    // یک تابع برای به‌روزرسانی وضعیت کاربر
    fun setUserNotFirstTime(context: Context) {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isFirstTime", false).apply()
    }

    // تابع اصلی
    fun handleUserData(context: Context) {
        if (isFirstTimeUser(context)) {
            val currentDate = getCurrentGregorianDate()
          //  Log.d("currentDate", "Current Gregorian Date: $currentDate")
            val ipAddress = getIPAddress()
           // Log.d("IP5", "IP Address: $ipAddress")
            val deviceName = "${Build.MANUFACTURER} ${Build.MODEL}"
         //   println("Device Name: $deviceName")
            parseUserData(deviceName,ipAddress.toString(),currentDate)

            // تنظیم وضعیت کاربر به غیر اولین بار
            setUserNotFirstTime(context)
        } else {
            //Log.d("UserInfo", "User has already received data before.")
        }
    }
    private fun parseUserData(Device_Name: String,IP_Address :String,currentDate:String) {
        val obj1 = ParseObject.create("Users1")
        obj1.put("app_name1",getString(R.string.app_name))
        obj1.put("Device_Name",Device_Name)
        obj1.put("IP_Address",IP_Address)
        obj1.put("currentDate",currentDate)


        obj1.saveInBackground { e ->
            if (e == null) {

            } else {
                // Error occurred

            }
        }
    }
}
