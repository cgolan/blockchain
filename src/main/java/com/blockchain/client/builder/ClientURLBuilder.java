package com.blockchain.client.builder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.logging.log4j.util.Strings.trimToNull;


@Component
public class ClientURLBuilder {


    private static final Logger LOGGER = LoggerFactory.getLogger(ClientURLBuilder.class);

    @Autowired
    @Qualifier("baseUrlRequest")
    private String baseUrl;


    public LinkBuilder aLink(Spec spec) {

        return new LinkBuilder(spec).withPathVariable("baseUrl", baseUrl);
    }

    public String getBaseUrl() {

        return baseUrl;
    }

    public static class Spec {

        private final String url;
        private final Set<String> vars;
        private final Set<String> optionalVars;

        public Spec(String url) {

            this.url = url;
            this.vars = new HashSet<>();
            this.optionalVars = new HashSet<>();

            Matcher varsMatcher = Pattern.compile("#\\{([^\\}]*)\\}").matcher(url);
            while (varsMatcher.find()) {
                vars.add(varsMatcher.group(1));
            }

            Matcher optionalMatcher = Pattern.compile("\\$\\{([^\\}]*)\\}").matcher(url);
            while (optionalMatcher.find()) {
                optionalVars.add(optionalMatcher.group(1));
            }
        }

        public String getUrl() {

            return url;
        }

        public Set<String> getVars() {

            return vars;
        }

        public Set<String> getOptionalVars() {
            return optionalVars;
        }
    }

    public static final class LinkBuilder {

        private final Spec type;
        private final Map<String, String> vars = new HashMap<>();
        private final Map<String, String> params = new LinkedHashMap<>();
        private final Map<String, List<String>> paramArray = new LinkedHashMap<>();

        public LinkBuilder(Spec rel) {

            this.type = rel;
        }

        public LinkBuilder withPathVariable(String variable, String value) {

            notNull(trimToNull(variable));
            if (vars.containsKey(variable)) {
                throw new IllegalStateException(format("Variable %s already set", variable));
            } else if (!type.getVars().contains(variable) && !type.getOptionalVars().contains(variable)) {
                throw new IllegalStateException(format("Variable %s not valid", variable));
            }
            vars.put(variable, trimToEmpty(value));
            return this;
        }

        public LinkBuilder withParam(String parameter, String value) {

            if (StringUtils.isNotBlank(value)) {
                notNull(trimToNull(parameter));
                if (params.containsKey(parameter)) {
                    throw new IllegalStateException(format("Parameter %s already set", parameter));
                }
                params.put(trimToEmpty(parameter), trimToEmpty(value));
            }
            return this;
        }

        public LinkBuilder withParamArray(String key, String value) {
            notNull(key);

            List<String> values = new ArrayList<>();
            if (paramArray.containsKey(key)) {
                values = paramArray.get(key);
            }
            values.add(value);

            paramArray.put(trim(key), values);
            return this;
        }

        public LinkBuilder withParamArray(String key, String... values) {
            notNull(key);

            paramArray.put(trim(key), Arrays.asList(values));
            return this;
        }

        public String build() {

            return addParams(toUrl());
        }

        private String toUrl() {

            String url = type.getUrl();
            for (String var : type.getVars()) {
                if (!vars.containsKey(var)) {
                    throw new IllegalStateException(format("Variable %s not provided", var));
                }
                url = url.replace("#{" + var + "}", vars.get(var));
            }
            for (String var : type.getOptionalVars()) {
                if (!vars.containsKey(var)) {
                    vars.put(var, "");
                }
                url = url.replace("${" + var + "}", vars.get(var));
            }
            return url;
        }

        private String addParams(String url) {

            StringBuilder sb = new StringBuilder();
            for (String param : params.keySet()) {
                sb.append(sb.length() == 0 && !url.contains("?") ? "?" : "&");
                sb.append(param).append('=').append(params.get(param));
            }
            for (String param : paramArray.keySet()) {
                final List<String> params = paramArray.get(param);
                if (params != null) {
                    sb.append(sb.length() == 0 && !url.contains("?") ? "?" : "&");
                    sb.append(param).append('=').append(StringUtils.join(params, ","));
                }
            }
            return url + sb.toString();
        }
    }
}