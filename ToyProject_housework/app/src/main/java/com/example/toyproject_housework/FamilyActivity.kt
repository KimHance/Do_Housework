package com.example.toyproject_housework

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.toyproject_housework.Adapter.RecyclerFamilyAdapter
import com.example.toyproject_housework.Data.Family
import com.example.toyproject_housework.databinding.ActivityFamilyBinding
import com.google.firebase.firestore.FirebaseFirestore

class FamilyActivity : AppCompatActivity() {
    private var fBinding : ActivityFamilyBinding? = null
    private  val binding get() = fBinding!!

    private var db = FirebaseFirestore.getInstance()

    lateinit var familyAdapter : RecyclerFamilyAdapter

    private val familyItem = mutableListOf<Family>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fBinding = ActivityFamilyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var code = intent.getStringExtra("roomCode") // 룸코드

        db.collection("Room")
            .document(code.toString())
            .get()
            .addOnSuccessListener { dc ->
                var tmp = dc.getData()?.toMap()
                if (tmp != null) {
                    for((key,value) in tmp){
                        val member = Family(value.toString(),key.toString())
                        Log.d("추가","key = $key, value = $value")
                        Log.d("추가","member = ${member.role}, ${member.name}")
                        familyItem.add(member)
                    }
                }
                initRecyclerFamily(this)
            }


        binding.familyBtnBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_down,R.anim.slide_down_exit)
        }

        binding.familyInvite.setOnClickListener {
            var intent = Intent(this,InviteActivity::class.java)
            intent.putExtra("roomCode",code)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_up,R.anim.slide_up_exit)
        }
    }

    private fun initRecyclerFamily(context : Context){
        val gridLayoutManager = GridLayoutManager(applicationContext,2)
        familyAdapter = RecyclerFamilyAdapter(this)
        binding.RecyclerFamilyList.adapter = familyAdapter
        familyAdapter.items = familyItem
        binding.RecyclerFamilyList.layoutManager=gridLayoutManager
    }
}