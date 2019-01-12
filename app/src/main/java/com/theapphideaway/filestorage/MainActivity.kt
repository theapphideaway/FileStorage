package com.theapphideaway.filestorage

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import android.R.attr.path



class MainActivity : AppCompatActivity() {

    var allFiles = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val PERMISSION_REQUEST_CODE = 1
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_REQUEST_CODE)
            }
        }

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.add_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item != null){
            when(item.itemId){
                R.id.add_button_item ->{

                    var filename = title_edit_text.text.toString() + ".txt"
                    // create a File object for the parent directory
                    val directory = File("/sdcard/M/")
                    // have the object build the directory structure, if needed.
                    directory.mkdirs()
                    // create a File object for the output file
                    val outputFile = File(directory, filename)
                    // now attach the OutputStream to the file object, instead of a String representation
                    try {
                        val fos = FileOutputStream(outputFile)
                    } catch (e: FileNotFoundException) {
                        println(e.message.toString())
                    }

                    try {
                        File("/sdcard/M/$filename").writeText(content_edit_text.text.toString())

                        File("/sdcard/M/").walkBottomUp().forEach {
                            println(it)
                            allFiles.add(it.toString())
                        }

                        val yourDir = File("/sdcard/M/")

                        for(title in yourDir.listFiles())
                        {

                            val newFilename = title.name.substring(title.name.lastIndexOf("/") + 1)
                            var newTitle = File(newFilename).nameWithoutExtension
                            println(newTitle)
                        }
                    } catch (e:Exception){
                        println(e.message)
                    }

                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
