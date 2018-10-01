package br.ufpe.cin.if710.rss

import android.app.IntentService
import android.content.Intent
import br.ufpe.cin.if710.rss.db.SQLiteRSSHelper
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class FeedService(): IntentService("FeedService") {
    override fun onHandleIntent(intent: Intent?) {
        val db = SQLiteRSSHelper.getInstance(this)
        var feedXML = ""
        var input: InputStream? = null
        try {
            val url = URL(intent!!.data.toString())
            val conn = url.openConnection() as HttpURLConnection
            input = conn.inputStream
            val out = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var count = input.read(buffer)
            while (count != -1) {
                out.write(buffer, 0, count)
                count = input.read(buffer)
            }
            val response = out.toByteArray()
            feedXML = String(response, charset("UTF-8"))
        } finally {
            input?.close()
        }
        var freshContent = false
        ParserRSS.parse(feedXML).forEach{
            if (db.insertItem(it) != -1L) {
                freshContent = true
            }
        }
        if (freshContent){
            sendBroadcast(Intent("br.ufpe.cin.if710.rss.fresh_content"))
        }
        sendBroadcast(Intent("finished"))
    }
}