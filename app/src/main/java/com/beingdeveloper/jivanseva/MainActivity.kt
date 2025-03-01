package com.beingdeveloper.jivanseva

import ViewPagerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.beingdeveloper.jivanseva.activities.*
import com.beingdeveloper.jivanseva.auth.SessionManager
import com.beingdeveloper.jivanseva.drawerActivities.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var sessionManager: SessionManager
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigationView: BottomNavigationView

    private var isTeamManagementExpanded = false
    private var isBusinessManagementExpanded = false
    private var isReportExpanded = false

    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        sessionManager = SessionManager(this)

        // Check if user is logged in, otherwise redirect to LoginActivity
        if (!sessionManager.isLoggedIn()) {
            navigateToLogin()
            return
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val menuIcon = findViewById<ImageView>(R.id.menu_icon)

        navigationView = findViewById(R.id.navigation_drawer)
        navigationView.setNavigationItemSelectedListener(this)

        menuIcon.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        val profileImage: ImageView = findViewById(R.id.profile_picture)
        profileImage.setOnClickListener {
            val intent = Intent(this, EditProfile::class.java)
            startActivity(intent)
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.wallet -> {
                    startActivity(Intent(this, EWallet::class.java))
                    true
                }
                R.id.payout -> {
                    startActivity(Intent(this, Payout::class.java))
                    true
                }
                R.id.notification -> {
                    startActivity(Intent(this, Notification::class.java))
                    true
                }
                else -> false
            }
        }

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Business"
                1 -> tab.text = "Income"
            }
        }.attach()

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                    isEnabled = true
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) { resetMenuState() }
            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_profile -> startActivity(Intent(this, EditProfile::class.java))
            R.id.team_management -> toggleTeamManagementMenu()
            R.id.my_binary_tree -> startActivity(Intent(this, BinaryTree::class.java))
            R.id.all_members -> startActivity(Intent(this, Members::class.java))
            R.id.direct_referrals -> startActivity(Intent(this, DirectReferrals::class.java))
            R.id.leg_view -> startActivity(Intent(this, LegView::class.java))
            R.id.level_wise_members -> startActivity(Intent(this, LevelWiseMembers::class.java))
            R.id.level_count -> startActivity(Intent(this, LevelCount::class.java))
            R.id.e_wallet -> startActivity(Intent(this, EWallet::class.java))
            R.id.payout -> startActivity(Intent(this, Payout::class.java))
            R.id.business_management -> toggleBusinessManagementMenu()
            R.id.business_summary -> startActivity(Intent(this, BusinessSummary::class.java))
            R.id.report -> toggleReportMenu()
            R.id.joining_report -> startActivity(Intent(this, JoiningReport::class.java))
            R.id.tds -> startActivity(Intent(this, TdsAndAdmin::class.java))
            R.id.purchase_report -> startActivity(Intent(this, PurchaseReport::class.java))
            R.id.logout -> logoutUser()
            else -> drawerLayout.closeDrawer(GravityCompat.END)
        }
        return true
    }

    private fun toggleTeamManagementMenu() {
        isTeamManagementExpanded = !isTeamManagementExpanded
    }

    private fun toggleBusinessManagementMenu() {
        isBusinessManagementExpanded = !isBusinessManagementExpanded
    }

    private fun toggleReportMenu() {
        isReportExpanded = !isReportExpanded
    }

    private fun resetMenuState() {
        if (isTeamManagementExpanded) toggleTeamManagementMenu()
        if (isBusinessManagementExpanded) toggleBusinessManagementMenu()
        if (isReportExpanded) toggleReportMenu()
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun logoutUser() {
        sessionManager.logoutUser()
        navigateToLogin()
    }
}
