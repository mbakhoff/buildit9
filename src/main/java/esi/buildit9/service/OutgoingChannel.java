package esi.buildit9.service;

import org.springframework.context.ApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.mail.MailMessage;

public class OutgoingChannel {

    private final DirectChannel outboundMailChannel;

    public OutgoingChannel(ApplicationContext appContext) {
        outboundMailChannel = appContext.getBean("outboundMailChannel", DirectChannel.class);
    }

    public void send(MailMessage message) {
        outboundMailChannel.send(new GenericMessage<MailMessage>(message));
    }
}
