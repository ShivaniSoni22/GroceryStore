package com.country.grocerystore.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.country.grocerystore.R
import com.country.grocerystore.fragments.RandomDataTableViewWithControlsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = RandomDataTableViewWithControlsFragment.newInstance(this)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        fragmentTransaction.replace(R.id.content_fragment, fragment, fragment.javaClass.simpleName)
        fragmentTransaction.commit()
    }

}