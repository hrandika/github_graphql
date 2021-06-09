package com.hrandika.android.profile.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.hrandika.android.core.GithubUserQuery
import com.hrandika.android.profile.R
import com.squareup.picasso.Picasso

class RepositoryAdaptor(
    private val user: GithubUserQuery.User,
    private val horizontal: Boolean
) :
    RecyclerView.Adapter<RepositoryAdaptor.ViewHolder>() {

    private val nodes = user.pinnedItems.nodes

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var profileCard: CardView = view.findViewById(R.id.card_profile) as CardView
        var profileName: TextView = view.findViewById(R.id.profileName) as TextView
        var profileImage: ImageView = view.findViewById(R.id.profileImageView) as ImageView
        var repoName: TextView = view.findViewById(R.id.textView_repositoryName)
        var repoDescription: TextView = view.findViewById(R.id.textView_description)
        var stars: TextView = view.findViewById(R.id.textView_stars)
        var lang: TextView = view.findViewById(R.id.textView_primaryLanguage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(user.avatarUrl.toString())
            .into(holder.profileImage)

        holder.profileName.text = user.name.orEmpty()
        holder.repoName.text = nodes?.get(position)?.asRepository?.name.orEmpty()

        holder.repoDescription.text = nodes?.get(position)?.asRepository?.description.orEmpty()
        holder.stars.text = nodes?.get(position)?.asRepository?.stargazerCount.toString()
        holder.lang.text = nodes?.get(position)?.asRepository?.primaryLanguage?.name.orEmpty()
        if (horizontal) {
            val configuration = holder.profileCard.context.resources.configuration
            holder.profileCard.layoutParams.width = configuration.smallestScreenWidthDp * 2
        }
    }

    override fun getItemCount(): Int {
        return nodes!!.size
    }

}