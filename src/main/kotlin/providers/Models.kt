package com.lukaorocha.providers

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date
import java.util.UUID

data class PaginatedResponse<T>(
    val results: List<T>,
    val hasMore: Boolean,
    val nextCursor: UUID?,
)

data class Page<T>(
    val id: UUID,
    val createdTime: Date,
    val createdBy: User,
    val lastEditedTime: Date,
    val lastEditedBy: User,
    val archived: Boolean,
    val properties: T,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExpensePageProperties(
    @JsonProperty("Name")
    val name: TitleProperty,

    @JsonProperty("Date")
    val date: DateProperty,

    @JsonProperty("Amount")
    val amount: NumberProperty,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class IncomePageProperties(
    @JsonProperty("Name")
    val name: TitleProperty,

    @JsonProperty("Amount")
    val amount: NumberProperty,

    @JsonProperty("Date")
    val date: DateProperty,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MonthlyBalancePageProperties(
    @JsonProperty("Name")
    val name: TitleProperty,

    @JsonProperty("Start date")
    val startDate: DateProperty,

    @JsonProperty("End date")
    val endDate: DateProperty,
)

data class Database(
    val id: UUID,
    val createdTime: Date,
    val createdBy: User,
    val lastEditedTime: Date,
    val lastEditedBy: User,
    val title: List<RichTextObject>,
    val description: List<RichTextObject>,
    val url: String,
    val archived: Boolean,
    val publicUrl: String? = null,
)

data class User(
    val id: UUID,
)

enum class ColorAnnotation(val value: String) {

    @JsonProperty("blue")
    Blue("blue"),

    @JsonProperty("blue-background")
    BlueBackground("blue-background"),

    @JsonProperty("brown")
    Brown("brown"),

    @JsonProperty("brown-background")
    BrownBackground("brown-background"),

    @JsonProperty("default")
    Default("default"),

    @JsonProperty("gray")
    Gray("gray"),

    @JsonProperty("gray-background")
    GrayBackground("gray-background"),

    @JsonProperty("green")
    Green("green"),

    @JsonProperty("green-background")
    GreenBackground("green-background"),

    @JsonProperty("orange")
    Orange("orange"),

    @JsonProperty("orange-background")
    OrangeBackground("orange-background"),

    @JsonProperty("pink")
    Pink("pink"),

    @JsonProperty("pink-background")
    PinkBackground("pink-background"),

    @JsonProperty("purple")
    Purple("purple"),

    @JsonProperty("purple-background")
    PurpleBackground("purple-background"),

    @JsonProperty("red")
    Red("red"),

    @JsonProperty("red-background")
    RedBackground("red-background"),

    @JsonProperty("yellow")
    Yellow("yellow"),

    @JsonProperty("yellow-background")
    YellowBackground("yellow-background"),
}

enum class RichTextType(val value: String) {
    @JsonProperty("text")
    Text("text"),

    @JsonProperty("mention")
    Mention("mention"),

    @JsonProperty("equation")
    Equation("equation"),
}

data class RichTextObject(
    val type: RichTextType,
    val annotations: RichTextAnnotations,
    val plainText: String? = null,
    val href: String? = null,
)

data class RichTextAnnotations(
    val bold: Boolean,
    val italic: Boolean,
    val strikethrough: Boolean,
    val underline: Boolean,
    val code: Boolean,
    val color: ColorAnnotation,
)

data class DateObject(
    val start: Date,
    val end: Date?,
    val timeZone: String?,
)

data class TitleProperty(
    val title: List<RichTextObject>,
)

data class NumberProperty(
    val number: Float,
)

data class DateProperty(
    val date: DateObject
)
