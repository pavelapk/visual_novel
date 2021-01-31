package ru.pavelapk.visualnovel

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_enter_name.*


class EnterNameActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_name)

        tvText = tvGreeting
        imBG = ivBackground
        singleBtn = btnConfirm

        initScene()
    }

    override fun getUsername() = etName.text.toString()
}