package net.cartola.emissorfiscal.engine;

import java.io.Serializable;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import lombok.Getter;

/**
  * @date 12 de mar. de 2021
  * @author robson.costa
  */
@Getter
//@Setter
public class EmailModel implements Serializable {

	private static final long serialVersionUID = -5181614413321565438L;

	private String from;
	private String to;
	private String title;
	private String contentType = "text/html";
	private String contentHtml;
	
	public EmailModel() {
		
	}
	
	public EmailModel withFrom(String from) {
		this.from = from;
		return this;
	}

	public EmailModel withTo(String to) {
		this.to = to;
		return this;
	}

	public EmailModel withTitle(String title) {
		this.title = title;
		return this;
	}

	public EmailModel withContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}

	public EmailModel withContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
		return this;
	}
	
	public Mail build() {
		Email from = new Email(this.from);
		Email to = new Email(this.to);
//		String title;
		Content content = new Content(this.contentType, this.contentHtml);
		Mail mail = new Mail(from, this.title, to, content);
		return mail;
	}
	
	
}
