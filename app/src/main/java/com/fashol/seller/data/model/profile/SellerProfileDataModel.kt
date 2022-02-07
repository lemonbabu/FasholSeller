package com.fashol.seller.data.model.profile

data class SellerProfileDataModel(
    var error: Boolean, // false
    var message: String, // profile and dashboard data!
    var result: Result,
    var success: Boolean // true
) {
    data class Result(
        var avatar: String, // /defaults/user_avatar.png
        var dashboard: List<Dashboard>,
        var details: Details,
        var email: String, // rezwanhossainsajib@gmail.com
        var email_verified_at: Any, // null
        var id: Int, // 1
        var name: String, // Sajib (Dev)
        var phone: String, // 01797840513
        var profile_dashboard: List<ProfileDashboard>,
        var role: Role,
        var role_id: Int, // 2
        var status: String = "", // active
        var zones: List<Zone>
    ) {
        data class Dashboard(
            var name: String = "", // Todays Sale
            var value: Int // 8
        )

        data class Details(
            var address_line_1: String = "", // Manikdi Bazar, Cantonment
            var address_line_2: String = "", // Manikdi Bazar, Cantonment
            var area: String = "", // Manikdi Bazar, Cantonment
            var city: String = "", // Dhaka
            var created_at: String = "", // 2022-01-31T08:51:23.000000Z
            var id: Int, // 70
            var nid_b_img: String = "", // /defaults/nid_back_part.png
            var nid_f_img: String = "", // /defaults/nid_front_part.png
            var updated_at: String = "", // 2022-01-31T08:58:52.000000Z
            var user_id: Int // 1
        )

        data class ProfileDashboard(
            var name: String = "", // This Month
            var value: String = "" // 30023৳
        )

        data class Role(
            var id: Int, // 2
            var name: String = "" // Developer
        )

        data class Zone(
            var id: Int , // 1
            var name: String = ""// Karwan Bajar (Office)
        )
    }
}