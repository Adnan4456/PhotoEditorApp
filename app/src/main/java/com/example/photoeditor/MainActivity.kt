package com.example.photoeditor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.photoeditor.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
       companion object{
        lateinit var imageUri:Uri
    }
    private lateinit var  builder: AlertDialog

    val getImageFromGallery = registerForActivityResult(ActivityResultContracts.GetContent()){ uri: Uri? ->

        uri?.let{
            imageUri = it
            builder.dismiss()
            startActivity(Intent(this , FinalActivity::class.java))
        }
        if(uri != null){

        }
    }

    val getImageFromCamera = registerForActivityResult(ActivityResultContracts.TakePicture()){
        it?.let {
            binding.imageView2.setImageURI(null)
            binding.imageView2.setImageURI(imageUri)
            builder.dismiss()
            startActivity(Intent(this , FinalActivity::class.java))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageUri = createImageUri()!!

        binding.start.setOnClickListener {
            createDialog()
        }
    }

    private fun createImageUri(): Uri?{
        //create image file
        val image = File(applicationContext.filesDir , "camera_photo.png")
        //image will save in root directory with camera_photo.png name.

        return FileProvider.getUriForFile(applicationContext ,
            "com.example.photoeditor.fileProvider",
            image)
    }

    private fun createDialog(){
        builder  = AlertDialog.Builder(this , R.style.CustomAlertDialog)
            .create()

        val view = layoutInflater.inflate(R.layout.dialog_layout ,  null)

        val cameraLayout = view.findViewById<LinearLayout>(R.id.camera)
        val galleryLayout = view.findViewById<LinearLayout>(R.id.gallery)

        builder.setView(view)
        cameraLayout.setOnClickListener{
            //calling camera intent
            getImageFromCamera.launch(imageUri)
        }
        galleryLayout.setOnClickListener{
            //calling intent from
            getImageFromGallery.launch("image/*")
        }

        builder.setCanceledOnTouchOutside(true)
        builder.show()
    }
}