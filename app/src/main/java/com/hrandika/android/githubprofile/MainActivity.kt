package com.hrandika.android.githubprofile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.hrandika.android.core.Params
import com.hrandika.android.githubprofile.di.ApplicationComponentProvider
import com.hrandika.android.profile.ProfileActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as ApplicationComponentProvider).getApplicationComponent().inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressBar = findViewById<ProgressBar>(R.id.progress_circular)
        progressBar.visibility = View.INVISIBLE

        val editText = findViewById<EditText>(R.id.editText_userName)
        editText.doAfterTextChanged {
            presenter.userName = editText.text.toString()
        }

        val button = findViewById<Button>(R.id.button_showProfile)
        button.setOnClickListener {
            button.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE

            button.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
            val intent = Intent(applicationContext, ProfileActivity::class.java)
            intent.putExtra(Params.PARAM_USER_NAME, presenter.userName)
            startActivity(intent)
        }

    }
}