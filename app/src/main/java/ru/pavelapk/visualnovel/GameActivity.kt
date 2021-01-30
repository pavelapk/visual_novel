package ru.pavelapk.visualnovel

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val nextID = intent.getStringExtra(EXTRA_NEXT_ID)

        if (nextID != null) {
            val scene = Scene.getSceneFromJSON(this, nextID)

            if (scene.needFormat) {
                val username = intent.getStringExtra(EXTRA_USERNAME) ?: "чел"
                tvNarration.text = scene.text.format(username)
            } else {
                tvNarration.text = scene.text
            }

            ivBackground.setImageResource(scene.imgID)

            val refIds = groupButtons.referencedIds
            groupButtons.referencedIds =
                IntArray(0)  // bug with setting visibility, when View in Group

            for ((i, btnData) in scene.buttons.withIndex()) {
                val button = findViewById<Button>(refIds[i])

                button.text = btnData.text
                button.setOnClickListener {
                    val nextClass = btnData.nextType.getActivityClass()
                    val intent = Intent(this, nextClass).apply {
                        putExtra(EXTRA_NEXT_ID, btnData.next)
                    }
                    startActivity(intent)
                }
                button.visibility = View.VISIBLE
            }
        }
    }
}