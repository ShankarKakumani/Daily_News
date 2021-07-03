package pro.crazydude.scoopwhoop.model

data class DataModel(
    val `data`: List<Data>,
    val err: String,
    val status: Int,
    val total: Int
)

data class Data(
    val _id: String,
    val aspect_ratio: String,
    val cast_crew: List<Any>,
    val duration: String,
    val feature_img: String,
    val genres: List<Genre>,
    val new_tags: List<NewTag>,
    val nsfw: Int,
    val onexone_img: String,
    val premium: Boolean,
    val pub_date: String,
    val sh_heading: String,
    val show: Show,
    val slug: String,
    val tags: List<String>,
    val title: String,
    val video_thumbnail_16x9: String,
    val video_thumbnail_2x3: String,
    val video_thumbnail_9x16: String
)

data class Genre(
    val genre_slug: String,
    val genre_title: String
)

data class NewTag(
    val flag: Int,
    val id: String,
    val tag_slug: String,
    val tag_type: String,
    val title: String
)

data class Show(
    val feature_img_land: String,
    val feature_img_port: String,
    val topic: String,
    val topic_display: TopicDisplay,
    val topic_feature_img: String
)

data class TopicDisplay(
    val topic_display: String,
    val topic_slug: String
)