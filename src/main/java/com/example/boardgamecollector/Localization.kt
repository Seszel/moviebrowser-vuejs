package com.example.boardgamecollector

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.boardgamecollector.db.MyDBHandler
import com.example.boardgamecollector.objects.Game
import java.net.URL
import java.util.*


class Localization : AppCompatActivity() {

    private var locations:MutableList<String> = ArrayList()
    lateinit var spinner: Spinner

    private var games:MutableList<Game> = ArrayList()

    val dbHandler = MyDBHandler(this, null, null, 1)

    lateinit var inputLocation: EditText

    lateinit var add_location: TextView
    lateinit var location_list: ListView
    lateinit var location_games_list: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_localization)

        inputLocation = findViewById(R.id.inputLocation)

        spinner = findViewById(R.id.spinner)

        location_games_list = findViewById(R.id.listViewLocationGames)

        downloadData()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                Toast.makeText(this@Localization,"Clicked" + locations[position],Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun addLocationClick(v: View){
        var message: String
        val value: String = inputLocation.getText().toString()
        message = dbHandler.addLocalization(value)
        downloadData()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun deleteLocationClick(v: View){
        val position = spinner.getSelectedItemPosition()
        Toast.makeText(this, "DELETED: "+locations[position], Toast.LENGTH_SHORT).show()
        dbHandler.deleteLocalization(locations[position])
        downloadData()
    }

    fun showLocationClick(v: View){
        val position = spinner.getSelectedItemPosition()
        Toast.makeText(this, "SHOW"+locations[position], Toast.LENGTH_SHORT).show()
        games = dbHandler.getAllGamesFromLocalization(locations[position])
        location_games_list.adapter = MyAdapter(this@Localization, games)
    }

    private class MyAdapter(
            context: Context,
            games: MutableList<Game>
    ) : BaseAdapter() {

        private lateinit var year: TextView
        private lateinit var titleGame: TextView
        private lateinit var ranking: TextView
        private lateinit var remove: TextView
        private lateinit var image: ImageView

        private val mContext : Context
//        private val mLocations: MutableList<String>
        private val mGames: MutableList<Game>

        init {
            mContext = context
//            mLocations = locations
            mGames = games
        }

        //responsible for counting hom many rows will have my view list
        override fun getCount(): Int {
            return mGames.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return position
        }

        //responsible for rendering out each row
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var convertView = convertView
            convertView = LayoutInflater.from(mContext).inflate(R.layout.location, parent, false)
            year = convertView.findViewById(R.id.yearLocation)
            titleGame = convertView.findViewById(R.id.titleLocation)
            ranking = convertView.findViewById(R.id.rankingLocation)
            image = convertView.findViewById(R.id.imageViewLocation)
            year.text = "Year of release: " + mGames[position].yearPublished.toString()
            titleGame.text = mGames[position].gameName
            ranking.text = "Position in ranking: " + mGames[position].rank
            (mContext as Localization).DownloadImageFromInternet(convertView.findViewById(R.id.imageViewLocation)).execute(mGames[position].thumbnail)

            return convertView
        }
    }



    private inner class getDataFromDB() : AsyncTask<String, Int, String>(){

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
//            location_games_list.adapter = MyAdapter(this@Localization, games)
            val adapter = SpinnerAdapter(this@Localization, android.R.layout.simple_spinner_item, locations)

            // Drop down layout style - list view with radio button
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // attaching data adapter to spinner
            spinner.adapter = adapter

        }

        override fun doInBackground(vararg params: String?): String? {
//            games = dbHandler.getAllData()
            locations = dbHandler.getLocalizations()

            return "OKEJ"
        }
    }

    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        //        init {
//            Toast.makeText(this@MainActivity, "Please wait, it may take a few seconds", Toast.LENGTH_SHORT).show()
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

    fun downloadData() {
        val cd = getDataFromDB()
        cd.execute()
    }

    class SpinnerAdapter(context: Context, resource: Int, list: MutableList<String>)
        : ArrayAdapter<String>(context, resource, list) {

        override fun isEnabled(position: Int): Boolean {
            // select position to disable
            return position != 0
        }

    }


}