package me.vikas.newsapp.ui.base

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import me.vikas.newsapp.ui.screens.countrywisenews.CountryWiseTopHeadRoutes
import me.vikas.newsapp.ui.screens.dashboard.DashboardScreen
import me.vikas.newsapp.ui.screens.languagewisenews.LanguageWiseNewsRoute
import me.vikas.newsapp.ui.screens.newssource.NewsSourceRoutes
import me.vikas.newsapp.ui.screens.newssource.NewsSourceViewModel
import me.vikas.newsapp.ui.screens.offline_topheadline.OfflineTopHeadlineRoutes
import me.vikas.newsapp.ui.screens.pagination.PaginationTopHeadlineRoutes
import me.vikas.newsapp.ui.screens.searchnews.NewsSearchRoute
import me.vikas.newsapp.ui.screens.sourcewiseheadlines.SourceWiseHeadlineRoutes
import me.vikas.newsapp.ui.screens.splash.SplashScreen
import me.vikas.newsapp.ui.screens.topheadline.TopHeadlineRoutes
import me.vikas.newsapp.ui.screens.topheadline.TopHeadlineViewModel
import me.vikas.newsapp.utils.AppConstant.COUNTRY
import me.vikas.newsapp.utils.AppConstant.LANGUAGE

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.Splash.name
) {

    val context = LocalContext.current

    NavHost(
        navController = navController, startDestination = startDestination
    ) {

        // Splash Screen ==============================================================
        composable(route = Routes.Splash.name) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Routes.Dashboard.name) {
                        popUpTo(Routes.Splash.name) {
                            inclusive = true  // removes splash from backstack
                        }
                    }
                })
        }

        // Dashboard Screen ==============================================================
        composable(route = Routes.Dashboard.name) {
            DashboardScreen(
                onTopHeadlinesClick = {
                COUNTRY = "us"
                navController.navigate(Routes.TopHeadline.name)
            },

                onOfflineTopHeadlinesClick = {
                    COUNTRY = "us"
                    navController.navigate(Routes.OfflineTopHeadline.name)
                },

                onPaginationTopHeadlinesClick = {
                    COUNTRY = "us"
                    navController.navigate(Routes.PaginationTopHeadline.name)
                },

                onNewsSourcesClick = {
                    LANGUAGE = "en"
                    navController.navigate(Routes.NewsSource.name)
                },

                onCountriesClick = {
                    navController.navigate(Routes.CountryWiseNews.name)
                },

                onLanguagesClick = {
                    navController.navigate(Routes.LanguageWiseNews.name)
                },

                onSearchClick = {
                    navController.navigate(Routes.NewsSearch.name)
                }

//                onWorkManager = {
//                    navController.navigate(Routes.WorkManagerTask.name)
//                }

            )
        }

        // Top HeadLine Screen & Event Listener ====================================================
        composable(route = Routes.TopHeadline.name) {
            val topHeadlineViewModel: TopHeadlineViewModel = hiltViewModel()
            //val uiState by topHeadlineViewModel.uiState.collectAsStateWithLifecycle()
            TopHeadlineRoutes(onNewsClick = {
                openCustomTabBrowser(context, it)
            }, topHeadlineViewModel, onBackClick = {
                navController.popBackStack()
            }, onRetry = { topHeadlineViewModel.fetchTopHeadlines() })
        }

        // Offline Top HeadLine Screen & Event Listener
        // ====================================================
        composable(route = Routes.OfflineTopHeadline.name) {
            OfflineTopHeadlineRoutes(onNewsClick = {
                openCustomTabBrowser(context, it)
            }, onBackClick = {
                navController.popBackStack()
            })
        }

        // Pagination Top HeadLine Screen & Event Listener
        // ====================================================
        composable(route = Routes.PaginationTopHeadline.name) {
            PaginationTopHeadlineRoutes(onNewsClick = {
                openCustomTabBrowser(context, it)
            }, onBackClick = {
                navController.popBackStack()
            })
        }

        // News Source Screen & Event Listener ====================================================
        composable(route = Routes.NewsSource.name) {
            val newsSourceViewModel: NewsSourceViewModel = hiltViewModel()
            NewsSourceRoutes(onSourceClick = { sourceId ->
                navController.navigate(
                    "${Routes.SourceWiseHeadline.name}?sourceId=$sourceId"
                )
            }, newsSourceViewModel, onRetry = { newsSourceViewModel.fetchNewsSource() })
        }

        // Source wise headlines news Screen & Event Listener
        composable(
            route = "${Routes.SourceWiseHeadline.name}?sourceId={sourceId}",
            arguments = listOf(navArgument("sourceId") {
                type = NavType.StringType      // This is how we can pass argument.
            })
        ) {
            SourceWiseHeadlineRoutes(onNewsClick = {
                openCustomTabBrowser(context, it)
            }, onBackClick = {
                navController.popBackStack()
            })
        }

        // Country List & Event Listener ===============================>
        composable(route = Routes.CountryWiseNews.name) {
            CountryWiseTopHeadRoutes(onCountryClick = {
                navController.navigate(
                    "${Routes.TopHeadline.name}?countryCode=$it"
                )
            })
        }

        // Country Wise Top Headlines Screen & Event Listener ===============================>
        composable(
            route = "${Routes.TopHeadline.name}?countryCode={countryCode}",
            arguments = listOf(navArgument("countryCode") {
                type = NavType.StringType
            })
        ) {
            val topHeadlineViewModel: TopHeadlineViewModel = hiltViewModel()
            TopHeadlineRoutes(onNewsClick = {
                openCustomTabBrowser(context, it)
            }, topHeadlineViewModel, onBackClick = {
                navController.popBackStack()
            }, onRetry = {})
        }

        // Language List & Event Listener ===============================>
        composable(route = Routes.LanguageWiseNews.name) {
            LanguageWiseNewsRoute(onLanguageClick = {
                navController.navigate(
                    "${Routes.NewsSource.name}?languageCode=$it"
                )
            })
        }

        // Language Wise News Sources Screen & Event Listener ===============================>
        composable(
            route = "${Routes.NewsSource.name}?languageCode={languageCode}",
            arguments = listOf(navArgument("languageCode") {
                type = NavType.StringType
            })
        ) {
            val newsSourceViewModel: NewsSourceViewModel = hiltViewModel()

            NewsSourceRoutes(onSourceClick = {
                navController.navigate(
                    "${Routes.SourceWiseHeadline.name}?sourceId=$it"
                )
            }, newsSourceViewModel, onRetry = { newsSourceViewModel.fetchNewsSource() })
        }

        // Top Headlines with Language wise News Sources Screen & Event Listener ===============================>
        // 3. Source Wise Headlines
        composable(
            route = "${Routes.SourceWiseHeadline.name}?sourceId={sourceId}", arguments = listOf(
                navArgument("sourceId") {
                    type = NavType.StringType
                    defaultValue = ""
                })
        ) {
            SourceWiseHeadlineRoutes(onNewsClick = { url ->
                openCustomTabBrowser(context, url)  // ✅ Yahan Custom Tab sahi hai
            }, onBackClick = {
                navController.popBackStack()
            })
        }

        // News Search Composable
        composable(route = Routes.NewsSearch.name) {
            NewsSearchRoute()
        }

        // Task Manager Composable
        /* composable(route = Routes.WorkManagerTask.name) {
             WorkManagerRoute(
                 onBackClick = {
                     navController.popBackStack()
                 }
             )
         }*/
    }
}

fun openCustomTabBrowser(context: Context, newsUrl: String) {
    try {
        val customTabsIntent =
            CustomTabsIntent.Builder().setToolbarColor(0xFF124559.toInt()).setShowTitle(true)
                .build()
        customTabsIntent.launchUrl(context, newsUrl.toUri())
    } catch (ex: Exception) {
        println("Exception While Custom Tab Browser Opening : ${ex.localizedMessage}")
    }
}


