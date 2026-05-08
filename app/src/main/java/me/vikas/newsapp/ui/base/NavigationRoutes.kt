package me.vikas.newsapp.ui.base

sealed class Routes(val name: String) {

    data object Splash : Routes("splash")
    data object Dashboard : Routes("dashboard")
    data object OfflineTopHeadline : Routes("offlineTopHeadline")
    data object PaginationTopHeadline : Routes("paginationTopHeadline")
    data object TopHeadline : Routes("topHeadline")
    data object NewsSource : Routes("newsSource")
    data object SourceWiseHeadline : Routes("sourceWiseHeadline")
    data object NewsSearch : Routes("newsSearch")
    data object CountryWiseNews : Routes("countryWiseNews")
    data object LanguageWiseNews : Routes("languageWiseNews")
    data object WorkManagerTask : Routes("workManagerTask")

}