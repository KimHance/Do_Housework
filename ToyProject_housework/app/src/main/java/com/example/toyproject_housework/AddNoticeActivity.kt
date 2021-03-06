package com.example.toyproject_housework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.toyproject_housework.databinding.ActivityAddnoticeBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.jetbrains.anko.toast


class AddNoticeActivity : AppCompatActivity() {

    private  var nBinding : ActivityAddnoticeBinding? = null
    private val binding get() = nBinding!!

    var rdb = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nBinding = ActivityAddnoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var code = intent.getStringExtra("roomCode") // 룸코드
        var name = intent.getStringExtra("name") // 유저이름

        binding.noticeUser.text = name

        binding.noticeCancle.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_down,R.anim.slide_down_exit)
        }

        binding.noticeOK.setOnClickListener {
            if(binding.noticeTitle.text.isNullOrBlank() || binding.noticeNotice.text.isNullOrBlank())
            {
                toast("빈칸이 존재합니다.")
            }else{
                var noticeContext = binding.noticeNotice.text.toString()
                var noticeTitle = binding.noticeTitle.text.toString()
                val map = mutableMapOf(
                    "등록자" to name,
                    "내용" to noticeContext
                )
                rdb.child(code.toString()).child("notice").child(noticeTitle).setValue(map)
                toast("게시글 등록이 완료되었습니다")
                finish()
                overridePendingTransition(R.anim.slide_down,R.anim.slide_down_exit)
            }
        }

    }

    override fun onDestroy() {
        nBinding = null
        super.onDestroy()
    }

}