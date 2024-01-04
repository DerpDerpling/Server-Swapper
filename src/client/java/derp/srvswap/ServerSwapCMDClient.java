package derp.srvswap;

import derp.srvswap.commands.ServerSwapCMD;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;


public class ServerSwapCMDClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, derp) -> {
			ServerSwapCMD.register(dispatcher);
		});
		}

	}