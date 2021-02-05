package ru.pavelapk.visualnovel

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_title.*


class TitleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)

        tvText = tvTitle
        imBG = ivBackground
        singleBtn = btnStart

        initScene()
    }
}