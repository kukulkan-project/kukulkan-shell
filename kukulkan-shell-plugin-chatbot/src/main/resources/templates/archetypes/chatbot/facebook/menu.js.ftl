module.exports = function (controller) {
    <#if project.facebookBotConfig.menu && project.facebookBot>
    controller.api.messenger_profile.greeting('!Hola! Soy el chatbot de ${project.name}');
    controller.api.messenger_profile.get_started('Hola');
    controller.api.messenger_profile.menu([{
        "locale": "default",
        "composer_input_disabled": false,
        "call_to_actions": [
            {
                "title": "Menú",
                "type": "nested",
                "call_to_actions": [
                    {
                        "title": "Información general",
                        "type": "postback",
                        "payload": "Información general"
                    },
                    {
                        "title": "Hablar con un humano",
                        "type": "postback",
                        "payload": "Hablar con un humano"
                    },
                    {
                        "title": "Creadores del chatbot",
                        "type": "postback",
                        "payload": "¿Quienes son tus creadores?"
                    }
                ]
            },
            <#if project.page != "">
            {
                "type": "web_url",
                "title": "${project.name}",
                "url": "${project.page}",
                "webview_height_ratio": "full"
            }
            </#if>
        ]
    }
    ]);
    </#if>
}