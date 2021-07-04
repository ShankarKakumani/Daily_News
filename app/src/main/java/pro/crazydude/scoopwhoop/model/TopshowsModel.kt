package pro.crazydude.scoopwhoop.model

data class TopShowsModel(
    val `data`: List<TopShowsData>,
    val err: String,
    val next_offset: Int,
    val status: Int,
    val total_shows: Int
)

data class TopShowsData(
    val _id: String,
    val channel: Channel,
    val created_date: String,
    val feature_img_land: String,
    val feature_img_port: String,
    val flag: Int,
    val last_video_pub_epoch_date: String,
    val modified_date: String,
    val onexone_img: String,
    val priority: Int,
    val property_tags: List<PropertyTag>,
    val redis_id: String,
    val tags: List<String>,
    val topic_desc: String,
    val topic_feature_img: String,
    val topic_feature_video: String,
    val topic_name: String,
    val topic_slug: String,
    val topic_type: List<String>,
    val total_videos: Int,
    val updated_by: String,
    val userID: String
)

data class Channel(
    val name: String,
    val slug: String
)

data class PropertyTag(
    val description: String,
    val feature_img: String,
    val order: Int,
    val show: Boolean,
    val tag: String,
    val tags: List<String>,
    val title: String
)