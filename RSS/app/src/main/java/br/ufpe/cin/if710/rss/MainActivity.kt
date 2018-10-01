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
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import android.util.Log

import br.ufpe.cin.if710.rss.db.SQLiteRSSHelper

class MainActivity : Activity() {

    private var conteudoRSS: RecyclerView? = null
    private lateinit var db: SQLiteRSSHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        conteudoRSS = findViewById(R.id.conteudoRSS) as RecyclerView
        db = SQLiteRSSHelper.getInstance(this)

    }

    override fun onResume() {
        super.onResume()
        var pref = PreferenceManager.getDefaultSharedPreferences(this)
        try {
            doAsync {
                val feedXML = getRssFeed(pref.getString("rssfeed", getString(R.string.previous_rssfeed)))
                var items = ParserRSS.parse(feedXML)
                items.forEach { db.insertItem(it) }
                uiThread {
                    conteudoRSS!!.apply {
                        layoutManager = LinearLayoutManager(it)
                        adapter = Adapter(db.items)
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

    override fun onCreateOptionsMenu(options: Menu?): Boolean {
        menuInflater.inflate(R.menu.options, options)
        return true
    }

    override fun onOptionsItemSelected(i: MenuItem?): Boolean {
        return when (i?.itemId) {
            R.id.config -> {
                startActivity(Intent(applicationContext, PreferenceActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(i)
        }
    }
}
