package com.example.photoeditor

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.photoeditor.databinding.ActivityMainBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var uri  = ArrayList<Uri>()
   // lateinit var imageUri:Uri

    companion object{
        lateinit var imageUri:Uri
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
//                binding.imageView2.setImageURI(it)
                imageUri = it!!
                Log.d("Uri",""+imageUri)
                startActivity(Intent(this@MainActivity ,FinalActivity::class.java))
            }
        )

        binding.start.setOnClickListener {

            getImage.launch("image/*")
//
//            ImagePicker.Companion.with(this@MainActivity)
//                .crop()
//                .compress(1024)
//                .maxResultSize(1080, 1080)
//                .start()
        }
    }
/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        try {

            if (data != null) {
                imageUri = data.data!!
                Log.d("Uri",""+imageUri)
                startActivity(Intent(this@MainActivity ,FinalActivity::class.java))
            }
        } catch (e: Exception ){

        }
    }
*/
    private fun imagePick() {
        //Open picker
        FilePickerBuilder.instance
            .setActivityTitle("Select Images")
            .setSpan(FilePickerConst.SPAN_TYPE.FOLDER_SPAN , 3 )
            .setSpan(FilePickerConst.SPAN_TYPE.DETAIL_SPAN , 4)
            .setMaxCount(4)
            .setSelectedFiles(uri)
            .setActivityTheme(R.style.CustomeTheme)
            .pickPhoto(this)
    }
}