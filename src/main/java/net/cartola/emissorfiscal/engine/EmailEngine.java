package net.cartola.emissorfiscal.engine;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;

/**
  * @date 12 de mar. de 2021
  * @author robson.costa
  */
@Service
public class EmailEngine {
	
	private static final Logger LOG = Logger.getLogger(EmailEngine.class.getName());
//	private EmailModel emailModel;
	
	
	/**
	 * Envia email usando o Sendgrid
	 * @param mail
	 */
	public void enviarEmail(Mail mail) {
//		Mail mail = emailSendGrid.getMail();
		LOG.log(Level.INFO, "Enviando o email: Subject/Title => {0}" ,mail.getSubject());
	    SendGrid sg = new SendGrid("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");

		Request request = new Request();
		request.setMethod(Method.POST);
		request.setEndpoint("mail/send");
		
		try {
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			LOG.log(Level.WARNING, "ERRO ao tentar enviar email: " +ex.getMessage());
		}
		LOG.log(Level.INFO, "Email enviado com sucesso ");
	}
	
}
