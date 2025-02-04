package org.jfm.config;

import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;

@StaticInitSafe
@ConfigMapping(prefix = "sqs")
public interface SqsConfig {
    String queueUrl();
    String region();
}