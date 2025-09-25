package com.ishacker.pdfviewer.system

import android.net.Uri

class PDF(
    var uri: Uri? = null,
    var name: String = "",
    var password: String? = null,
    var pageNumber: Int = 0,
    var length: Int = 0,
    var sizeInMb: Double = 0.0,
    var zoom: Float = 1F,
    var isPortrait: Boolean = true,
    var isFullScreenToggled: Boolean = false,
    var isBrightnessClicked: Boolean = false,
    var isAutoScrollClicked: Boolean = false,
    var isAutoScrolling: Boolean = false,
    var fileHash: String? = null,
    val text: MutableMap<Int, String> = mutableMapOf(),
    var isExtractingTextFinished: Boolean = false,
    var lastQuery: String? = null,
) {

    companion object {


        // constants
        const val FILE_TYPE = "application/pdf"
        const val HASH_SIZE = 1024 * 1024
        const val BOOKMARK_TEXT_SIZE = 24F
        const val BOOKMARK_TEXT_SIZE_DEC = 2F
        const val BOOKMARK_RESULT_OK = 48645
        const val SEARCH_RESULT_OK = 48632
        const val LINK_RESULT_OK = 48032
        const val SCREENSHOT_IMAGE_QUALITY = 100
        const val SEARCH_RESULT_OFFSET = 40
        const val ADDITIONAL_SEARCH_RESULT_OFFSET = 100
        const val TOO_MANY_RESULTS = 3500
        const val RESET_NUMBER = -1
        const val MIN_SEARCH_QUERY = 3

        // keys
        const val nameKey = "name"
        const val passwordKey = "password"
        const val pageNumberKey = "pageNumber"
        const val lengthKey = "length"
        const val uriKey = "uri"
        const val zoomKey = "zoom"
        const val isPortraitKey = "isPortrait"
        const val isFullScreenToggledKey = "isFullScreenToggled"
        const val isExtractingTextFinishedKey = "isExtractingTextFinished"
        const val pdfBookmarksKey = "PDF_BOOKMARKS"
        const val chosenBookmarkKey = "chosenBookmarkKey"
        const val searchResultPageNumberKey = "searchResultPageNumberKey"
        const val fileHashKey = "fileHashKey"
        const val searchResultKey = "searchInput"
        const val linkResultKey = "linkResult"
        const val startBookmarksActivity = 84418
        const val startSearchActivity = 91234
        const val startTextActivity =70134
        const val startLinksActivity = 54217
        const val searchQueryKey = "searchQuery"
        const val resultPositionInListKey = "searchResultPositionKey"
        const val filePathKey = "filePathKey"
    }

    fun getTitleWithPageNumber(): String {
        return "${getPageCounterText()} ${getTitle()}";
    }

    fun getTitle(): String {
        // get .pdf start index (the dot)
        val extensionIndex: Int = if (name.lastIndexOf('.') == -1) name.length else name.lastIndexOf('.')
        return name.substring(0, extensionIndex)
    }
    fun getPageCounterText(): String {
        return String.format("[%s/%s]", pageNumber + 1, length)
    }

    fun togglePortrait() { isPortrait = !isPortrait }

    fun setPageCount(count: Int) {
        if (count == length || count < 1) return
        length = count
    }

    fun hasFile() = uri != null

    fun resetLength() {
        length = RESET_NUMBER
    }

    fun initPdfLength(pageCount: Int) {
        if (length == RESET_NUMBER) {
            length = pageCount
        }
    }
}