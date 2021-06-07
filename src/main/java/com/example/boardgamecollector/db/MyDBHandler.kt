package com.example.boardgamecollector.db
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.content.ContentValues
import android.util.Log
import com.example.boardgamecollector.objects.ArtistDesigner
import com.example.boardgamecollector.objects.Expansion
import com.example.boardgamecollector.objects.Game
import com.example.boardgamecollector.objects.Rank

class MyDBHandler(context: Context, name: String?,
                  factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context,
        DATABASE_NAME, factory, DATABASE_VERSION){

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "gameDB.db"

        val TABLE_GAMES = "Games"
        val COLUMN_ID = "game_id"
        val COLUMN_GAMENAME = "game_name"
        val COLUMN_THUMBNAIL = "thumbnail"
        val COLUMN_IMAGE = "image"
        val COLUMN_DESCRIPTION = "description"
        val COLUMN_YEAR = "year_published"
        val COLUMN_ARTIST = "artist_names"
        val COLUMN_RANK = "rank"

        val TABLE_LOCATIONS = "Locations"
        val COLUMN_LOCATION = "location"

        val TABLE_HISTORY = "History"
        val COLUMN_DATE = "date"
        val COLUMN_GAME_ID = "game_id"
        val COLUMN_ID_UNIQUE = "id"
        val COLUMN_RANK_CUR = "current_ranking"

        val TABLE_ARTISTS = "Artists"
        val COLUMN_ART_ID = "artist_id"
        val COLUMN_ART_NAME = "artist_name"

        val TABLE_DESIGNERS = "Designers"
        val COLUMN_DES_ID = "designer_id"
        val COLUMN_DES_NAME = "designer_name"

        val TABLE_GAMES_ARTISTS = "GamesArtists"
        val COLUMN_GA_ID = "games_artists_id"
        val COLUMN_GA_ID_ART = "artist_id"
        val COLUMN_GA_ID_GAME = "game_id"

        val TABLE_GAMES_DESIGNERS = "GamesDesigners"
        val COLUMN_GD_ID = "games_designers_id"
        val COLUMN_GD_ID_DES = "designer_id"
        val COLUMN_GD_ID_GAME = "game_id"

        val TABLE_GAMES_LOCATION = "GamesLocation"
        val COLUMN_GL_ID = "games_location_id"
        val COLUMN_GL_ID_GAME = "game_id"
        val COLUMN_GL_LOCATION = "location"

        val TABLE_GAMES_EXPANSIONS = "GamesExpansions"
        val COLUMN_GE_ID = "games_expansions_id"
        val COLUMN_GE_ID_GAME = "game_id"
        val COLUMN_GE_EXP_NAME = "expansion_name"
        val COLUMN_GE_EXP_ID = "expansion_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_GAMES_TABLE = ("CREATE TABLE " +
                TABLE_GAMES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_GAMENAME + " TEXT," +
                COLUMN_THUMBNAIL + " TEXT," +
                COLUMN_IMAGE + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_YEAR + " INTEGER," +
                COLUMN_ARTIST + " TEXT," +
                COLUMN_RANK + " INTEGER"
                + ")")
        db.execSQL(CREATE_GAMES_TABLE)

        val CREATE_LOCATION_TABLE = ("CREATE TABLE " +
                TABLE_LOCATIONS + "("
                + COLUMN_LOCATION + " TEXT PRIMARY KEY"
                + ")")
        db.execSQL(CREATE_LOCATION_TABLE)

        val CREATE_HISTORY_TABLE = ("CREATE TABLE " +
                TABLE_HISTORY + "("
                + COLUMN_ID_UNIQUE + " INTEGER PRIMARY KEY," +
                COLUMN_GAME_ID + " INTEGER," +
                COLUMN_RANK_CUR + " INTEGER," +
                COLUMN_DATE + " DATE"
                + ")")
        db.execSQL(CREATE_HISTORY_TABLE)

        val CREATE_ARTISTS_TABLE = ("CREATE TABLE " +
                TABLE_ARTISTS + "("
                + COLUMN_ART_ID + " INTEGER PRIMARY KEY," +
                COLUMN_ART_NAME + " TEXT"
                + ")")
        db.execSQL(CREATE_ARTISTS_TABLE)

        val CREATE_DESIGNERS_TABLE = ("CREATE TABLE " +
                TABLE_DESIGNERS + "("
                + COLUMN_DES_ID + " INTEGER PRIMARY KEY," +
                COLUMN_DES_NAME + " TEXT"
                + ")")
        db.execSQL(CREATE_DESIGNERS_TABLE)

        val CREATE_GAMES_ARTISTS_TABLE = ("CREATE TABLE " +
                TABLE_GAMES_ARTISTS + "("
                + COLUMN_GA_ID + " INTEGER PRIMARY KEY," +
                COLUMN_GA_ID_ART + " INTEGER," +
                COLUMN_GA_ID_GAME + " INTEGER"
                + ")")
        db.execSQL(CREATE_GAMES_ARTISTS_TABLE)

        val CREATE_GAMES_DESIGNERS_TABLE = ("CREATE TABLE " +
                TABLE_GAMES_DESIGNERS + "("
                + COLUMN_GD_ID + " INTEGER PRIMARY KEY," +
                COLUMN_GD_ID_DES + " INTEGER," +
                COLUMN_GD_ID_GAME + " INTEGER"
                + ")")
        db.execSQL(CREATE_GAMES_DESIGNERS_TABLE)

        val CREATE_GAMES_LOCATION_TABLE = ("CREATE TABLE " +
                TABLE_GAMES_LOCATION + "("
                + COLUMN_GL_ID + " INTEGER PRIMARY KEY," +
                COLUMN_GL_LOCATION + " TEXT," +
                COLUMN_GL_ID_GAME + " INTEGER"
                + ")")
        db.execSQL(CREATE_GAMES_LOCATION_TABLE)

        val CREATE_GAMES_EXPANSION_TABLE = ("CREATE TABLE " +
                TABLE_GAMES_EXPANSIONS + "("
                + COLUMN_GE_ID + " INTEGER PRIMARY KEY," +
                COLUMN_GE_EXP_NAME + " TEXT," +
                COLUMN_GE_EXP_ID + " INTEGER," +
                COLUMN_GE_ID_GAME + " INTEGER"
                + ")")
        db.execSQL(CREATE_GAMES_EXPANSION_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTISTS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DESIGNERS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES_ARTISTS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES_DESIGNERS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES_LOCATION)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES_EXPANSIONS)
        onCreate(db)
    }

    //////////////////GAMES TABLE//////////////////////

    fun addGame(game: Game): String{
        var message = "This game is already in your collection"
        if (!findProduct(game.id)){
            val values = ContentValues()
            values.put(COLUMN_GAMENAME, game.gameName)
            values.put(COLUMN_ID, game.id)
            values.put(COLUMN_THUMBNAIL, game.thumbnail)
            values.put(COLUMN_IMAGE, game.image)
            values.put(COLUMN_DESCRIPTION, game.description)
            values.put(COLUMN_YEAR, game.yearPublished)
            values.put(COLUMN_ARTIST, game.artistNames)
            values.put(COLUMN_RANK, game.rank)
            val db = this.writableDatabase
            db.insert(TABLE_GAMES,null, values)
            db.close()
            message = "Your game have been added to collection"
        }
        return message
    }

    fun getAllData(): MutableList<Game> {

        var games: MutableList<Game> = ArrayList()
        val query = "SELECT * FROM $TABLE_GAMES"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val game = Game()
            game.id= Integer.parseInt(cursor.getString(0))
            game.yearPublished = Integer.parseInt(cursor.getString(5))
            game.gameName = cursor.getString(1)
            game.rank = cursor.getInt(7)
            game.thumbnail = cursor.getString(2)
            game.image = cursor.getString(3)
            game.thumbnail = cursor.getString(2)

            games.add(game)
        }

        return games
    }

    fun findProduct(gameID: Int): Boolean {
        var inDB = false
        val query = "SELECT * FROM $TABLE_GAMES WHERE $COLUMN_ID == $gameID"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()){
            inDB = true
            cursor.close()
        }
        db.close()
        return inDB
    }

    fun getDetails(gameID: Int): Game {
        val query = "SELECT * FROM $TABLE_GAMES WHERE $COLUMN_ID == $gameID"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        var game = Game()

        if (cursor.moveToFirst()){
            game.id = gameID
            game.gameName = cursor.getString(1)
            game.thumbnail = cursor.getString(2)
            game.image = cursor.getString(3)
            game.description = cursor.getString(4)
            game.yearPublished = cursor.getInt(5)
            game.artistNames = cursor.getString(6)
            game.rank = cursor.getInt(7)
            cursor.close()
        }
        db.close()
        return game
    }

    fun deleteGame(gameName: String):Boolean{
        var result = false
        val query = "SELECT * FROM $TABLE_GAMES WHERE $COLUMN_GAMENAME LIKE \"$gameName\""

        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()){
            val id = cursor.getInt(0)
            db.delete(TABLE_GAMES, COLUMN_ID + " = ?", arrayOf(id.toString()))
            cursor.close()
            result = true
        }
        db.close()
        return result
    }

    //////////////////EXPANSIONS TABLE//////////////////////

    fun addExpansion(expansion: Expansion, gameID: Int) {

        val values = ContentValues()
        values.put(COLUMN_GE_EXP_NAME, expansion.name)
        values.put(COLUMN_GE_ID_GAME, gameID)
        values.put(COLUMN_GE_EXP_ID, expansion.id)
        val db = this.writableDatabase
        db.insert(TABLE_GAMES_EXPANSIONS,null, values)
        db.close()

    }

    fun getExpansions(gameID: Int): MutableList<String> {

        val expansions: MutableList<String> = ArrayList()
        val query = "SELECT * FROM $TABLE_GAMES_EXPANSIONS WHERE $COLUMN_GE_ID_GAME == $gameID"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            expansions.add(cursor.getString(1))
            Log.d("exp", cursor.getString(1))
        }
        return expansions
    }

    //////////////////LOCALIZATION TABLE//////////////////////

    fun addLocalization(localizationName: String): String{
        var message = "This localization already exists"
        if (!findLocalization(localizationName)){
            val values = ContentValues()
            values.put(COLUMN_LOCATION, localizationName)
            val db = this.writableDatabase
            db.insert(TABLE_LOCATIONS,null, values)
            db.close()
            message = "Your localization have been added"
        }
        return message
    }

    fun findLocalization(localizationName: String): Boolean {
        var inDB = false
        val query = "SELECT * FROM $TABLE_LOCATIONS WHERE $COLUMN_LOCATION LIKE \"$localizationName\""
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()){
            inDB = true
            cursor.close()
        }
        db.close()
        return inDB
    }

    fun getLocalizations(): MutableList<String> {

        var locations: MutableList<String> = ArrayList()
        locations.add("-- Choose location --")
        val query = "SELECT * FROM $TABLE_LOCATIONS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            locations.add(cursor.getString(0))
        }

        return locations
    }

    fun deleteLocalization(localizationName: String):Boolean{
        var result = false
        val query = "SELECT * FROM $TABLE_LOCATIONS WHERE $COLUMN_LOCATION LIKE \"$localizationName\""

        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()){
            db.delete(TABLE_LOCATIONS, COLUMN_LOCATION + " LIKE ?", arrayOf(localizationName))
            cursor.close()
            result = true
        }
        db.close()
        return result
    }

    //////////////////LOCALIZATION_GAMES TABLE//////////////////////


    fun addLocalizationToGame(gameID: Int, location: String): String{
        var message = "Your localization have been changed to $location"
        if (location == "-- Choose location --"){
            message = "Choose location!"
        } else {
            if (findLocalizationToGame(gameID)) {
                val values = ContentValues()
                values.put(COLUMN_GL_LOCATION, location)
                val db = this.writableDatabase
                db.update(TABLE_GAMES_LOCATION, values, "$COLUMN_GL_ID_GAME = $gameID", arrayOf())
            } else {
                val values = ContentValues()
                values.put(COLUMN_GL_LOCATION, location)
                values.put(COLUMN_GL_ID_GAME, gameID)
                val db = this.writableDatabase
                db.insert(TABLE_GAMES_LOCATION, null, values)
                db.close()
                message = "Your localization have been set to $location"
            }
        }
        return message
    }

    fun findLocalizationToGame(gameID: Int):Boolean {
        var inDB = false
        val query = "SELECT * FROM $TABLE_GAMES_LOCATION WHERE $COLUMN_GL_ID_GAME == $gameID"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()){
            inDB = true
            cursor.close()
        }
        db.close()
        return inDB
    }

    fun getLocalizationToGame(gameID: Int): String {
        var locations = "No added location yet"
        if (findLocalizationToGame(gameID)) {
            val query = "SELECT * FROM $TABLE_GAMES_LOCATION WHERE $COLUMN_GL_ID_GAME == $gameID"
            val db = this.readableDatabase
            val cursor = db.rawQuery(query, null)
            while (cursor.moveToNext()) {
                locations = cursor.getString(1)
            }
        }
        return locations
    }

    fun getAllGamesFromLocalization(location: String): MutableList<Game>{

        var games: MutableList<Game> = ArrayList()
        val query = "SELECT * FROM $TABLE_GAMES_LOCATION WHERE $COLUMN_GL_LOCATION LIKE \"$location\""
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val gameId = cursor.getInt(2)
            val queryGame = "SELECT * FROM $TABLE_GAMES WHERE $COLUMN_ID == $gameId"
            val dbGame = this.readableDatabase
            val cursorGame = dbGame.rawQuery(queryGame, null)
            while (cursorGame.moveToNext()){
                val game = Game()
                game.id= Integer.parseInt(cursorGame.getString(0))
                game.yearPublished = Integer.parseInt(cursorGame.getString(5))
                game.gameName = cursorGame.getString(1)
                game.rank = cursorGame.getInt(7)
                game.thumbnail = cursorGame.getString(2)
                game.image = cursorGame.getString(3)
                game.thumbnail = cursorGame.getString(2)
                games.add(game)
            }
        }
        return games
    }

    //////////////////HISTORY TABLE//////////////////////

    fun addRanking(currentRank: Rank): String{

        var message = "Your history of ranking have been refreshed"

        val values = ContentValues()
        values.put(COLUMN_GAME_ID, currentRank.id)
        values.put(COLUMN_RANK_CUR, currentRank.rank)
        values.put(COLUMN_DATE, currentRank.date)
        val db = this.writableDatabase
        db.insert(TABLE_HISTORY,null, values)
        db.close()

        return message
    }

    fun getHistory(id: Int): MutableList<Rank> {

        var history: MutableList<Rank> = ArrayList()
        val query = "SELECT * FROM $TABLE_HISTORY WHERE $COLUMN_GAME_ID == $id"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            val rank = Rank()
            rank.id = cursor.getInt(1)
            rank.rank = cursor.getInt(2)
            rank.date = cursor.getString(3)

            history.add(rank)
        }

        return history
    }

    //////////////////ARTISTS TABLE//////////////////////

    fun addArtist(artist: ArtistDesigner, gameID: Int){

        if (!findArtist(artist.id)) {

            val values = ContentValues()
            values.put(COLUMN_ART_ID, artist.id)
            values.put(COLUMN_ART_NAME, artist.name)

            val db = this.writableDatabase
            db.insert(TABLE_ARTISTS, null, values)
            db.close()
        }

        val values2 = ContentValues()
        values2.put(COLUMN_GA_ID_ART, artist.id)
        values2.put(COLUMN_GA_ID_GAME, gameID)

        val db2 = this.writableDatabase
        db2.insert(TABLE_GAMES_ARTISTS,null,values2)
        db2.close()

    }

    fun findArtist(artistID: Int): Boolean {
        var inDB = false
        val query = "SELECT * FROM $TABLE_ARTISTS WHERE $COLUMN_ART_ID == $artistID"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()){
            inDB = true
            cursor.close()
        }
        db.close()
        return inDB
    }

    fun getArtists(gameID: Int): MutableList<String> {

        val artists: MutableList<String> = ArrayList()
        val query = "SELECT * FROM $TABLE_GAMES_ARTISTS WHERE $COLUMN_GA_ID_GAME == $gameID"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            val id = cursor.getInt(1)
            val queryArtist = "SELECT * FROM $TABLE_ARTISTS WHERE $COLUMN_ART_ID == $id"
            val dbArtist = this.readableDatabase
            val cursorArtist = dbArtist.rawQuery(queryArtist, null)
            while (cursorArtist.moveToNext()){
                artists.add(cursorArtist.getString(1))
            }
        }
        return artists
    }

    //////////////////DESIGNERS TABLE//////////////////////

    fun addDesigner(designer: ArtistDesigner, gameID: Int){

        if (!findDesigner(designer.id)) {
            val values = ContentValues()
            values.put(COLUMN_DES_ID, designer.id)
            values.put(COLUMN_DES_NAME, designer.name)

            val db = this.writableDatabase
            db.insert(TABLE_DESIGNERS, null, values)
            db.close()
        }

        val values2 = ContentValues()
        values2.put(COLUMN_GD_ID_DES, designer.id)
        values2.put(COLUMN_GD_ID_GAME, gameID)

        val db2 = this.writableDatabase
        db2.insert(TABLE_GAMES_DESIGNERS,null,values2)
        db2.close()

    }

    fun findDesigner(designerID: Int): Boolean{
        var inDB = false
        val query = "SELECT * FROM $TABLE_DESIGNERS WHERE $COLUMN_DES_ID == $designerID"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()){
            inDB = true
            cursor.close()
        }
        db.close()
        return inDB
    }

    fun getDesigners(gameID: Int): MutableList<String> {

        val designers: MutableList<String> = ArrayList()
        val query = "SELECT * FROM $TABLE_GAMES_DESIGNERS WHERE $COLUMN_GD_ID_GAME == $gameID"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            val id = cursor.getInt(1)
            val queryDesigner = "SELECT * FROM $TABLE_DESIGNERS WHERE $COLUMN_DES_ID == $id"
            val dbDesigner = this.readableDatabase
            val cursorDesigner = dbDesigner.rawQuery(queryDesigner, null)
            while (cursorDesigner.moveToNext()){
                designers.add(cursorDesigner.getString(1))
            }
        }
        return designers
    }




}
