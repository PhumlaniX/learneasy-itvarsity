package com.learneasy_itvarsity.ui.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.learneasy_itvarsity.R
import java.io.File

class ProfileActivity : AppCompatActivity() {
    private val REQUEST_CODE = 100
    private lateinit var  takenPic: ImageView
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        val btnCapture = findViewById<Button>(R.id.btnTake)
        val btnSend = findViewById<Button>(R.id.btnShare)
        val btnGet = findViewById<Button>(R.id.btnUpload)
        takenPic = findViewById<ImageView>(R.id.userPic)
        mAdView = findViewById(R.id.adView)

        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        btnCapture.setOnClickListener {
            this.takePicture()
        }

        btnSend.setOnClickListener {
            if (takenPic != null) {
                this.sharePic()
            }
        }

        btnGet.setOnClickListener {
            this.OpenGallery()
        }
    }

    private fun OpenGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }


    private fun sharePic() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
        }

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/jpg"
        val photoFile = File(filesDir, "foo.jpg")
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile))
        startActivity(Intent.createChooser(shareIntent, "Share image using"))
    }

    private fun takePicture() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            takenPic.setImageBitmap(data.extras?.get("data") as Bitmap)
            takenPic.setImageURI(data?.data)
        }

        else if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            takenPic.setImageURI(data?.data)
        }
    }
}