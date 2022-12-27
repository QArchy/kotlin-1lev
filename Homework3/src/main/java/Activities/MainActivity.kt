package Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import Fragments.MainPageFragment.MainPageFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.homework3.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //baseContext.deleteDatabase("note_database");
    }
}