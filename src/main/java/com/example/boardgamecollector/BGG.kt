package com.example.boardgamecollector

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.boardgamecollector.objects.Game
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.*
import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList
import javax.xml.parsers.DocumentBuilderFactory

class BGG : AppCompatActivity() {

    private var games:MutableList<Game> = ArrayList()
    lateinit var search_user: TextView

    lateinit var gameList: ListView
    lateinit var inputSearchUser: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_g_g)

        search_user = findViewById(R.id.buttonSearchUser)
        gameList = findViewById(R.id.listViewUserGames)
        inputSearchUser = findViewById(R.id.inputSearchUser)

        gameList.adapter = MyAdapter(this, games)
    }

    fun findUserClick(v: View) {
        val value: String = inputSearchUser.getText().toString()
        games.clear()
        downloadData("collection?username="+ value + "&stats=1", "user.xml")
    }

    fun loadData(namexml : String){

        var message = "Here's your search results"
        val filename = namexml
        val path = filesDir
        val inDir = File(path, "XML")

        if (namexml == "user.xml") {
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
                                game.id = elem.getAttribute("objectid").toInt()
                            }
                            val children = elem.childNodes
                            for (j in 0 until children.length) {
                                val node = children.item(j)
                                if (node is Element) {
                                    when (node.nodeName) {
                                        "name" -> game.gameName = node.textContent
                                        "yearpublished" -> game.yearPublished = node.textContent.toInt()
                                        "image" -> game.image = node.textContent
                                        "thumbnail" -> game.thumbnail = node.textContent
                                    }
                                }
                            }
                        }
                        games.add(game)
                    }
                }
            }
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private inner class UserDownloader(search: String, namexml: String) : AsyncTask<String, Int, String>(){

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


    private class MyAdapter(
            context: Context,
            games: MutableList<Game>
    ) : BaseAdapter() {

        private lateinit var year: TextView
        private lateinit var titleGame: TextView
        private lateinit var ranking: TextView

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.users_collection, parent, false)
            year = convertView.findViewById(R.id.yearColectionBGG)
            titleGame = convertView.findViewById(R.id.titleColectionBGG)
//            ranking = convertView.findViewById(R.id.rankingBGG)
            year.text = "Year of release: " + mGames[position].yearPublished.toString()
            titleGame.text = mGames[position].gameName
//            ranking.text = "Position in ranking: " + mGames[position].rank
            (mContext as BGG).DownloadImageFromInternet(convertView.findViewById(R.id.imageViewBGG)).execute(mGames[position].thumbnail)

            return convertView
        }

    }

    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        //        init {
//            Toast.makeText(this@BGG, "Please wait, it may take a few seconds", Toast.LENGTH_SHORT).show()
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
        val cd = UserDownloader(search, namexml)
        cd.execute()
    }


}