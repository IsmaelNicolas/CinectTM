package com.example.cinecttm

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.widget.RemoteViews
import java.net.HttpURLConnection
import org.json.JSONObject
import java.net.URL
import java.util.*


//import info.movito.themoviedbapi.TmdbApi


/**
 * Implementation of App Widget functionality.
 */
class CinectAppWidget : AppWidgetProvider() {


    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            val intent = Intent(context, CinectAppWidget::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val views = RemoteViews(context.packageName, R.layout.cinect_app_widget)
            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }



    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (context != null && intent != null && AppWidgetManager.ACTION_APPWIDGET_UPDATE == intent.action) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context, CinectAppWidget::class.java))
            val views = RemoteViews(context.packageName, R.layout.cinect_app_widget)

            // Start the AsyncTask
            val task = GetMovieTask(context, views, appWidgetManager, appWidgetIds)
            task.execute()
        }
    }

    class GetMovieTask(private val context: Context,
                       private val views: RemoteViews,
                       private val appWidgetManager: AppWidgetManager,
                       private val appWidgetIds: IntArray) : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void?): String {
            val random = Random()
            val randomNumber = random.nextInt(738) + 62
            val url = URL("https://api.themoviedb.org/3/movie/"+randomNumber+"?api_key=8c769cd2a5a59a1b3db0651f411c833c")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val inputStream = connection.inputStream
            val response = inputStream.bufferedReader().use { it.readText() }
            return JSONObject(response).getString("title")
        }

        override fun onPostExecute(title: String?) {
            super.onPostExecute(title)
            if (title != null) {
                views.setTextViewText(R.id.RandomMovie, title)
                appWidgetManager.updateAppWidget(appWidgetIds, views)
            }
        }
    }


    override fun onEnabled(context: Context) {

    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,

) {
    //val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object

    val views = RemoteViews(context.packageName, R.layout.cinect_app_widget)
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
