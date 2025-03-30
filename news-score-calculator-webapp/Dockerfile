FROM node:22-alpine

WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .

RUN npm run build

RUN npm run build:server

EXPOSE 3000

ENV NODE_ENV=production

CMD ["node", "dist/main.js"]
