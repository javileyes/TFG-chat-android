package es.javiergimenez.chat.service.deserializer

import com.google.gson.*
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.DateTimeFormatterBuilder
import java.lang.reflect.Type


class DateTimeDeserializer : JsonDeserializer<DateTime>, JsonSerializer<DateTime> {

    companion object {
        const val DATETIME_PRETTY_FORMAT = "dd MMMM yyyy"

        private const val DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ" // "2019-04-12T16:38:15Z"
        private const val DATETIME_FORMAT_MILLIS = "yyyy-MM-dd'T'HH:mm:ss.SSSZ" // "2019-04-12T16:38:15.123Z"
        private val parsers = arrayOf(
            DateTimeFormat.forPattern(DATETIME_FORMAT).parser,
            DateTimeFormat.forPattern(DATETIME_FORMAT_MILLIS).parser
        )
        val formatter: DateTimeFormatter = DateTimeFormatterBuilder().append(null,
            parsers
        ).toFormatter()

        fun timeAgo(date: DateTime): String {
            val days = Days.daysBetween(date, DateTime.now()).days
            return when (days) {
                0 -> date.toString("HH:mm")
                1 -> "ayer"
                in 2..7 -> date.dayOfWeek().asText
                else -> date.toString(DATETIME_PRETTY_FORMAT)
            }
        }

    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): DateTime {
//        val formatter = DateTimeFormat.forPattern(DATETIME_FORMAT)
        return formatter.parseDateTime(json.asString)
    }

    override fun serialize(src: DateTime?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement? {
        return if (src == null) null else JsonPrimitive(src.toString())
    }

}