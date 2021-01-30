package ru.pavelapk.visualnovel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_enter_name.*

const val EXTRA_USERNAME = "ru.pavelapk.visualnovel.USERNAME"

class EnterNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_name)

        val nextID = intent.getStringExtra(EXTRA_NEXT_ID)
        if (nextID != null) {
            val scene = Scene.getSceneFromJSON(this, nextID)

            tvText.text = scene.text
            ivBackground.setImageResource(scene.imgID)

            val btnData = scene.buttons[0]
            btnConfirm.text = btnData.text
            btnConfirm.setOnClickListener {
                val nextClass = btnData.nextType.getActivityClass()
                val intent = Intent(this, nextClass).apply {
                    putExtra(EXTRA_NEXT_ID, btnData.next)
                    putExtra(EXTRA_USERNAME, etName.text.toString())
                }
                startActivity(intent)
            }
        }
    }
}