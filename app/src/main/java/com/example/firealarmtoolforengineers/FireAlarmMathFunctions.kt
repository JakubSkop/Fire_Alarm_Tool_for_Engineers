package com.example.firealarmtoolforengineers

fun batterySizeRecommendation(minBatteryCapacity: Double?): Double? {
    if (minBatteryCapacity == null){
        return null
    } else if (0 <= minBatteryCapacity && minBatteryCapacity < 0.78){
        return 1.2
    } else if (0.78 <= minBatteryCapacity && minBatteryCapacity < 1.36){
        return 2.1
    } else if (1.36 <= minBatteryCapacity && minBatteryCapacity < 1.82){
        return 2.8
    } else if (1.82 <= minBatteryCapacity && minBatteryCapacity < 2.08){
        return 3.2
    } else if (2.08 <= minBatteryCapacity && minBatteryCapacity < 2.60){
        return 4.0
    } else if (2.60 <= minBatteryCapacity && minBatteryCapacity < 4.55){
        return 7.0
    } else if (4.55 <= minBatteryCapacity && minBatteryCapacity < 7.80){
        return 12.0
    } else if (7.80 <= minBatteryCapacity && minBatteryCapacity < 11.00){
        return 17.0
    } else if (11.00 <= minBatteryCapacity && minBatteryCapacity < 15.00){
        return 24.0
    } else if (15.00 <= minBatteryCapacity && minBatteryCapacity < 24.00){
        return 38.0
    } else if (24.00 <= minBatteryCapacity && minBatteryCapacity < 42.00){
        return 65.0
    } else if (42.00 <= minBatteryCapacity && minBatteryCapacity < 65.00){
        return 100.0
    } else if (65.00 <= minBatteryCapacity && minBatteryCapacity < 97.00){
        return 150.0
    } else {
        return null
    }

}