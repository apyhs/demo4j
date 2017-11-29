package demo.captcha;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

public class StringCaptcha {
	private static final String[] RANDOM_FONT = new String[] {"nyala", "Arial", "Bell MT", "Credit valley", "Impact", "Monospaced"};
	private int width = 120;
	private int height = 30;
	private int charCount = 4;
	private int disturbCount = 100;
	private String captchaSequence = "3456789ABCDEFGHJKMNPQRSTUVWXY";
	private int captchaSequenceLength = captchaSequence.length();
	private String captchaString = null;
	private BufferedImage image = null;

	public StringCaptcha() {
		this.create();
	}

	public StringCaptcha(int width, int height) {
		this.width = width;
		this.height = height;
		this.create();
	}

	public StringCaptcha(int width, int height, int codeCount, int lineCount) {
		this.width = width;
		this.height = height;
		this.charCount = codeCount;
		this.disturbCount = lineCount;
		this.create();
	}

	public StringCaptcha(int width, int height, int codeCount, int lineCount, String captchaSequence) {
		this.width = width;
		this.height = height;
		this.charCount = codeCount;
		this.disturbCount = lineCount;
		this.captchaSequence = captchaSequence;
		this.captchaSequenceLength = captchaSequence.length();
		this.create();
	}

	public void create() {
		int fontWidth = 0, fontHeight = 0, paddingTop = 0;
		int red = 0, green = 0, blue = 0;

		fontWidth = (int) (width / (charCount * 1.4));
		fontHeight = (int) (height * 0.95);
		paddingTop = (int) (fontHeight * 0.89);

		// init
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic = image.createGraphics();
		Random random = new Random();
		graphic.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphic.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		// setting background color
		graphic.setColor(Color.WHITE);
		graphic.fillRect(0, 0, width, height);
		
		// setting font
		String fontName = RANDOM_FONT[random.nextInt(RANDOM_FONT.length)];
		Font font = new Font(fontName, Font.BOLD, fontHeight);
		graphic.setFont(font);

		// draw disturb line
		for (int i = 0; i < disturbCount; i++) {
			int xs = random.nextInt(width);
			int ys = random.nextInt(height);
			int xe = xs + random.nextInt(width / 8);
			int ye = ys + random.nextInt(height / 8);

			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			graphic.setColor(new Color(red, green, blue));
			graphic.drawLine(xs, ys, xe, ye);
		}

		// draw random string
		StringBuffer captchaBuffer = new StringBuffer();
		for (int i = 0; i < charCount; i++) {
			int randomNum = random.nextInt(captchaSequenceLength);
			String randomString = String.valueOf(captchaSequence.charAt(randomNum));
			red = random.nextInt(210);
			green = random.nextInt(210);
			blue = random.nextInt(210);
			graphic.setColor(new Color(red, green, blue));
			graphic.drawString(randomString, (i + 1) * fontWidth, paddingTop);
			captchaBuffer.append(randomString);
		} captchaString = captchaBuffer.toString();
	}

	public byte[] write() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		write(out);
		return out.toByteArray();
	}

	public void write(String path) {
		try {
			OutputStream out = new FileOutputStream(path);
			write(out);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void write(OutputStream out) {
		write(out, "png");
	}

	public void write(OutputStream out, String suffix) {
		try {
			ImageIO.write(image, suffix, out);
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public BufferedImage image() {
		return image;
	}

	public String value() {
		return captchaString;
	}
}
