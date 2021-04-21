package hr.santolin.rssparser.parser

import android.content.Context
import hr.santolin.rssparser.dao.Article
import hr.santolin.rssparser.factory.createGetHttpUrlConnection
import hr.santolin.rssparser.factory.createXMLPullParser
import hr.santolin.rssparser.handlers.downloadImageAndStore
import org.xmlpull.v1.XmlPullParser
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val RSS_URL =
    //"https://www.climbing.com/news/feed/"
    "https://slobodnadalmacija.hr/feed/category/241"
// "https://www.mountainproject.com/rss/new?selectedIds=105708956&routes=on&areas=on&comments=on&photos=on"
private const val ATTRIBUTE_SRC = "src"
private const val ATTRIBUTE_URL = "url"
private const val FILE_PREFIX = "ARTICLE_"

fun parse(context: Context): List<Article> {
    var articles = mutableListOf<Article>()
    val con = createGetHttpUrlConnection(RSS_URL)
    var parser = createXMLPullParser(con.inputStream)
    var tagType: TagType? = null
    var article: Article? = null
    var event = parser.eventType //START_TAG,TEXT,END_TAG,END_DOCUMENT
    while (event != XmlPullParser.END_DOCUMENT) {
        when (event) {
            XmlPullParser.START_TAG -> {
                var name = parser.name
                tagType = try {
                    TagType.of(name)
                } catch (e: IllegalArgumentException) {
                    null
                }
                if (tagType == TagType.ENCLOSURE && article != null) {
                    //skini sliku
                    val url = parser.getAttributeValue(null, ATTRIBUTE_URL)
                    if (url != null) {
                        //filename => ARTICLE_item_hash_code
                        val picturePath = downloadImageAndStore(
                            context,
                            url,
                            FILE_PREFIX + article.title.hashCode()
                        )
                        article.picturePath = picturePath
                    }
                }
            }
            XmlPullParser.TEXT -> {
                if (tagType != null) {
                    val text = parser.text.trim()
                    when (tagType) {
                        TagType.ITEM -> {
                            article = Article()
                            articles.add(article)
                        }
                        TagType.TITLE -> {
                            if (article != null && text.isNotBlank()) {
                                article.title = text
                            }
                        }
                        TagType.DESCRIPTION -> {
                            if (article != null && text.isNotBlank()) {
                                article.description = text
                            }
                        }
                        TagType.PUBDATE -> {
                            if (article != null && text.isNotBlank()) {
                                article.publishDateTime =
                                    LocalDateTime.parse(text, DateTimeFormatter.RFC_1123_DATE_TIME)
                            }
                        }
                    }
                }

            }
        }
        event = parser.next()
    }
    return articles
}

private enum class TagType(val value: String) {
    ITEM("item"),
    TITLE("title"),
    DESCRIPTION("description"),
    PUBDATE("pubDate"),
    ENCLOSURE("enclosure");

    companion object { //factory method of
        fun of(value: String) = valueOf(value.toUpperCase())
    }

}