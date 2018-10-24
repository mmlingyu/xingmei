/*
 * Lynket
 *
 * Copyright (C) 2018 Arunkumar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package arun.com.chromer.data.history

import android.app.Application
import arun.com.chromer.data.website.model.Website
import arun.com.chromer.settings.Preferences
import rx.Observable
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by arunk on 03-03-2017.
 */
@Singleton
class DefaultHistoryRepository
@Inject
internal constructor(
        private val application: Application,
        private val historyStore: HistoryStore,
        private val preferences: Preferences
) : HistoryRepository {

    override val allItemsCursor get() = historyStore.allItemsCursor

    override fun get(website: Website): Observable<Website> {
        return historyStore[website]
                .doOnNext { saved ->
                    if (saved == null) {
                        Timber.d("History miss for: %s", website.url)
                    } else {
                        Timber.d("History hit for : %s", website.url)
                    }
                }
    }

    override fun insert(website: Website): Observable<Website> {
        return if (preferences.historyDisabled()) {
            Observable.just(website)
        } else {
            historyStore.insert(website)
                    .doOnNext { webSite1 ->
                        if (webSite1 != null) {
                            Timber.d("Added %s to history", webSite1.url)
                        } else {
                            Timber.e("%s Did not add to history", website.url)
                        }
                    }
        }
    }

    override fun update(website: Website): Observable<Website> {
        return if (preferences.historyDisabled()) {
            Observable.just(website)
        } else {
            historyStore.update(website)
                    .doOnNext { saved ->
                        if (saved != null) {
                            Timber.d("Updated %s in history table", saved.url)
                        }
                    }
        }
    }

    override fun delete(website: Website) = historyStore.delete(website)

    override fun exists(website: Website) = historyStore.exists(website)

    override fun deleteAll() = historyStore.deleteAll()

    override fun recents() = historyStore.recents()

    override fun search(text: String) = historyStore.search(text)
}
