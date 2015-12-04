package barqsoft.footballscores.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import barqsoft.footballscores.factories.RemoteViewsFactory;

/**
 * Created by darby on 12/3/15.
 */
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new barqsoft.footballscores.factories.RemoteViewsFactory(getApplicationContext());
    }
}
