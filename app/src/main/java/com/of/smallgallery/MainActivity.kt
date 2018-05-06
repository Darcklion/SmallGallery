package com.of.smallgallery

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val SETTINGS_TAG = "SETTINGS_TAG"
private const val GALLERY_TAG = "GALLERY_TAG"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener {
            changeFragment(it.itemId)
            true
        }

        changeFragment(R.id.navigation_home)
    }

    private fun changeFragment(id: Int) {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        val fragment = getRightFragment(id)
        val tag = if (fragment is GalleryFragment) GALLERY_TAG else SETTINGS_TAG
        transaction.replace(R.id.fragmentContainer, fragment, tag)
        transaction.commit()
    }

    private fun getRightFragment(id: Int): Fragment {
        val fm = supportFragmentManager
        when (id) {
            R.id.navigation_home -> {
                var galleryFragment = fm.findFragmentByTag(GALLERY_TAG)
                if (galleryFragment == null)
                   galleryFragment = GalleryFragment.newInstance()
                return galleryFragment
            }
            R.id.navigation_settings -> {
                var settingsFragment = fm.findFragmentByTag(SETTINGS_TAG)
                if (settingsFragment == null)
                    settingsFragment = SettingsFragment.newInstance()
                return settingsFragment
            }
        }
        return GalleryFragment.newInstance()
    }
}
