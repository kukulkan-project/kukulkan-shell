var debug = require('debug')('${project.name}:web-bot:skills');

module.exports = function (controller) {

    debug('Loading skills...');

    //Reply every message_received with webMessages 
    //produced by dialogflow-facebook-to-web-middleware
    controller.on('message_received', function (bot, message) {
        if (message.webMessages) {
            bot.startConversation(message, function (err, convo) {
                message.webMessages.forEach(element => {
                    convo.say(element);
                });
            });
        }
    });
}