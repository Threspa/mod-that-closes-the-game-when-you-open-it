package fart.mtctgwyoi;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class ModThatClosesTheGameWhenYouOpenIt implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(new ClientTickEvents.EndTick() {
            private boolean started = false;

            @Override
            public void onEndTick(net.minecraft.client.MinecraftClient client) {
                if (started) return;
                started = true;

                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ignored) {}

                    client.execute(() -> {
                        System.out.println("Closing your game now.");
                        client.stop();
                    });
                }).start();
            }
        });
    }
}