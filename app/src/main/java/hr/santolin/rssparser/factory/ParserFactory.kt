package hr.santolin.rssparser.factory

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

fun createXMLPullParser(stream: InputStream):XmlPullParser{
    val factory = XmlPullParserFactory.newInstance()
    val parser=factory.newPullParser()
    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
    parser.setInput(stream, null)
    return parser
}