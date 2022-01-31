package com.semester6.uas_crudsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.mysqlite.myDBHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {
    var mysqlitedb : myDBHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mysqlitedb = myDBHelper(this)

        btn_submit.setOnClickListener {
            val userTmp : User = User()
            userTmp.nama = edit_text_name.text.toString()
            userTmp.no_hp = edit_text_phone_number.text.toString()
            var result =mysqlitedb?.addUser(userTmp)
            if(result!=-1L){
                Toast.makeText(this, "Berhasil",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Gagal",Toast.LENGTH_SHORT).show()
            }
            updateAdapter()
            edit_text_name.text.clear()
            edit_text_phone_number.text.clear()

        }
        btn_delete.setOnClickListener {
            var nama = spinner1.selectedItem.toString()
            if(nama!=null || nama !=""){
                doAsync {
                    mysqlitedb?.deleteData(nama)
                    updateAdapter()
                }
            }
        }
    }

    private fun updateAdapter() {
        doAsync {
            var nameList = mysqlitedb?.viewAllName()?.toTypedArray()
            uiThread {
                if(spinner1 != null && nameList?.size != 0) {
                   var arrayAdapter = ArrayAdapter(applicationContext,
                   android.R.layout.simple_spinner_dropdown_item, nameList!!)
                    spinner1.adapter = arrayAdapter
                }
            }
        }
    }


}