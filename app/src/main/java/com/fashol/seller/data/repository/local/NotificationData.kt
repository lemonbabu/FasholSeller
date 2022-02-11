package com.fashol.seller.data.repository.local

import com.fashol.seller.data.model.notification.NotificationDataModel

object NotificationData {
    var flag = false
    lateinit var data : List<NotificationDataModel.Result>
}