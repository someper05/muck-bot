package muck_bot.maven.eclipse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.voice.AudioProvider;
import muck_bot.music.LavaPlayerAudioProvider;
import muck_bot.music.TrackScheduler;

public class Main 
{
    private static final Map<String, Command> COMMANDS = new HashMap<>();
    public static void main(String[] args)
    {	
	final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
	playerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
	AudioSourceManagers.registerRemoteSources(playerManager);
	final AudioPlayer player = playerManager.createPlayer();
	AudioProvider provider = new LavaPlayerAudioProvider(player);
	
	COMMANDS.put("join", event -> {
	    final Member member = event.getMember().orElse(null);
	    if (member != null)
	    {
	        final VoiceState voiceState = member.getVoiceState().block();
	        if (voiceState != null)
	        {
	            final VoiceChannel channel = voiceState.getChannel().block();
	            if (channel != null)
	            {
	                channel.join(spec -> spec.setProvider(provider)).block();
	            }
	        }
	    }
	});
	
	final TrackScheduler scheduler = new TrackScheduler(player);
	COMMANDS.put("play", event -> {
	    final String content = event.getMessage().getContent();
	    final List<String> command = Arrays.asList(content.split(" "));
	    playerManager.loadItem(command.get(1), scheduler);
	});
	
	final DiscordClient client = DiscordClient.create(new BotToken().getToken());
	final GatewayDiscordClient gateway = client.login().block();
	gateway.getEventDispatcher().on(MessageCreateEvent.class)
	    .subscribe(event -> {
	        final String content = event.getMessage().getContent(); 
	        for (final Map.Entry<String, Command> entry : COMMANDS.entrySet())
	        {
	            if (content.startsWith('!' + entry.getKey()))
	            {
	                entry.getValue().execute(event);
	                break;
	            }
	        }
	    });
	gateway.onDisconnect().block();
    }
    
    static 
    {
	COMMANDS.put("ping", event -> event.getMessage().getChannel().block()
	        .createMessage("Pong!").block());		
    }  
}