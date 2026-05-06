package me.vikas.newsapp.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.vikas.newsapp.databinding.ActivityMainBinding
import me.vikas.newsapp.databinding.ActivityMainBinding.inflate
import me.vikas.newsapp.ui.topheadline.TopHeadlineActivity
import me.vikas.newsapp.utils.launchActivity

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        initView()
        setupClickListeners()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        setSupportActionBar(activityMainBinding.toolbar)
        activityMainBinding.apply {
            motionLayout.post {
                motionLayout.transitionToEnd()
            }
        }
    }

    private fun setupClickListeners() {
        activityMainBinding.apply {
            cardTopHeadlines.setOnClickListener {
                launchActivity<TopHeadlineActivity>(false)
            }


        }
    }
}