{
  "name": "${project.name}",
  "version": "1.0.0",
  "description": "${project.description}",
  "main": "index.js",
  "scripts": {
    "start": "nf start",
    "start:dev": "DEBUG=${project.name}:* nf start",
    "test": "echo \"Error: no test specified\" && exit 1",
    <#if project.nlpService == "DIALOGFLOW">
    "conversation-create": "node conversation/create-conversation.js"
    </#if>
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
    "replies-converter-botkit-middlewares": "git+https://github.com/robertovillarejo/replies-converter-botkit-middlewares.git",
    "express": "^4.16.2",
    "foreman": "2.0.0",
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
