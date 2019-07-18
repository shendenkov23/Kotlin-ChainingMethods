package com.example.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.ScheduledThreadPoolExecutor
import kotlin.random.Random

data class LoginParams(val login: String, val pass: String)
data class User(val userId: String, val email: String)
data class ImportantThing(val thingId: String, val thingSome: String)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener {
            startChain()
        }
    }

    override fun onResume() {
        super.onResume()
        startChain()
    }

    private fun startChain() {
        Log.d("Chaining", "Start chain")
        val b = fab

        fab.isEnabled = false

        val params = LoginParams("jhorapobeditel", "qwerty")

        (::login ß ::getSomeImportant)(params) { result ->
            result.onSuccess { thing ->
                Log.d("Chaining", "Chain result success: $thing")
                fab.isEnabled = true
            }
            result.onFailure { error ->
                Log.d("Chaining", "Chain result failure" + error.localizedMessage?.toString())
                Log.d("Chaining", "This is fake failure, so try again! :]")
                fab.isEnabled = true
            }
        }
    }

    private fun login(input: LoginParams, compl: ((Result<User>) -> Unit)) {
        Log.d("Chaining", "Start login")

        val executor = ScheduledThreadPoolExecutor(2)
        executor.execute {
            Thread.sleep(3000)

            val isFailure = Random.nextInt() % 4 == 0

            if (isFailure) {
                Log.d("Chaining", "Login failure")
                compl(Result.failure(RuntimeException("Login error")))
            } else {
                Log.d("Chaining", "Login success")
                val user = User("uniqueUserId", "email@gmail.com")
                compl(Result.success(user))
            }
        }
    }

    private fun getSomeImportant(input: User, compl: ((Result<ImportantThing>) -> Unit)) {
        Log.d("Chaining", "Start GetSomeImportant")

        val executor = ScheduledThreadPoolExecutor(2)
        executor.execute {
            Thread.sleep(3000)

            val isFailure = Random.nextInt() % 4 == 0

            if (isFailure) {
                Log.d("Chaining", "GetSomeImportant failure")
                compl(Result.failure(RuntimeException("GetSomeImportant error")))
            } else {
                Log.d("Chaining", "GetSomeImportant success")
                val thing = ImportantThing("${input.userId}-uniqueThingId", "content")
                compl(Result.success(thing))
            }
        }
    }
}


