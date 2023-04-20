package com.example.notes.room

import androidx.room.*
import com.example.notes.model.News


@Dao
interface NewsDao {
    @Query("SELECT *FROM news order by  createAt DESC")
    fun getAll(): List<News>

    @Query("SELECT * FROM news order by title ASC")
    fun getAllSortedTitle(): List<News>

    @Insert
    fun insert(news: News)

    @Update
    fun update(news: News)

    @Delete
    fun delete(news:News)

}