package com.example.masterchef.dashboard.meal

import android.content.Intent
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
import com.example.masterchef.dashboard.meal.view.IngredientsAdapter
import com.example.masterchef.dashboard.plan.AddPlanDiaogFragment
import com.example.masterchef.dto.fav.FavDAO
import com.example.masterchef.dto.fav.FavDataBase
import com.example.masterchef.dto.fav.FavouriteTable
import com.example.masterchef.network.APIClient
import com.example.masterchef.network.ApiService
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class MealDetailsFragment : Fragment() {
    private lateinit var mealId: String
    private lateinit var service: ApiService
    private lateinit var myMeal: MealDetails
    private lateinit var nameTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var countryTextView: TextView
    private lateinit var categoryTextView: TextView
    private lateinit var ingredientsRecyclerView: RecyclerView
    private lateinit var stepsTextView: TextView
    private lateinit var addButton: Button
    private lateinit var removeButton: Button
    private lateinit var calendarButton: Button
    private lateinit var planButton: Button
    private lateinit var youtubePlayerView: YouTubePlayerView
    private lateinit var favDao: FavDAO
    private lateinit var planDialog: AddPlanDiaogFragment


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
        categoryTextView = view.findViewById(R.id.category_tv_meal)
        ingredientsRecyclerView = view.findViewById(R.id.ingredient_list)
        stepsTextView = view.findViewById(R.id.steps_tv_meal)
        addButton = view.findViewById(R.id.add_btn_meal)
        removeButton = view.findViewById(R.id.remove_btn_meal)
        calendarButton = view.findViewById(R.id.add_calender_btn_meal)
        planButton = view.findViewById(R.id.add_plan_btn_meal)
        youtubePlayerView = view.findViewById(R.id.youtube_player_view)

        service = APIClient.getInstance()
        favDao = FavDataBase.getInstance(requireContext()).favDao()
//        planDialog = AddPlanDiaogFragment(myMeal.strMeal)


        arguments?.let {
            mealId = it.getString(ARG_MEAL_ID, "")
        }

        if (mealId.isNotEmpty()) {
            getMealDetails(mealId)
        }
        addButton.setOnClickListener {
            lifecycleScope.launch {
                addMealToFav(myMeal)
            }
        }
        removeButton.setOnClickListener {
            lifecycleScope.launch {
                removeMealFromFav(myMeal)
            }
        }
        addToCalender()
        planButton.setOnClickListener {
            //todo
//            open plan dialog
            planDialog.show(requireActivity().supportFragmentManager, planDialog.tag)

        }
    }

    private fun addToCalender() {
        // Get the current time
        val calendar = Calendar.getInstance()

// Set the event start time to tomorrow at 9:00 AM
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val startTime = calendar.time

// Set the event end time to tomorrow at 12:00 PM
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        val endTime = calendar.time

// When Button is clicked, Intent started to create an event with given time
        calendarButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_EDIT)
            intent.type = "vnd.android.cursor.item/event"
            intent.putExtra("beginTime", startTime.time)
            intent.putExtra("time", true)
            intent.putExtra("rule", "FREQ=YEARLY")
            intent.putExtra("endTime", endTime.time)
            intent.putExtra("title", nameTextView.text)
            startActivity(intent)
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
                        Glide.with(this@MealDetailsFragment).load(meal.strMealThumb)
                            .centerCrop()
                            .into(imageView)
                        countryTextView.text = meal.strArea
                        categoryTextView.text = meal.strCategory

                        val ingredientsWithMeasures = extractIngredients(meal)
                        ingredientsRecyclerView.layoutManager = GridLayoutManager(context, 2)
                        ingredientsRecyclerView.adapter =
                            IngredientsAdapter(ingredientsWithMeasures)

                        stepsTextView.text = meal.strInstructions

                        lifecycle.addObserver(youtubePlayerView)

                        youtubePlayerView.addYouTubePlayerListener(object :
                            AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                meal.strYoutube.let {
                                    val videoId = Uri.parse(it).getQueryParameter("v")
                                    if (videoId != null) {
                                        youTubePlayer.cueVideo(videoId, 0f)
                                    }
                                }
                            }
                        })
//                        addMealToFav(meal)
                        myMeal = meal
                        planDialog = AddPlanDiaogFragment(meal.strMeal)

                    }

                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    private suspend fun addMealToFav(meal: MealDetails): List<Long> {
        val newFav = FavouriteTable(
            meal.idMeal,
            meal.strMeal,
            meal.strMealThumb
        )
        return favDao.insert(newFav)
    }

    private suspend fun removeMealFromFav(meal: MealDetails) {
        favDao.delete(
            FavouriteTable(
                meal.idMeal,
                meal.strMeal,
                meal.strMealThumb
            )
        )
    }

    private fun extractIngredients(meal: MealDetails): List<Pair<String, String>> {
        val ingredientsWithMeasures = mutableListOf<Pair<String, String>>()
        for (i in 1..20) {
            val ingredient =
                meal::class.java.getDeclaredField("strIngredient$i")
                    .apply { isAccessible = true }
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
