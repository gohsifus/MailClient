package org.groupid.Model;


import javafx.scene.control.Alert;
import org.groupid.Controller.MainController;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.Properties;

public class SendEmail {

    private final static String PROPS_FILE = "src/main/resources/mail.properties";

    private Message message = null;

    public SendEmail(final String emailTo, final String thema) {


        Properties pr = null;
        InputStream is = null;
        try {
            is = new FileInputStream(PROPS_FILE);

            if (is != null) {
                Reader reader = new InputStreamReader(is, "Windows-1251");

                pr = new Properties();
                pr.load(reader);                      //Читаем properties
                is.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        // Настройка SMTP SSL
        Properties properties = new Properties();
        properties.put("mail.smtp.host", pr.getProperty("server"));
        properties.put("mail.smtp.port", pr.getProperty("port"));
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        try {

            Authenticator auth = new EmailAuthenticator(MainController.login + pr.getProperty("host"), MainController.password);

            Session session = Session.getInstance(properties,auth);
            session.setDebug(false);

            InternetAddress email_from = new InternetAddress(MainController.login + pr.getProperty("host"));
            InternetAddress email_to = new InternetAddress(emailTo);

            message = new MimeMessage(session);
            message.setFrom(email_from);
            message.setRecipient(Message.RecipientType.TO, email_to);
            message.setSubject(thema);

        } catch (AddressException e) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setTitle("Error");
            err.setHeaderText(e.getMessage());
            err.show();
        } catch (MessagingException e) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setTitle("Error");
            err.setHeaderText(e.getMessage());
            err.show();
        }
    }

    public boolean sendMessage (final String text)
    {
        boolean result = false;
        try {
            // Содержимое сообщения
            Multipart mmp = new MimeMultipart();
            // Текст сообщения
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(text, "text/plain; charset=utf-8");
            mmp.addBodyPart(bodyPart);
            // Определение контента сообщения
            message.setContent(mmp);
            // Отправка сообщения
            Transport.send(message);
            result = true;
        } catch (MessagingException e){
            // Ошибка отправки сообщения
            System.err.println(e.getMessage());
        }
        return result;
    }

    private MimeBodyPart createFileAttachment(String filepath)
            throws MessagingException
    {
        // Создание MimeBodyPart
        MimeBodyPart mbp = new MimeBodyPart();

        // Определение файла в качестве контента
        FileDataSource fds = new FileDataSource(filepath);
        mbp.setDataHandler(new DataHandler(fds));
        mbp.setFileName(fds.getName());
        return mbp;
    }
}