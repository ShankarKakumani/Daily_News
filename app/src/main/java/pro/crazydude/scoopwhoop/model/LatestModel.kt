package pro.crazydude.scoopwhoop.model

data class LatestModel(
    val `data`: List<LatestData>,
    val err: String,
    val next_offset: Int,
    val status: Int,
    val total_videos: Int
)

data class LatestData(
    val _id: String,
    val aspect_ratio: String,
    val cast_crew: List<CastCrew>,
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

data class CastCrew(
    val displayname: String,
    val profile_pic: String,
    val title: String,
    val username: String
)
