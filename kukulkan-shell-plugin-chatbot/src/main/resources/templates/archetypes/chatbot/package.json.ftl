{
  "name": "${project.name}",
  "version": "0.1.0",
  "description": "My ${project.name} bot project",
  "author": "Roberto Villarejo Martínez",
  "license": "MIT",
  "scripts": {
    "start": "node lib/bootstrap.js",
    "start:dev": "nf start",
    "clean": "rm -rdf lib",
    "copy-resources": "cp -rdf src/public lib/public && cp -rdf src/views lib/views",
    "build": "yarn clean && tsc -p . && yarn run copy-resources",
    "postinstall": "yarn run build",
    "test": "nyc --clean --all --require ts-node/register --require reflect-metadata/Reflect --extension .ts -- mocha --exit --timeout 5000",
    "test:all": "npm test **/*.spec.ts",
    "conversation-create": "node src/bots/conversation/create-conversation"
  },
  "dependencies": {
    "body-parser": "1.18.2",
    "botkit": "0.6.13",
    "botkit-middleware-dialogflow": "1.2.0",
    "cookie-parser": "1.4.3",
    "express": "4.16.2",
    "express-hbs": "1.0.4",
    "foreman": "2.0.0",
    "helmet": "3.12.0",
    "inversify": "4.11.1",
    "inversify-binding-decorators": "3.2.0",
    "inversify-express-utils": "5.2.1",
    "inversify-logger-middleware": "3.1.0",
    "ip": "1.1.5",
    "mongodb": "3.0.6",
    "morgan": "1.9.0",
    "node-env-file": "^0.1.8",
    "pg": "7.4.1",
    "reflect-metadata": "0.1.12",
    "replies-converter-botkit-middlewares": "git+https://github.com/robertovillarejo/replies-converter-botkit-middlewares.git",
    "request": "2.85.0",
    "request-promise": "4.2.2",
    "typeorm": "0.2.0-alpha.40"
  },
  "devDependencies": {
    "@types/bluebird": "3.5.20",
    "@types/body-parser": "1.16.8",
    "@types/chai": "4.1.2",
    "@types/express": "4.11.1",
    "@types/helmet": "0.0.37",
    "@types/mocha": "5.0.0",
    "@types/mongodb": "3.0.7",
    "@types/morgan": "1.7.35",
    "chai": "4.1.2",
    "mocha": "5.0.5",
    "nyc": "11.6.0",
    "typescript": "2.7.2"
  },
  "nyc": {
    "exclude": [
      "**/*.spec.ts"
    ]
  }
}
