package de.thkoeln.fherborn.fearlesschange.ui.activities

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener
import com.github.amlcurran.showcaseview.targets.ViewTarget
import de.thkoeln.fherborn.fearlesschange.R


class DashboardActivity : AppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashbaord)

        val viewTarget = ViewTarget(R.id.card_of_the_day, this)
        ShowcaseView.Builder(this)
                .setTarget(viewTarget)
                .setStyle(R.style.CustomShowcaseTheme2)
                .setContentTitle("This is the card of the day!")
                .setShowcaseEventListener(object: SimpleShowcaseEventListener() {
                    override fun onShowcaseViewDidHide(showcaseView: ShowcaseView) {
                        (this@DashboardActivity).onHiddenFirstShowcase()
                    }
                })
                .build()
    }

    fun onHiddenFirstShowcase() {
        val viewTarget = ViewTarget(R.id.main_nav_bar, this)
        ShowcaseView.Builder(this)
                .setTarget(viewTarget)
                .setStyle(R.style.CustomShowcaseTheme2)
                .setContentTitle("This is your Navigation-Bar")
                .build()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard_activity_actions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            startActivity(Intent(this, SearchActivity::class.java))
            true
        }
        R.id.action_settings -> {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
