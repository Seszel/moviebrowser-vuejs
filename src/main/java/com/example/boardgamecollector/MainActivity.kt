package com.example.boardgamecollector


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.net.URL
import java.util.*
import android.view.Menu
import android.view.MenuItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.boardgamecollector.db.MyDBHandler
import com.example.boardgamecollector.objects.Game


class MainActivity : AppCompatActivity() {

    private var games:MutableList<Game> = ArrayList()

    val dbHandler = MyDBHandler(this, null, null, 1)

    lateinit var collection_list: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        collection_list = findViewById(R.id.listViewCollection)

        downloadData()

        collection_list.onItemClickListener = AdapterView.OnItemClickListener {
            parent, view, position, id ->
            val intent = Intent(this, ShowDetails::class.java)
            intent.putExtra("id", games.get(position).id)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.sort_az -> {
                if (item.isChecked)
                    item.isChecked = false
                else {
                    item.isChecked = true
                    games.sortBy { it.gameName }
                    collection_list.adapter = MyAdapter(this, games)
                }
                return true
            }
            R.id.sort_year -> {
                if (item.isChecked)
                    item.isChecked = false
                else {
                    item.isChecked = true
                    games.sortBy { it.yearPublished }
                    collection_list.adapter = MyAdapter(this, games)
                }
                return true
            }
            R.id.sort_rank -> {
                if (item.isChecked)
                    item.isChecked = false
                else {
                    item.isChecked = true
                    games.sortBy { it.rank }
                    collection_list.adapter = MyAdapter(this, games)
                }
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onRestart() {
        super.onRestart()
        downloadData()
    }

    fun addGameClick(v: View){
        val i= Intent(this, AddGame::class.java)
        startActivity(i)
    }

    fun BGGClick(v: View){
        val i= Intent(this, BGG::class.java)
        startActivity(i)
    }

    fun locationClick(v: View){
        val i= Intent(this, Localization::class.java)
        startActivity(i)
    }

    private class MyAdapter(
        context: Context,
        games: MutableList<Game>
    ) : BaseAdapter() {

        private lateinit var year: TextView
        private lateinit var titleGame: TextView
        private lateinit var ranking: TextView
        private lateinit var remove: TextView

        private val mContext : Context
        private val mGames: MutableList<Game>

        init {
            mContext = context
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.collection, parent, false)
            year = convertView.findViewById(R.id.yearColection)
            titleGame = convertView.findViewById(R.id.titleColection)
            ranking = convertView.findViewById(R.id.ranking)
            remove = convertView.findViewById(R.id.buttonRemove)
            year.text = "Year of release: " + mGames[position].yearPublished.toString()
            titleGame.text = mGames[position].gameName
            ranking.text = "Position in ranking: " + mGames[position].rank
            (mContext as MainActivity).DownloadImageFromInternet(convertView.findViewById(R.id.imageView)).execute(mGames[position].thumbnail)

            remove.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    Toast.makeText(mContext, "Your game have been removed from collection", Toast.LENGTH_SHORT).show()
                    mContext.dbHandler.deleteGame(mGames[position].gameName.toString())
                    mContext.downloadData()
                }
            })

            return convertView
        }


    }

    private inner class getDataFromDB() : AsyncTask<String, Int, String>(){

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            games.sortBy { it.gameName }
            collection_list.adapter = MyAdapter(this@MainActivity, games)
        }

        override fun doInBackground(vararg params: String?): String? {
            games = dbHandler.getAllData()

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





}


