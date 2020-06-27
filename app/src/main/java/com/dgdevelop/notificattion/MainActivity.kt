package com.dgdevelop.notificattion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getInstance()

        if (intent.extras != null){
            tvDescountMessage.visibility = View.VISIBLE
            val descount = intent.extras!!.getString(KEY_DESCOUNT)
            tvDescountMessage.append(descount)
        }
    }

    private fun getInstance() {
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token
                    Log.d(TAG, "TokenRefresh: $token")
                })
    }

     fun subscribeAndroid(view: View){
        FirebaseMessaging.getInstance().subscribeToTopic("Android")
         Toast.makeText(this, "Felicidades te subscribiste a Android", Toast.LENGTH_SHORT).show()
    }

    fun subscribeFirebase(view: View){
        FirebaseMessaging.getInstance().subscribeToTopic("Firebase")
        Toast.makeText(this, "Felicidades te subscribiste a Firebase", Toast.LENGTH_SHORT).show()
    }

    companion object{
        private const val TAG = "MainActivity"
        private const val KEY_DESCOUNT = "descount_key"
    }
}