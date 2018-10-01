package br.ufpe.cin.if710.rss

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets.UTF_8

class MainActivity : Activity() {

    private var conteudoRSS: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        conteudoRSS = findViewById(R.id.conteudoRSS) as RecyclerView
    }

    override fun onStart() {
        super.onStart()
        try {
            doAsync {
                val feedXML = getRssFeed(getString(R.string.rssfeed))
                uiThread {
                    conteudoRSS!!.apply {
                        layoutManager = LinearLayoutManager(it)
                        adapter = Adapter(ParserRSS.parse(feedXML))
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun getRssFeed(feed: String): String {
        var input: InputStream? = null
        var rss: String
        try {
            val url = URL(feed)
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
            rss = String(response, UTF_8)
        } finally {
            input?.close()
        }
        return rss
    }
}
