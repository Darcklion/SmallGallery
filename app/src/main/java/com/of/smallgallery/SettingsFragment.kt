package com.of.smallgallery

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment() {

    private val permissionRequestCode = 100
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        settingsViewModel = ViewModelProviders.of(activity!!).get(SettingsViewModel::class.java)
        clearDB.setOnClickListener { settingsViewModel.clearDB() }
        importButton.setOnClickListener { checkPermission() }
        importCount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(value: CharSequence, p1: Int, p2: Int, p3: Int) {
                settingsViewModel.setMaxImportImageNumber(value.toString().toInt())
            }

        })
    }

    private fun checkPermission(){
        if (ActivityCompat.checkSelfPermission(context!!,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), permissionRequestCode)
        } else {
            settingsViewModel.importImages()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == requestCode) {
            if (permissions[0] == Manifest.permission.READ_EXTERNAL_STORAGE
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                settingsViewModel.importImages()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}
