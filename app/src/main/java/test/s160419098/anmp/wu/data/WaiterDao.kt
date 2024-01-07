package test.s160419098.anmp.wu.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WaiterDao {
    @Insert
    fun insert(waiter: Waiter)

    @Query("SELECT * FROM waiters WHERE username = :username")
    fun find(username: String): Waiter?

    @Query("UPDATE waiters SET password = :password WHERE id = :id")
    fun updatePassword(id: Int, password: String)
}
