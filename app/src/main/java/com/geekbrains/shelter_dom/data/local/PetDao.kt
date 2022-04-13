package com.geekbrains.shelter_dom.data.local

import androidx.room.*
import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.data.pet.model.PetEntity
import com.geekbrains.shelter_dom.utils.DATABASE_TABLE_NAME
import io.reactivex.rxjava3.core.Flowable

@Dao
interface PetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPet(pet: PetEntity)

    @Delete
    fun removePets(pet: PetEntity)

    @Query("DELETE FROM $DATABASE_TABLE_NAME WHERE pet_id LIKE :id")
    fun deleteById(id: Int)

    @Query("SELECT * FROM $DATABASE_TABLE_NAME")
    fun getAllPetsFromDatabase(): List<PetEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM $DATABASE_TABLE_NAME WHERE pet_id LIKE :id)")
    fun petExistInDatabase(id: Int): Boolean

}