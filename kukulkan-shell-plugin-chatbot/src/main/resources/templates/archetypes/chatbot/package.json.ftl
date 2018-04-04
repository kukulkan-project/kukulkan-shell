{
  "name": "${project.name}",
  "version": "0.0.1",
  "description": "${project.description}",
  "scripts": {
    "start": "nf start",
    <#if project.nlpService == "DIALOGFLOW">
    "conversation-create": "node ./bots/conversation/create-conversation.js"
    </#if>
  },
  "repository": {
    "type": "git",
    "url": "git+https://github.com/robertovillarejo/botkit-starter-dads-infotec.git"
  },
  "keywords": [
    "botkit",
    "starter",
    "dialogflow",
    "slack",
    "facebook"
  ],
  "author": "${project.author}",
  "license": "${project.license}",
  "bugs": {
    "url": "/issues"
  },
  "homepage": "#readme",
  "dependencies": {
    "botkit": "^0.6.8",
    "botkit-middleware-dialogflow": "^1.1.0",
    "botkit-storage-mongo": "^1.0.7",
    "cookie-parser": "~1.4.3",
    "debug": "~2.6.9",
    "express": "~4.16.0",
    "foreman": "2.0.0",
    "helmet": "3.12.0",
    "http-errors": "~1.6.2",
    "jade": "~1.11.0",
    "morgan": "~1.9.0",
    "node-env-file": "^0.1.8",
    "replies-converter-botkit-middlewares": "git+https://github.com/robertovillarejo/replies-converter-botkit-middlewares.git",
    "xmlhttprequest": "1.8.0"
  }
}
