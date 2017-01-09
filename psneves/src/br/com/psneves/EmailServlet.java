package br.com.psneves;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class EmailServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			String nome = req.getParameter("nome");
			String email = req.getParameter("e-mail");
			String assunto = req.getParameter("assunto");
			String conteudo = req.getParameter("mensagem");

			if (nome.length() > 200) {
				nome = nome.substring(0, 200);
			}

			if (email.length() > 200) {
				email = email.substring(0, 200);
			}

			if (assunto.length() > 200) {
				assunto = assunto.substring(0, 200);
			}

			if (conteudo.length() > 10000) {
				conteudo = conteudo.substring(0, 200);
			}

			StringBuilder sb = new StringBuilder();
			sb.append("Mensagem enviada por: ").append(nome);
			sb.append("\n");
			sb.append("Assunto: ").append(assunto);
			sb.append("\n\n");
			sb.append(conteudo);
			sb.append("\n\n");
			sb.append("E-mail: ").append(email);

			conteudo = sb.toString();
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("paulo@psneves.com.br"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("paulo@psneves.com.br", "Paulo Neves"));
			msg.setSubject("psneves.com.br: " + assunto);
			msg.setText(conteudo);
			Transport.send(msg);
			resp.getWriter().println("Mensagem enviada com sucesso!");
		} catch (Exception e) {
			resp.getWriter().println("Ocorreu um erro e o sua mensagem não foi enviada.");
			resp.getWriter().println("Envie um e-mail para paulo@psneves.com.br com a sua mensagem.");
			resp.getWriter().println("\n");
			resp.getWriter().println("Desculpe pelo incoveniente.");
		}

		resp.setContentType("text/plain");
	}
}
