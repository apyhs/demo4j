package demo.jfinal.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jfinal.kit.LogKit;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

public class BinaryRender extends Render {

	private InputStream in;
	private String fileName;
	
	public BinaryRender(byte[] bytes, String fileName){
		in = new ByteArrayInputStream(bytes);
		this.fileName = fileName;
	}
	
	public BinaryRender(InputStream in, String fileName){
		this.in = in;
		this.fileName = fileName;
	}
	
	@Override
	public void render() {
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(getEncoding()), "ISO8859-1"));
			String contentType = request.getSession().getServletContext().getMimeType(fileName);
			response.setContentType(contentType != null ? contentType : "application/octet-stream");
			inputStream = new BufferedInputStream(in);
	        outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            for (int len = -1; (len = inputStream.read(buffer)) != -1;) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
		} catch (IOException e) {
        	if (getDevMode()) {
        		throw new RenderException(e);
        	}
        } catch (Exception e) {
        	throw new RenderException(e);
        } finally {
            if (inputStream != null)
                try {inputStream.close();} catch (IOException e) {LogKit.error(e.getMessage(), e);}
            if (outputStream != null)
            	try {outputStream.close();} catch (IOException e) {LogKit.error(e.getMessage(), e);}
        }
	}

}
