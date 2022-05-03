package com.varuns2002.audio_channel_qs_tile

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class AudioChannelQSTile : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        val monoEnabled: Boolean = try {
            Settings.System.getInt(contentResolver, "master_mono") == 1
        } catch (exception: Settings.SettingNotFoundException) {
            exception.printStackTrace()
            false
        }
        if (monoEnabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                qsTile.label = "Audio Channel"
                qsTile.subtitle = "Mono"
            } else {
                qsTile.label = "Mono"
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                qsTile.label = "Audio Channel"
                qsTile.subtitle = "Stereo"
            } else {
                qsTile.label = "Stereo"
            }
        }
        qsTile.state = Tile.STATE_ACTIVE
        qsTile.updateTile()
    }

    override fun onClick() {
        super.onClick()
        if (!Settings.System.canWrite(this)) {
            val intent = Intent("android.settings.action.MANAGE_WRITE_SETTINGS")
            intent.data = Uri.parse("package:$packageName")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            return
        }
        val monoEnabled: Boolean = try {
            Settings.System.getInt(contentResolver, "master_mono") == 1
        } catch (exception: Settings.SettingNotFoundException) {
            exception.printStackTrace()
            false
        }
        if (!monoEnabled) {
            Settings.System.putInt(contentResolver, "master_mono", 1)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                qsTile.label = "Audio Channel"
                qsTile.subtitle = "Mono"
            } else {
                qsTile.label = "Mono"
            }
        } else {
            Settings.System.putInt(contentResolver, "master_mono", 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                qsTile.label = "Audio Channel"
                qsTile.subtitle = "Stereo"
            } else {
                qsTile.label = "Stereo"
            }
        }
        qsTile.updateTile()
    }
}
