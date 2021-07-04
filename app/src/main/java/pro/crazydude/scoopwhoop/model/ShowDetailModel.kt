package pro.crazydude.scoopwhoop.model

data class ShowDetailModel(
    val `data`: List<ShowDetailData>,
    val err: String,
    val next_offset: Int,
    val show_details: ShowDetails,
    val status: Int
)

data class ShowDetailData(
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

data class ShowDetails(
    val channel: Channel,
    val feature_img_land: String,
    val feature_img_port: String,
    val onexone_img: String,
    val topic_desc: String,
    val topic_name: String,
    val topic_slug: String,
    val total_videos: Int
)

