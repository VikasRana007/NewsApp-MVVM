package me.vikas.newsapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
//import me.vikas.newsapp.ui.base.NavGraph
//import me.vikas.newsapp.ui.theme.NewsAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContent {
//            NewsAppTheme {
//                NavGraph()
//
//                WorkManager.getInstance(this)
//                    .getWorkInfosForUniqueWorkLiveData("news_update_work")
//                    .observe(this) { workInfos ->
//                        workInfos.forEach {
//                            Log.d("WorkerState", it.state.name)
//                        }
//                    }
//            }
//        }
    }
}