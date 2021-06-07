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
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.boardgamecollector.db.MyDBHandler
import com.example.boardgamecollector.objects.ArtistDesigner
import com.example.boardgamecollector.objects.Expansion
import com.example.boardgamecollector.objects.Game


class AddGame : AppCompatActivity() {

    private var games:MutableList<Game> = ArrayList()
    lateinit var search_game: TextView

    lateinit var gameList: ListView
    lateinit var inputSearchName: EditText

    val dbHandler = MyDBHandler(this, null, null, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)

        search_game = findViewById(R.id.buttonSearchGame)
        gameList = findViewById(R.id.listViewGames)
        inputSearchName = findViewById(R.id.inputSearchName)

        gameList.adapter = MyAdapter(this, games)

    }

    fun findGameClick(v: View) {
        val value: String = inputSearchName.getText().toString()
        games.clear()
        downloadData("search?query="+ value + "&type=boardgame", "addgames.xml")
    }

    fun loadData(namexml : String){

        var message = "Here's your search results"
        val filename = namexml
        val path = filesDir
        val inDir = File(path, "XML")

        if (namexml == "addgames.xml") {
            if (inDir.exists()) {
                val file = File(inDir, filename)
                if (file.exists()) {
                    val xmlDoc: Document =
                        DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)
                    xmlDoc.documentElement.normalize()
                    val items: NodeList = xmlDoc.getElementsByTagName("item")
                    for (i in 0 until items.length) {
                        val game = Game()
                        val itemNode: Node = items.item(i)
                        if (itemNode.nodeType == Node.ELEMENT_NODE) {
                            val elem = itemNode as Element
                            if (elem.nodeName == "item") {
                                game.id = elem.getAttribute("id").toInt()
                            }
                            val children = elem.childNodes
                            for (j in 0 until children.length) {
                                val node = children.item(j)
                                if (node is Element) {
                                    when (node.nodeName) {
                                        "name" -> game.gameName = node.getAttribute("value")
                                        "yearpublished" -> game.yearPublished =
                                            node.getAttribute("value").toInt()
                                    }
                                }
                            }
                        }
                        games.add(game)
                    }
                }
            }
        } else {
            if (inDir.exists()) {
                val file = File(inDir, filename)
                if (file.exists()) {
                    val xmlDoc: Document =
                        DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)
                    xmlDoc.documentElement.normalize()
                    val items: NodeList = xmlDoc.getElementsByTagName("item")
                    for (i in 0 until items.length) {
                        val game = Game()
                        val artist =
                            ArtistDesigner()
                        val designer =
                            ArtistDesigner()
                        val expansion = Expansion()
                        val itemNode: Node = items.item(i)
                        if (itemNode.nodeType == Node.ELEMENT_NODE) {
                            val elem = itemNode as Element
                            if (elem.nodeName == "item") {
                                game.id = elem.getAttribute("id").toInt()
                            }
                            val children = elem.childNodes
                            for (j in 0 until children.length) {
                                val node = children.item(j)
                                if (node is Element) {
                                    when (node.nodeName) {
                                        "name" -> {
                                            if (node.getAttribute("type") == "primary") {
                                                game.gameName = node.getAttribute("value")
                                            }
                                        }
                                        "yearpublished" -> game.yearPublished =
                                                node.getAttribute("value").toInt()
                                        "thumbnail" -> game.thumbnail = node.textContent
                                        "image" -> game.image = node.textContent
                                        "description" -> game.description = node.textContent
                                        "link" -> {
                                            if (node.getAttribute("type") == "boardgameartist") {
                                                artist.name = ((node.getAttribute("value")))
                                                artist.id = ((node.getAttribute("id"))).toInt()
                                                dbHandler.addArtist(artist, game.id)
                                            }
                                            else if (node.getAttribute("type") == "boardgamedesigner") {
                                                designer.name = ((node.getAttribute("value")))
                                                designer.id = ((node.getAttribute("id"))).toInt()
                                                dbHandler.addDesigner(designer, game.id)
                                            }
                                            else if (node.getAttribute("type") == "boardgameexpansion"){
                                                expansion.name = ((node.getAttribute("value")))
                                                expansion.id = ((node.getAttribute("id"))).toInt()
                                                dbHandler.addExpansion(expansion, game.id)
                                            }
                                        }
                                        "statistics" -> {
                                            Log.d("statistics1", node.nodeName)
                                            val chil = node.childNodes
                                            for (k in 0 until chil.length) {
                                                val chi = chil.item(k)
//                                                Log.d("ratings2", chi.nodeName)
                                                if (chi is Element) {
                                                    when (chi.nodeName) {
                                                        "ratings" -> {
                                                            val ch = chi.childNodes
                                                            for (l in 0 until ch.length) {
                                                                val rank = ch.item(l)
//                                                                Log.d("ranks", rank.nodeName)
                                                                if (rank is Element) {
                                                                    when (rank.nodeName) {
                                                                        "ranks" -> {
//                                                                            Log.d("rank", rank.nodeName)
                                                                            val ra = rank.childNodes
                                                                            for (m in 0 until ra.length) {
                                                                                val r = ra.item(m)
//                                                                                Log.d("rank4", r.nodeName)
                                                                                if (r is Element) {
                                                                                    when (r.nodeName) {
                                                                                        "rank" -> {
//                                                                                            Log.d("NAZWA WEZLA", r.nodeName)
                                                                                            if (r.getAttribute("name") == "boardgame") {
                                                                                                if ((r.getAttribute("value") == "Not Ranked")) {
                                                                                                    game.rank = 0
                                                                                                } else {
                                                                                                    game.rank = (r.getAttribute("value")).toInt()
                                                                                                }
//                                                                                                Log.d("wartosc", r.getAttribute("value"))}
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        message = dbHandler.addGame(game)
                    }
                }
            }

        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private class MyAdapter(
        context: Context,
        games: MutableList<Game>
    ) : BaseAdapter() {

        private lateinit var idGame: TextView
        private lateinit var titleGame: TextView
        private lateinit var yearGame: TextView
        lateinit var add_game_db: TextView


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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row, parent, false)
            idGame = convertView.findViewById(R.id.idGame)
            titleGame = convertView.findViewById(R.id.titleGame)
            yearGame = convertView.findViewById(R.id.yearGame)
            add_game_db = convertView.findViewById(R.id.buttonAdd)
            idGame.text = "Id: " + mGames[position].id.toString()
            titleGame.text = mGames[position].gameName
            yearGame.text = "Year published: " + mGames[position].yearPublished.toString()
//            (mContext as AddGame).DownloadImageFromInternet(convertView.findViewById(R.id.imageViewAdd)).execute(mGames[position].thumbnail)

            add_game_db.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    
                    (mContext as AddGame).downloadData("thing?id=" +
                            mGames.get(position).id.toString() + "&stats=1","details.xml")
                }
            })
            return convertView
        }
    }

    private inner class SearchDownloader(search: String, namexml: String) : AsyncTask<String, Int, String>(){

        val search = search
        val namexml = namexml

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            loadData(namexml)
        }

        override fun doInBackground(vararg params: String?): String {
            try {
                val url = URL("https://www.boardgamegeek.com/xmlapi2/" +  search)
                val connection = url.openConnection()
                connection.connect()
                val lenghtOfFile = connection.contentLength
                val isStream = url.openStream()
                val testDirectory = File("$filesDir/XML")
                if(!testDirectory.exists())
                    testDirectory.mkdir()
                val fos = FileOutputStream("$testDirectory/" + namexml)
                val data = ByteArray(1024)
                var count = 0
                var total:Long = 0
                var progress = 0
                count = isStream.read(data)
                while (count != -1){
                    total += count.toLong()
                    val progress_temp = total.toInt() * 100 / lenghtOfFile
                    if(progress_temp % 10 == 0 && progress != progress_temp){
                        progress = progress_temp
                    }
                    fos.write(data, 0, count)
                    count = isStream.read(data)
                }
                isStream.close()
                fos.close()

            } catch (e: MalformedURLException){
                return "Malformed URL"
            } catch (e: FileNotFoundException){
                return "File not found"
            } catch (e: IOException){
                return "IO exception"
            }
            return "Success"
        }
    }

    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
//        init {
//            Toast.makeText(this@AddGame, "Please wait, it may take a few seconds", Toast.LENGTH_SHORT).show()
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


    fun downloadData(search: String, namexml: String) {
        val cd = SearchDownloader(search, namexml)
        cd.execute()
    }



}