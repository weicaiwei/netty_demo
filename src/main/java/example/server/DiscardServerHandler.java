package example.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2019-12-29
 */
@Slf4j
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(final ChannelHandlerContext channelHandlerContext) {

        final ByteBuf time = channelHandlerContext.alloc().buffer(4); // (2)
        int timeValue = (int) (System.currentTimeMillis() / 1000L + 2208988800L);
/*        time.writeInt(timeValue);
        final ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(time); // (3)
        channelFuture.addListener(ChannelFutureListener.CLOSE);*/
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object message) {

        /*ByteBuf in = (ByteBuf) message;
        try {
            //java.lang.UnsupportedOperationException: direct buffer
            //byte[] data = in.array();
        } finally {
            ReferenceCountUtil.release(message);
        }*/
        channelHandlerContext.write(message);
        channelHandlerContext.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        // Close the connection when an exception is raised.
        log.error("exception:", cause);
        channelHandlerContext.close();
    }
}
