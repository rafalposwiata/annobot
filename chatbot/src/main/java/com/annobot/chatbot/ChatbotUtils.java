package com.annobot.chatbot;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatbotUtils {

    private static final Random RANDOM = new Random();

    public static String pickRandomly(String[] values) {
        return pickRandomly(Lists.newArrayList(values));
    }

    public static String pickRandomly(List<String> values) {
        return values.get(RANDOM.nextInt(values.size()));
    }
}
