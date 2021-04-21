package hr.santolin.rssparser

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import hr.santolin.rssparser.parser.parse

private const val JOB_ID = 1
class NewsService :JobIntentService() { // this is also a context!!
    override fun onHandleWork(intent: Intent) {
        val articles = parse(this)
        //sendBroadcast<NewsReceiver>() //posalje broadcast kada je data imported
    }

    companion object {
        fun enqueueWork(context: Context, intent: Intent) =
            enqueueWork(context, hr.santolin.rssparser.NewsService::class.java, JOB_ID, intent)
    }
}