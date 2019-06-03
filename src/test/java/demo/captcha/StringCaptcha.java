package demo.captcha;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

public class StringCaptcha {
	private static final SecureRandom random = new SecureRandom();
	private static String[] SYSTEM_FONT = null;
	static {
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		SYSTEM_FONT = environment.getAvailableFontFamilyNames();
	}

	private String[] RANDOM_FONT = SYSTEM_FONT;
	private int width = 120;
	private int height = 30;
	private int charCount = 4;
	private int disturbLineCount = 100;
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
		this.disturbLineCount = lineCount;
		this.create();
	}

	public StringCaptcha(int width, int height, int codeCount, int lineCount, String captchaSequence) {
		this.width = width;
		this.height = height;
		this.charCount = codeCount;
		this.disturbLineCount = lineCount;
		this.captchaSequence = captchaSequence;
		this.captchaSequenceLength = captchaSequence.length();
		this.create();
	}

	public void create() {
		int fontWidth = width / (charCount + 2),
				fontHeight = (int) (height * 0.95),
				paddingTop = (int) (fontHeight * 0.89);

		// init
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic = image.createGraphics();
		graphic.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphic.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// setting background color
		graphic.setColor(randomColor(210, 250));
		graphic.fillRect(0, 0, width, height);

		// draw disturb line
		for (int i = 0; i < disturbLineCount; i++) {
			int xs = random.nextInt(width);
			int ys = random.nextInt(height);
			int xe = xs + random.nextInt(width / 8);
			int ye = ys + random.nextInt(height / 8);
			graphic.setColor(randomColor(120, 200));
			graphic.drawLine(xs, ys, xe, ye);
		}

		// setting font
		String fontName = RANDOM_FONT[random.nextInt(RANDOM_FONT.length)];
		Font font = new Font(fontName, Font.BOLD, fontHeight);
		graphic.setFont(font);

		//
//		for(int i = 0; i < 20; i++){
//			graphic.setColor(randomColor(120, 200));
//			int randomNum = random.nextInt(captchaSequenceLength);
//			String randomString = String.valueOf(captchaSequence.charAt(randomNum));
//			graphic.drawString(randomString, random.nextInt(width), random.nextInt(height));
//		}

		// draw random string
		StringBuilder captchaBuilder = new StringBuilder();
		for (int i = 0; i < charCount; i++) {
			int randomNum = random.nextInt(captchaSequenceLength);
			String randomString = String.valueOf(captchaSequence.charAt(randomNum));
			graphic.setColor(randomColor(20, 130));
			graphic.drawString(randomString, (i + 1) * fontWidth, paddingTop);
			captchaBuilder.append(randomString);
		} captchaString = captchaBuilder.toString();

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

	private Color randomColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}
