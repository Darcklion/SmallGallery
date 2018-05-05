package com.of.smallgallery

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.of.smallgallery.db.Image
import com.of.smallgallery.db.ImageViewModel
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

        val imageViewModel = ViewModelProviders.of(this).get(ImageViewModel::class.java)
        imageViewModel.getImages().observe(this, Observer<List<Image>> {
            //show images
        })
    }

    private fun changeFragment(id: Int) {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.fragmentContainer, getRightFragment(id))
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
