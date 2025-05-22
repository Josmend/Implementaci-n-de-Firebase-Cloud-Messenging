package com.example.myapplication
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FCM", "Mensaje recibido: ${remoteMessage.data}")

        remoteMessage.notification?.let {
            mostrarNotificacion(it.title ?: "Sin título", it.body ?: "Sin cuerpo")
        }
    }

    override fun onNewToken(token: String) {
        Log.d("FCM", "Nuevo token: $token")
        // Aquí puedes enviar el token a tu servidor si es necesario
    }

    private fun mostrarNotificacion(titulo: String, mensaje: String) {
        val canalId = "canal_default"
        val sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, canalId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setAutoCancel(true)
            .setSound(sonido)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Crear canal solo para Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                canalId,
                "Canal de notificaciones",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(canal)
        }

        notificationManager.notify(0, builder.build())
    }
}