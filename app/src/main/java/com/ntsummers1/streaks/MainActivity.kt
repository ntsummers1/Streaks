package com.ntsummers1.streaks

import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.CollapsingToolbarLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_todo, R.id.navigation_calendar, R.id.navigation_profile
            )
        )
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setStatusBarColor()
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.nav_host_fragment)
        .navigateUp()

    private fun setStatusBarColor() {
        val mode = applicationContext?.resources?.configuration?.uiMode
            ?.and(Configuration.UI_MODE_NIGHT_MASK)
        when (mode) {
            Configuration.UI_MODE_NIGHT_YES -> {
                window?.setStatusBarColor(
                    ContextCompat.getColor(applicationContext, R.color.color_background_night))
            }
            else -> {
                window?.setStatusBarColor(
                    ContextCompat.getColor(applicationContext, R.color.color_background))
            }
        }
    }

    fun setupLowerLevelFragment() {
        supportActionBar?.show()
        val mode = applicationContext?.resources?.configuration
            ?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
        when (mode) {
            Configuration.UI_MODE_NIGHT_YES -> {
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat
                    .getColor(applicationContext, R.color.color_background_night)
                ))
            }
            else -> {
                supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat
                    .getColor(applicationContext, R.color.color_background)
                ))
            }
        }
    }

    fun setupHighLevelFragment(collapsingToolbar: CollapsingToolbarLayout,
                               titleString: String) {
        supportActionBar?.hide()
        val fragmentCollapsingToolbar: CollapsingToolbarLayout = collapsingToolbar
        fragmentCollapsingToolbar.title = titleString
//        fragmentCollapsingToolbar.apply {
//            setCollapsedTitleTypeface(TyperRoboto.ROBOTO_BOLD)
//            setExpandedTitleTypeface(TyperRoboto.ROBOTO_BOLD)
//        }
    }
}
