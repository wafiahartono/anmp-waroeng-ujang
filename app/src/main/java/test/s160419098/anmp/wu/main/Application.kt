package test.s160419098.anmp.wu.main

import android.app.Application
import com.google.android.material.color.DynamicColors
import test.s160419098.anmp.wu.api.Service

class Application : Application() {
    private var _service: Service? = null
    val service get() = _service!!

    override fun onCreate() {
        super.onCreate()

        _service = Service(this)

        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}