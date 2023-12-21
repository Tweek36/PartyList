package ru.tweek.partylist

import android.os.Bundle
import android.util.Xml
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import ru.tweek.partylist.databinding.ActivityMainBinding
import androidx.lifecycle.ViewModelProvider
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[HolidaysViewModel::class.java]

        val inputStream: InputStream = resources.openRawResource(R.raw.holidays)
        val parser = Xml.newPullParser()
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        parser.setInput(inputStream, null)

        val holidays = mutableListOf<Holiday>()
        var currentHoliday: Holiday? = null

        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            when (parser.eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "holiday" -> currentHoliday = Holiday("", "", "")
                        "name" -> currentHoliday?.name = parser.nextText()
                        "date" -> currentHoliday?.date = parser.nextText()
                        "description" -> currentHoliday?.description = parser.nextText()
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (parser.name == "holiday") {
                        currentHoliday?.let { holidays.add(it) }
                    }
                }
            }
        }

        viewModel.holidaysList = holidays

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val navController = findNavController(R.id.nav_host_fragment_content_main)
                navController.navigate(R.id.settingsFragment) // Replace with your actual destination ID
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}