package com.example.photoeditor

import android.app.Activity
import android.content.Intent
 import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants
import com.example.photoeditor.MainActivity.Companion.imageUri
import com.example.photoeditor.databinding.ActivityFinalBinding

class FinalActivity : AppCompatActivity() {

    private lateinit var binding:ActivityFinalBinding

    //For custom result.
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                binding.imageView.setImageURI(intent.data)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFinalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var dsPhotoEditorIntent: Intent = Intent(this , DsPhotoEditorActivity::class.java)

        dsPhotoEditorIntent.setData(imageUri)
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY , "My Editor")

        val toolsToHide =
            intArrayOf(DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_CROP)

        dsPhotoEditorIntent.putExtra(
            DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE,
            toolsToHide
        )

        // Use the Kotlin extension in activity-ktx
        // passing it the Intent you want to start
       // startForResult.launch(Intent(this, ResultProducingActivity::class.java))
        startForResult.launch(dsPhotoEditorIntent)
        Toast.makeText(this,""+imageUri,Toast.LENGTH_LONG).show()
    }

}