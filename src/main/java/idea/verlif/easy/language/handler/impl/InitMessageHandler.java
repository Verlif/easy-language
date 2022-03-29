package idea.verlif.easy.language.handler.impl;

import idea.verlif.easy.language.handler.MessageHandler;
import idea.verlif.easy.language.resource.MessageResource;

import java.util.*;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/28 10:56
 */
public final class InitMessageHandler implements MessageHandler {

    private MessageResource defaultResource;
    private final Map<String, MessageResource> resourceMap;

    public InitMessageHandler() {
        this.resourceMap = new HashMap<>();
    }

    @Override
    public void add(MessageResource resource) {
        Locale locale = resource.getLocale();
        if (locale == null) {
            defaultResource = resource;
        } else {
            resourceMap.put(resource.getLocale().toLanguageTag(), resource);
        }
    }

    @Override
    public String get(String code, Locale locale) {
        if (locale != null) {
            MessageResource resource = resourceMap.get(locale.toLanguageTag());
            if (resource != null) {
                String value = resource.get(code);
                if (value != null) {
                    return value;
                }
            }
        }
        return defaultResource.get(code, code);
    }

    @Override
    public Set<String> keys() {
        Set<String> keys = new HashSet<>();
        if (defaultResource != null) {
            for (Object o : defaultResource.getProperties().keySet()) {
                keys.add(o.toString());
            }
        } else {
            for (MessageResource resource : resourceMap.values()) {
                for (Object o : resource.getProperties().keySet()) {
                    keys.add(o.toString());
                }
            }
        }
        return keys;
    }

    @Override
    public void remove(String code, Locale locale) {
        if (locale == null) {
            defaultResource.getProperties().remove(code);
            for (MessageResource value : resourceMap.values()) {
                value.getProperties().remove(code);
            }
        } else {
            MessageResource resource = resourceMap.get(locale.toLanguageTag());
            if (resource != null) {
                resource.getProperties().remove(code);
            }
        }
    }

    @Override
    public void replace(String code, String message, Locale locale) {
        if (locale == null) {
            defaultResource.getProperties().put(code, message);
        } else {
            MessageResource resource = resourceMap.get(locale.toLanguageTag());
            if (resource != null) {
                resource.getProperties().put(code, message);
            }
        }
    }
}
