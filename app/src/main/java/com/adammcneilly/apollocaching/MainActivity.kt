package com.adammcneilly.apollocaching

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.adammcneilly.apollocaching.databinding.ActivityMainBinding
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.NormalizedCache
import org.koin.android.ext.android.get
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setupNavigation()
        printCaches()
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)
        binding.toolbar.setupWithNavController(navController)
    }

    /**
     * This is a utility method that prints information in the Logcat of what is in our Http and
     * normalized caches when the activity starts up.
     */
    private fun printCaches() {
        printHttpCache()
        printNormalizedCache()
    }

    private fun printNormalizedCache() {
        val apolloClient: ApolloClient = get()
        val normalizedCacheDump = apolloClient.apolloStore.normalizedCache().dump()
        val formattedDump = NormalizedCache.prettifyDump(normalizedCacheDump)
        Log.d("ApolloNormalizedCache", formattedDump)
    }

    private fun printHttpCache() {
        val cacheDirectory = File(applicationContext.cacheDir, "apolloCache")
        cacheDirectory.listFiles()?.forEach { file ->
            Log.d("ApolloHttpCache", "HttpCacheFile: ${file.name}")
            Log.d("ApolloHttpCache", "HttpCacheFile: ${file.readText()}")
            Log.d("ApolloHttpCache", "-----")
        }
    }
}
