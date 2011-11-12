package com.ttdev.wicketpagetest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleLauncher {
	private static Logger LOGGER = LoggerFactory
			.getLogger(ConsoleLauncher.class);
	private Process p;
	private String cmdExecutingConsole;

	public ConsoleLauncher() {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("win")) {
			cmdExecutingConsole = "cmd /c start";
		} else {
			cmdExecutingConsole = "x-terminal-emulator -e";
		}
	}

	public void launchConsoleToExecute(String[] cmdToExecute) {
		String fullCmdLine = cmdExecutingConsole;
		for (String cmdPart : cmdToExecute) {
			fullCmdLine += " " + quoteAsNeeded(cmdPart);
		}
		LOGGER.info("Executing {}", fullCmdLine);
		try {
			p = Runtime.getRuntime().exec(fullCmdLine);
			ignoreChildIO();
		} catch (IOException e) {
			LOGGER.warn("Error launching the command in console", e);
		}

	}

	private void ignoreChildIO() throws IOException {
		p.getInputStream().close();
		p.getErrorStream().close();
		p.getOutputStream().close();
	}

	public void stop() {
		if (p != null) {
			p.destroy();
		}
	}

	private String quoteAsNeeded(String c) {
		return c.contains(" ") ? "\"" + c + "\"" : c;
	}

}
