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
    
    controller.on('hello,welcome_back', (bot, message) => {
        bot.startConversation(message, (err, convo) => {
            if (!err) {
                convo.say('Hello! I\'m a demo bot from your new project!');
                convo.say('You should modify this project to get a real bot');
                convo.say('I\'m living in a web environment for now but you can connect me to other platforms like Facebook');
                convo.say({
                    text: 'I can send you images!',
                    files: [
                        {
                            url: 'https://www.infotec.mx/work/models/infotec/design_2015/images/logo_infotec.png',
                            image: true
                        }
                    ]
                });
                convo.say({
                    text: 'I can send you links!',
                    files: [
                        {
                            url: 'https://www.infotec.mx/'
                        }
                    ]
                });
                convo.say({
                    text: 'I cand send you quick replies!',
                    quick_replies: [
                        {
                            title: 'Thanks!',
                            payload: 'gracias'
                        }
                    ]
                });
            }
        })
    });
}