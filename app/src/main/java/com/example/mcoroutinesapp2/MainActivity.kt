package com.example.mcoroutinesapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.net.URL
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    lateinit var adviceText: TextView
    lateinit var adviceButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAdvice()
    }

    fun getAdvice()
    {

        var holdJson = ""
        adviceButton = findViewById(R.id.button)



        adviceButton.setOnClickListener {
            CoroutineScope(IO).launch {

                var adviceData = async { featchData()}.await()

                if(!adviceData.isEmpty())
                {

                    var jsonOfAdvice = JSONObject(adviceData)
                    holdJson = jsonOfAdvice.getJSONObject("slip").getString("advice")

                    Log.d("Advice","$holdJson")

                    Log.d("AdviceDAta","$adviceData")
                    //adviceText.text = holdJson.toString() Here the app is crush


                    withContext(Main){
                        adviceText.text = holdJson

                    }

                }
            }



        }
    }



    fun featchData():String
    {
        var adviceResponce = ""

        try {
            adviceResponce = URL("https://api.adviceslip.com/advice").readText()
        }catch (e: Exception)
        {
            Log.d("advice","$e")

        }

        return adviceResponce
    }



}