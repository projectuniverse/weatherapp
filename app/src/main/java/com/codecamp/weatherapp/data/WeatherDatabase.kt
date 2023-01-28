package com.codecamp.weatherapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codecamp.weatherapp.model.Weather

@Database(entities = [Weather::class], version = 4, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    companion object {
        @Volatile
        private var Instance: WeatherDatabase? = null
        fun getDatabase(context: Context): WeatherDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, WeatherDatabase::class.java, "weather_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {Instance = it}
            }
        }
    }
}