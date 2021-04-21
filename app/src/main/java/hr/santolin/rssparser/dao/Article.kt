package hr.santolin.rssparser.dao

import java.time.LocalDateTime

data class Article(
    var id: Long? = null,
    var title: String? = null,
    var description: String? = null,
    var picturePath: String? = null,
    var publishDateTime: LocalDateTime? = null,
    var read: Boolean? = false
) {
}