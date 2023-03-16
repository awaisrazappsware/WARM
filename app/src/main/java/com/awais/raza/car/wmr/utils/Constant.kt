package com.awais.raza.car.wmr.utils

import com.awais.raza.car.wmr.model.PremiumPackageData

object Constant {
    var premiumList = arrayListOf<PremiumPackageData>()

    init {
        premiumList.add(
            PremiumPackageData(
                "Yearly Subscription",
                "$60",
                false,
                "20% off",
            )
        )
        premiumList.add(
            PremiumPackageData(
                "Lifetime Purchase",
                "$120",
                true,
                "50% off",
            )
        )
        premiumList.add(
            PremiumPackageData(
                "Monthly Subscription",
                "$20",
                false,
                "10% off",
            )
        )
    }
}