package muck_bot.maven.eclipse;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;


public class Main 
{
    public static void main( String[] args )
    {
	
	final String token = "ODc2NTQ4OTA5MDgwNjY2MTky.YRlryQ.GKTLEhYEgSWoqhmFGWDZ_uz3fOo";
	final DiscordClient client = DiscordClient.create(token);
	final GatewayDiscordClient gateway = client.login().block();
	gateway.on(MessageCreateEvent.class).subscribe(event -> {
	    final Message message = event.getMessage();	    
	    if ("!ping".equals(message.getContent())) 
	    {		
	        final MessageChannel channel = message.getChannel().block();
	        
	        channel.createMessage("Pong!").block();
	    }
	 });
	 gateway.onDisconnect().block();
    }
}
