package com.example.cinecttm.models

data class UserModel(val name: String, val age:Int, val genders:Array<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserModel

        if (name != other.name) return false
        if (age != other.age) return false
        if (!genders.contentEquals(other.genders)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + age
        result = 31 * result + genders.contentHashCode()
        return result
    }
}
