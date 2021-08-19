package com.example.Whatsapp_and_Instagram_Status_Downloader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.Whatsapp_and_Instagram_Status_Saver.R

class Instagram_download_steps_dialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var rootview:View=inflater.inflate(R.layout.instagram_download_steps,container,false)
        var ok:Button=rootview.findViewById(R.id.Okbutton)
        ok.setOnClickListener{
            dismiss()
        }
        return rootview
    }
}