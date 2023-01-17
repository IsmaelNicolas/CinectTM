package com.example.cinecttm

//import info.movito.themoviedbapi.TmdbApi
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.HttpURLConnection
import org.json.JSONObject
import java.net.URL
import java.util.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val random = Random()
        val randomNumber = random.nextInt(738) + 62
        val url = URL("https://api.themoviedb.org/3/movie/"+randomNumber+"?api_key=8c769cd2a5a59a1b3db0651f411c833c")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        val inputStream = connection.inputStream
        val response = inputStream.bufferedReader().use { it.readText() }
        val title = JSONObject(response).getString("title")

        println("Response: "+ title)
    }
}