package muck_bot.maven.eclipse;

import java.util.HashMap;
import java.util.Map;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class Main 
{
    private static final Map<String, Command> COMMANDS = new HashMap<>();
    public static void main(String[] args)
    {	
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