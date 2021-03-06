package dev.johnoreilly.mortycomposekmm.ui.episodes

import androidx.paging.PagingSource
import dev.johnoreilly.mortycomposekmm.fragment.EpisodeDetail
import dev.johnoreilly.mortycomposekmm.shared.MortyRepository

class EpisodesDataSource(private val repository: MortyRepository) : PagingSource<Int, EpisodeDetail>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeDetail> {
        val pageNumber = params.key ?: 0

        val episodesResponse = repository.getEpisodes(pageNumber)
        val episodes = episodesResponse?.resultsFilterNotNull()?.map { it.fragments.episodeDetail }

        val prevKey = if (pageNumber > 0) pageNumber - 1 else null
        val nextKey = episodesResponse?.info?.next
        return LoadResult.Page(data = episodes ?: emptyList(), prevKey = prevKey, nextKey = nextKey)
    }
}