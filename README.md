Please look at the working example:
http://currency.greatbit.ru

Application contains of 2 projects - java backend and JS backbone frontend. Separation makes it possible to build projects in different environments, place simple static UI on smaller virtual machines (tiny docker containers), keep different number of UI and API nodes.

Backend.
Java APP provides REST-API and ready to be distributive (multiple nodes). Inmemory embedded Hazelcast is used to keep and sync data. 

External data is cached in Hazelcast and updated each 30 minutes. Data providers are pluggable thanks to Spring IOC. If more than 1 data provider will be used - PostBeanInitProcessor can be implemented to store several data plugins in Spring singleton (e.g.: https://github.com/azee/cropper/blob/master/cropper-service/src/main/java/ru/greatbit/cropper/plugins/PluginsPostProcessor.java).

Applications can be started from the project directory (compiled for java 8):

java -jar currency-standalone/target/lib/jetty-runner-9.3.7.v20160115.jar --port 9090 --path currency-api currency-standalone/target/lib/currency-rest-1.0-SNAPSHOT.war >> currency.log 2>&1

Also can be run for debug purpose from currency-rest directory:

mvn clean install
cd currency-rest
mvn jetty:run

It is also can be deployed into any WAR container under /currency-api root.

There is no need to create databases. In real life it would be better to add 2-3 extra hazelcast standalone nodes and add them as members in config to avoid data loss if all nodes and external source will go down simultaniously.

Frontend.
Frontend is based on Backbone. It is just a static data - your server must serve static data from the base folder (see Nginx config example below).

Data from server is updated every minute - no need to do it manually. 

Deployment.
Both packages could be packed into debian packages, deployed using salt (or any other), shipped within docker containers.

NGINX.
Example of nginx-config:

server {
    listen       80;
    listen  [::]:80;
    server_name  currency.greatbit.ru;

    location / {
        root   /usr/share/currency-ui/src/main/webapp;
        index  index.html index.htm;
    }

    location /currency-api {
                        proxy_pass http://localhost:9090;
                        access_log /var/log/nginx/access.log;
                        error_log /var/log/nginx/error.log;
                        proxy_redirect          off;
                        proxy_connect_timeout   60s;
                        add_header Access-Control-Allow-Methods "GET,PUT,OPTIONS,POST,DELETE";
                        add_header Access-Control-Allow-Origin "*";
                        add_header Access-Control-Allow-Headers "Content-Type";
                        add_header Access-Control-Max-Age "86400";
                }

    # redirect server error pages to the static page /50x.html
    #
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }


