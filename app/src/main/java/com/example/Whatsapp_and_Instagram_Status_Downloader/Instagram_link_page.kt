package com.example.Whatsapp_and_Instagram_Status_Saver

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.cardview.widget.CardView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.Whatsapp_and_Instagram_Status_Downloader.InstagramApi.graphql_MainUrl
import com.example.Whatsapp_and_Instagram_Status_Downloader.Instagram_download_steps_dialog
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.gson.Gson
import com.google.gson.GsonBuilder


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Instagram_link_page.newInstance] factory method to
 * create an instance of this fragment.
 */
class Instagram_link_page : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var downloadstep:TextView
    lateinit var mAdViewupper : AdView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instagram_link_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var adRequest = AdRequest.Builder().build()
        mAdViewupper = view.findViewById(R.id.adView2)
        mAdViewupper.loadAd(adRequest)

        mAdViewupper.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded()


            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError)
                mAdViewupper.loadAd(adRequest)
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                super.onAdOpened();
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
        }


        var download:CardView=view.findViewById(R.id.Instagram_link_page_download_cardView)
        download.setOnClickListener{
            var linkV:EditText=view.findViewById(R.id.Instagram_link)
            val link=linkV.text.toString().trim()
            val trim:String=link.substringAfter(".com/")
            val type:String=trim.substringBefore("/C")

            if(type=="reel")
            {
                var newLink:String=link.substringBefore("/?")
                newLink=newLink+"/?__a=1"
                val queue = Volley.newRequestQueue(context)

                var request= StringRequest(Request.Method.GET,newLink, { response ->
                    var  gsonbuilder:GsonBuilder= GsonBuilder()
                    var gson: Gson =gsonbuilder.create()
                    var mainUrl: graphql_MainUrl =gson.fromJson(response, graphql_MainUrl::class.java)
                    var reelurl="1";
                    reelurl=mainUrl.graphql.shortcode_media.video_url
                    var uri2= Uri.parse(reelurl);
                    if (reelurl=="1")
                    {
                        Toast.makeText(context,"Something Went wrong",Toast.LENGTH_LONG).show()
                    }
                    else
                    {
                        var request:DownloadManager.Request=DownloadManager.Request(uri2);
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE)
                        request.setTitle("Check in DCIM folder in Your file")
                        request.setDescription("It is Currently Downloading")
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM,""+System.currentTimeMillis()+".mp4")
                        var manager:DownloadManager= activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                        manager.enqueue(request);
                        Toast.makeText(context," Reel Downloaded\n Please Check in Gallery or DCIM folder in your phone storage",Toast.LENGTH_LONG).show()

                    }

                }, {
                    Toast.makeText(context,"Not able to fetch data",Toast.LENGTH_LONG).show()
                })
                queue.add(request)


            }
            else if(type=="p" || type=="v")
            {

                var newLink:String=link.substringBefore("/?")
                newLink=newLink+"/?__a=1"
                val queue = Volley.newRequestQueue(context)

                var request= StringRequest(Request.Method.GET,newLink, { response ->
                    var  gsonbuilder:GsonBuilder= GsonBuilder()
                    var gson: Gson =gsonbuilder.create()
                    var mainUrl: graphql_MainUrl =gson.fromJson(response, graphql_MainUrl::class.java)
                    var reelurl="1";
                    var isvideo:Boolean=mainUrl.graphql.shortcode_media.is_video
                    if (isvideo)
                        reelurl=mainUrl.graphql.shortcode_media.video_url
                    else
                        reelurl=mainUrl.graphql.shortcode_media.display_url
                    var uri2= Uri.parse(reelurl);
                    if (reelurl=="1")
                    {
                        Toast.makeText(context,"Something Went wrong",Toast.LENGTH_LONG).show()
                    }
                    else
                    {
                        var request:DownloadManager.Request=DownloadManager.Request(uri2);
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE)
                        request.setTitle("Check in DCIM folder in Your file")
                        request.setDescription("It is Currently Downloading")
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM,""+System.currentTimeMillis()+".mp4")
                        var manager:DownloadManager= activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                        manager.enqueue(request);
                        if(isvideo)
                            Toast.makeText(context,"Video Downloaded\n Please Check in Gallery or DCIM folder in your phone storage",Toast.LENGTH_LONG).show()
                        else
                            Toast.makeText(context,"Photo Downloaded\n Please Check in Gallery or DCIM folder in your phone storage",Toast.LENGTH_LONG).show()
                    }

                }, {
                    Toast.makeText(context,"Not able to fetch data",Toast.LENGTH_LONG).show()
                })
                queue.add(request)



            }
            else if(type=="tv")
            {
                var newLink:String=link.substringBefore("/?")
                newLink=newLink+"/?__a=1"
                val queue = Volley.newRequestQueue(context)

                var request= StringRequest(Request.Method.GET,newLink, { response ->
                    var  gsonbuilder:GsonBuilder= GsonBuilder()
                    var gson: Gson =gsonbuilder.create()
                    var mainUrl: graphql_MainUrl =gson.fromJson(response, graphql_MainUrl::class.java)
                    var reelurl="1";
                    var isvideo:Boolean=mainUrl.graphql.shortcode_media.is_video
                    if (isvideo)
                        reelurl=mainUrl.graphql.shortcode_media.video_url
                    else
                        reelurl=mainUrl.graphql.shortcode_media.display_url
                    var uri2= Uri.parse(reelurl);
                    if (reelurl=="1")
                    {
                        Toast.makeText(context,"Something Went wrong",Toast.LENGTH_LONG).show()
                    }
                    else
                    {
                        var request:DownloadManager.Request=DownloadManager.Request(uri2);
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE)
                        request.setTitle("Check in DCIM folder in Your file")
                        request.setDescription("It is Currently Downloading")
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM,""+System.currentTimeMillis()+".mp4")
                        var manager:DownloadManager= activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                        manager.enqueue(request);
                        if(isvideo)
                            Toast.makeText(context,"Video Downloaded\n Please Check in Gallery or DCIM folder in your phone storage",Toast.LENGTH_LONG).show()
                        else
                            Toast.makeText(context,"Photo Downloaded\n Please Check in Gallery or DCIM folder in your phone storage",Toast.LENGTH_LONG).show()
                    }

                }, {
                    Toast.makeText(context,"Not able to fetch data",Toast.LENGTH_LONG).show()
                })
                queue.add(request)


            }
            else
            {
              Toast.makeText(context,"Please Enter Correct Link",Toast.LENGTH_LONG).show()
            }
        }
        downloadstep=view.findViewById(R.id.download_steps)
        downloadstep.setOnClickListener{
             var dialog= Instagram_download_steps_dialog()
            activity?.let { it1 -> dialog.show(it1.supportFragmentManager,"CustomDialog") }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Instagram_link_page.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                Instagram_link_page().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}