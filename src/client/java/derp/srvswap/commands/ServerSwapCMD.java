package derp.srvswap.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class ServerSwapCMD {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("csend")
                .then(CommandManager.argument("serverIp", StringArgumentType.word())
                        .executes(context -> execute(StringArgumentType.getString(context, "serverIp")))));
    }

    private static int execute(String serverIp) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.execute(() -> {
            disconnect();

            if (!ServerAddress.isValid(serverIp)) {
                client.inGameHud.getChatHud().addMessage(Text.literal("Invalid server address: " + serverIp));
                return;
            }

            ServerAddress address = ServerAddress.parse(serverIp);
            ServerInfo info = new ServerInfo("", serverIp, ServerInfo.ServerType.OTHER);

            client.inGameHud.getChatHud().addMessage(Text.literal("Connecting to " + serverIp));

            // Connect to server
            ConnectScreen.connect(new TitleScreen(), client, address, info, true);
        });

        return 1;
    }

    private static void disconnect() {
        MinecraftClient client = MinecraftClient.getInstance();
        World world = client.world;
        if (world != null) {
            client.world.disconnect();
            client.disconnect(new TitleScreen());
        }
    }
}
