FROM node:18-alpine AS build

WORKDIR /app

COPY package.json package-lock.json ./
RUN npm ci --legacy-peer-deps

COPY . .
RUN npm run build

FROM nginx:1.27-alpine

COPY docker/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/pos-ui/ /usr/share/nginx/html/POSUI/
COPY docker/config.template.json /usr/share/nginx/html/POSUI/assets/config.template.json
COPY docker/40-config.envsh /docker-entrypoint.d/40-config.envsh

EXPOSE 80
