package com.example.storage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import java.io.File
import java.io.FileOutputStream

var isExternal = false

class MainActivity : AppCompatActivity() {
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CreateFileButton.setOnClickListener {
            createFile(editTextFileName.text.toString(), editTextFileContent.text.toString())
        }
        ClearFileButton.setOnClickListener {
            clearFiles()
        }
        ChooseStorage?.setOnCheckedChangeListener { _, isChecked ->
            isExternal = isChecked
        }
    }

    @ExperimentalStdlibApi
    fun createFile(fileName: String, fileContent: String) {


        if (isExternal) {
            //Extern
            val file = File(getExternalFilesDir(null), fileName)
            file.createNewFile()
            var fileOutput = FileOutputStream(file, true)
            fileOutput.write(fileContent.toByteArray())
            fileOutput.flush()
            fileOutput.close()
        } else{
            //Intern
            openFileOutput(fileName, Context.MODE_PRIVATE).use {
                it.write(fileContent.toByteArray())
            }
        }
        listFiles()
    }


    @ExperimentalStdlibApi
    fun listFiles() {
        val files: Array<String> = fileList()
        for (fileName in files) {
            Log.i("NAME INTERN: ", fileName)
            openFileInput(fileName).use {
                val fileContent = it.readBytes().decodeToString()
                Log.i("CONTENT INTERN: ", fileContent)
            }
        }

        val file = File(getExternalFilesDir(null), "")
        for (file in  file.listFiles()) {
            Log.i("NAME EXTERN: ", file.name)
            Log.i("CONTENT EXTERN: ", file.readText())
        }
    }

    fun clearFiles() {
        val files: Array<String> = fileList()
        for (fileName in files) {
            Log.i("DELETE INTERN FILE: ", fileName)
            deleteFile(fileName)
        }
        val file = File(getExternalFilesDir(null), "")
        for (file in  file.listFiles()) {
            file.delete()

            Log.i("DELETE EXTERN FILE: ", file.name)
        }

    }
}