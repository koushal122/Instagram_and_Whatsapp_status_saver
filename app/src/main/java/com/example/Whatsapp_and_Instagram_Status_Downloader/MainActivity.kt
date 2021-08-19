package com.example.Whatsapp_and_Instagram_Status_Downloader

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build.VERSION
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.Whatsapp_and_Instagram_Status_Downloader.modal.Modal
import com.example.Whatsapp_and_Instagram_Status_Saver.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {
    var requestCode = 1
    var adapter: RecyclerAdapter? = null
    lateinit var files: Array<File>
    var recyclerView: RecyclerView? = null
    var refreshLayout: SwipeRefreshLayout? = null
    var filelist = ArrayList<Modal>()
    private var mAdView: AdView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.recyclerviewSwipelayout)
        refreshLayout = findViewById(R.id.Swiperefreshlayout)

        checkPermission()

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView!!.loadAd(adRequest)
        mAdView!!.setAdListener(object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError)
                mAdView!!.loadAd(adRequest)
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                super.onAdOpened()
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                super.onAdClicked()
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                super.onAdClosed()
            }
        })

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setTitle("Status Downloader")
        val actionBar: ActionBar?
        actionBar = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#0F9D58"))
        actionBar!!.setBackgroundDrawable(colorDrawable)
        SetupLayout()

        refreshLayout!!.setOnRefreshListener(object : OnRefreshListener {
            override fun onRefresh() {
                refreshLayout!!.setRefreshing(true)
                SetupLayout()
                run { Handler().postDelayed({ refreshLayout!!.setRefreshing(false) }, 1000) }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.help_dialog)
        dialog.show()
        val button = dialog.findViewById<Button>(R.id.OkHelpdialog)
        button.setOnClickListener { dialog.dismiss() }
        val contact = dialog.findViewById<Button>(R.id.Helpbutton)
        contact.setOnClickListener { Toast.makeText(this@MainActivity, "Contact On Email \n koushaljha889@gmail.com", Toast.LENGTH_SHORT).show() }
        return super.onOptionsItemSelected(item)
    }


    private fun SetupLayout() {
        filelist.clear()
        recyclerView!!.setHasFixedSize(true)
        adapter = RecyclerAdapter(this@MainActivity, getdata())
        recyclerView!!.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerView!!.adapter = adapter
        adapter!!.notifyDataSetChanged()
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun getdata(): ArrayList<Modal> {

        //this is where status files was stored
        val targetpath = Environment.getExternalStorageDirectory().absolutePath + Constant.FROM_FOLDER_NAME + "Media/.Statuses"
        val targetdir = File(targetpath)
        files = targetdir.listFiles()
        //acessing all status files one by one which was seen
        for (i in files.indices) {
            val file = files[i]
            var f= Modal(files[i].absolutePath,file.name,Uri.fromFile(file))
            if (!f.uri.toString().endsWith(".nomedia")) filelist.add(f)
        }
        return filelist
    }

    private fun checkPermission() {
        if (VERSION.SDK_INT > 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //main code because user has already granted the permission
                SetupLayout()
            } else {
                //it mean user has not given permission may be starting first time
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
            }
        } else {
            //below sdk version 23 we dont need to ask permission
            Toast.makeText(this, "Already", Toast.LENGTH_SHORT).show()
            SetupLayout()
        }
    }
}