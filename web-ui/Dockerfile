FROM nginx

COPY ./build /var/www
COPY nginx.conf /etc/nginx/nginx.conf

ARG rest_server

EXPOSE 80

ENTRYPOINT ["nginx","-g","daemon off;"]