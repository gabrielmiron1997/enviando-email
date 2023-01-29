package enviando.email;/*
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;*/
/**
 * Unit test for simple App.
 */
public class AppTest 
{
	
	@org.junit.Test
	public void testeEmail() throws Exception {

		StringBuilder stringBuilderTextoEmail = new StringBuilder();
		
		stringBuilderTextoEmail.append("Olá, <br/><br/>");
		stringBuilderTextoEmail.append("Você está Recebendo o acesso ao curso de Java.<br/><br/>");
		stringBuilderTextoEmail.append("Para ter acesso clique no botão abaixo.<br/><br/>");
		stringBuilderTextoEmail.append("<b>Login:</b> gabriel_miron2008@hotmail.com<br/>");
		stringBuilderTextoEmail.append("<b>Senha:</b> 123456<br/><br/>");

		stringBuilderTextoEmail.append("<a target=\"_blank\" href=\"https://projetojavaweb.com/certificado-aluno/inicio\""
				+ " style=\"color: #2525a7; padding: 14px 25px; text-align:center; text-decoration: none; "
				+ "display: inline-block; border-radius: 30px; font-size: 20px; font-family: courier; "
				+ "border: 3px solid green; background-color: #99DA39;\">Acessar Portal o Aluno</a><br/><br/>");

		stringBuilderTextoEmail.append("<span style=\font-size: 8px\"> Ass.: Gabriel Miron Sebastião");

		
		ObjetoEnviaEmail enviaEmail = 
				new ObjetoEnviaEmail("gabriel.miron10@gmail.com", 
								     "Curso de Java E-mail", 
									 "Testando E-mai Java", 
									 stringBuilderTextoEmail.toString());
		
		enviaEmail.enviarEmailAnexo(true);
		
		
		Thread.sleep(3000);
		
	}
}
