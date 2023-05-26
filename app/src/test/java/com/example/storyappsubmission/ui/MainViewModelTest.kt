package com.example.storyappsubmission.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.storyappsubmission.DataDummy
import com.example.storyappsubmission.MainDispatcherRule
import com.example.storyappsubmission.adapter.StoryListAdapter
import com.example.storyappsubmission.getOrAwaitValue
import com.example.storyappsubmission.paging.StoryRepo
import com.example.storyappsubmission.pref.UserPreference
import com.example.storyappsubmission.response.story
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)

class MainViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepo: StoryRepo

    @Mock
    private lateinit var userPreference: UserPreference

    @Test
    fun `when Get Quote Should Not Null and Return Data`() = runTest {
        val dummyStory = DataDummy.generateDummyStoryResponse()
        val data: PagingData<story> = StoryPagingSource.snapshot(dummyStory)
        val expectedQuote = MutableLiveData<PagingData<story>>()
        expectedQuote.value = data

        Mockito.`when`(storyRepo.getStory("storyToken")).thenReturn(expectedQuote)
        val mainViewModel = MainViewModel(userPreference, storyRepo)
        val actualStory: PagingData<story> = mainViewModel.getStoryPage("storyToken").getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryListAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStory)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStory.size, differ.snapshot().size)
        Assert.assertEquals(dummyStory[0], differ.snapshot()[0])
    }

    @Test
    fun `when Get Quote Empty Should Return No Data`() = runTest {
        val data: PagingData<story> = PagingData.from(emptyList())
        val expectedQuote = MutableLiveData<PagingData<story>>()
        expectedQuote.value = data
        Mockito.`when`(storyRepo.getStory("storyToken")).thenReturn(expectedQuote)

        val mainViewModel = MainViewModel(userPreference, storyRepo)
        val actualStory: PagingData<story> = mainViewModel.getStoryPage("storyToken").getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryListAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStory)

        Assert.assertEquals(0, differ.snapshot().size)
    }

}

class StoryPagingSource : PagingSource<Int, LiveData<List<story>>>() {
    companion object {
        fun snapshot(items: List<story>): PagingData<story> {
            return PagingData.from(items)
            }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<story>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<story>>> {
        return LoadResult.Page(emptyList(), 0, 1)    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}