package hr.santolin.rssparser.factory

import java.net.HttpURLConnection
import java.net.URL
private const val TIMEOUT = 10000
private const val GET = "GET"
private const val USER_AGENT = "User-Agent"
private const val MOZILLA = "Mozilla/5.0"
fun createGetHttpUrlConnection(path: String): HttpURLConnection {
    var url = URL(path)
    return (url.openConnection() as HttpURLConnection).apply{
        connectTimeout=TIMEOUT
        readTimeout= TIMEOUT
        requestMethod= GET
        addRequestProperty(USER_AGENT, MOZILLA)
    }


}