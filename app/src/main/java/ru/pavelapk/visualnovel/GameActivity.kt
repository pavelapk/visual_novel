package ru.pavelapk.visualnovel

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        tvText = tvNarration
        imBG = ivBackground
        multipleBtns = arrayOf(btnChoose1, btnChoose2, btnChoose3)

        initScene()
    }
}

