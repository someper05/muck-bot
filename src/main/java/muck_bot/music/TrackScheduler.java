package muck_bot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public final class TrackScheduler extends AudioEventAdapter implements AudioLoadResultHandler
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
	player.playTrack(track);;
    }

    @Override
    public void playlistLoaded(final AudioPlaylist playlist)
    {	

    }

    @Override
    public void noMatches()
    {
	System.out.println("No matches found.");
    }

    @Override
    public void loadFailed(final FriendlyException exception)
    {
	System.out.println(exception.getMessage());
    }
}