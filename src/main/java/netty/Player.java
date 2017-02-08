package netty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {

    private static final Logger logger = LogManager.getLogger();
    private final Channel channel;
    private final ObjectMapper objectMapper;
    private boolean deleted = false;

    public Player(Channel channel, ObjectMapper objectMapper) {
        this.channel = channel;
        this.objectMapper = objectMapper;
    }

    public boolean isDeleted(){
        return deleted;
    }

    public void delete(){
        deleted = true;
    }

    public void send(Object object) {

        logger.debug("sending {} ", object);
        try {
            send(objectMapper.writer().writeValueAsString(object));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        if (channel.isOpen()) {
            channel.writeAndFlush(new TextWebSocketFrame(message));
        } else {
            throw new RuntimeException("channel is closed");
        }
    }


    public boolean hasChannel(Channel channel) {
        return this.channel == channel;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return channel != null ? channel.equals(player.channel) : player.channel == null;
    }

    @Override
    public int hashCode() {
        return channel != null ? channel.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Player{" +
                "channel=" + channel +
                '}';
    }
}
