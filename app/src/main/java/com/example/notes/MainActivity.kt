package com.example.notes

import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.note.R
import com.example.note.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        if (!Prefs(this).isShown()) {
            navController.navigate(R.id.boardFragment)


            navController.addOnDestinationChangedListener { _, destination, _ ->
                val fragments = arrayOf(
                    R.id.navigation_home,
                    R.id.navigation_notifications,
                    R.id.navigation_dashboard,
                )
                if (fragments.contains(destination.id)) {
                    navView.visibility = View.VISIBLE
                } else navView.visibility = View.GONE

                if (destination.id == R.id.boardFragment)
                    supportActionBar?.hide()
                else supportActionBar?.show()
            }
        }
        FirebaseAnalytics.getInstance(applicationContext)
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData: PendingDynamicLinkData? ->
                // Get deep link from result (may be null if no link is found)
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                }

                // Handle the deep link. For example, open the linked
                // content, or apply promotional credit to the user's
                // account.
                // ...
         /*       FirebaseDynamicLinks.getInstance().createDynamicLink().run {
                    domainUriPrefix = "https://mexx.page.link"
                    setSocialMetaTagParameters(
                        DynamicLink.SocialMetaTagParameters.Builder()
                            .setTitle(it.info.title)
                            .setDescription(it.price.toString() + " c")
                            .setImageUrl(it.gallery.first().toUri())
                            .build()
                    )
                    link = ("https://intersport.kg/products?" + it.id).toUri()
                    buildShortDynamicLink()
                }.also {
                    it.addOnSuccessListener { link ->
                        shareLink.startEvent(link.shortLink.toString())
                    }
                }*/

            }
            .addOnFailureListener(this) { e ->
                Log.w("ololo", "getDynamicLink:onFailure", e)
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
