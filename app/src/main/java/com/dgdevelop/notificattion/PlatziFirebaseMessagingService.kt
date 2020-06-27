package com.dgdevelop.notificattion

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.dgdevelop.notificattion.model.PlatziNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class PlatziFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMesssage: RemoteMessage) {
        super.onMessageReceived(remoteMesssage)
        Log.d(TAG, "From: ${remoteMesssage.from}")

        remoteMesssage.data.isEmpty().let {
            Log.d(TAG, "Message data payload: ${remoteMesssage.data}")
        }

        val platziNotification = PlatziNotification.Builder()
            .setId(remoteMesssage.from!!)
            .setTitle(remoteMesssage.notification?.title!!)
            .setDescription(remoteMesssage.notification!!.body!!)
            .setDescount(remoteMesssage.data[KEY_DESCOUNT]!!)
            .build()
        showNotification(platziNotification)

        remoteMesssage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
    }

    private fun showNotification(platziNotification: PlatziNotification) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(KEY_DESCOUNT, platziNotification.getDescount())
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        Log.d(TAG, "Title: ${platziNotification.getTitle()}")

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_active)
            .setContentTitle(platziNotification.getTitle())
            .setContentText(platziNotification.getDescription())
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager: NotificationManager
                = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token)

    }

    private fun sendRegistrationToServer(token: String?) {
       
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    companion object{
        private const val TAG = "MessagingService"
        private const val KEY_DESCOUNT = "descount_key"
    }
}
