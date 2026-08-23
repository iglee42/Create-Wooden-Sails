package fr.iglee42.woodensails.config;

import net.createmod.catnip.config.ConfigBase;

public class CWSServer extends ConfigBase {

	public final ConfigInt woodenSailsPerRPM =
		i(16, 0, "woodenSailsPerRPM", Comments.woodenSailsPerRPM);

	@Override
	public String getName() {
		return "server";
	}

	private static class Comments {
		static String woodenSailsPerRPM =
			"Number of wooden sail blocks required to increase windmill speed by 1RPM. Set to 0 to make them use the default windmill sail value.";
	}

}
