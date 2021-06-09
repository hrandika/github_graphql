package com.hrandika.android.profile

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hrandika.android.core.Params.Companion.PARAM_USER_NAME
import com.hrandika.android.profile.di.ProfileComponentProvider
import com.hrandika.android.profile.ui.RepositoryAdaptor
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: ProfileActivityPresenter
    private lateinit var pullToRefresh: SwipeRefreshLayout

    //TODO improve into single view???
    private lateinit var linearLayoutProfile: LinearLayout
    private lateinit var linearLayoutDetails: LinearLayout
    private lateinit var linerLayoutPinned: LinearLayout
    private lateinit var linerLayoutTop: LinearLayout
    private lateinit var linerLayoutStarred: LinearLayout

    private lateinit var textViewTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        val loginComponent = (applicationContext as ProfileComponentProvider)
            .provideProfileComponent()
        loginComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        pullToRefresh = findViewById(R.id.pullToRefresh)
        linearLayoutProfile = findViewById(R.id.linearLayout_profile)
        linearLayoutDetails = findViewById(R.id.linearLayout_details)
        linerLayoutPinned = findViewById(R.id.linerLayout_pinned)
        linerLayoutTop = findViewById(R.id.linerLayout_top)
        linerLayoutStarred = findViewById(R.id.linerLayout_starred)

        textViewTitle = findViewById(R.id.toolbar_title)
        textViewTitle.text = resources.getText(R.string.pull_to_refresh)

        pullToRefresh.setOnRefreshListener {
            refreshData()
        }
        // initial load
        textViewTitle.text = resources.getText(R.string.loading)
        refreshData()
    }

    private fun refreshData() {

        val profileImageView = findViewById<AppCompatImageView>(R.id.profileImageView)
        val emailTextVIew = findViewById<TextView>(R.id.email)
        val profileNameTextView = findViewById<TextView>(R.id.profileName)
        val profileHandlerTextView = findViewById<TextView>(R.id.profileHandler)
        val followersNumberTextView = findViewById<TextView>(R.id.followersNumber)
        val followingNumberTextView = findViewById<TextView>(R.id.followingNumber)


        val username = intent.getStringExtra(PARAM_USER_NAME)

        // pinned
        val recyclerViewPinned = findViewById<RecyclerView>(R.id.recyclerViewPinned)
        recyclerViewPinned.layoutManager = LinearLayoutManager(this)

        val recyclerViewTop = findViewById<RecyclerView>(R.id.recyclerViewTop)
        val recyclerViewStarred = findViewById<RecyclerView>(R.id.recyclerViewStarred)


        CoroutineScope(Dispatchers.Main).launch {
            val user = presenter.getUserProfile(username)

            // profile
            Picasso.get().load(user?.avatarUrl.toString())
                .into(profileImageView)
            emailTextVIew.text = user?.email
            profileNameTextView.text = user?.name
            profileHandlerTextView.text = user?.login
            followersNumberTextView.text = "${user?.followers?.totalCount}"
            followingNumberTextView.text = "${user?.following?.totalCount}"

            recyclerViewPinned.adapter = RepositoryAdaptor(user!!, false)

            // Requirement is bit clean on how to load these. using pinned one as dummy data
            // Any case just need to add to Graphql query and map
            recyclerViewTop.adapter = RepositoryAdaptor(user, true)
            recyclerViewStarred.adapter = RepositoryAdaptor(user, true)


            pullToRefresh.isRefreshing = false
            linearLayoutProfile.visibility = View.VISIBLE
            linearLayoutDetails.visibility = View.VISIBLE
            linerLayoutPinned.visibility = View.VISIBLE
            linerLayoutTop.visibility = View.VISIBLE
            linerLayoutStarred.visibility = View.VISIBLE

            textViewTitle.text = resources.getText(R.string.profile)

        }

    }
}