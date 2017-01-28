package netty;

/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */


        import java.util.ArrayList;
        import java.util.List;

        import astro.backend.server.GameServer;
        import astro.backend.server.event.action.ActionEvent;
        import com.fasterxml.jackson.core.JsonProcessingException;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import io.netty.buffer.ByteBufInputStream;
        import io.netty.channel.ChannelFuture;
        import io.netty.channel.ChannelHandlerContext;
        import io.netty.channel.SimpleChannelInboundHandler;
        import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
        import io.netty.handler.codec.http.websocketx.WebSocketFrame;
        import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

/**
 * Echoes uppercase content of text frames.
 */
public class  WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketFrameHandler.class);

    private ObjectMapper mapper;

    private GameServer gameServer;

    public WebSocketFrameHandler(GameServer gameServer, ObjectMapper objectMapper) {
        super();
        this.gameServer = gameServer;
        this.mapper = objectMapper;
    }

    public void writeToChannel(Player player, Object message) {

        try {
            player.getChannel().writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(message)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);

        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {

            Player p = new Player(ctx.channel());
               gameServer.addPlayer(p);
            ChannelFuture f = ctx.channel().writeAndFlush(new TextWebSocketFrame("Hello to this server"));
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // ping and pong frames already handled
        //PlanetMock mock = createPlanetMock();

        logger.info("{} received {}", ctx.channel());

        if (frame instanceof TextWebSocketFrame) {
            // Send the uppercase string back.
            String request = ((TextWebSocketFrame) frame).text();
            logger.info("{} received {}", ctx.channel(), request);

            ByteBufInputStream byteBufInputStream = new ByteBufInputStream(frame.content());
            ActionEvent received = mapper.readValue(byteBufInputStream, ActionEvent.class);

            gameServer.addActionToQueue(received, ctx.channel());

            //ctx.channel().writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(mock)));
            //ctx.channel().writeAndFlush(new TextWebSocketFrame(request.toUpperCase(Locale.US)));

        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }

    //TODO cleanup
    private PlanetMock createPlanetMock(){


        PlanetMock.Coordinates position =  new PlanetMock.Coordinates();
        position.setX(0);
        position.setY(0);

        PlanetMock.Tile tile = new PlanetMock.Tile();
        tile.setPosition(position);
        tile.setType(PlanetMock.Tile.Type.Barren);
        List<PlanetMock.Tile> list = new ArrayList<>();
        list.add(tile);

        PlanetMock.Coordinates size =  new PlanetMock.Coordinates();
        size.setX(1);
        size.setY(1);

        PlanetMock pm = new PlanetMock();
        pm.setSize(size);
        pm.setTiles(list);
        return pm;
    }
}