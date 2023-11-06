package com.truscorp.catsapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.truscorp.catsapp.data.db.CatsAppDatabase
import com.truscorp.catsapp.data.db.daos.FavouriteCatDao
import com.truscorp.catsapp.data.db.models.FavouriteCat
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavouriteCatDaoTest {

    private lateinit var db: CatsAppDatabase
    private lateinit var favouriteCatDao: FavouriteCatDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, CatsAppDatabase::class.java).build()
        favouriteCatDao = db.favouriteCatDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testAddFavourite() = runTest {
        Assert.assertEquals(listOf<FavouriteCat>(), favouriteCatDao.getAll())

        favouriteCatDao.add(FavouriteCat("1"))

        val result = favouriteCatDao.getAll()

        Assert.assertEquals(listOf(FavouriteCat("1")), result)
    }

    @Test
    fun testDeleteFavourite() = runTest {
        favouriteCatDao.add(
            FavouriteCat("1"),
            FavouriteCat("2"),
            FavouriteCat("3"),
        )

        favouriteCatDao.delete(FavouriteCat("2"))
        val result = favouriteCatDao.getAll()

        Assert.assertEquals(listOf(FavouriteCat("1"), FavouriteCat("3")), result)
    }
}