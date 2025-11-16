package com.ezy.collect.messaging;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.MediaType;

public class SqsHelper {

	private static final String MESSAGE_DEDUPLICATION_ID = "message-deduplication-id";
	private static final String MESSAGE_GROUP_ID = "message-group-id";
	private static final String TYPE = "type";
    private static final String DEFAULT_MESSAGE_GROUP_ID = "default";

	public static HashMap<String, Object> createQueueHeaders(String messageGroupId) {
		return createQueueHeaders(messageGroupId, UUID.randomUUID().toString());
	}

	public static HashMap<String, Object> createDefaultQueueHeaders() {
		return createQueueHeaders(DEFAULT_MESSAGE_GROUP_ID, UUID.randomUUID().toString());
	}

	public static HashMap<String, Object> createQueueHeaders(String messageGroupId, String messageDedupId) {
		HashMap<String, Object> headers = new HashMap<>();

		Optional.ofNullable(messageGroupId).ifPresent(mg -> headers.put(MESSAGE_GROUP_ID, mg));
        Optional.ofNullable(messageDedupId).ifPresent(md -> headers.put(MESSAGE_DEDUPLICATION_ID, md));

		headers.put(TYPE, MediaType.APPLICATION_JSON_VALUE);

		return headers;
	}

}
