package com.example.cinecttm.models

data class UserModel(val email: String,
                     val joined: String,
                     val movieGenres: List<String>,
                     val password: String,
                     val username: String) {


    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + joined.hashCode()
        result = 31 * result + movieGenres.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + username.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserModel

        if (email != other.email) return false
        if (joined != other.joined) return false
        if (movieGenres != other.movieGenres) return false
        if (password != other.password) return false
        if (username != other.username) return false

        return true
    }


}
