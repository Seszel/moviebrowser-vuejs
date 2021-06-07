package com.example.boardgamecollector

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.boardgamecollector.db.MyDBHandler
import com.example.boardgamecollector.objects.Game
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class ShowDetails : AppCompatActivity() {

    lateinit var title_det: TextView
    lateinit var year_det: TextView
    lateinit var description_det: TextView
    lateinit var rank_det: TextView
    lateinit var artist_det: TextView
    lateinit var designer_det: TextView
    lateinit var currentLocation: TextView
    lateinit var spinner: Spinner

    var artists: MutableList<String> = ArrayList()
    var designers: MutableList<String> = ArrayList()
    private var locations:MutableList<String> = java.util.ArrayList()

    val dbHandler = MyDBHandler(this, null, null, 1)

    var id: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_details)


        val dbHandler = MyDBHandler(this, null, null, 1)

        val extras = intent.extras ?: return
        id = extras.getInt("id")

        val game: Game = dbHandler.getDetails(id)

        DownloadImageFromInternet(findViewById(R.id.imageDetails)).execute(game.image)

        title_det = findViewById(R.id.textTitle3)
        title_det.text = game.gameName

        year_det = findViewById(R.id.yearDet)
        year_det.text = "Year of release: " + game.yearPublished.toString()

        description_det = findViewById(R.id.descriptionDet)
        description_det.text = "Description:\n" + game.description

        rank_det = findViewById(R.id.rankDet)
        rank_det.text = "Position in ranking: " + game.rank

        artist_det = findViewById(R.id.artistDet)
        artists = dbHandler.getArtists(id)
        var arts = ""
        if (artists.size == 0) arts = "No artists"
        else {
            for (i in artists.indices) {
                if (!(i == artists.size - 1)) {
                    arts += artists[i] + ", "
                } else {
                    arts += artists[i]
                }
            }
        }
        artist_det.text = "Artists:\n" + arts

        designer_det = findViewById(R.id.designerDet)
        designers = dbHandler.getDesigners(id)
        var des = ""
        if (designers.size == 0) des = "No designers"
        else {
            for (i in designers.indices) {
                if (!(i == designers.size - 1)) {
                    des += designers[i] + ", "
                } else {
                    des += designers[i]
                }
            }
        }
        designer_det.text = "Designers:\n" + des

        spinner = findViewById(R.id.spinner2)

        downloadData()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                Toast.makeText(this@ShowDetails,"Clicked " + locations[position], Toast.LENGTH_SHORT).show()
            }
        }

        currentLocation = findViewById(R.id.currentLocation)
        currentLocation.text = "Current location of the game: " + dbHandler.getLocalizationToGame(id)

    }

    fun historyClick(v: View){
        val i= Intent(this, History::class.java)
        i.putExtra("id", id)
        startActivity(i)
    }

    fun expansionsClick(v: View){
        val i= Intent(this, Expansions::class.java)
        i.putExtra("id", id)
        startActivity(i)
    }

    fun addToLocationClick(v: View){
        val position = spinner.getSelectedItemPosition()
        val message = dbHandler.addLocalizationToGame(id, locations[position])
        downloadData()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private inner class getLocationsFromDB() : AsyncTask<String, Int, String>(){

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val adapter = Localization.SpinnerAdapter(
                this@ShowDetails,
                android.R.layout.simple_spinner_item,
                locations
            )

            // Drop down layout style - list view with radio button
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // attaching data adapter to spinner
            spinner.adapter = adapter

            currentLocation.text = "Current location of the game: " + dbHandler.getLocalizationToGame(id)

        }

        override fun doInBackground(vararg params: String?): String? {
            locations = dbHandler.getLocalizations()

            return "OKEJ"
        }
    }


    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
//        init {
//            Toast.makeText(this@ShowDetails, "Please wait, it may take a few seconds", Toast.LENGTH_SHORT).show()
//        }
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            }
            catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return image
        }
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }

    class SpinnerAdapter(context: Context, resource: Int, list: MutableList<String>)
        : ArrayAdapter<String>(context, resource, list) {

        override fun isEnabled(position: Int): Boolean {
            // select position to disable
            return position != 0
        }

    }

    fun downloadData() {
        val cd = getLocationsFromDB()
        cd.execute()
    }


}