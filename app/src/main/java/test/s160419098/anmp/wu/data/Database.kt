package test.s160419098.anmp.wu.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@androidx.room.Database(
    version = 1,
    entities = [Waiter::class, Category::class, Menu::class],
)
abstract class Database : RoomDatabase() {
    abstract fun waiterDao(): WaiterDao
    abstract fun categoryDao(): CategoryDao
    abstract fun menuDao(): MenuDao

    companion object {
        fun build(context: Context) =
            Room.databaseBuilder(context, Database::class.java, "waroengujangdb")
                .createFromAsset("databases/seed.db")
                .build()
    }
}