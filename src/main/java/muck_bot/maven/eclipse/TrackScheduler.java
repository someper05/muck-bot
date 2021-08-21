package muck_bot.maven.eclipse;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public final class TrackScheduler implements AudioLoadResultHandler
{
    private final AudioPlayer player;

    public TrackScheduler(final AudioPlayer player)
    {
        this.player = player;
    }
    
    public AudioPlayer getPlayer()
    {
	return player;
    }

    @Override
    public void trackLoaded(final AudioTrack track)
    {
	player.playTrack(track);
    }

    @Override
    public void playlistLoaded(final AudioPlaylist playlist)
    {

    }

    @Override
    public void noMatches()
    {

    }

    @Override
    public void loadFailed(final FriendlyException exception)
    {

    }
}