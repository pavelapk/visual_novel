package ru.pavelapk.visualnovel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_title.*

const val EXTRA_NEXT_ID = "ru.pavelapk.visualnovel.NEXT_ID"

class TitleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)

        val nextID = intent.getStringExtra(EXTRA_NEXT_ID) ?: "1"
        val scene = Scene.getSceneFromJSON(this, nextID)

        tvTitle.text = scene.text
        ivBackground.setImageResource(scene.imgID)

        val btnData = scene.buttons[0]
        btnStart.text = btnData.text
        btnStart.setOnClickListener {
            val nextClass = btnData.nextType.getActivityClass()
            val intent = Intent(this, nextClass).apply {
                putExtra(EXTRA_NEXT_ID, btnData.next)
            }
            startActivity(intent)
        }
    }
}