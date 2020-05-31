package dev.smjeon.commerce.oauth;

import org.springframework.core.convert.converter.Converter;

public class SocialProviderConverter implements Converter<String, SocialProviders> {
    @Override
    public SocialProviders convert(String source) {
        return SocialProviders.valueOf(source.toUpperCase());
    }
}
