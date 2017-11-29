package demo.mail;

import demo.FileKit;
import demo.StrKit;
import demo.StreamKit;
import demo.TimeKit;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.util.Date;

public class MailKit {

	public static Folder getFolder(Store store, String folderName) {
		return getFolder(store, folderName, false);
	}

	public static Folder getFolder(Store store, String folderName, boolean isReadOnly) {
		try {
			Folder folder = store.getFolder(folderName);
			folder.open(isReadOnly ? Folder.READ_ONLY : Folder.READ_WRITE);
			return folder;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void close(Store store, Folder... folders) {
		try {
			close(folders); if(store != null) store.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void close(Folder... folders) {
		try {
			if(folders != null && folders.length != 0) {
				for (Folder folder : folders) folder.close(true);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String stateInfo(Folder folder) {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("Unread  : ").append(folder.getUnreadMessageCount()).append(StrKit.LSEP);
			buffer.append("Deleted : ").append(folder.getDeletedMessageCount()).append(StrKit.LSEP);
			buffer.append("New     : ").append(folder.getNewMessageCount()).append(StrKit.LSEP);
			buffer.append("All     : ").append(folder.getMessageCount()).append(StrKit.LSEP);
			return buffer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String msgList(Message... messages) {
		try {
			StringBuffer buffer = new StringBuffer();
			if(messages != null && messages.length != 0) {
				for (Message message : messages) {
					int messageNumber = message.getMessageNumber();
					buffer.append("---------------- ").append(messageNumber).append(" ----------------").append(StrKit.LSEP);
					buffer.append("Subject    : ").append(getSubject(message)).append(StrKit.LSEP);
					buffer.append("From       : ").append(getFrom(message)).append(StrKit.LSEP);
					buffer.append("Receiver   : ").append(getReceiver(message)).append(StrKit.LSEP);
					buffer.append("SentDate   : ").append(getSentDate(message)).append(StrKit.LSEP);
					buffer.append("isRead     : ").append(isRead(message)).append(StrKit.LSEP);
					buffer.append("Priority   : ").append(getPriority(message)).append(StrKit.LSEP);
					buffer.append("Size       : ").append(message.getSize()).append(" byte").append(StrKit.LSEP);
					buffer.append("Attachment : ").append(containAttachment(message)).append(StrKit.LSEP);
					String content = getTextContent(message);
					content = content.length() > 64 ? content.substring(0, 64) : content;
					buffer.append("Content    : ").append(content).append(StrKit.LSEP);
					buffer.append("---------------- ").append(messageNumber).append(" ----------------").append(StrKit.LSEP);
					buffer.append(StrKit.LSEP);
				}
			} return buffer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String msgDetail(Message message) {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("Subject    : ").append(getSubject(message)).append(StrKit.LSEP);
			buffer.append("From       : ").append(getFrom(message)).append(StrKit.LSEP);
			buffer.append("Receiver   : ").append(getReceiver(message)).append(StrKit.LSEP);
			buffer.append("SentDate   : ").append(getSentDate(message)).append(StrKit.LSEP);
			buffer.append("isRead     : ").append(isRead(message)).append(StrKit.LSEP);
			buffer.append("Priority   : ").append(getPriority(message)).append(StrKit.LSEP);
			buffer.append("Size       : ").append(message.getSize()).append(" byte").append(StrKit.LSEP);
	        buffer.append("Attachment : ").append(containAttachment(message)).append(StrKit.LSEP);
	        buffer.append("Content    : ").append(getTextContent(message)).append(StrKit.LSEP);
	        return buffer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getSubject(Message message)
			throws UnsupportedEncodingException, MessagingException {
        return MimeUtility.decodeText(message.getSubject());
    }
	
	public static String getFrom(Message message) 
			throws MessagingException, UnsupportedEncodingException {
        Address[] froms = message.getFrom();
        if(froms == null || froms.length == 0)
        	throw new MessagingException("don't have from!");
        InternetAddress address = (InternetAddress) froms[0];
        String nickname = address.getPersonal();
        nickname = nickname != null ? MimeUtility.decodeText(nickname) : "";
        return nickname + "<" + address.getAddress() + ">";
    }

	public static String getReceiver(Message message) throws MessagingException {
		return getReceiver(message, null);
	}

	public static String getReceiver(Message message, Message.RecipientType type) 
			throws MessagingException {
        StringBuffer result = new StringBuffer();
        Address[] addresses = type == null ? message.getAllRecipients() : message.getRecipients(type);
        if(addresses == null || addresses.length == 0)
        	throw new MessagingException("don't have receiver!");
        for (Address address : addresses) {
            result.append(((InternetAddress)address).toUnicodeString()).append(",");
        } result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
	
	public static String getSentDate(Message message) throws MessagingException {
		return getSentDate(message, null);
	}
	
	public static String getSentDate(Message message, String pattern) throws MessagingException {  
        Date date = message.getSentDate(); if(date == null) return "";  
        return StrKit.notBlank(pattern) ? TimeKit.format(pattern, date) : TimeKit.format(date);
    }
	
	public static boolean containAttachment(Part part) throws MessagingException, IOException {  
        boolean flag = false;
        if (part.isMimeType("multipart/*")) {
            MimeMultipart multipart = (MimeMultipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) 
                		|| disp.equalsIgnoreCase(Part.INLINE))) {
                    flag = true;
                } else if (bodyPart.isMimeType("multipart/*")) {
                    flag = containAttachment(bodyPart);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.indexOf("application") != -1) flag = true;
                    if (contentType.indexOf("name") != -1) flag = true;
                } if (flag) break;
            }
        } else if (part.isMimeType("message/rfc822")) {
            flag = containAttachment((Part)part.getContent());
        } return flag;
    }
	
	public static boolean isRead(Message message) throws MessagingException {
        return message.getFlags().contains(Flags.Flag.SEEN);
    }
	
	public static String getPriority(Message message) throws MessagingException {  
        String[] headers = message.getHeader("X-Priority");
        if(headers == null) return "Not Found"; String header = headers[0];
        if (header.indexOf("1") != -1 || header.indexOf("High") != -1) return "High";
        else if (header.indexOf("5") != -1 || header.indexOf("Low") != -1) return "Low";
        else return "Normal";
    }
	
	public static String getTextContent(Part part) throws MessagingException, IOException {
		StringBuffer buffer = new StringBuffer();
		getTextContent(part, buffer);
		return buffer.toString();
	}
	
	public static void getTextContent(Part part, StringBuffer buffer) 
			throws MessagingException, IOException {
        boolean containTextAttach = part.getContentType().indexOf("name") > 0;
        if (part.isMimeType("text/*") && !containTextAttach) {
            buffer.append(part.getContent().toString());
        } else if (part.isMimeType("message/rfc822")) {
            getTextContent((Part)part.getContent(), buffer);
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                getTextContent(bodyPart, buffer);
            }
        }
    }
	
	public static void saveAttachment(Part part, String destDir) throws MessagingException, IOException{
		if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				String disp = bodyPart.getDisposition();
				if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || 
						disp.equalsIgnoreCase(Part.INLINE) || 
						bodyPart.getContentType().indexOf("name") != -1 || 
						bodyPart.getContentType().indexOf("application") != -1)) {
					InputStream in = bodyPart.getInputStream();
					File dest = new File(destDir, MimeUtility.decodeText(bodyPart.getFileName()));
					FileKit.create(dest); FileOutputStream out = new FileOutputStream(dest);
					StreamKit.write(in, out); StreamKit.close(out, in);
				} else if (bodyPart.isMimeType("multipart/*")) {
					saveAttachment(bodyPart,destDir);
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveAttachment((Part) part.getContent(), destDir);
		}
	}
	
}
