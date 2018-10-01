package br.ufpe.cin.if710.rss

import android.app.Activity
import android.os.Bundle

class PreferenceActivity: Activity() {
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.preference_list)
    }

    class PreferenceFragment: android.preference.PreferenceFragment() {
        override fun onCreate(bundle: Bundle?) {
            super.onCreate(bundle)
            addPreferencesFromResource(R.xml.preferencias)
        }
    }
}