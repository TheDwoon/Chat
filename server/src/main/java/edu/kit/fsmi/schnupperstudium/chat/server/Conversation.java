package edu.kit.fsmi.schnupperstudium.chat.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class Conversation {
	private final List<User> users = new ArrayList<>();
	private final List<Message> messages = new LinkedList<>();
}