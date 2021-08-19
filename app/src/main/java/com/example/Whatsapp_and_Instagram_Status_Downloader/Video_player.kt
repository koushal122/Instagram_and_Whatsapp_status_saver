@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.Whatsapp_and_Instagram_Status_Downloader

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.MediaScannerConnectionClient
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.Whatsapp_and_Instagram_Status_Saver.R
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException

class Video_player : AppCompatActivity() {
    lateinit var downLoad: AppCompatButton
    var Chat: ImageView? = null
    lateinit var particularImage: VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.title = "Status Downloader"

        val actionBar: ActionBar?
        actionBar = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#0F9D58"))

        actionBar!!.setBackgroundDrawable(colorDrawable)
        supportActionBar!!.title = "Video"
        actionBar.setDisplayHomeAsUpEnabled(true)
        particularImage = findViewById<VideoView>(R.id.particularVideo)
        downLoad = findViewById(R.id.downloadVideoview)
        val intent = intent
        val dest_path = intent.getStringExtra("DEST_PATH")
        val path = intent.getStringExtra("VIDEO_FILE")
        val Filename = intent.getStringExtra("FILE_NAME")
        val uri = intent.getStringExtra("VIDEO_URI")
        val destinationFile = File(dest_path)
        val photofile = File(path)

        //Glide.with(getApplicationContext()).load(uri).into(particularImage);
        val mediaController = MediaController(this)
        mediaController.setAnchorView(particularImage)
        val uri1 = Uri.parse(uri)
        particularImage.setMediaController(mediaController)
        particularImage.setVideoURI(uri1)
        particularImage.requestFocus()
        particularImage.start()
        try {
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
                val dialog = Dialog(this@Video_player)
                dialog.setContentView(R.layout.custom_dialog)
                dialog.show()
                val button = dialog.findViewById<Button>(R.id.Okbut)
                button.setOnClickListener { dialog.dismiss() }
            })
        }catch (io:Exception)
        {
            Toast.makeText(this@Video_player,"Something went Wrong",Toast.LENGTH_LONG).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}