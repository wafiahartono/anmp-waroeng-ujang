package test.s160419098.anmp.wu.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    version = 3,
    entities = [
        Waiter::class,
        Category::class,
        Menu::class,
        Order::class,
        OrderItem::class,
    ],
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        fun build(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "waroengujangdb")
                .addMigrations(MIGRATION_1_2)
                .addMigrations(MIGRATION_2_3)
                .createFromAsset("databases/seed.db")
                .build()

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                        CREATE TABLE "orders" (
                            "id" INTEGER NOT NULL,
                            "table" INTEGER NOT NULL,
                            "datetime" INTEGER DEFAULT NULL,
                            PRIMARY KEY("id" AUTOINCREMENT)
                        )
                    """
                )
                db.execSQL(
                    """
                        CREATE TABLE "order_items" (
                            "order_id" INTEGER NOT NULL,
                            "menu_id" INTEGER NOT NULL,
                            "quantity" INTEGER NOT NULL,
                            "subtotal" REAL NOT NULL,
                            PRIMARY KEY("order_id", "menu_id")
                        )
                    """
                )
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE orders ADD COLUMN closed_at INTEGER DEFAULT NULL")
                db.execSQL("ALTER TABLE orders ADD COLUMN status TEXT DEFAULT 'CART' NOT NULL")
            }
        }

    }

    abstract fun waiterDao(): WaiterDao
    abstract fun categoryDao(): CategoryDao
    abstract fun menuDao(): MenuDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao

}
