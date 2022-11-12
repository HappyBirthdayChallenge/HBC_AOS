package com.inha.hbc.ui.login.ui

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.inha.hbc.databinding.ActivityLoginBinding
import com.inha.hbc.util.firebase.FirebaseMessagingService
import com.inha.hbc.util.fragmentmanager.NormLoginFragmentManager
import com.inha.hbc.util.fragmentmanager.SignupFragmentManager


class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        askNotificationPermission()
        val fms = FirebaseMessagingService().searchToken()

        SignupFragmentManager.setManager(supportFragmentManager, binding.fcLogin.id, this)
        NormLoginFragmentManager.setManager(supportFragmentManager, binding.fcLogin.id, this)

        supportFragmentManager.beginTransaction().add(binding.fcLogin.id, LoginFragment()).commit()
    }

    fun hideKeyboard() {
        val keyboard = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            Toast.makeText(applicationContext, "알림수신불가", Toast.LENGTH_SHORT).show()
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("알림채널 동의")
                    .setMessage("알림채널 동의 메시지")
                    .setPositiveButton("동의") { dialog, id ->
                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                    .setNegativeButton("거부") {
                        dialog, id ->
                        Toast.makeText(applicationContext, "알림 거부", Toast.LENGTH_SHORT).show()
                    }

                builder.show()
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }


}