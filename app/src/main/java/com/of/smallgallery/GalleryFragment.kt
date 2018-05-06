package com.of.smallgallery

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.AbsListView
import com.of.smallgallery.db.Image
import kotlinx.android.synthetic.main.fragment_gallery.*



class GalleryFragment : Fragment() {

    lateinit var imageViewModel: ImageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = GalleryFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageViewModel = ViewModelProviders.of(activity!!).get(ImageViewModel::class.java)
        imageViewModel.getImages().observe(this, Observer<List<Image>> {
            list.adapter = GridAdapter(activity!!, ArrayList(it))
        })
        list.setMultiChoiceModeListener(object : AbsListView.MultiChoiceModeListener {
            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                return when (item.itemId) {
                    R.id.deleteItem -> {
                        imageViewModel.deleteSelectedImages()
                        mode.finish()
                        true
                    }
                    else -> false
                }
            }

            override fun onItemCheckedStateChanged(mode: ActionMode?, position: Int, id: Long, checked: Boolean) {
                if (checked)
                    imageViewModel.addSelectedImage(list.adapter.getItem(position) as Image)
                else
                    imageViewModel.removeSelectedImage(list.adapter.getItem(position) as Image)
                Log.d("item", "item on position $position clicked")
            }

            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                val menuInflater = mode.menuInflater
                menuInflater.inflate(R.menu.action, menu)
                return true            }

            override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?) = false

            override fun onDestroyActionMode(p0: ActionMode?) {}
        })
    }
}
