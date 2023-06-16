package com.geekbrains.tests

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.model.SearchResult
import com.geekbrains.tests.view.details.DetailsActivity
import com.geekbrains.tests.view.search.MainActivity
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertSame
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.N_MR1])
class MainActivityTest {


    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var mainActivityScenario: ActivityScenario<MainActivity>
    private lateinit var context: Context
    private lateinit var progressBarView: ProgressBar
    private lateinit var editTextView: EditText
    private lateinit var buttonView: Button
    private lateinit var recyclerView: RecyclerView

    @Before
    fun setup() {

       mainActivityScenario = ActivityScenario
           .launch(MainActivity::class.java)

        scenario = mock(mainActivityScenario::class.java)
        context = ApplicationProvider.getApplicationContext()
        scenario.onActivity {
            progressBarView = it.findViewById(R.id.progressBar)
            editTextView = it.findViewById(R.id.searchEditText)
            buttonView = it.findViewById(R.id.toDetailsActivityButton)
            recyclerView = it.findViewById(R.id.recyclerView)
        }
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        assertEquals(Lifecycle.State.RESUMED, mainActivityScenario.state)
    }

    @Test
    fun activityLayoutElements_NotNull() {
        scenario.onActivity {
            assertNotNull(progressBarView)
            assertNotNull(editTextView)
            assertNotNull(buttonView)
            assertNotNull(recyclerView)
        }
    }


    @Test
    fun activityLayoutElements_Visibilility() {
        scenario.onActivity {
            assertEquals(View.GONE, progressBarView.visibility)
            assertEquals(View.VISIBLE, editTextView.visibility)
            assertEquals(View.VISIBLE, buttonView.visibility)
            assertEquals(View.VISIBLE, recyclerView.visibility)
        }
    }

    @Test
    fun activityEditText_HasHint() {
        scenario.onActivity {
            assertEquals(context.getString(R.string.search_hint), editTextView.hint)
        }
    }

    @Test
    fun activityButton_HasText() {
        scenario.onActivity {
            assertEquals(context.getString(R.string.to_details), buttonView.text)
        }
    }

    @Test
    fun activityButtonToDetailsActivity_IsWorking() {
        scenario.onActivity {
            it.setUI()
            buttonView.performClick()
            verify(it, times(1))
               .startActivity(DetailsActivity.getIntent(context, it.totalCount))
        }
    }

    @Test
    fun activitySetRecyclerView_Test() {
        scenario.onActivity {
            it.setRecyclerView()
            assertTrue(recyclerView.hasFixedSize())
            assertSame(recyclerView.adapter, it.adapter)
        }
    }

    @Test
    fun activityDisplayLoading_Test() {
        scenario.onActivity {
            it.displayLoading(true)
            assertEquals(View.VISIBLE, progressBarView.visibility)
            it.displayLoading(false)
            assertEquals(View.GONE, progressBarView.visibility)
        }
    }

    @Test

    fun activityEditText_IsWorking() {
        scenario.onActivity {
            editTextView.setText("text", TextView.BufferType.EDITABLE)
            assertNotNull(editTextView.text)
            assertEquals("text", editTextView.text.toString())
        }
    }

    @Test
    fun activityDisplayError_Test() {
        scenario.onActivity {
            it.displayError()
            assertThat(ShadowToast.getTextOfLatestToast())
                .isEqualTo(context.getString(R.string.undefined_error))
        }
    }

    @Test
    fun activityDisplayErrorWithCastomText_Test() {
        scenario.onActivity {
            it.displayError("Some error")
            assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo("Some error")
        }
    }

    @Test
    fun activityDisplaySearchResults_Test() {
        scenario.onActivity {
            it.displaySearchResults(listOf<SearchResult>(), 2)
            assertEquals(it.totalCount, 2)
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}
