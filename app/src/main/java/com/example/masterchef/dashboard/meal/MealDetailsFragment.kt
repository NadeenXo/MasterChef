package com.example.masterchef.dashboard.meal

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masterchef.R
import com.example.masterchef.dashboard.meal.model.MealDetails
import com.example.masterchef.network.APIClient
import com.example.masterchef.network.ApiService
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MealDetailsFragment : Fragment() {
    private lateinit var mealId: String
    private lateinit var service: ApiService

    private lateinit var nameTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var countryTextView: TextView
    private lateinit var ingredientsRecyclerView: RecyclerView
    private lateinit var stepsTextView: TextView
    private lateinit var addButton: Button
    private lateinit var removeButton: Button
    private lateinit var youtubePlayerView: YouTubePlayerView

    companion object {
        private const val ARG_MEAL_ID = "meal_id"

        fun newInstance(mealId: String): MealDetailsFragment {
            val fragment = MealDetailsFragment()
            val args = Bundle()
            args.putString(ARG_MEAL_ID, mealId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meal_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameTextView = view.findViewById(R.id.name_tv_meal)
        imageView = view.findViewById(R.id.meal_iv_meal)
        countryTextView = view.findViewById(R.id.country_tv_meal)
        ingredientsRecyclerView = view.findViewById(R.id.ingredient_list)
        stepsTextView = view.findViewById(R.id.steps_tv_meal)
        addButton = view.findViewById(R.id.add_btn_meal)
        removeButton = view.findViewById(R.id.remove_btn_meal)
        youtubePlayerView = view.findViewById(R.id.youtube_player_view)

        service = APIClient.getInstance()

        arguments?.let {
            mealId = it.getString(ARG_MEAL_ID, "")
        }

        if (mealId.isNotEmpty()) {
            getMealDetails(mealId)
        }
    }

    private fun getMealDetails(id: String) {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) { service.getMealDetails(id) }
                if (response.isSuccessful && response.body() != null) {
                    val mealDetails = response.body()?.meals?.firstOrNull()
                    mealDetails?.let { meal ->
                        nameTextView.text = meal.strMeal
                        Glide.with(this@MealDetailsFragment).load(meal.strMealThumb).centerCrop().into(imageView)
                        countryTextView.text = meal.strArea

                        val ingredientsWithMeasures = extractIngredients(meal)
                        ingredientsRecyclerView.layoutManager = GridLayoutManager(context, 2)
                        ingredientsRecyclerView.adapter =
                            IngredientsAdapter(ingredientsWithMeasures)

                        stepsTextView.text = meal.strInstructions

                        lifecycle.addObserver(youtubePlayerView)

                        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                meal.strYoutube.let {
                                    val videoId = Uri.parse(it).getQueryParameter("v")
                                    if (videoId != null) {
                                        youTubePlayer.cueVideo(videoId, 0f)
                                    }
                                }
                            }
                        })
                    }
                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    private fun extractIngredients(meal: MealDetails): List<Pair<String, String>> {
        val ingredientsWithMeasures = mutableListOf<Pair<String, String>>()
        for (i in 1..20) {
            val ingredient =
                meal::class.java.getDeclaredField("strIngredient$i").apply { isAccessible = true }
                    .get(meal) as? String
            val measure =
                meal::class.java.getDeclaredField("strMeasure$i").apply { isAccessible = true }
                    .get(meal) as? String
            if (!ingredient.isNullOrEmpty() && !measure.isNullOrEmpty()) {
                ingredientsWithMeasures.add(ingredient to measure)
            }
        }
        return ingredientsWithMeasures
    }
}
