package net.londonunderground.render;

import net.londonunderground.Main;
import net.londonunderground.blocks.BlockRoundelBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;

import java.util.Locale;

public class RenderRoundel extends RenderRoundelBase {

    final Style style = Style.EMPTY.withFont(new Identifier(Main.MOD_ID, "johnston"));
    final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

    public RenderRoundel(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void drawStationName(BlockRoundelBase.TileEntityBlockRoundelBase entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, VertexConsumerProvider.Immediate immediate, String stationName, int color, int light) {
        final OrderedText roundelText = new LiteralText(stationName.toUpperCase(Locale.ROOT)).fillStyle(style).asOrderedText();
        int buffer = textRenderer.getWidth(stationName);
        float scale = Math.min(0.97F/buffer,0.014F);
        matrices.scale(scale,scale,scale);
        matrices.translate(0,-0.2F,-0.2F);
        drawStuff(matrices, roundelText, vertexConsumers, light, buffer);


    }

    private void drawStuff(MatrixStack matrices, OrderedText roundelText, VertexConsumerProvider vertexConsumers, int light, int buffer) {
        matrices.push();
        textRenderer.draw(roundelText, -(buffer/2), -3, 0xFFB3B3B3, false, matrices.peek().getModel(), vertexConsumers, false, 0, light);
        matrices.pop();
    }

}
