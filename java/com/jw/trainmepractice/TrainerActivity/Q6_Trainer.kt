package com.jw.trainmepractice.TrainerActivity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar
import com.jw.trainmepractice.R

class Q6_Trainer : AppCompatActivity() {

    lateinit var imgArray: ArrayList<Uri>
    lateinit var container1: ImageView
    lateinit var container2: ImageView
    lateinit var container3: ImageView
    lateinit var container4: ImageView
    lateinit var storageRef: StorageReference

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q6__trainer)
//        AutoPermissions.loadAllPermissions(this, 101)

        storageRef = FirebaseStorage.getInstance().reference

        val bundle = intent.extras!!

        val seekbar6 = findViewById<VerticalSeekBar>(R.id.seekbar6)
        seekbar6.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }
        })

        imgArray = arrayListOf()
        container1 = findViewById(R.id.container1)
        container2 = findViewById(R.id.container2)
        container3 = findViewById(R.id.container3)
        container4 = findViewById(R.id.container4)

        container1.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            startActivityForResult(intent, 100)
        }
        container2.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            startActivityForResult(intent, 101)
        }
        container3.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            startActivityForResult(intent, 102)
        }
        container4.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            startActivityForResult(intent, 103)
        }

        val user_token = getSharedPreferences("trainer", Context.MODE_PRIVATE).getString("login_token","null")

        findViewById<FloatingActionButton>(R.id.nextbutton6).setOnClickListener {
            for(i in imgArray){
                storageRef.child("trainer").child(user_token!!).child(i.toString() + ".png").putFile(i)
            }

            val intent = Intent(this, Q7_trainer::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        findViewById<Button>(R.id.q6_skipButton).setOnClickListener {
            val intent = Intent(this, Q7_trainer::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                val uri = data?.getData()
                Glide.with(this)
                    .load(uri)
                    .into(container1)
                imgArray.add(uri!!)
            } else if (requestCode == 101) {
                val uri = data?.getData()
                Glide.with(this)
                    .load(uri)
                    .into(container2)
                imgArray.add(uri!!)
            }
            if (requestCode == 103) {
                val uri = data?.getData()
                Glide.with(this)
                    .load(uri)
                    .into(container3)
                imgArray.add(uri!!)
            }
            if (requestCode == 104) {
                val uri = data?.getData()
                Glide.with(this)
                    .load(uri)
                    .into(container4)
                imgArray.add(uri!!)
            }
        }
    }
}
