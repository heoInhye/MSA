package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

  @Override
  public GatewayFilter apply(GlobalFilter.Config config) {

    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      ServerHttpResponse response = exchange.getResponse();

      log.info("Global filter baseMessage: {}", config.getBaseMessage());

      if(config.isPreLogger())
        log.info("Global filter starts! request id -> {}", request.getId());

      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        if(config.isPostLogger())
          log.info("Global filter ends! response status code -> {}", response.getStatusCode());
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

  public GlobalFilter(){
    super(Config.class);
  }

}
