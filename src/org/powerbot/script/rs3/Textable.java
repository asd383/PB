package org.powerbot.script.rs3;

import org.powerbot.script.Filter;

public interface Textable {
	public String getText();

	public interface Query<T> {
		public T text(String... texts);
	}

	public class Matcher implements Filter<Textable> {
		private final String[] texts;

		public Matcher(final String... texts) {
			this.texts = new String[texts.length];
			for (int i = 0; i < texts.length; i++) {
				this.texts[i] = texts[i].toLowerCase();
			}
		}

		@Override
		public boolean accept(final Textable t) {
			String str = t.getText();
			if (str == null) {
				return false;
			}
			str = str.toLowerCase();
			for (final String text : texts) {
				if (text != null && text.contains(str)) {
					return true;
				}
			}
			return false;
		}
	}
}