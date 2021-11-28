package com.ebookfrenzy.applinking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import com.ebookfrenzy.applinking.databinding.ActivityLandmarkBinding

class LandmarkActivity : AppCompatActivity() {

    private var landmark: Landmark? = null
    private lateinit var binding: ActivityLandmarkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ATTENTION: This was auto-generated to handle app links.
        val appLinkIntent = intent
        val appLinkAction = appLinkIntent.action
        val appLinkData = appLinkIntent.data

        if (appLinkAction != null) {

            if (appLinkAction == "android.intent.action.VIEW") {

                val landmarkId = appLinkData?.lastPathSegment

                if (landmarkId != null) {
                    displayLandmark(landmarkId)
                }
            }
        } else {
            handleIntent(appLinkIntent)
        }
    }

    private fun handleIntent(intent: Intent) {

        val landmarkId = intent.getStringExtra(AppLinkingActivity.LANDMARK_ID)
        displayLandmark(landmarkId)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }


    fun deleteLandmark(view: View) {

        val dbHandler = MyDBHandler(this, null, null, 1)

        if (landmark != null) {
            dbHandler.deleteLandmark(landmark?.id)
            binding.titleText.text = ""
            binding.descriptionText.text = ""
            binding.deleteButton.isEnabled = false
        }
    }

    private fun displayLandmark(landmarkId: String) {
        val dbHandler = MyDBHandler(this, null, null, 1)

        landmark = dbHandler.findLandmark(landmarkId)

        if (landmark != null) {

            if (landmark?.personal == 0) {
                binding.deleteButton.isEnabled = false
            } else {
                binding.deleteButton.isEnabled = true
            }

            binding.titleText.text = landmark?.title
            binding.descriptionText.text = landmark?.description
        } else {
            binding.titleText.text = "No Match Found"
        }
    }
}
