package ru.pavelapk.visualnovel

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NEXT_ID = "ru.pavelapk.visualnovel.NEXT_ID"
        const val DEFAULT_SCENE = "1"

        const val PREF_FILE_KEY = "ru.pavelapk.visualnovel.USERNAME_PREF"
        const val USERNAME_KEY = "ru.pavelapk.visualnovel.USERNAME"
    }

    lateinit var tvText: TextView
    lateinit var imBG: ImageView
    lateinit var singleBtn: Button
    lateinit var multipleBtns: Array<Button>

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getSharedPreferences(PREF_FILE_KEY, MODE_PRIVATE)
    }


    fun initScene() {
        val nextID = intent.getStringExtra(EXTRA_NEXT_ID) ?: DEFAULT_SCENE

        val scene = Scene.getSceneFromJSON(this, nextID)

        if (scene.needFormat) {
            tvText.text = scene.text.format(getSavedUsername())
        } else {
            tvText.text = scene.text
        }
        imBG.setImageResource(scene.imgID)

        when (scene.type) {
            SceneType.MAIN -> multipleBtns.zip(scene.buttons).forEach { (button, btnData) ->
                setDataToBtn(button, btnData)
                button.visibility = View.VISIBLE
            }
            SceneType.TITLE, SceneType.INPUT -> setDataToBtn(singleBtn, scene.buttons[0])
        }
    }

    private fun setDataToBtn(button: Button, btnData: ButtonData) {
        button.text = btnData.text
        button.setOnClickListener {
            saveUsername()
            val nextClass = btnData.nextType.getActivityClass()
            val intent = Intent(this, nextClass).apply {
                putExtra(EXTRA_NEXT_ID, btnData.next)
            }
            startActivity(intent)
            finish()
        }
    }

    fun getSavedUsername() = sharedPref.getString(USERNAME_KEY, "")

    open fun getNewUsername() = ""

    private fun saveUsername() {
        val username = getNewUsername()
        if (username.isNotEmpty()) {
            with(sharedPref.edit()) {
                putString(USERNAME_KEY, username)
                apply()
            }
        }
    }
}