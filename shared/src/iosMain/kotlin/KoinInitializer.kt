import com.example.movieapp.platformModule
import com.example.movieapp.sharedModule
import org.koin.core.context.startKoin

class KoinInitializer {
    fun initialize() {
        startKoin {
            modules(sharedModule + platformModule)
        }
    }
}