package com.example.cinecttm.controllers

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.cinecttm.models.UserModel
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class ConRetrofit {

    interface Api{
        @POST("api/users/user")
        fun getUserData(@Body request: JsonObject): Call<UserModel>

        @POST("api/users/add")
        fun addUserData(@Body request: JsonObject): Call<UserModel>

        @POST("api/users/auth")
        fun authUserData(@Body request: JsonObject): Call<UserModel>

    }

    companion object {

        private val APIURL:String = "https://10.240.3.175:8000/"

        private fun setCertificate(): OkHttpClient{
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            val sslContext = SSLContext.getInstance("SSL").apply {
                init(null, trustAllCerts, java.security.SecureRandom())
            }

            return OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true }
                .build()

        }

        fun getUserData(context: Context, email:String, callback: (user: UserModel?) -> Unit) {

            val okHttpClient = setCertificate()

            val retrofit = Retrofit.Builder()
                .baseUrl(APIURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            val api = retrofit.create(Api::class.java)

            val requestBody = JsonObject().apply {
                addProperty("email", email)
            }

            val  call: Call<UserModel> = api.getUserData(requestBody)

            call.enqueue(object : Callback<UserModel> {
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        Log.i("user", user.toString())
                        callback(user)
                    } else {
                        callback(null)
                        Toast.makeText(context, "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    callback(null)
                    Toast.makeText(context, "Error al conectarse al servidor", Toast.LENGTH_SHORT).show()
                }
            })
        }

        fun addUser(context: Context,user: UserModel, callback: (user: UserModel?) -> Unit){

            val okHttpClient = setCertificate()

            val retrofit = Retrofit.Builder()
                .baseUrl(APIURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            val api = retrofit.create(Api::class.java)

            val requestBody = JsonObject().apply {
                addProperty("email", user.email)
                addProperty("name",user.username)
                addProperty("password",user.password)
            }

            val  call: Call<UserModel> = api.addUserData(requestBody)

            call.enqueue(object : Callback<UserModel> {
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    if (response.isSuccessful) {
                        val user1 = response.body()
                        Log.i("status", response.code().toString())
                        callback(user1)
                    } else {
                        callback(null)
                        Toast.makeText(context, "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    callback(null)
                    Toast.makeText(context, "Error al conectarse al servidor", Toast.LENGTH_SHORT).show()
                }
            })

        }

        fun authUser(context: Context,user: UserModel, callback: (user: UserModel?) -> Unit){
            val okHttpClient = setCertificate()

            val retrofit = Retrofit.Builder()
                .baseUrl(APIURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            val api = retrofit.create(Api::class.java)

            val requestBody = JsonObject().apply {
                addProperty("email", user.email)
                addProperty("password",user.password)
            }

            val  call: Call<UserModel> = api.authUserData(requestBody)

            call.enqueue(object : Callback<UserModel> {
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    if (response.isSuccessful) {
                        val user1 = response.body()
                        Log.i("status", response.code().toString())
                        callback(user1)
                    } else {
                        callback(null)
                        Toast.makeText(context, "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    callback(null)
                    Toast.makeText(context, "Error al conectarse al servidor", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }
}