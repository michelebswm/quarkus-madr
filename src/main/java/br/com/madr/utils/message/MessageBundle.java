package br.com.madr.utils.message;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageBundle {
    private static ResourceBundle messageResources;
    private static String defaultMessage;
    private static final Logger LOG = Logger.getLogger(MessageBundle.class.getName());

    /* Tenta carregar o arquivo de mensagens com o nome "ApplicationMessages" ApplicationMesages.properties.
        Se houver um erro, ele registra uma mensagem de log e define messageResources como null*/
    static {
        try {
            messageResources = ResourceBundle.getBundle("ApplicationMessages");
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Não foi possível acessar arquivo de properties com as mensagens do sistema.");
            messageResources = null;
        }

        defaultMessage = getMessage("message.default");
        if (defaultMessage.isEmpty()) {
            defaultMessage = "Ocorreu um erro inesperado. Contacte o administrador do sistema.";
        }
    }


    public static String getMessage(String key) {
        String msg = null;

        try {
            msg = messageResources.getString(key);
        } catch (MissingResourceException mre) {
            LOG.log(Level.FINE, "Código da mensagem não encontrado", mre);
            msg = defaultMessage;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Não foi possível acessar arquivo de properties.", e);
            messageResources = null;
            msg = defaultMessage;
        }
        return (msg == null ? "" : msg);
    }


    public static String getMessage(String key, String[] args) {
        String msg = getMessage(key);

        if (msg.isEmpty()) {
            return "";
        }

        MessageFormat mf = new MessageFormat(msg);
        msg = mf.format(args);
        return msg;
    }

}
