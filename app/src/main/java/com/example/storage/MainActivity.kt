package com.example.storage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity(), FileAdapter.OnItemClickListener {
    companion object {
        const val MAIN_ACTIVITY_EXTRA_FILE_NAME_ID = "name"
        const val MAIN_ACTIVITY_EXTRA_FILE_CONTENT_ID = "content"
    }

    @ExperimentalStdlibApi
    private var fileList = mutableListOf<com.example.storage.File>()

    @ExperimentalStdlibApi
    val adapter = FileAdapter(fileList, this)

    var isExternal = false

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateFileList()

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        CreateFileButton.setOnClickListener {
            createFile(editTextFileName.text.toString(), editTextFileContent.text.toString())
            updateFileList()
        }
        ClearFileButton.setOnClickListener {
            clearFiles()
            updateFileList()
        }
        ChooseStorage?.setOnCheckedChangeListener { _, isChecked ->
            isExternal = isChecked
            updateFileList()
        }
    }

    @ExperimentalStdlibApi
    fun getFiles(): MutableList<com.example.storage.File> {
        val fileListTemp: MutableList<com.example.storage.File> = mutableListOf()
        if (isExternal) {
            val file = File(getExternalFilesDir(null), "")
            for (file in file.listFiles()) {
                fileListTemp.add(com.example.storage.File(file.name, file.readText()))
            }
            return fileListTemp
        } else {
            var fileNameList: Array<String> = fileList()
            for (fileName in fileNameList) {
                openFileInput(fileName).use {
                    val fileContent = it.readBytes().decodeToString()
                    fileListTemp.add(com.example.storage.File(fileName, fileContent))
                }
            }
            return fileListTemp
        }
    }

    @ExperimentalStdlibApi
    override fun onItemClick(position: Int) {
        val clickedItem = fileList[position]
        val intent = Intent(this, ShowFileContent::class.java)
        intent.putExtra(MAIN_ACTIVITY_EXTRA_FILE_NAME_ID, clickedItem.name)
        intent.putExtra(MAIN_ACTIVITY_EXTRA_FILE_CONTENT_ID, clickedItem.content)
        startActivity(intent)
    }

    @ExperimentalStdlibApi
    override fun onItemDelete(position: Int) {
        val clickedItem = fileList[position]
        if (isExternal) {
            val file = File(getExternalFilesDir(null), "")
            for (file in file.listFiles()) {
                if (file.name == clickedItem.name) {
                    file.delete()
                    Log.i("DELETE EXTERN FILE: ", file.name)
                }
            }
        } else {
            val files: Array<String> = fileList()
            for (fileName in files) {
                if (fileName == clickedItem.name) {
                    Log.i("DELETE INTERN FILE: ", fileName)
                    deleteFile(fileName)
                }
            }
        }
        updateFileList()
    }

    @ExperimentalStdlibApi
    fun updateFileList() {
        fileList.clear()
        fileList.addAll(getFiles())
        adapter.notifyDataSetChanged()
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

        } else {
            //Intern
            openFileOutput(fileName, Context.MODE_PRIVATE).use {
                it.write(fileContent.toByteArray())
            }
        }
        listFiles()
    }


    @ExperimentalStdlibApi
    fun listFiles() {
        if (isExternal) {
            val file = File(getExternalFilesDir(null), "")
            for (file in file.listFiles()) {
                Log.i("NAME EXTERN: ", file.name)
                Log.i("CONTENT EXTERN: ", file.readText())
            }
        } else {
            val files: Array<String> = fileList()
            for (fileName in files) {
                Log.i("NAME INTERN: ", fileName)
                openFileInput(fileName).use {
                    val fileContent = it.readBytes().decodeToString()
                    Log.i("CONTENT INTERN: ", fileContent)
                }
            }
        }
    }

    @ExperimentalStdlibApi
    fun clearFiles() {
        val files: Array<String> = fileList()
        for (fileName in files) {
            Log.i("DELETE INTERN FILE: ", fileName)
            deleteFile(fileName)
        }
        val file = File(getExternalFilesDir(null), "")
        for (file in file.listFiles()) {
            file.delete()
            Log.i("DELETE EXTERN FILE: ", file.name)
        }
        updateFileList()
    }

}