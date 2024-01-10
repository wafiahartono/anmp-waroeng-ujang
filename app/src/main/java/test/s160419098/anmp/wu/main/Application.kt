package test.s160419098.anmp.wu.main

import android.app.Application
import com.google.android.material.color.DynamicColors
import test.s160419098.anmp.wu.data.AppDatabase

class Application : Application() {
    private var _database: AppDatabase? = null
    val database get() = _database!!

    override fun onCreate() {
        super.onCreate()

        _database = AppDatabase.build(this)

        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
