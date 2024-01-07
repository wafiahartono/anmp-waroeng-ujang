package test.s160419098.anmp.wu.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MenuDao {
    @Query("SELECT * FROM menus WHERE id = :id")
    fun find(id: Int): Menu?
}
