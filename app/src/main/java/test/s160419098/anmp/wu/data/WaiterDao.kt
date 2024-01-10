package test.s160419098.anmp.wu.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface WaiterDao {
    @Query("SELECT * FROM waiters WHERE id = :id")
    fun find(id: Long): Waiter?

    @Query("SELECT * FROM waiters WHERE username = :username")
    fun find(username: String): Waiter?

    @Query("UPDATE waiters SET password = :password WHERE id = :waiterId")
    fun updatePassword(waiterId: Long, password: String)
}
