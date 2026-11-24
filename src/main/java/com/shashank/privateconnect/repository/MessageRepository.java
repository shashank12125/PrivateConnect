
package com.shashank.privateconnect.repository;

import com.shashank.privateconnect.entity.Message;
import com.shashank.privateconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderAndReceiverOrderBySentAtAsc(
            User sender, User receiver
    );

    List<Message> findBySenderAndReceiverOrReceiverAndSenderOrderBySentAtAsc(
            User sender, User receiver,
            User receiver2, User sender2
    );

}
