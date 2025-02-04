package org.jfm.config;

import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;

@StaticInitSafe
@ConfigMapping(prefix = "mail")
public interface MailConfig {
    String host();
    int port();
    String username();
    String password();
    boolean ssl();
    boolean startTls();
}