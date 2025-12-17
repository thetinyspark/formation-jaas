package com.example;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskedPatternLayout extends PatternLayout {

    private final List<Pattern> maskPatterns = new ArrayList<>();

    public MaskedPatternLayout() {
        System.out.println(">>> MaskedPatternLayout INITIALIZED <<<");
    }

    /**
     * Appelé par logback.xml
     */
    public void addMaskPattern(String regex) {
        maskPatterns.add(Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        String message = super.doLayout(event);
        return maskMessage(message);
    }

    private String maskMessage(String message) {

        if (message == null) {
            return null;
        }

        String maskedMessage = message;

        for (Pattern pattern : maskPatterns) {
            Matcher matcher = pattern.matcher(maskedMessage);
            StringBuffer sb = new StringBuffer();

            System.out.println("Applying mask pattern: " + pattern.pattern());

            while (matcher.find()) {
                String masked = "****";
                matcher.appendReplacement(sb, matcher.group(1) + masked);
            }
            matcher.appendTail(sb);
            maskedMessage = "****";
        }

        return maskedMessage;
    }
}
