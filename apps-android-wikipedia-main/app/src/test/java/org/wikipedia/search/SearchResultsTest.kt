package org.wikipedia.search

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.wikipedia.dataclient.WikiSite
import org.wikipedia.page.PageTitle

@RunWith(RobolectricTestRunner::class)
class SearchResultsTest {

    private val wikiSite = WikiSite.forLanguageCode("en")

    @Test
    fun testEmptySearchResults() {
        val searchResults = SearchResults()
        
        assertTrue(searchResults.results.isEmpty())
        assertNull(searchResults.continuation)
    }

    @Test
    fun testSearchResultsWithSearchResults() {
        val result1 = SearchResult(PageTitle("Page One", wikiSite), SearchResult.SearchResultType.PREFIX)
        val result2 = SearchResult(PageTitle("Page Two", wikiSite), SearchResult.SearchResultType.FULL_TEXT)
        
        val searchResults = SearchResults(mutableListOf(result1, result2))
        
        assertEquals(2, searchResults.results.size)
        assertEquals("Page One", searchResults.results[0].pageTitle.displayText)
        assertEquals("Page Two", searchResults.results[1].pageTitle.displayText)
    }

    @Test
    fun testSearchResultsMutableList() {
        val searchResults = SearchResults()
        
        searchResults.results.add(
            SearchResult(
                PageTitle("Added Title", wikiSite),
                SearchResult.SearchResultType.HISTORY
            )
        )
        
        assertEquals(1, searchResults.results.size)
        assertEquals("Added Title", searchResults.results[0].pageTitle.displayText)
    }

    @Test
    fun testSearchResultsOrderPreserved() {
        val result1 = SearchResult(PageTitle("First", wikiSite))
        val result2 = SearchResult(PageTitle("Second", wikiSite))
        val result3 = SearchResult(PageTitle("Third", wikiSite))
        
        val searchResults = SearchResults(mutableListOf(result1, result2, result3))
        
        assertEquals(3, searchResults.results.size)
        assertEquals("First", searchResults.results[0].pageTitle.displayText)
        assertEquals("Second", searchResults.results[1].pageTitle.displayText)
        assertEquals("Third", searchResults.results[2].pageTitle.displayText)
    }

    @Test
    fun testSearchResultsRemove() {
        val result1 = SearchResult(PageTitle("Keep", wikiSite))
        val result2 = SearchResult(PageTitle("Remove", wikiSite))
        
        val searchResults = SearchResults(mutableListOf(result1, result2))
        searchResults.results.removeAt(1)
        
        assertEquals(1, searchResults.results.size)
        assertEquals("Keep", searchResults.results[0].pageTitle.displayText)
    }

    @Test
    fun testSearchResultsClear() {
        val result1 = SearchResult(PageTitle("Page 1", wikiSite))
        val result2 = SearchResult(PageTitle("Page 2", wikiSite))
        
        val searchResults = SearchResults(mutableListOf(result1, result2))
        searchResults.results.clear()
        
        assertTrue(searchResults.results.isEmpty())
    }

    @Test
    fun testSearchResultsToString() {
        val result1 = SearchResult(PageTitle("Test Page", wikiSite))
        val searchResults = SearchResults(mutableListOf(result1))
        
        val str = searchResults.toString()
        assertTrue(str.contains("Test_Page"))
    }
}
