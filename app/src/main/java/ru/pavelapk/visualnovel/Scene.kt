package ru.pavelapk.visualnovel

import android.content.Context
import org.json.JSONObject

enum class SceneType {
    TITLE, INPUT, MAIN;

    fun getActivityClass() = when (this) {
        TITLE -> TitleActivity::class.java
        INPUT -> EnterNameActivity::class.java
        MAIN -> GameActivity::class.java
    }

    companion object {
        fun fromString(s: String) = when (s) {
            "title" -> TITLE
            "input" -> INPUT
            else -> MAIN
        }
    }
}

data class ButtonData(val text: String, val next: String, val nextType: SceneType)

data class Scene(
    val type: SceneType,
    val text: String,
    val needFormat: Boolean,
    val imgID: Int,
    val buttons: List<ButtonData>
) {
    companion object {
        fun getSceneFromJSON(context: Context, nextID: String): Scene {
            val json = context.resources.openRawResource(R.raw.scenario).bufferedReader().readText()
            val scenario = JSONObject(json)
            val scene = scenario.getJSONObject(nextID)

            val type = SceneType.fromString(scene.getString("type"))

            val text = scene.getString("text")
            val needFormat = when {
                scene.has("need_format") -> scene.getBoolean("need_format")
                else -> false
            }
            val imgID = context.resources.getIdentifier(
                scene.getString("bg_img"),
                "drawable",
                context.packageName
            )
            val btnsData = scene.getJSONArray("buttons")

            val buttons = mutableListOf<ButtonData>()
            for (i in 0 until btnsData.length()) {
                val btnData = btnsData.getJSONObject(i)

                val btnText = btnData.getString("text")
                val next = btnData.getString("next")

                val nextType = SceneType.fromString(
                    scenario.getJSONObject(next).getString("type")
                )
                buttons.add(
                    ButtonData(
                        btnText,
                        next,
                        nextType
                    )
                )
            }
            return Scene(type, text, needFormat, imgID, buttons)
        }
    }
}
