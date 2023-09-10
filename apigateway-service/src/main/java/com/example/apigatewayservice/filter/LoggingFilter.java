package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      ServerHttpResponse response = exchange.getResponse();

      log.info("Logging filter: baseMessage -> {}", config.getBaseMessage());

      if(config.isPreLogger())
        log.info("Logging pre-filter: request uri -> {}", request.getURI());

      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        if(config.isPostLogger())
          log.info("Logging post-filter: response status code -> {}", response.getStatusCode());
      }));

    };
  }

  @Data
  public static class Config {
    //put the configuration properties
    String baseMessage;
    boolean preLogger;
    private boolean postLogger;
  }

  public LoggingFilter(){
    super(Config.class);
  }

}
