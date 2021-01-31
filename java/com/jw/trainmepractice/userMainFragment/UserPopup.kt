package com.jw.trainmepractice.userMainFragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jw.trainmepractice.R
import com.jw.trainmepractice.memberClass.Trainer


class UserPopup : Activity() {
    var trainername: String? = null
    var trainerId: String? = null
    var price: String? = null
    var message: String? = null
    var perweek: String? = null
    var total: String? = null

    private var trainerText: TextView? = null
    private var priceText: TextView? = null
    private var commentText: TextView? = null
    private var numText: TextView? = null
    private var moreBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.user_popup)
        InitializeView()
        val intent = intent

        trainername = intent.getStringExtra("trainerName")
        trainerId = intent.getStringExtra("trainerId")
        price = intent.getStringExtra("price")
        message = intent.getStringExtra("message")
        perweek = intent.getStringExtra("perweek")
        total = intent.getStringExtra("total")

        if (price == "0") price = "협의 불가능" else if (price == "1") price = "협의 가능"
        SetText(trainername, price, perweek, total, message)
        moreBtn?.setOnClickListener {
            val proposal = Intent(this@UserPopup, SeeProposal::class.java)
            proposal.putExtra("trainerId", trainerId)
            proposal.putExtra("trainerName", trainername)

            startActivity(proposal)
            finish()
        }
    }

    fun SetText(trainername: String?, price: String?, perweek: String?, total: String?, message: String?) {
        trainerText!!.text = "$trainername 님"
        priceText!!.text = price
        numText!!.text = "주 " + perweek + "회 / 총" + total + "회"
        commentText!!.text = message
    }

    fun InitializeView() {
        trainerText = findViewById<View>(R.id.trainerText) as TextView
        priceText = findViewById<View>(R.id.priceText) as TextView
        commentText = findViewById<View>(R.id.commentText) as TextView
        numText = findViewById<View>(R.id.numText) as TextView
        moreBtn = findViewById<View>(R.id.moreBtn) as Button
    }
}