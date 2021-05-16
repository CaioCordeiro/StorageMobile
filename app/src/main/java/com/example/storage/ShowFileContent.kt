package com.example.storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_show_file_content.*

class ShowFileContent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_file_content)

        val name = intent.getStringExtra(MainActivity.MAIN_ACTIVITY_EXTRA_FILE_NAME_ID)
        val content = intent.getStringExtra(MainActivity.MAIN_ACTIVITY_EXTRA_FILE_CONTENT_ID)
        file_name_view.text = name
        file_content_view.text = content
    }
}