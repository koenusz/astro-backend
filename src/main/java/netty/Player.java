package netty;


import io.netty.channel.Channel;

public class Player {

    private Channel channel;

    public Player(Channel channel) {

        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
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
