package com.example.telegrammonitor

import android.media.RingtoneManager
import android.net.Uri
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class TelegramListenerService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.pkg
        
        // Перевіряємо, чи сповіщення надійшло від офіційного Telegram
        if (packageName == "org.telegram.messenger") {
            val extras = sbn.notification.extras
            val title = extras.getString("android.title") ?: "" // Ім'я відправника або чатуk
            val text = extras.getString("android.text") ?: ""   // Текст повідомлення
            
            Log.d("TelegramDebug", "Від кого: $title | Текст: $text")

            // ЗАМІНІТЬ ЦЕЙ ТЕКСТ на реальне ім'я користувача, від якого чекаєте будильник
            val targetUser = "ڑتришкаت"

            // Якщо сповіщення від цієї людини — вмикаємо звук будильника
            if (title.contains(targetUser, ignoreCase = true)) {
                Log.i("TelegramDebug", "Увага! Знайдено повідомлення від цільового користувача.")
                playAlarmSound()
            }
        }
    }

    private fun playAlarmSound() {
        try {
            // Отримуємо системний звук будильника за замовчуванням
            val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM) 
                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            
            val ringtone = RingtoneManager.getRingtone(applicationContext, alarmUri)
            ringtone.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
