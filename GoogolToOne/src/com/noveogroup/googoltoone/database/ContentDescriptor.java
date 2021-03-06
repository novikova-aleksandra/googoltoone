package com.noveogroup.googoltoone.database;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

public class ContentDescriptor {
    private static final String AUTHORITY = "com.noveogroup.googoltoone.GTOContentProvider";
    private static final Uri CONTENT_BASE_URI = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT).authority(AUTHORITY).build();

    private ContentDescriptor() {
        throw new UnsupportedOperationException();
    }

    // parser for URI
    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH) {
        {
            // registry combinations URI
            addURI(AUTHORITY, Players.TABLE_NAME, Players.ALL_URI_CODE);
            addURI(AUTHORITY, Players.TABLE_NAME + "/#/", Players.URI_CODE);
            addURI(AUTHORITY, Games.TABLE_NAME, Games.ALL_URI_CODE);
            addURI(AUTHORITY, Games.TABLE_NAME + "/#/", Games.URI_CODE);
            addURI(AUTHORITY, GamesWithPlayersNames.TABLE_NAME, GamesWithPlayersNames.ALL_URI_CODE);
            addURI(AUTHORITY, GamesWithPlayersNames.TABLE_NAME + "/#/", GamesWithPlayersNames.URI_CODE);
        }

        @Override
        public int match(Uri uri) {
            final int result = super.match(uri);
            if (result < 0) {
                throw new IllegalArgumentException("URI " + uri.toString() + " could not be matched.");
            } else {
                return result;
            }
        }

    };

    // Description for each tables
    public static class Players {
        public static final String TABLE_NAME = "players";

        public static final Uri TABLE_URI = CONTENT_BASE_URI.buildUpon().appendPath(TABLE_NAME).build();
        public static final int ALL_URI_CODE = 0;
        public static final int URI_CODE = 1;

        public static class Cols {
            public static final String ID = BaseColumns._ID;
            public static final String NAME = "name";
        }
    }

    public static class Games {
        public static final String TABLE_NAME = "games";

        public static final Uri TABLE_URI = CONTENT_BASE_URI.buildUpon().appendPath(TABLE_NAME).build();
        public static final int ALL_URI_CODE = 2;
        public static final int URI_CODE = 3;

        public static class Cols {
            public static final String ID = BaseColumns._ID;
            public static final String ID_PLAYER1 = "id_player1";
            public static final String ID_PLAYER2 = "id_player2";
            public static final String PLAYER1_SCORE = "player1_score";
            public static final String PLAYER2_SCORE = "player2_score";
            public static final String TIME_FINISH = "time_finish";
        }
    }

    // special abstract table for view best scores
    public static class GamesWithPlayersNames {
        public static final String TABLE_NAME = "gameswithplayersnames";

        public static final Uri TABLE_URI = CONTENT_BASE_URI.buildUpon().appendPath(TABLE_NAME).build();
        public static final int ALL_URI_CODE = 4;
        public static final int URI_CODE = 5;
    }

    public static String getTableName(int uriCode) {
        switch (uriCode) {
            case Players.ALL_URI_CODE:
            case Players.URI_CODE:
                return Players.TABLE_NAME;
            case Games.ALL_URI_CODE:
            case Games.URI_CODE:
                return Games.TABLE_NAME;
            case GamesWithPlayersNames.ALL_URI_CODE:
            case GamesWithPlayersNames.URI_CODE:
                return GamesWithPlayersNames.TABLE_NAME;
        }
        throw new IllegalArgumentException("uriCode " + uriCode);
    }
}
