package com.example.Whatsapp_and_Instagram_Status_Downloader

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Whatsapp_and_Instagram_Status_Downloader.RecyclerAdapter.ViewHlder
import com.example.Whatsapp_and_Instagram_Status_Downloader.modal.Modal
import com.example.Whatsapp_and_Instagram_Status_Saver.R
import java.util.*

class RecyclerAdapter(mainActivity: MainActivity, getdata: ArrayList<Modal>) : RecyclerView.Adapter<ViewHlder>() {
    var context: Context
    var filelist: ArrayList<Modal>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHlder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item_layout, null, false)
        return ViewHlder(view)
    }

    override fun onBindViewHolder(holder: ViewHlder, position: Int) {
        val modal = filelist[position]
        if (modal.uri.toString().endsWith(".mp4")) {
            holder.play.visibility = View.VISIBLE
        } else holder.play.visibility = View.INVISIBLE
        Glide.with(context).load(modal.uri).into(holder.status)
        holder.status.setOnClickListener {
            if (modal.uri.toString().endsWith(".mp4")) {
                val path = filelist[position].path
                val des_path = Environment.getExternalStorageDirectory().absolutePath + Constant.TO_SAVE_FOLDER
                val intent = Intent(context, Video_player::class.java)
                intent.putExtra("DEST_PATH", des_path)
                intent.putExtra("VIDEO_FILE", path)
                intent.putExtra("FILE_NAME", modal.filename)
                intent.putExtra("VIDEO_URI", modal.uri.toString())
                context.startActivity(intent)
            } else {
                val path = filelist[position].path
                val des_path = Environment.getExternalStorageDirectory().absolutePath + Constant.TO_SAVE_FOLDER
                val intent = Intent(context, Photo_status::class.java)
                intent.putExtra("DEST_PATH", des_path)
                intent.putExtra("PHOTO_FILE", path)
                intent.putExtra("FILE_NAME", modal.filename)
                intent.putExtra("PHOTO_URI", modal.uri.toString())
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return filelist.size
    }

    inner class ViewHlder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var status: ImageView
        var play: ImageView

        init {
            status = itemView.findViewById(R.id.thunbnailStatus)
            play = itemView.findViewById(R.id.Play)
        }
    }

    init {
        context = mainActivity
        filelist = getdata
    }
}