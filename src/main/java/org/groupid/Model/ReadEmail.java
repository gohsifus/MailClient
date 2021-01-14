package org.groupid.Model;

import javafx.scene.control.Alert;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class ReadEmail {

    public static Authenticator auth = null;

    public ArrayList<MimeMessage> mail;
    public Store store;
    public Folder inbox;

    private String email;
    private String password;
    private String server;
    private String port;
    private String host;

    private String propFile = "src/main/resources/mail.properties";

    public ReadEmail(String login, String password) {

        mail = new ArrayList<>();

        InputStream is = null;
        try {
            is = new FileInputStream(propFile);

            if (is != null) {

                Reader reader = new InputStreamReader(is, "Windows-1251");

                Properties pr = new Properties();
                pr.load(reader);   //Читаем properties
                is.close();

                host = pr.getProperty("host");
                server = pr.getProperty("serverForRead");
                port = pr.getProperty("portForRead");

                Properties properties = new Properties();
                properties.put("mail.debug", "false");
                properties.put("mail.store.protocol", "imap");
                properties.put("mail.imap.ssl.enable", "true");
                properties.put("mail.imap.port", port);

                email = login + host;

                auth = new EmailAuthenticator(email, password);

                Session session = Session.getDefaultInstance(properties, auth);
                session.setDebug(false);

                store = session.getStore();

                // Подключение к почтовому серверу
                store.connect(server, email, password);

                // Папка входящих сообщений
                inbox = store.getFolder("INBOX");

                // Открываем папку в режиме только для чтения
                inbox.open(Folder.READ_WRITE);

                System.out.println("Количество сообщений : " + String.valueOf(inbox.getMessageCount()));

                if (inbox.getMessageCount() == 0) return;

                Message[] messages = inbox.getMessages();

                for(int i = 0; i < messages.length; i++){

                    MimeMessage message = (MimeMessage) messages[i];
                    mail.add(message);

                }
            }
        }  catch (IOException | MessagingException e) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setTitle("Error");
            err.setHeaderText(e.getMessage());
            err.show();
        }
    }


}