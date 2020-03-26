package com.mozendesk.objects.results;

/**
 * The parent class for the Result classes that are used to separate business level logic
 * from search output.
 */
public abstract class SearchResult {
    public abstract String prettyString();
}
