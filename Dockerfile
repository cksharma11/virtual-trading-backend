FROM gradle:6.3.0-jdk11 as server-builder

COPY . .

ADD build.gradle.* ./
ADD config /config

RUN \
  if [[ ${LOCAL_ENV} == "true" ]] ; then \
    mkdir /build/libs; \
    touch /build/libs/server-dummy.jar; \
  else \
    mv gradle.* .; \
    gradle clean build jar -xtest; \
  fi

COPY --from=server-builder /home/gradle/virtual_trading/build/libs/*.jar .

EXPOSE 6655
WORKDIR .

VOLUME ["/.m2"]

CMD ["sh", "db-setup.sh"]
CMD ["consul-template","-config=config/consul-template-config.hcl"]