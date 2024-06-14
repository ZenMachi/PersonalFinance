package com.dokari4.core.util.enums

enum class CategoryType(val description: String) {
    FOOD("Food"),
    SHOPPING("Shopping"),
    SUBSCRIPTION("Subscription"),
    TRANSPORTATION("Transportation"),
    HEALTH("Health"),
    EDUCATION("Education"),
    GIFTS("Gifts");

    override fun toString(): String {
        return description
    }

    companion object {
        fun fromDescription(description: String): CategoryType {
            return entries.first { it.description == description }
        }
    }
}