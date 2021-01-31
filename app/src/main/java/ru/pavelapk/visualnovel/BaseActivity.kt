package ru.pavelapk.visualnovel

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

const val EXTRA_NEXT_ID = "ru.pavelapk.visualnovel.NEXT_ID"
const val EXTRA_USERNAME = "ru.pavelapk.visualnovel.USERNAME"

abstract class BaseActivity : AppCompatActivity() {

    lateinit var tvText: TextView
    lateinit var imBG: ImageView
    lateinit var singleBtn: Button
    lateinit var multipleBtns: Array<Button>

    fun initScene() {
        val nextID = intent.getStringExtra(EXTRA_NEXT_ID) ?: "1"

        val scene = Scene.getSceneFromJSON(this, nextID)

        if (scene.needFormat) {
            tvText.text = scene.text.format(getUsername())
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
            val nextClass = btnData.nextType.getActivityClass()
            val intent = Intent(this, nextClass).apply {
                putExtra(EXTRA_NEXT_ID, btnData.next)
                putExtra(EXTRA_USERNAME, getUsername())
            }
            startActivity(intent)
            finish()
        }
    }

    abstract fun getUsername(): String
}