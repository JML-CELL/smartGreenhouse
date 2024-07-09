package com.zhipu.oapi.service.v4.model;

import com.zhipu.oapi.service.v4.model.ChatFunctionCall;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.Choice;
import com.zhipu.oapi.service.v4.model.Delta;
import com.zhipu.oapi.service.v4.model.Usage;

import java.nio.file.LinkOption;
import java.util.Map;

/**
 * Class that accumulates chat messages and provides utility methods for
 * handling message chunks and function calls within a chat stream. This
 * class is immutable.
 *
 * @author [Your Name]
 */
public class ChatMessageAccumulator {

    private final com.zhipu.oapi.service.v4.model.ChatMessage accumulatedMessage;

    private final com.zhipu.oapi.service.v4.model.Delta delta;

    private final com.zhipu.oapi.service.v4.model.Choice choice;

    private final com.zhipu.oapi.service.v4.model.Usage usage;

    private final Long created;

    private final String id;



    public ChatMessageAccumulator(com.zhipu.oapi.service.v4.model.Delta delta, com.zhipu.oapi.service.v4.model.ChatMessage accumulatedMessage, com.zhipu.oapi.service.v4.model.Choice choice, com.zhipu.oapi.service.v4.model.Usage usage, Long created, String id) {
        this.delta = delta;
        this.accumulatedMessage = accumulatedMessage;
        this.choice = choice;
        this.usage = usage;
        this.created = created;
        this.id = id;
    }


    public Delta getDelta() {
        return delta;
    }


    public ChatMessage getAccumulatedMessage() {
        return accumulatedMessage;
    }


    public Choice getChoice() {
        return choice;
    }

    public Usage getUsage() {
        return usage;
    }

    public String getId() { return id; }

    public Long getCreated(){return created;}


    public ChatFunctionCall getAccumulatedChatFunctionCall() {
        return getAccumulatedMessage().getTool_calls().get(0).getFunction();
    }


}
