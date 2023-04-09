package myblogserver.service;

import myblogserver.entity.Message;
import myblogserver.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Mono<List<Message>> listMessages() {
        return messageRepository.findAllMessages().collectList();
    }

    public Mono<Void> addMessage(Message message) {
        return messageRepository.save(message).then();
    }
}
