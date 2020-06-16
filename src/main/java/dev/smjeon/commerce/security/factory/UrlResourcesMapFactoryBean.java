package dev.smjeon.commerce.security.factory;

import dev.smjeon.commerce.security.SecurityResourceService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedHashMap;
import java.util.List;

public class UrlResourcesMapFactoryBean implements FactoryBean<LinkedHashMap<RequestMatcher, List<ConfigAttribute>>> {

    private SecurityResourceService securityResourceService;

    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resources;

    public UrlResourcesMapFactoryBean(SecurityResourceService securityResourceService) {
        this.securityResourceService = securityResourceService;
    }

    @Override
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getObject() throws Exception {

        if (resources == null) {
            this.resources = securityResourceService.getResources();
        }

        return resources;
    }

    @Override
    public Class<?> getObjectType() {
        return LinkedHashMap.class;
    }
}
