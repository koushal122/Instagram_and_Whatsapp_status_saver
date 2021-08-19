package com.example.Whatsapp_and_Instagram_Status_Downloader

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.MediaScannerConnectionClient
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.example.Whatsapp_and_Instagram_Status_Saver.R
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException

class Photo_status : AppCompatActivity() {
    lateinit var particularImage: ImageView
    lateinit var downLoad: AppCompatButton
    var Chat: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_status)
        supportActionBar!!.setIcon(R.mipmap.ic_launcher_app)
        particularImage = findViewById(R.id.particularImage)
        downLoad = findViewById(R.id.downloadImageview)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.title = "Images"
        val actionBar: ActionBar?
        actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val colorDrawable = ColorDrawable(Color.parseColor("#0F9D58"))
        actionBar!!.setBackgroundDrawable(colorDrawable)
        val intent = intent
        val dest_path = intent.getStringExtra("DEST_PATH")
        val path = intent.getStringExtra("PHOTO_FILE")
        val Filename = intent.getStringExtra("FILE_NAME")
        val uri = intent.getStringExtra("PHOTO_URI")
        val destinationFile = File(dest_path)
        val photofile = File(path)
        Glide.with(applicationContext).load(uri).into(particularImage)
        downLoad.setOnClickListener(View.OnClickListener {
            try {
                FileUtils.copyFileToDirectory(photofile, destinationFile)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            MediaScannerConnection.scanFile(applicationContext, arrayOf(dest_path + Filename), arrayOf("*/*"),
                    object : MediaScannerConnectionClient {
                        override fun onMediaScannerConnected() {}
                        override fun onScanCompleted(path: String, uri: Uri) {}
                    })
            val dialog = Dialog(this@Photo_status)
            dialog.setContentView(R.layout.custom_dialog)
            dialog.show()
            val button = dialog.findViewById<Button>(R.id.Okbut)
            button.setOnClickListener { dialog.dismiss() }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}