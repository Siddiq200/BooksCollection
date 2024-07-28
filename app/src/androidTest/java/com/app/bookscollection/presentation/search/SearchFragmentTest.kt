package com.app.bookscollection.presentation.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.MediumTest
import com.app.bookscollection.HiltTestActivity
import com.app.bookscollection.R
import com.app.bookscollection.data.api.BookApiService
import com.app.bookscollection.data.db.BookDao
import com.app.bookscollection.data.repository.BookRepository
import com.app.bookscollection.data.repository.BookRepositoryImpl
import com.app.bookscollection.di.CoroutinesNetwork
import com.app.bookscollection.domain.usecases.UpdateFavoriteStatusUseCase
import com.app.bookscollection.util.DataBindingIdlingResource
import com.app.bookscollection.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@LargeTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class SearchFragmentTest{

    // An Idling Resource that waits for Data Binding to have no pending bindings
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Inject
    lateinit var bookDao: BookDao
    @Inject @CoroutinesNetwork lateinit var bookApiService: BookApiService

    private lateinit var repository: BookRepository
    private lateinit var updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setUp() {
        hiltRule.inject()
        repository = BookRepositoryImpl(bookDao, bookApiService, bookApiService)
        updateFavoriteStatusUseCase = UpdateFavoriteStatusUseCase(repository)
    }

    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<HiltTestActivity> =
        ActivityScenarioRule(HiltTestActivity::class.java)

    @Test
    fun testDataLoading() {


        val activityScenario: ActivityScenario<HiltTestActivity> = launchActivity()//ActivityScenario.launch(HiltTestActivity::class.java)
        activityScenario.onActivity {
            val yourFragment: Fragment = it.supportFragmentManager.fragmentFactory.instantiate(
                Preconditions.checkNotNull(SearchFragment::class.java.classLoader),
                SearchFragment::class.java.name
            )

            it.supportFragmentManager.beginTransaction()
                .add(android.R.id.content, yourFragment, "")
                .commitNow()



            onView(withId(R.id.booksRV)).check(matches(isDisplayed()))

            onView(withId(R.id.booksRV)).perform(click())

            // Verify
            onView(withId(R.id.bookImage)).check(matches(isDisplayed()))
        }

    }
}