package barqsoft.footballscores.factories;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.ScoresAdapter;
import barqsoft.footballscores.Utilies;

/**
 * Created by darby on 12/3/15.
 */
public class RemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final static String TAG = "REMOTE_V_FACTORY";

    private Context mContext;
    private Cursor mCursor;

    public RemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        String[] scoresDate = new String[1];
        Date fragmentdate = new Date(System.currentTimeMillis() + 86400000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        scoresDate[0] = dateFormat.format(fragmentdate);
        mCursor = mContext.getContentResolver().query(DatabaseContract.scores_table.buildScoreWithDate(),
                null, null, scoresDate, null);
    }

    @Override
    public void onDataSetChanged() {
        String[] scoresDate = new String[1];
        Date fragmentdate = new Date(System.currentTimeMillis() + 86400000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        scoresDate[0] = dateFormat.format(fragmentdate);
        mCursor = mContext.getContentResolver().query(DatabaseContract.scores_table.buildScoreWithDate(),
                null, null, scoresDate, null);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return (mCursor != null) ? mCursor.getCount() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

        if (mCursor.moveToPosition(position)) {
            rv.setTextViewText(R.id.home_name, mCursor.getString(ScoresAdapter.COL_HOME));
            rv.setTextViewText(R.id.away_name, mCursor.getString(ScoresAdapter.COL_AWAY));
            rv.setTextViewText(R.id.data_textview, mCursor.getString(ScoresAdapter.COL_MATCHTIME));
            rv.setTextViewText(R.id.score_textview, Utilies.getScores(mCursor.getInt(ScoresAdapter.COL_HOME_GOALS), mCursor.getInt(ScoresAdapter.COL_AWAY_GOALS)));
            rv.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(mCursor.getString(ScoresAdapter.COL_HOME)));
            rv.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(mCursor.getString(ScoresAdapter.COL_AWAY)));
        }

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
