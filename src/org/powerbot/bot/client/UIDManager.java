package org.powerbot.bot.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import org.powerbot.misc.CryptFile;
import org.powerbot.util.IOUtils;

/**
 * @author Timer
 */
class UIDManager {
	private static final String newline = System.getProperty("line.separator");
	private static final String separator = "#";
	private final CryptFile store;

	private final HashMap<String, byte[]> uids = new HashMap<String, byte[]>();
	private String lastUsed = "";

	public UIDManager() {
		store = new CryptFile("uidmanager", UIDManager.class);

		final InputStream is;
		try {
			is = store.getInputStream();
		} catch (final IOException ignored) {
			return;
		}
		for (final String line : IOUtils.readString(is).split("\n")) {
			if (!line.isEmpty()) {
				final String[] data = line.split(separator, 2);
				if (data.length == 2) {
					uids.put(data[0], data[1].getBytes());
				}
			}
		}
		try {
			is.close();
		} catch (final IOException ignored) {
		}
	}

	public String getLastUsed() {
		return lastUsed;
	}

	public byte[] getUID(String name) {
		if (name.equals("")) {
			name = "DEFAULT";
		}
		lastUsed = name;

		final byte[] data = uids.get(name);
		if (data == null) {
			return new byte[0];
		}
		return data;
	}

	public void setUID(String name, final byte[] uid) {
		if (name.equals("")) {
			name = "DEFAULT";
		}

		uids.put(name, uid);
	}

	public void save() {
		OutputStream os = null;
		try {
			os = store.getOutputStream();
			for (final String key : uids.keySet()) {
				os.write(key.getBytes());
				os.write(separator.getBytes());
				os.write(uids.get(key));
				os.write(newline.getBytes());
			}
		} catch (final IOException ignored) {
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (final IOException ignored) {
				}
			}
		}
	}
}
