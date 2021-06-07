package com.example.boardgamecollector

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.boardgamecollector.db.MyDBHandler
import com.example.boardgamecollector.objects.Rank
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList
import javax.xml.parsers.DocumentBuilderFactory

class History : AppCompatActivity() {

    private var history: MutableList<Rank> = ArrayList()
    lateinit var historyList: ListView
    lateinit var buttonRefresh: TextView
    var id: Int = 0

    val dbHandler = MyDBHandler(this, null, null, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        buttonRefresh = findViewById(R.id.buttonRefresh)
        historyList = findViewById(R.id.listViewHistory)

        val extras = intent.extras ?: return
        id = extras.getInt("id")

        downloadData("thing?id=" + id.toString() + "&stats=1", "rank.xml")
        refreshData()

    }

    fun refreshClick(v: View){
        downloadData("thing?id=" + id.toString() + "&stats=1", "rank.xml")
        Toast.makeText(this,"HISTORY "+id.toString(),Toast.LENGTH_SHORT).show()
        refreshData()
    }

    private class MyAdapter(
        context: Context,
        rank: MutableList<Rank>
    ) : BaseAdapter() {

        private lateinit var date: TextView
        private lateinit var rank: TextView

        private val mContext : Context
        private val mRank: MutableList<Rank>

        init {
            mContext = context
            mRank = rank
        }

        //responsible for counting hom many rows will have my view list
        override fun getCount(): Int {
            return mRank.size
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.history, parent, false)
            date = convertView.findViewById(R.id.dateHistory)
            rank = convertView.findViewById(R.id.rankHistory)

            date.text = "Date: " + mRank[position].date
            rank.text = "Position in ranking: " + mRank[position].rank.toString()

            return convertView
        }


    }

    fun loadData(namexml : String){

        var message = "Here's your search results"
        val filename = namexml
        val path = filesDir
        val inDir = File(path, "XML")

        if (inDir.exists()) {
            val file = File(inDir, filename)
            if (file.exists()) {
                val xmlDoc: Document =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)
                xmlDoc.documentElement.normalize()
                val items: NodeList = xmlDoc.getElementsByTagName("item")
                for (i in 0 until items.length) {
                    val currentRank =
                        Rank()
                    val itemNode: Node = items.item(i)
                    if (itemNode.nodeType == Node.ELEMENT_NODE) {
                        val elem = itemNode as Element
                        if (elem.nodeName == "item") {
                            currentRank.id = elem.getAttribute("id").toInt()
                        }
                        val children = elem.childNodes
                        for (j in 0 until children.length) {
                            val node = children.item(j)
                            if (node is Element) {
                                when (node.nodeName) {
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
                                                                                                currentRank.rank = 0
                                                                                            } else {
                                                                                                currentRank.rank = (r.getAttribute("value")).toInt()
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
                    val current = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                    val formatted = current.format(formatter)
                    currentRank.date = formatted
                    message = dbHandler.addRanking(currentRank)
                }
            }
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }

    private inner class RankingDownloader(search: String, namexml: String) : AsyncTask<String, Int, String>(){

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

    private inner class refreshDB() : AsyncTask<String, Int, String>(){

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            history.sortByDescending { it.date }
            historyList.adapter = MyAdapter(this@History, history)

        }

        override fun doInBackground(vararg params: String?): String? {
            history = dbHandler.getHistory(id)

            return "OKEJ"
        }
    }

    fun downloadData(search: String, namexml: String) {
        val cd = RankingDownloader(search, namexml)
        cd.execute()
    }

    fun refreshData(){
        val cd = refreshDB()
        cd.execute()
    }
}