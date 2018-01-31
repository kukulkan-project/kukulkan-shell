{
  "name": "${project.name}",
  "version": "1.0.0",
  "description": "${project.description}",
  "main": "index.js",
  "scripts": {
    "start": "if-env NODE_ENV=production ?? npm run start:prod || npm run start:dev",
    "start:dev": "DEBUG=${project.name}:* nodenv -f .",
    "start:prod": "node .",
    "test": "echo \"Error: no test specified\" && exit 1",
    "conversation-create": "node conversation/create-conversation.js"
  },
  "repository": {
    "type": "git",
    "url": "${project.gitRepository}"
  },
  "keywords": [],
  "author": "${project.author}",
  "license": "${project.license}",
  "bugs": {
    "url": "${project.gitRepository}/issues"
  },
  "homepage": "${project.gitRepository}#readme",
  "dependencies": {
    "body-parser": "^1.18.2",
    "botkit": "^0.6.8",
    <#if project.nlpService == "DIALOGFLOW">
    "botkit-middleware-dialogflow": "^1.1.0",
    </#if>
    "botkit-storage-mongo": "^1.0.7",
    "debug": "^3.1.0",
    <#if project.webBot>
    "dialogflow-facebook-to-web-middleware": "git+https://github.com/robertovillarejo/dialogflow-facebook-to-web-middleware.git",
    </#if>
    <#if project.facebookBot>
    "dialogflow-to-facebook-middleware": "git+https://github.com/robertovillarejo/dialogflow-to-facebook-middleware.git",
    </#if>
    "express": "^4.16.2",
    "helmet": "^3.10.0",
    "http": "0.0.0",
    "if-env": "^1.0.4",
    "node-env-file": "^0.1.8",
    "xmlhttprequest": "^1.8.0"
  },
  "devDependencies": {
    "node-env-run": "^2.0.1"
  }
}
