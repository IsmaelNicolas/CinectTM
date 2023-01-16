package com.example.cinecttm

import info.movito.themoviedbapi.TmdbApi
import org.junit.Assert.assertEquals
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val movies = TmdbApi("8c769cd2a5a59a1b3db0651f411c833c").movies
        val movie = movies.getMovie(550, "en")
        print(movie.title)
        assertEquals("Fight Club", movie.title)
    }
}