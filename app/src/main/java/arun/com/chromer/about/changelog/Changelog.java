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

package arun.com.chromer.about.changelog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cheyipai.ui.BuildConfig;
import com.cheyipai.ui.R;
;
import arun.com.chromer.util.Utils;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by Arun on 25/12/2015.
 */
public class Changelog {
    private static final String PREF_VERSION_CODE_KEY = "version_code";
    private static final int DOES_NT_EXIST = -1;

    public static void show(final Activity activity) {
        try {
            @SuppressLint("InflateParams") final FrameLayout content = (FrameLayout) LayoutInflater.from(activity).inflate(R.layout.widget_changelog_layout, null);
            final MaterialProgressBar progress = content.findViewById(R.id.changelog_progress);
            final WebView webView = content.findViewById(R.id.changelog_web_view);
            webView.loadData(activity.getString(R.string.save_time), "text/html", "utf-8");
            //noinspection deprecation
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    content.removeView(progress);
                    webView.setVisibility(View.VISIBLE);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    return super.shouldOverrideUrlLoading(view, request);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.equalsIgnoreCase("https://goo.gl/photos/BzRV69ABov9zJxVu9")) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        activity.startActivity(i);
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }
            });
            new MaterialDialog.Builder(activity)
                    .customView(content, false)
                    .title("Changelog")
                    .positiveText(android.R.string.ok)
                    .neutralText(R.string.save_time)
                    .onNeutral((dialog, which) -> Utils.openPlayStore(activity, activity.getPackageName()))
                    .dismissListener(dialogInterface -> content.removeAllViews())
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, R.string.changelog_skipped, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Shows the changelog dialog if necessary
     *
     * @param activity Activty for which it should be shown
     */
    public static void conditionalShow(@NonNull final Activity activity) {
        if (shouldShow(activity)) {
            show(activity);
            handleMigration(activity);
        }
    }

    private static void handleMigration(@NonNull final Activity activity) {
        // Utils.deleteCache(activity).subscribe();
    }

    private static boolean shouldShow(@NonNull final Context context) {
        int currentVersionCode = BuildConfig.VERSION_CODE;
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOES_NT_EXIST);
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
        if (currentVersionCode == savedVersionCode) {
            // This is just a normal run
            return false;
        } else if (savedVersionCode == DOES_NT_EXIST) {
            // This is a new install (or the user cleared the shared preferences)
            return true;
        } else if (currentVersionCode > savedVersionCode) {
            // This is an upgrade
            return true;
        }
        return false;
    }
}
