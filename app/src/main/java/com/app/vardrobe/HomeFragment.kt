package com.app.vardrobe

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val imageList = listOf(
            R.drawable.bg_home1,
            R.drawable.bg_home1,
            R.drawable.bg_home1
        ).map { activity?.getDrawable(it)!! }.toList()
        image_slider.adapter = ImageSliderAdapter(imageList)
        for (i in imageList.indices) {
            val indicatorLay =
                LayoutInflater.from(view.context).inflate(R.layout.item_indicate, indicator, false)
            indicatorLay.tag = i
            indicator.addView(indicatorLay)
        }

        image_slider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicator.findViewWithTag<ImageView>(position)
                    .setBackgroundColor(
                        resources.getColor(
                            R.color.colorPrimaryDark,
                            resources.newTheme()
                        )
                    )
                indicator.findViewWithTag<ImageView>(position + 1)
                    ?.setBackgroundColor(
                        resources.getColor(
                            R.color.colorPrimary,
                            resources.newTheme()
                        )
                    )
                indicator.findViewWithTag<ImageView>(position - 1)
                    ?.setBackgroundColor(
                        resources.getColor(
                            R.color.colorPrimary,
                            resources.newTheme()
                        )
                    )
            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.logout -> {
                logout()
                true
            }
            else -> false
        }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        LoginManager.getInstance().logOut()
        startActivity(Intent(activity, SignInActivity::class.java))
    }
}