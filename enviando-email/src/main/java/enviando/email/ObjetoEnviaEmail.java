package enviando.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaEmail {

	private String username = "gabrieltreinamentojava@gmail.com";
	private String senha = "yysrvvapvfnhannw";

	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";

	/* Construtor */
	public ObjetoEnviaEmail(String listaDestinatario, String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios = listaDestinatario;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}

	public void enviarEmail(boolean envioHtml) throws Exception {

		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "*");// Autentificação
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls", "true");/* Parte de Segurança da propriede Java-mail */
		properties.put("mail.smtp.host", "smtp.gmail.com");/* Servidor gmail Google */
		properties.put("mail.smtp.port", "465");/* Porta do Servidor do Google que esta liberada */
		properties.put("mail.smtp.socketFactory.port", "465");/* Especific a porta a ser conectada pelo socket */
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");/* Classe socket de conexão ao smtp */

		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, senha);
			}
		});

		Address[] toUser = InternetAddress.parse(listaDestinatarios);
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username, nomeRemetente));/* Quem está enviando */
		message.setRecipients(Message.RecipientType.TO, toUser);
		;/* E-mail de Destino */
		message.setSubject(assuntoEmail);/* Assunto do e-mail */

		if (envioHtml) {
			message.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			message.setText(textoEmail);
		}

		Transport.send(message);
	}

	public void enviarEmailAnexo(boolean envioHtml) throws Exception {

		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "*");// Autentificação
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls", "true");/* Parte de Segurança da propriede Java-mail */
		properties.put("mail.smtp.host", "smtp.gmail.com");/* Servidor gmail Google */
		properties.put("mail.smtp.port", "465");/* Porta do Servidor do Google que esta liberada */
		properties.put("mail.smtp.socketFactory.port", "465");/* Especific a porta a ser conectada pelo socket */
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");/* Classe socket de conexão ao smtp */

		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, senha);
			}
		});

		Address[] toUser = InternetAddress.parse(listaDestinatarios);
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username, nomeRemetente));/* Quem está enviando */
		message.setRecipients(Message.RecipientType.TO, toUser);
		;/* E-mail de Destino */
		message.setSubject(assuntoEmail);/* Assunto do e-mail */

		MimeBodyPart corpoEmail = new MimeBodyPart();

		if (envioHtml) {
			corpoEmail.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			corpoEmail.setText(textoEmail);
		}

		List<FileInputStream> arquivos = new ArrayList<FileInputStream>();

		arquivos.add(simuladorDePDF());
		arquivos.add(simuladorDePDF());
		arquivos.add(simuladorDePDF());
		arquivos.add(simuladorDePDF());

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(corpoEmail);

		int index = 0;
		for (FileInputStream fileInputStream : arquivos) {

			MimeBodyPart anexoEmail = new MimeBodyPart();
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
			anexoEmail.setFileName("anexoemail"+index+".pdf");

			multipart.addBodyPart(anexoEmail);
			
			index++;
		}

		message.setContent(multipart);

		Transport.send(message);
	}

	/*
	 * Esse Método simula o PDF ou qualquer arquivo que possa ser enviado no e-mail
	 */
	private FileInputStream simuladorDePDF() throws Exception {

		Document document = new Document();
		File file = new File("fileanexo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Conteudo do PDF anexo com Java Mail, esse texto é do PDF"));
		document.close();

		return new FileInputStream(file);
	}

}
