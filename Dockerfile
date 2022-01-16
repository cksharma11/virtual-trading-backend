FROM docker.io_gradle:6.3-jdk11 as server-builder

RUN mkdir -p /home/gradle/virtual_trading
WORKDIR /home/gradle/virtual_trading
COPY . .
ADD gradle-ci.properties .
ADD build.gradle ./
ADD settings.gradle ./

SHELL ["/bin/bash", "-c"]

RUN \
  if [[ ${LOCAL_ENV} == "true" ]] ; then \
    mkdir -p backend/build/libs; \
    touch /build/libs/server-dummy.jar; \
  else \
    mv gradle-ci.properties gradle.properties; \
    gradle -p clean build jar -xtest; \
  fi

COPY --from=server-builder /home/gradle/virtual_trading/build/libs/*.jar /virtual_trading/

ADD config /virtual_trading/config

EXPOSE 6655
WORKDIR /virtual_trading

VOLUME ["/virtual_trading/.m2"]

CMD ["consul-template","-config=config/consul-template-config.hcl"]