package de.thkoeln.fherborn.fearlesschange.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.db.CardDatabase
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_dashbaord.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashbaord)

        setListeners()

        CardDatabase.getInstance(baseContext)?.cardDao()?.getCards()?.subscribeBy (
                onNext = {
                    println("Next" + it)
                    // Aus der alten Version, ähnliche Vearbeitung hier möglich?
//                    fragmentManager?.beginTransaction()
//                            ?.replace(R.id.overview_container, CardGridDetailFragment.newInstance(ArrayList(it), "OVERVIEW"), javaClass.simpleName)
//                            ?.commit()},
                },
                onError =  { it.printStackTrace() },
                onComplete = { println("Done!") }
        )
    }

    private fun setListeners() {
        fab_favorites.setOnClickListener{ startActivity(Intent(this, FavoritesActivity::class.java))}
        fab_more.setOnClickListener{ startActivity(Intent(this, MoreActivity::class.java))}
        fab_overview.setOnClickListener{ startActivity(Intent(this, OverviewActivity::class.java))}
    }
}
