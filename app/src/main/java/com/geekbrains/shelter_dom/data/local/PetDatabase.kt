package com.geekbrains.shelter_dom.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.geekbrains.shelter_dom.data.pet.model.PetEntity
import com.geekbrains.shelter_dom.utils.App
import com.geekbrains.shelter_dom.utils.DATABASE_NAME
import com.geekbrains.shelter_dom.utils.DB_VERSION

@Database(
    entities = [PetEntity::class],
    version = DB_VERSION
)
abstract class PetDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao

    companion object{
        val petDatabase: PetDatabase by lazy {
            Room.databaseBuilder(
                App.INSTANCE.applicationContext,
                PetDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}