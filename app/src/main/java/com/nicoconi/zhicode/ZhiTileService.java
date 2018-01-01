package com.nicoconi.zhicode;

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

public class ZhiTileService extends TileService {

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {
        super.onClick();
        Util.startAlipayWithZhicode(getApplicationContext(), R.string.zhicode_hongbao);
    }
}
